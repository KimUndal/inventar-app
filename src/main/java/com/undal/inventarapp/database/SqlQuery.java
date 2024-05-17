package com.undal.inventarapp.database;

import com.undal.inventarapp.shared.model.Inventory;
import com.undal.inventarapp.shared.model.Mobel;
import com.undal.inventarapp.shared.model.TekniskUtstyr;
import com.undal.inventarapp.shared.model.Utsmykning;
import com.undal.inventarapp.shared.util.Util;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

public class SqlQuery {
    private String jdbcUrl = Util.JDBC_URL;

    public List<Inventory> getInventoryByType(String inventoryType){
        List<Inventory> inventoryList = new ArrayList<>();
        String sqlQuery = "";
        String tableName = "";
        try(Connection conn = DriverManager.getConnection(jdbcUrl)){
            sqlQuery = "SELECT i.id, u.*\n" +
                    "FROM Inventory i\n" +
                    "         JOIN "+ inventoryType+" u ON i."+inventoryType+" = u.id\n" +
                    "WHERE i." + inventoryType + " IS NOT NULL;" ;
            Statement pstm = conn.createStatement();
            ResultSet rs = pstm.executeQuery(sqlQuery);

            while(rs.next()) {
               Inventory inventory = resultset.apply(rs, inventoryType);
               inventoryList.add(inventory);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
        return inventoryList;
    }

//    private List<Inventory> getInventoryByDescription(String desc){
//        List<Inventory> inventoryList = new ArrayList<>();
//        try(Connection conn = DriverManager.getConnection(jdbcUrl)){
//          String sqlQuery = "SELECT i.id, u.*\n" +
//                    "FROM Inventory i\n" +
//                    "         JOIN "+ inventoryType+" u ON i."+inventoryType+" = u.id\n" +
//                    "WHERE i." + inventoryType + " IS NOT NULL;" ;
//            Statement pstm = conn.createStatement();
//            ResultSet rs = pstm.executeQuery(sqlQuery);
//
//            while(rs.next()) {
//                Inventory inventory = resultset.apply(rs, inventoryType);
//                inventoryList.add(inventory);
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e.getMessage());
//        }
//    }

    private BiFunction<ResultSet, String, Inventory> resultset =(rs, inventoryType) ->{
        try {
            if (inventoryType.equals("Mobel")) {
                Mobel mobel = new Mobel();
                mobel.setId(rs.getInt("id"));
                mobel.setCategory(rs.getString("category"));
                mobel.setDescription(rs.getString("description"));
                // Directly get the SQL Date
                mobel.setDateOfPurchase(getDateFromString(rs.getString("dateofpurchase")));

                mobel.setPrice(rs.getInt("price"));
                mobel.setLifeExpectancy(rs.getInt("lifeexpectancy"));
                mobel.setNumberOfPurchase(rs.getInt("numberofpurchase"));
                mobel.setPlacement(rs.getString("placement"));
                return mobel;
            } else if (inventoryType.equals("Tekniskutstyr")) {
                TekniskUtstyr tekniskUtstyr = new TekniskUtstyr();

                tekniskUtstyr.setId(rs.getInt("id"));
                tekniskUtstyr.setCategory(rs.getString("category"));
                tekniskUtstyr.setDescription(rs.getString("description"));
                tekniskUtstyr.setDateOfPurchase(getDateFromString(rs.getString("dateofpurchase")));
                tekniskUtstyr.setPrice(rs.getInt("price"));
                tekniskUtstyr.setNumberOfPurchase(rs.getInt("numberofpurchase"));
                tekniskUtstyr.setPlacement(rs.getString("placement"));
                return tekniskUtstyr;
            } else {
                Utsmykning utsmykning = new Utsmykning();
                utsmykning.setId(rs.getInt("id"));
                utsmykning.setCategory(rs.getString("category"));
                utsmykning.setDescription(rs.getString("description"));
                utsmykning.setDateOfPurchase(getDateFromString(rs.getString("dateofpurchase")));
                utsmykning.setPrice(rs.getInt("price"));
                utsmykning.setNumberOfPurchase(rs.getInt("numberofpurchase"));
                utsmykning.setPlacement(rs.getString("placement"));
                return utsmykning;
            }

        }catch (SQLException e){
            throw new RuntimeException(e.getMessage());
        }

    };

    private Date getDateFromString(String date){
        try {
            return new SimpleDateFormat("YYYY-MM-DD").parse(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
