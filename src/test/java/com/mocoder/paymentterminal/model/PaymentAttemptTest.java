package com.mocoder.paymentterminal.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class PaymentAttemptTest {

    @Test
    void hasNoReversalRequestByDefault() {
        PaymentRequest request = new PaymentRequest("PAY-1", 5_000, CardType.DEBIT, true);
        PaymentAttempt attempt = new PaymentAttempt(request, LocalDateTime.of(2026, 8, 1, 9, 0));

        assertNull(attempt.getReversalRequestedAt());
    }

    @Test
    void requestReversalRecordsTheRequestTime() {
        PaymentRequest request = new PaymentRequest("PAY-1", 5_000, CardType.DEBIT, true);
        PaymentAttempt attempt = new PaymentAttempt(request, LocalDateTime.of(2026, 8, 1, 9, 0));

        attempt.requestReversal(LocalDateTime.of(2026, 8, 1, 9, 15));

        assertEquals(LocalDateTime.of(2026, 8, 1, 9, 15), attempt.getReversalRequestedAt());
    }
}
