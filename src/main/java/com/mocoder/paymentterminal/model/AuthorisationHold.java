package com.mocoder.paymentterminal.model;

public class AuthorisationHold {

    private final String holdId;
    private final long amountCents;
    private final boolean active;

    public AuthorisationHold(String holdId, long amountCents, boolean active) {
        this.holdId = holdId;
        this.amountCents = amountCents;
        this.active = active;
    }

    public String getHoldId() {
        return holdId;
    }

    public long getAmountCents() {
        return amountCents;
    }

    public boolean isActive() {
        return active;
    }

    @Override
    public String toString() {
        return holdId + ": " + amountCents;
    }
}
