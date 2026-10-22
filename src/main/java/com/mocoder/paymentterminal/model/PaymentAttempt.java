package com.mocoder.paymentterminal.model;

import java.time.LocalDateTime;

public class PaymentAttempt {

    private final PaymentRequest request;
    private final LocalDateTime authorisedAt;
    private LocalDateTime reversalRequestedAt;

    public PaymentAttempt(PaymentRequest request, LocalDateTime authorisedAt) {
        this.request = request;
        this.authorisedAt = authorisedAt;
    }

    public PaymentRequest getRequest() {
        return request;
    }

    public LocalDateTime getAuthorisedAt() {
        return authorisedAt;
    }

    public LocalDateTime getReversalRequestedAt() {
        return reversalRequestedAt;
    }

    public void requestReversal(LocalDateTime requestedAt) {
        this.reversalRequestedAt = requestedAt;
    }
}
