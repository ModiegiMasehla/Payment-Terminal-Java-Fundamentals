package com.mocoder.paymentterminal.model;

public class PaymentRequest {

    private final String reference;
    private final long amountCents;
    private final CardType cardType;
    private final boolean domesticCard;
    private boolean cardBlocked;
    private int reversalWindowMinutes;

    public PaymentRequest(String reference, long amountCents, CardType cardType, boolean domesticCard) {
        this.reference = reference;
        this.amountCents = amountCents;
        this.cardType = cardType;
        this.domesticCard = domesticCard;
        this.cardBlocked = false;
        this.reversalWindowMinutes = 0;
    }

    public String getReference() {
        return reference;
    }

    public long getAmountCents() {
        return amountCents;
    }

    public CardType getCardType() {
        return cardType;
    }

    public boolean isDomesticCard() {
        return domesticCard;
    }

    public boolean isCardBlocked() {
        return cardBlocked;
    }

    public void blockCard() {
        this.cardBlocked = true;
    }

    public int getReversalWindowMinutes() {
        return reversalWindowMinutes;
    }

    public void setReversalWindowMinutes(int reversalWindowMinutes) {
        this.reversalWindowMinutes = reversalWindowMinutes;
    }

    @Override
    public String toString() {
        return reference + " (" + amountCents + " cents, " + cardType + ")";
    }
}
