package com.sora.ryokka.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ProjectStatus {
    NOT_STARTED("Not Started"),
    IN_PROGRESS("In Progress"),
    COMPLETED("Completed"),
    ON_HOLD("On Hold");

    private final String description;

    @Override
    public String toString() {
        return description;
    }

    public static ProjectStatus fromDescription(String description) {
        for (ProjectStatus status : values()) {
            if (status.getDescription().equalsIgnoreCase(description)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Unknown status: " + description);
    }
}

