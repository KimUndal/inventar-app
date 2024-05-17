package com.undal.inventarapp.server;

import com.undal.inventarapp.database.CreateTable;
import com.undal.inventarapp.database.SqlQuery;

public class InventarServer {
    public static void main(String[] args) {
        CreateTable ct = new CreateTable();
        SqlQuery sqlQuery = new SqlQuery();
        System.out.println(sqlQuery.getInventoryByType("utsmykning"));

        //ct.createTables(true);
    }
}
