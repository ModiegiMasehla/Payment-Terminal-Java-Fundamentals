package com.mocoder.paymentterminal;

import com.mocoder.paymentterminal.model.AuthorisationHold;
import com.mocoder.paymentterminal.model.CardType;
import com.mocoder.paymentterminal.model.PaymentAttempt;
import com.mocoder.paymentterminal.model.PaymentRequest;
import com.mocoder.paymentterminal.model.Terminal;
import com.mocoder.paymentterminal.service.PaymentService;
import com.mocoder.paymentterminal.service.ReversalResult;

import java.time.LocalDateTime;

public class Main {

    public static void main(String[] args) {
        Terminal terminal = new Terminal("TRM-100", "QuickTap Countertop", 500_000);
        terminal.acceptCardType(CardType.DEBIT);
        terminal.acceptCardType(CardType.CREDIT);

        PaymentRequest request = new PaymentRequest("PAY-2001", 12_500, CardType.DEBIT, true);
        request.setReversalWindowMinutes(30);

        PaymentService paymentService = new PaymentService();

        boolean canAuthorise = paymentService.canAuthorise(terminal, request);
        System.out.println(terminal.getModel() + " can authorise " + request.getReference() + ": " + canAuthorise);

        PaymentAttempt attempt = new PaymentAttempt(request, LocalDateTime.of(2026, 8, 1, 9, 0));
        attempt.requestReversal(LocalDateTime.of(2026, 8, 1, 9, 45));
        terminal.addHold(new AuthorisationHold("HOLD-A", 12_500, true));

        ReversalResult result = paymentService.evaluateReversal(terminal, attempt);
        System.out.println("Reversal safe: " + result.isSafe());
        result.getIssues().forEach(issue -> System.out.println(" - " + issue));
    }
}
