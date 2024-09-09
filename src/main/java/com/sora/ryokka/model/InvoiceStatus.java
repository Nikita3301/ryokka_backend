package com.sora.ryokka.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum InvoiceStatus {
    PENDING ("Pending"),
    PAID ("Paid"),
    OVERDUE ("Overdue");

    private final String description;

    @Override
    public String toString() {
        return description;
    }
}

