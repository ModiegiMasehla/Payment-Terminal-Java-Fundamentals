package com.mocoder.paymentterminal.service;

import com.mocoder.paymentterminal.model.AuthorisationHold;
import com.mocoder.paymentterminal.model.CardType;
import com.mocoder.paymentterminal.model.PaymentAttempt;
import com.mocoder.paymentterminal.model.PaymentRequest;
import com.mocoder.paymentterminal.model.Terminal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PaymentServiceTest {

    private static final LocalDateTime AUTHORISED_AT = LocalDateTime.of(2026, 8, 1, 9, 0);

    private PaymentService paymentService;
    private Terminal terminal;
    private PaymentRequest request;

    @BeforeEach
    void setUp() {
        paymentService = new PaymentService();
        terminal = new Terminal("TRM-100", "QuickTap Countertop", 100_000);
        terminal.acceptCardType(CardType.DEBIT);

        request = new PaymentRequest("PAY-1", 25_000, CardType.DEBIT, true);
    }

    private PaymentAttempt attemptWithReversalAt(int minutesAfterAuthorisation) {
        PaymentAttempt attempt = new PaymentAttempt(request, AUTHORISED_AT);
        attempt.requestReversal(AUTHORISED_AT.plusMinutes(minutesAfterAuthorisation));
        return attempt;
    }

    @Test
    void allowsAuthorisationWhenAllConditionsAreMet() {
        assertTrue(paymentService.canAuthorise(terminal, request));
    }

    @Test
    void deniesAuthorisationWhenTerminalIsOffline() {
        terminal.goOffline();

        assertFalse(paymentService.canAuthorise(terminal, request));
    }

    @Test
    void deniesAuthorisationWhenTerminalIsBusy() {
        terminal.startTransaction();

        assertFalse(paymentService.canAuthorise(terminal, request));
    }

    @Test
    void deniesAuthorisationWhenAmountExceedsTheLimit() {
        PaymentRequest tooLarge = new PaymentRequest("PAY-2", 100_001, CardType.DEBIT, true);

        assertFalse(paymentService.canAuthorise(terminal, tooLarge));
    }

    @Test
    void allowsAuthorisationWhenAmountEqualsTheLimit() {
        PaymentRequest atLimit = new PaymentRequest("PAY-3", 100_000, CardType.DEBIT, true);

        assertTrue(paymentService.canAuthorise(terminal, atLimit));
    }

    @Test
    void deniesForeignCardWhenInternationalPaymentsAreDisabled() {
        PaymentRequest foreign = new PaymentRequest("PAY-4", 25_000, CardType.DEBIT, false);

        assertFalse(paymentService.canAuthorise(terminal, foreign));
    }

    @Test
    void allowsForeignCardWhenInternationalPaymentsAreEnabled() {
        terminal.setInternationalEnabled(true);
        PaymentRequest foreign = new PaymentRequest("PAY-4", 25_000, CardType.DEBIT, false);

        assertTrue(paymentService.canAuthorise(terminal, foreign));
    }

    @Test
    void deniesAuthorisationWhenCardTypeIsNotAccepted() {
        PaymentRequest credit = new PaymentRequest("PAY-5", 25_000, CardType.CREDIT, true);

        assertFalse(paymentService.canAuthorise(terminal, credit));
    }

    @Test
    void deniesAuthorisationWhenCardIsBlocked() {
        request.blockCard();

        assertFalse(paymentService.canAuthorise(terminal, request));
    }

    @Test
    void reversalHasNoIssuesWhenRequestedWithinWindowAndHoldIsValid() {
        request.setReversalWindowMinutes(30);
        terminal.addHold(new AuthorisationHold("HOLD-A", 25_000, true));

        ReversalResult result = paymentService.evaluateReversal(terminal, attemptWithReversalAt(10));

        assertTrue(result.isSafe());
        assertTrue(result.getIssues().isEmpty());
    }

    @Test
    void reversalIsSafeWhenRequestedExactlyAtTheEndOfTheWindow() {
        request.setReversalWindowMinutes(30);
        terminal.addHold(new AuthorisationHold("HOLD-A", 25_000, true));

        ReversalResult result = paymentService.evaluateReversal(terminal, attemptWithReversalAt(30));

        assertTrue(result.isSafe());
    }

    @Test
    void reversalFlagsRequestPastTheWindow() {
        request.setReversalWindowMinutes(15);
        terminal.addHold(new AuthorisationHold("HOLD-A", 25_000, true));

        ReversalResult result = paymentService.evaluateReversal(terminal, attemptWithReversalAt(45));

        assertFalse(result.isSafe());
        assertEquals(1, result.getIssues().size());
    }

    @Test
    void reversalFlagsRequestWhenPaymentHasNoWindow() {
        terminal.addHold(new AuthorisationHold("HOLD-A", 25_000, true));

        ReversalResult result = paymentService.evaluateReversal(terminal, attemptWithReversalAt(5));

        assertFalse(result.isSafe());
    }

    @Test
    void reversalIgnoresTheWindowWhenNoReversalWasRequested() {
        terminal.addHold(new AuthorisationHold("HOLD-A", 25_000, true));
        PaymentAttempt attempt = new PaymentAttempt(request, AUTHORISED_AT);

        ReversalResult result = paymentService.evaluateReversal(terminal, attempt);

        assertTrue(result.isSafe());
    }

    @Test
    void reversalDoesNotFlagARequestMadeBeforeAuthorisation() {
        terminal.addHold(new AuthorisationHold("HOLD-A", 25_000, true));

        ReversalResult result = paymentService.evaluateReversal(terminal, attemptWithReversalAt(-5));

        assertTrue(result.isSafe());
    }

    @Test
    void reversalFlagsWhenNoHoldsExist() {
        PaymentAttempt attempt = new PaymentAttempt(request, AUTHORISED_AT);

        ReversalResult result = paymentService.evaluateReversal(terminal, attempt);

        assertFalse(result.isSafe());
        assertEquals(1, result.getIssues().size());
        assertEquals("No authorisation holds are available to reverse on TRM-100", result.getIssues().get(0));
    }

    @Test
    void reversalFlagsHoldThatDoesNotMatchThePaymentAmount() {
        terminal.addHold(new AuthorisationHold("HOLD-A", 20_000, true));
        PaymentAttempt attempt = new PaymentAttempt(request, AUTHORISED_AT);

        ReversalResult result = paymentService.evaluateReversal(terminal, attempt);

        assertFalse(result.isSafe());
        assertEquals(1, result.getIssues().size());
    }

    @Test
    void reversalFlagsHoldThatIsNoLongerActive() {
        terminal.addHold(new AuthorisationHold("HOLD-A", 25_000, false));
        PaymentAttempt attempt = new PaymentAttempt(request, AUTHORISED_AT);

        ReversalResult result = paymentService.evaluateReversal(terminal, attempt);

        assertFalse(result.isSafe());
        assertEquals(1, result.getIssues().size());
    }

    @Test
    void reversalFlagsHoldThatIsBothMismatchedAndInactive() {
        terminal.addHold(new AuthorisationHold("HOLD-A", 20_000, false));
        PaymentAttempt attempt = new PaymentAttempt(request, AUTHORISED_AT);

        ReversalResult result = paymentService.evaluateReversal(terminal, attempt);

        assertFalse(result.isSafe());
        assertEquals(1, result.getIssues().size());
        assertEquals("HOLD-A does not match the payment amount and is no longer active", result.getIssues().get(0));
    }

    @Test
    void reversalReportsEveryProblemHoldSeparately() {
        terminal.addHold(new AuthorisationHold("HOLD-A", 20_000, true));
        terminal.addHold(new AuthorisationHold("HOLD-B", 25_000, false));
        PaymentAttempt attempt = new PaymentAttempt(request, AUTHORISED_AT);

        ReversalResult result = paymentService.evaluateReversal(terminal, attempt);

        assertEquals(2, result.getIssues().size());
    }
}
