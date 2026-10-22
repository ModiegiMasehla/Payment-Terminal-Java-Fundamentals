package com.mocoder.paymentterminal.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TerminalTest {

    private Terminal newTerminal() {
        return new Terminal("TRM-1", "QuickTap Countertop", 100_000);
    }

    @Test
    void isOnlineByDefault() {
        assertTrue(newTerminal().isOnline());
    }

    @Test
    void goOfflineAndGoOnlineToggleOnlineState() {
        Terminal terminal = newTerminal();

        terminal.goOffline();
        assertFalse(terminal.isOnline());

        terminal.goOnline();
        assertTrue(terminal.isOnline());
    }

    @Test
    void isNotBusyByDefault() {
        assertFalse(newTerminal().isBusy());
    }

    @Test
    void startTransactionAndFinishTransactionToggleBusyState() {
        Terminal terminal = newTerminal();

        terminal.startTransaction();
        assertTrue(terminal.isBusy());

        terminal.finishTransaction();
        assertFalse(terminal.isBusy());
    }

    @Test
    void acceptsOnlyTheCardTypesItWasToldAbout() {
        Terminal terminal = newTerminal();

        terminal.acceptCardType(CardType.DEBIT);

        assertTrue(terminal.accepts(CardType.DEBIT));
        assertFalse(terminal.accepts(CardType.CREDIT));
    }

    @Test
    void addHoldMakesItRetrievable() {
        Terminal terminal = newTerminal();

        terminal.addHold(new AuthorisationHold("HOLD-A", 5_000, true));

        assertTrue(terminal.getHolds().stream().anyMatch(hold -> hold.getHoldId().equals("HOLD-A")));
    }
}
