package com.undal.inventarapp.database;

import com.undal.inventarapp.shared.util.SQLiteSingletonHandler;
import com.undal.inventarapp.shared.util.Util;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static com.undal.inventarapp.shared.util.Util.JDBC_URL;

public class CreateTable{


    public static final String JDBC_URL = Util.JDBC_URL;

    public void createTables(boolean addMockdata){
        boolean isTriggerCreated = false;
        try(Connection conn = SQLiteSingletonHandler.getInstance().getConnection()) {
            String inventoryTableSql = "CREATE TABLE IF NOT EXISTS Inventory (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "inventoryType TEXT NOT NULL, " +
                    "category TEXT NOT NULL, " +
                    "description TEXT, " +
                    "dateofpurchase TEXT, " +
                    "price INTEGER, " +
                    "lifeexpectancy INTEGER, " +
                    "numberofpurchase INTEGER, " +
                    "placement TEXT" +
                    ");";

            String inventoryStatusTableSql = "CREATE TABLE IF NOT EXISTS InventoryStatus (" +
                    "statusId INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "inventoryId INTEGER NOT NULL, " +
                    "statuscode TEXT NOT NULL, " +
                    "status TEXT NOT NULL, " +
                    "statusDescription TEXT, " +
                    "dateChanged TEXT NOT NULL, " +
                    "FOREIGN KEY (inventoryId) REFERENCES Inventory(id), " +
                    "UNIQUE (statusId, inventoryId)" +
                    ");";

            try (PreparedStatement inventoryTableStmt = conn.prepareStatement(inventoryTableSql);
                 PreparedStatement inventoryStatusTableStmt = conn.prepareStatement(inventoryStatusTableSql);
                ) {
                 inventoryTableStmt.execute();
                 inventoryStatusTableStmt.execute();


            }
            String setStatusTriggerSql = "CREATE TRIGGER IF NOT EXISTS SetStatusAfterInsert " +
                    "AFTER INSERT ON Inventory " +
                    "FOR EACH ROW " +
                    "BEGIN " +
                    "    INSERT INTO InventoryStatus (inventoryId, statuscode, status, statusdescription, dateChanged) " +
                    "    VALUES (NEW.id, '200', 'In Use', null, DATE('now')); " +
                    "END;";
            try(PreparedStatement   setStatusTriggerStmt = conn.prepareStatement(setStatusTriggerSql)){
             setStatusTriggerStmt.execute();
            }
            if(addMockdata ){
                addMockData();
            }
        } catch (SQLException exception) {
            throw new RuntimeException(exception.getMessage());
        }
    }
    private List<String> getTableName(){
        List<String>filesList = new ArrayList<>();
        File folderPath = new File("src/main/java/com/undal/inventarapp/shared/model");
        File[] files = folderPath.listFiles();
        if(files != null) {
            Arrays.stream(files)
                    .filter(File::isFile)
                    .forEach(file ->
                            filesList.add(file.getName()
                                    .replace(".java",""))
                    );
        }

        return filesList;
    }

    public void addMockData(){
        try(Connection conn = DriverManager.getConnection(JDBC_URL);
            Scanner scanner = new Scanner(new File("src/main/resources/testfiles/mockdata.txt"))){
            String inventoryType = "";
            while(scanner.hasNextLine()){
                String line = scanner.nextLine();
                if(line.isEmpty()){
                    continue;
                }

                String[] data = line.split(",");
                insertData(conn, data);
            }
            System.out.println("Mock data inserted succesfully...");
        } catch (SQLException | FileNotFoundException e) {
            System.out.println("Error inserting mock data...");
            throw new RuntimeException(e.getMessage());
        }
    }

    private void insertData(Connection conn, String[] data) {
        String sql ="";

        sql = "INSERT INTO Inventory (inventoryType, category, description, dateofpurchase, price, lifeexpectancy, numberofpurchase, placement) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try(PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, data[0]); // Assuming the new column is the first column
            pstmt.setString(2, data[1]); // Existing column
            pstmt.setString(3, data[2]); // Existing column
            pstmt.setString(4, data[3]); // String value for the fourth column
            pstmt.setInt(5, Integer.parseInt(data[4])); // price is now in the 6th position
            // placement is last
            // numberofpurchase is next to last
            if(data[0].equals("Mobel")) {
                pstmt.setInt(6, Integer.parseInt(data[5]));
                pstmt.setInt(7, Integer.parseInt(data[6]));
                pstmt.setString(8, data[7]);
            }else{
                pstmt.setObject(6, null); // lifeexpectancy is now in the 5th position
                pstmt.setInt(7, Integer.parseInt(data[5]));
                pstmt.setString(8, data[6]);
            }




            pstmt.executeUpdate();
        }catch (SQLException e){
            throw new RuntimeException(e.getMessage());
        }
    }
}