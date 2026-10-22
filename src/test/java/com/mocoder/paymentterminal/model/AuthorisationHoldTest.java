package com.mocoder.paymentterminal.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AuthorisationHoldTest {

    @Test
    void exposesItsAmountAndActiveState() {
        AuthorisationHold hold = new AuthorisationHold("HOLD-A", 12_500, true);

        assertEquals("HOLD-A", hold.getHoldId());
        assertEquals(12_500L, hold.getAmountCents());
        assertTrue(hold.isActive());
    }

    @Test
    void canRepresentAnExpiredHold() {
        AuthorisationHold hold = new AuthorisationHold("HOLD-B", 9_900, false);

        assertFalse(hold.isActive());
    }
}
