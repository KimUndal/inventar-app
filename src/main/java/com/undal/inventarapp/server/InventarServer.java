package com.undal.inventarapp.server;

import com.undal.inventarapp.database.CreateTable;
import com.undal.inventarapp.database.SqlQuery;

public class InventarServer {
    public static void main(String[] args) {
        CreateTable ct = new CreateTable();
        SqlQuery sqlQuery = new SqlQuery();
        //System.out.println(sqlQuery.getInventoryByType("Utsmykning"));
      //  sqlQuery.getInventoryByDescription("Wooden").forEach(System.out::println);
        //sqlQuery.getInventoryByDateOfPurchase("2023").forEach(System.out::println);
        //sqlQuery.getInventoryByCategory("Cha").forEach(System.out::println);
        //sqlQuery.getInventoryByPrice(100,900).forEach(System.out::println);
        //sqlQuery.getInventoryByLifeExpectancy(1,4).forEach(System.out::println);
        sqlQuery.getInventoryItemsToBeDisposedOfBetweenTwoYears(2025,2027).forEach(System.out::println);

        //ct.createTables(true);
    }
}
