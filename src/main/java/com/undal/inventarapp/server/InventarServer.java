package com.undal.inventarapp.server;

import com.undal.inventarapp.database.CreateTable;

public class InventarServer {
    public static void main(String[] args) {
        CreateTable ct = new CreateTable();
        ct.createTables();
    }
}
