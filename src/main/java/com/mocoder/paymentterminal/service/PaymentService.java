package com.mocoder.paymentterminal.service;

import com.mocoder.paymentterminal.model.AuthorisationHold;
import com.mocoder.paymentterminal.model.PaymentAttempt;
import com.mocoder.paymentterminal.model.PaymentRequest;
import com.mocoder.paymentterminal.model.Terminal;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PaymentService {

    public boolean canAuthorise(Terminal terminal, PaymentRequest request) {
        return isTerminalReady(terminal)
                && isWithinLimit(terminal, request)
                && isCardUsable(terminal, request);
    }

    public ReversalResult evaluateReversal(Terminal terminal, PaymentAttempt attempt) {
        List<String> issues = new ArrayList<>();

        checkReversalTiming(terminal, attempt, issues);
        checkHolds(terminal, attempt.getRequest(), issues);

        return new ReversalResult(issues.isEmpty(), issues);
    }

    private boolean isTerminalReady(Terminal terminal) {
        return terminal.isOnline() && !terminal.isBusy();
    }

    private boolean isWithinLimit(Terminal terminal, PaymentRequest request) {
        return request.getAmountCents() <= terminal.getPerTransactionLimitCents();
    }

    private boolean isCardUsable(Terminal terminal, PaymentRequest request) {
        boolean originAllowed = request.isDomesticCard() || terminal.isInternationalEnabled();
        boolean typeAccepted = terminal.accepts(request.getCardType());
        boolean notBlocked = !request.isCardBlocked();
        return originAllowed && typeAccepted && notBlocked;
    }

    private void checkReversalTiming(Terminal terminal, PaymentAttempt attempt, List<String> issues) {
        LocalDateTime reversalRequestedAt = attempt.getReversalRequestedAt();
        if (reversalRequestedAt == null || !reversalRequestedAt.isAfter(attempt.getAuthorisedAt())) {
            return;
        }

        PaymentRequest request = attempt.getRequest();
        int windowMinutes = request.getReversalWindowMinutes();
        if (windowMinutes <= 0) {
            issues.add("Reversal for " + terminal.getTerminalId() + " was requested after authorisation and "
                    + request.getReference() + " has no reversal window");
            return;
        }

        long minutesSinceAuthorised = Duration.between(attempt.getAuthorisedAt(), reversalRequestedAt).toMinutes();
        if (minutesSinceAuthorised > windowMinutes) {
            issues.add("Reversal for " + terminal.getTerminalId() + " was requested " + minutesSinceAuthorised
                    + " minutes after authorisation, past the reversal window for "
                    + request.getReference());
        }
    }

    private void checkHolds(Terminal terminal, PaymentRequest request, List<String> issues) {
        List<AuthorisationHold> holds = terminal.getHolds();
        if (holds == null || holds.isEmpty()) {
            issues.add("No authorisation holds are available to reverse on " + terminal.getTerminalId());
            return;
        }
        for (AuthorisationHold hold : holds) {
            checkHold(hold, request, issues);
        }
    }

    private void checkHold(AuthorisationHold hold, PaymentRequest request, List<String> issues) {
        boolean amountMatches = hold.getAmountCents() == request.getAmountCents();
        boolean active = hold.isActive();

        if (amountMatches && active) {
            return;
        }
        if (!amountMatches && !active) {
            issues.add(hold.getHoldId() + " does not match the payment amount and is no longer active");
        } else if (!amountMatches) {
            issues.add(hold.getHoldId() + " does not match the payment amount");
        } else {
            issues.add(hold.getHoldId() + " matches the payment amount but is no longer active");
        }
    }
}
