package com.undal.inventarapp.shared.util;

import java.io.IOException;

public class RetryCommandImpl implements RetryCommand {
    private final int maxAttempts;
    private final long delayMillis;
    private final Runnable operation;

    public RetryCommandImpl(int maxAttempts, long delayMillis, Runnable operation) {
        this.maxAttempts = maxAttempts;
        this.delayMillis = delayMillis;
        this.operation = operation;
    }

    @Override
    public void execute() throws Exception {
        int attempt = 0;
        Exception lastException = null;
        while (attempt < maxAttempts) {
            try {
                operation.run();
                return; // Operation succeeded, exit the loop
            } catch (Exception e) {
                lastException = e;
                System.err.println("Failed to execute operation: " + e.getMessage());
                attempt++;
                if (attempt < maxAttempts) {
                    System.out.println("Retrying in " + delayMillis / 1000 + " seconds...");
                    try {
                        Thread.sleep(delayMillis);
                    } catch (InterruptedException ex) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }
        if (lastException != null) {
            throw lastException;
        }
    }
}