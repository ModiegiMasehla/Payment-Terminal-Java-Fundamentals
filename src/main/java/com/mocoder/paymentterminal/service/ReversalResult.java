package com.mocoder.paymentterminal.service;

import java.util.Collections;
import java.util.List;

public class ReversalResult {

    private final boolean safe;
    private final List<String> issues;

    public ReversalResult(boolean safe, List<String> issues) {
        this.safe = safe;
        this.issues = List.copyOf(issues);
    }

    public boolean isSafe() {
        return safe;
    }

    public List<String> getIssues() {
        return Collections.unmodifiableList(issues);
    }
}
