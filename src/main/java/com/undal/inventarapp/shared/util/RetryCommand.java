package com.undal.inventarapp.shared.util;

import java.io.IOException;

public interface RetryCommand {
    void execute() throws Exception;
}