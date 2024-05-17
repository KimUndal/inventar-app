package com.undal.inventarapp.database;

import com.undal.inventarapp.shared.util.Util;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class CreateTable{


    public static final String JDBC_URL = Util.JDBC_URL;

    public void createTables(boolean addMockdata){
        try(Connection conn = DriverManager.getConnection(JDBC_URL)) {
            String sql = "";
            for (String fileName : getTableName()) {
                switch (fileName) {
                    case "Mobel" -> {
                        sql = "CREATE TABLE IF NOT EXISTS " + fileName + " (" +
                                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                "category TEXT NOT NULL, " +
                                "description TEXT, " +
                                "dateofpurchase TEXT, " +
                                "price INTEGER, " +
                                "lifeexpectancy INTEGER, " +
                                "numberofpurchase INTEGER, " +
                                "placement TEXT" +
                                ");";
                    }
                    case "TekniskUtstyr" -> {
                        sql = "CREATE TABLE IF NOT EXISTS " + fileName + " (" +
                                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                "category TEXT NOT NULL, " +
                                "description TEXT, " +
                                "dateofpurchase TEXT, " +
                                "price INTEGER, " +
                                "numberofpurchase INTEGER, " +
                                "placement TEXT" +
                                ");";
                    }
                    case "Utsmykning" -> {
                        sql = "CREATE TABLE IF NOT EXISTS " + fileName + " (" +
                                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                "category TEXT NOT NULL, " +
                                "description TEXT, " +
                                "dateofpurchase TEXT, " +
                                "price INTEGER, " +
                                "numberofpurchase INTEGER, " +
                                "placement TEXT" +
                                ");";
                    }
                    default -> {
                        sql = "CREATE TABLE IF NOT EXISTS " + fileName + " (" +
                                "id TEXT PRIMARY KEY, " +
                                "mobel INTEGER, " +
                                "tekniskutstyr INTEGER, " +
                                "utsmykning INTEGER, " +
                                "FOREIGN KEY (mobel) REFERENCES Mobel(id), " +
                                "FOREIGN KEY (tekniskutstyr) REFERENCES TekniskUtstyr(id), " +
                                "FOREIGN KEY (utsmykning) REFERENCES Utsmykning(id)" +
                                ");";
                    }
                }
                try (PreparedStatement pstm = conn.prepareStatement(sql)) {
                    pstm.execute();
                }
            }
            if(addMockdata){
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
            String tableName = "";
            while(scanner.hasNextLine()){
                String line = scanner.nextLine();
                if(line.isEmpty()){
                    continue;
                }
                if(!line.contains(",")){
                    tableName = line.trim();
                    continue;
                }
                String[] data = line.split(",");
                insertData(conn, tableName, data);
            }
            System.out.println("Mock data inserted succesfully...");
        } catch (SQLException | FileNotFoundException e) {
            System.out.println("Error inserting mock data...");
            throw new RuntimeException(e.getMessage());
        }
    }

    private void insertData(Connection conn, String tableName, String[] data) {
        String sql ="";
        if (tableName.equals("Mobel")){
            sql = "INSERT INTO " + tableName + " (category, description, dateofpurchase, price, lifeexpectancy, numberofpurchase, placement) " +
                    "VALUES (?, ?, ?, ?, ?, ?,?)";
        } else {
            sql =  sql = "INSERT INTO " + tableName + " (category, description, dateofpurchase, price, numberofpurchase,placement) " +
                    "VALUES (?, ?, ?, ?, ?,?)";
        }
        try(PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, data[0]);
            pstmt.setString(2, data[1]);
            pstmt.setString(3, data[2]);
            pstmt.setInt(4, Integer.parseInt(data[3]));
            pstmt.setInt(5, Integer.parseInt(data[data.length - 2])); // numberofpurchase is next to last
            if (tableName.equals("Mobel")) {
                pstmt.setInt(6, Integer.parseInt(data[4])); // lifeexpectancy
                pstmt.setString(7, data[data.length - 1]); // placement is last
            } else {
                pstmt.setString(6, data[data.length - 1]); // placement is last
            }

            pstmt.executeUpdate();
        }catch (SQLException e){
            throw new RuntimeException(e.getMessage());
        }
    }
}