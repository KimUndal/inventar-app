package com.undal.inventarapp.database;

import com.undal.inventarapp.shared.model.*;
import com.undal.inventarapp.shared.util.Util;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class SqlQuery {
    private static final String QUERY = "SELECT * FROM Inventory";
    private String jdbcUrl = Util.JDBC_URL;

    public List<Inventory> getInventoryByType(String inventoryType){
        List<Inventory> inventoryList = new ArrayList<>();
        String sqlQuery = "";
        String tableName = "";
        try(Connection conn = DriverManager.getConnection(jdbcUrl)){
            sqlQuery = "select * from Inventory where inventoryType = ?";
            PreparedStatement pstm = conn.prepareStatement(sqlQuery);
            pstm.setString(1, inventoryType);
            ResultSet rs = pstm.executeQuery();

            while(rs.next()) {
               Inventory inventory = resultset.apply(rs);
               inventoryList.add(inventory);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
        return inventoryList;
    }

    public List<Inventory> getInventoryByDescription(String desc){
        List<Inventory> inventoryList = new ArrayList<>();
        try(Connection conn = DriverManager.getConnection(jdbcUrl)){
          String sqlQuery = QUERY + " where description like ?";
            PreparedStatement pstm = conn.prepareStatement(sqlQuery);
            pstm.setString(1, "%" + desc + "%");
            ResultSet rs = pstm.executeQuery();

            while(rs.next()) {
                Inventory inventory = resultset.apply(rs);
                inventoryList.add(inventory);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
        return inventoryList;
    }

    public List<Inventory> getInventoryByDateOfPurchase(String year){
        List<Inventory> inventoryList = new ArrayList<>();
        try(Connection conn = DriverManager.getConnection(jdbcUrl)){
            String sqlQuery = QUERY + " where Inventory.dateofpurchase like ?";
            PreparedStatement pstm = conn.prepareStatement(sqlQuery);
            pstm.setString(1, "%" + year + "%");
            ResultSet rs = pstm.executeQuery();

            while(rs.next()) {
                Inventory inventory = resultset.apply(rs);
                inventoryList.add(inventory);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
        return inventoryList;
    }

    public List<Inventory>getInventoryByCategory(String category){
        List<Inventory> inventoryList = new ArrayList<>();
        try(Connection conn = DriverManager.getConnection(jdbcUrl)){
            String sqlQuery = QUERY + " where Inventory.category like ?";
            PreparedStatement pstm = conn.prepareStatement(sqlQuery);
            pstm.setString(1, "%" + category + "%");
            ResultSet rs = pstm.executeQuery();

            while(rs.next()) {
                Inventory inventory = resultset.apply(rs);
                inventoryList.add(inventory);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
        return inventoryList;
    }

    public List<Inventory>getInventoryByPrice(int minPrice, int maxPrice){
        List<Inventory> inventoryList = new ArrayList<>();
        try(Connection conn = DriverManager.getConnection(jdbcUrl)){
            String sqlQuery = QUERY + " where Inventory.price between ? and ? order by Inventory.price desc";
            getInventoryByMinMax(minPrice, maxPrice, inventoryList, conn, sqlQuery);
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
        return inventoryList;
    }

    public List<Inventory>getInventoryByLifeExpectancy(int minYear, int maxYear){
        List<Inventory> inventoryList = new ArrayList<>();
        try(Connection conn = DriverManager.getConnection(jdbcUrl)){
            String sqlQuery = QUERY + " where Inventory.lifeexpectancy between ? and ? order by Inventory.lifeexpectancy desc";
            getInventoryByMinMax(minYear, maxYear, inventoryList, conn, sqlQuery);
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
        return inventoryList;
    }

    public List<Inventory>getInventoryItemsToBeDisposedOfBetweenTwoYears(int minYear, int maxYear){
        List<Inventory> inventoryList = new ArrayList<>();
        try(Connection conn = DriverManager.getConnection(jdbcUrl)){
            String sqlQuery = QUERY + " WHERE strftime('%Y', dateOfPurchase) + lifeExpectancy BETWEEN ? AND ?;";
            getInventoryByMinMax(minYear, maxYear, inventoryList, conn, sqlQuery);
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
        return inventoryList;
    }

    public List<Inventory> getInventoryItemsByPlacement(String placement){
        List<Inventory> inventoryList = new ArrayList<>();
        try(Connection conn = DriverManager.getConnection(jdbcUrl)) {
            String sqlQuery = QUERY + " where Inventory.placement like ?";
            PreparedStatement pstm = conn.prepareStatement(sqlQuery);
            pstm.setString(1, "%" + placement + "%");
            ResultSet rs = pstm.executeQuery();
            while(rs.next()){
                Inventory inventory = resultset.apply(rs);
                inventoryList.add(inventory);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return inventoryList;
    }

    public List<Inventory>getInventoryByNumberOfPurchase(int min, int max){
        List<Inventory> inventoryList = new ArrayList<>();
        try(Connection conn = DriverManager.getConnection(jdbcUrl)){
            String sqlQuery = QUERY + " WHERE Inventory.numberofpurchase between ? and ?;";
            getInventoryByMinMax(min, max, inventoryList, conn, sqlQuery);
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
        return inventoryList;
    }

    public List<Inventory> getInventoryByStatusCode(int statusCode){
        List<Inventory> inventoryList = new ArrayList<>();
        try(Connection conn = DriverManager.getConnection(jdbcUrl)){
            String sqlQuery = "SELECT i.*\n" +
                    "FROM Inventory i\n" +
                    "JOIN InventoryStatus s ON i.id = s.inventoryId\n" +
                    "WHERE s.statuscode = ? ;";
            PreparedStatement pstm = conn.prepareStatement(sqlQuery);
            pstm.setInt(1, statusCode);
            ResultSet rs = pstm.executeQuery();
            while(rs.next()) {
                Inventory inventory = resultset.apply(rs);
                inventoryList.add(inventory);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
        return inventoryList;
    }

    public List<Inventory> getInventoryByStatusDescription(String description){
        List<Inventory> inventoryList = new ArrayList<>();
        try(Connection conn = DriverManager.getConnection(jdbcUrl)){
            String sqlQuery = "SELECT i.*\n" +
                    "FROM Inventory i\n" +
                    "JOIN InventoryStatus s ON i.id = s.inventoryId\n" +
                    "WHERE s.statusDescription = ? ;";
            PreparedStatement pstm = conn.prepareStatement(sqlQuery);
            pstm.setString(1, description);
            ResultSet rs = pstm.executeQuery();
            while(rs.next()) {
                Inventory inventory = resultset.apply(rs);
                inventoryList.add(inventory);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
        return inventoryList;
    }

    public List<Inventory> getInventoryByOutOfUseAndYear(String year){
        List<Inventory> inventoryList = new ArrayList<>();
        try(Connection conn = DriverManager.getConnection(jdbcUrl)){
            String sqlQuery = "SELECT i.*\n" +
                    "FROM Inventory i\n" +
                    "JOIN InventoryStatus s ON i.id = s.inventoryId\n" +
                    "WHERE 1=1 " +
                    "AND status='Out of Use'" +
                    "AND s.dateChanged like ? ;";

            PreparedStatement pstm = conn.prepareStatement(sqlQuery);
            pstm.setString(1, "%"+year+"%");
            ResultSet rs = pstm.executeQuery();
            while(rs.next()) {
                Inventory inventory = resultset.apply(rs);
                inventoryList.add(inventory);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
        return inventoryList;
    }


    private void getInventoryByMinMax(int minYear, int maxYear, List<Inventory> inventoryList, Connection conn, String sqlQuery) throws SQLException {
        PreparedStatement pstm = conn.prepareStatement(sqlQuery);
        pstm.setInt(1, minYear);
        pstm.setInt(2, maxYear);
        ResultSet rs = pstm.executeQuery();

        while(rs.next()) {
            Inventory inventory = resultset.apply(rs);
            inventoryList.add(inventory);
        }
    }

    private Function<ResultSet, Inventory> resultset =rs ->{
        try {

            if (rs.getString("inventoryType").equals("Mobel")) {
                Mobel mobel = new Mobel();
                extractedInventory(rs, mobel);
                return mobel;
            } else if (rs.getString("inventoryType").equals("tekniskutstyr")) {
                Inventory tekniskUtstyr = new TekniskUtstyr();

                 extractedInventory(rs, tekniskUtstyr);
                return tekniskUtstyr;
            } else {
                Inventory utsmykning = new Utsmykning();
                extractedInventory(rs, utsmykning);
                return utsmykning;
            }

        }catch (SQLException e){
            throw new RuntimeException(e.getMessage());
        }

    };

    private void extractedInventory(ResultSet rs, Inventory inventory) throws SQLException {
        setInventory(rs, inventory);
        inventory.setNumberOfPurchase(rs.getInt("numberofpurchase"));
        inventory.setPlacement(rs.getString("placement"));
        if(inventory instanceof Mobel){
            setInventory(rs, inventory);
            ((Mobel) inventory).setLifeExpectancy(rs.getInt("lifeexpectancy"));
            inventory.setNumberOfPurchase(rs.getInt("numberofpurchase"));
            inventory.setPlacement(rs.getString("placement"));
        }
    }

    private void setInventory(ResultSet rs, Inventory inventory) throws SQLException {
        inventory.setId(rs.getInt("id"));
        inventory.setInventoryType(rs.getString("inventoryType"));
        inventory.setCategory(rs.getString("category"));
        inventory.setDescription(rs.getString("description"));
        inventory.setDateOfPurchase(parseLocalDate(rs.getString("dateofpurchase")));
        inventory.setPrice(rs.getInt("price"));
    }

    private LocalDate parseLocalDate(String date){
        return LocalDate.parse(date);
    }
}
