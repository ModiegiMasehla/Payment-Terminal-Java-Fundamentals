package com.mocoder.paymentterminal.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PaymentRequestTest {

    @Test
    void isNotBlockedByDefault() {
        PaymentRequest request = new PaymentRequest("PAY-1", 5_000, CardType.DEBIT, true);

        assertFalse(request.isCardBlocked());
    }

    @Test
    void blockCardMarksTheCardAsBlocked() {
        PaymentRequest request = new PaymentRequest("PAY-1", 5_000, CardType.DEBIT, true);

        request.blockCard();

        assertTrue(request.isCardBlocked());
    }

    @Test
    void hasNoReversalWindowByDefault() {
        PaymentRequest request = new PaymentRequest("PAY-1", 5_000, CardType.DEBIT, true);

        assertEquals(0, request.getReversalWindowMinutes());
    }

    @Test
    void reportsWhetherTheCardIsDomestic() {
        PaymentRequest local = new PaymentRequest("PAY-1", 5_000, CardType.DEBIT, true);
        PaymentRequest foreign = new PaymentRequest("PAY-2", 5_000, CardType.CREDIT, false);

        assertTrue(local.isDomesticCard());
        assertFalse(foreign.isDomesticCard());
    }
}
