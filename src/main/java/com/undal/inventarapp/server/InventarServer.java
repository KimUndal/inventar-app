package com.undal.inventarapp.server;

import com.undal.inventarapp.database.CreateTable;
import com.undal.inventarapp.database.SqlQuery;
import com.undal.inventarapp.shared.model.Inventory;

import java.net.ServerSocket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InventarServer {

    private final ServerHandler serverHandler;

    public InventarServer(ServerHandler serverHandler) {
        this.serverHandler = serverHandler;
    }

    public static void main(String[] args) {
//    InventarServer is = new InventarServer(new ServerHandler());
//    is.startServer();
        //    CreateTable ct = new CreateTable();
    //    SqlQuery sqlQuery = new SqlQuery();
     //   ct.createTables(true);
        //System.out.println(sqlQuery.getInventoryByType("Utsmykning"));
      //  sqlQuery.getInventoryByDescription("Wooden").forEach(System.out::println);
        //sqlQuery.getInventoryByDateOfPurchase("2023").forEach(System.out::println);
        //sqlQuery.getInventoryByCategory("Cha").forEach(System.out::println);
        //sqlQuery.getInventoryByPrice(100,900).forEach(System.out::println);
        //sqlQuery.getInventoryByLifeExpectancy(1,4).forEach(System.out::println);
        //sqlQuery.getInventoryItemsToBeDisposedOfBetweenTwoYears(2025,2027).forEach(System.out::println);
        //sqlQuery.getInventoryByNumberOfPurchase(1,3).forEach(System.out::println);
        //sqlQuery.getInventoryByStatusCode(200).forEach(System.out::println);
        //sqlQuery.getInventoryByStatusDescription("sold").forEach(System.out::println);
        //sqlQuery.getInventoryByOutOfUseAndYear("2024").forEach(System.out::println);

        //ct.createTables(true);
        SqlQuery inventoryService = new SqlQuery();
        Map<String, Object> searchCriteria = new HashMap<>();
 /*       searchCriteria.put("inventoryType", "Utsmykning");
        searchCriteria.put("description", "Lenestol");
        searchCriteria.put("category", "Grafikk");
        searchCriteria.put("minPrice", 10000);
        searchCriteria.put("maxPrice", 20000);
        searchCriteria.put("minLifeExpectancy", 5);
        searchCriteria.put("maxLifeExpectancy", 7);
        searchCriteria.put("statusCode", 200);*/
        searchCriteria.put("inventoryType", "Tekniskutstyr");
        searchCriteria.put("minPrice",1000);
        searchCriteria.put("maxPrice", 5000);
        searchCriteria.put("dateOfPurchase", "2022");

        List<Inventory> results = inventoryService.searchInventory(searchCriteria);
        results.forEach(System.out::println);
    }
    public void startServer(){
        serverHandler.start();
    }
}
