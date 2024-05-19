package com.undal.inventarapp.shared.model;

public enum Status {
    IN_USE(200, "In Use"),
    DISCARDED(500, "Out of Use");


    private final int statusCode;
    private final String description;

    Status(int statusCode, String description) {
        this.statusCode = statusCode;
        this.description = description;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getStatusDescription() {
        return description;
    }

    @Override
    public String toString() {
        return statusCode + " " + description;
    }
    public static Status getStatusByStatusCode(int statusCode) {
        for (Status status : values()) {
            if (status.getStatusCode() == statusCode) {
                return status;
            }
        }
        throw new IllegalArgumentException("No matching status for code: " + statusCode);
    }
}