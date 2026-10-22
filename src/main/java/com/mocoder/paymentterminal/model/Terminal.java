package com.mocoder.paymentterminal.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

public class Terminal {

    private final String terminalId;
    private final String model;
    private final long perTransactionLimitCents;
    private final Set<CardType> acceptedCardTypes = EnumSet.noneOf(CardType.class);
    private final List<AuthorisationHold> holds = new ArrayList<>();
    private boolean online;
    private boolean busy;
    private boolean internationalEnabled;

    public Terminal(String terminalId, String model, long perTransactionLimitCents) {
        this.terminalId = terminalId;
        this.model = model;
        this.perTransactionLimitCents = perTransactionLimitCents;
        this.online = true;
        this.busy = false;
        this.internationalEnabled = false;
    }

    public String getTerminalId() {
        return terminalId;
    }

    public String getModel() {
        return model;
    }

    public long getPerTransactionLimitCents() {
        return perTransactionLimitCents;
    }

    public void acceptCardType(CardType cardType) {
        acceptedCardTypes.add(cardType);
    }

    public boolean accepts(CardType cardType) {
        return acceptedCardTypes.contains(cardType);
    }

    public boolean isOnline() {
        return online;
    }

    public void goOffline() {
        this.online = false;
    }

    public void goOnline() {
        this.online = true;
    }

    public boolean isBusy() {
        return busy;
    }

    public void startTransaction() {
        this.busy = true;
    }

    public void finishTransaction() {
        this.busy = false;
    }

    public boolean isInternationalEnabled() {
        return internationalEnabled;
    }

    public void setInternationalEnabled(boolean internationalEnabled) {
        this.internationalEnabled = internationalEnabled;
    }

    public void addHold(AuthorisationHold hold) {
        holds.add(hold);
    }

    public List<AuthorisationHold> getHolds() {
        return Collections.unmodifiableList(holds);
    }

    @Override
    public String toString() {
        return model + " (" + terminalId + ")";
    }
}
