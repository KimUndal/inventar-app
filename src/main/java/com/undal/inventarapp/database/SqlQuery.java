package com.undal.inventarapp.database;

import com.undal.inventarapp.shared.model.*;
import com.undal.inventarapp.shared.util.SQLiteSingletonHandler;
import com.undal.inventarapp.shared.util.Util;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SqlQuery {
    private static final String QUERY = "SELECT * FROM Inventory";

    public List<Inventory> getInventoryByType(String inventoryType){
        List<Inventory> inventoryList = new ArrayList<>();
        String sqlQuery = "";
        String tableName = "";
        try(Connection conn = SQLiteSingletonHandler.getInstance().getConnection()){
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
        try(Connection conn =SQLiteSingletonHandler.getInstance().getConnection()){
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
        try(Connection conn = SQLiteSingletonHandler.getInstance().getConnection()){
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
        try(Connection conn = SQLiteSingletonHandler.getInstance().getConnection()){
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
        try(Connection conn = SQLiteSingletonHandler.getInstance().getConnection()){
            String sqlQuery = QUERY + " where Inventory.price between ? and ? order by Inventory.price desc";
            getInventoryByMinMax(minPrice, maxPrice, inventoryList, conn, sqlQuery);
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
        return inventoryList;
    }

    public List<Inventory>getInventoryByLifeExpectancy(int minYear, int maxYear){
        List<Inventory> inventoryList = new ArrayList<>();
        try(Connection conn = SQLiteSingletonHandler.getInstance().getConnection()){
            String sqlQuery = QUERY + " where Inventory.lifeexpectancy between ? and ? order by Inventory.lifeexpectancy desc";
            getInventoryByMinMax(minYear, maxYear, inventoryList, conn, sqlQuery);
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
        return inventoryList;
    }

    public List<Inventory>getInventoryItemsToBeDisposedOfBetweenTwoYears(int minYear, int maxYear){
        List<Inventory> inventoryList = new ArrayList<>();
        try(Connection conn = SQLiteSingletonHandler.getInstance().getConnection()){
            String sqlQuery = QUERY + " WHERE strftime('%Y', dateOfPurchase) + lifeExpectancy BETWEEN ? AND ?;";
            getInventoryByMinMax(minYear, maxYear, inventoryList, conn, sqlQuery);
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
        return inventoryList;
    }

    public List<Inventory> getInventoryItemsByPlacement(String placement){
        List<Inventory> inventoryList = new ArrayList<>();
        try(Connection conn = SQLiteSingletonHandler.getInstance().getConnection()) {
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
        try(Connection conn = SQLiteSingletonHandler.getInstance().getConnection()){
            String sqlQuery = QUERY + " WHERE Inventory.numberofpurchase between ? and ?;";
            getInventoryByMinMax(min, max, inventoryList, conn, sqlQuery);
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
        return inventoryList;
    }

    public List<Inventory> getInventoryByStatusCode(int statusCode){
        List<Inventory> inventoryList = new ArrayList<>();
        try(Connection conn = SQLiteSingletonHandler.getInstance().getConnection()){
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
        try(Connection conn =SQLiteSingletonHandler.getInstance().getConnection()){
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
        try(Connection conn = SQLiteSingletonHandler.getInstance().getConnection()){
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

    //#####CHATGPT
    public List<Inventory> searchInventory(Map<String, Object> searchCriteria) {
        List<Inventory> inventoryList = new ArrayList<>();
        StringBuilder sqlQuery = new StringBuilder("SELECT i.* FROM Inventory i ");
        StringBuilder whereClause = new StringBuilder("WHERE 1=1 ");
        List<Object> parameters = new ArrayList<>();

        // Join InventoryStatus table if statusCode or statusDescription is present
        if (searchCriteria.containsKey("statusCode") || searchCriteria.containsKey("statusDescription") || searchCriteria.containsKey("status")) {
            sqlQuery.append("JOIN InventoryStatus s ON i.id = s.inventoryId ");
        }

        // Build the WHERE clause based on the search criteria
        for (Map.Entry<String, Object> entry : searchCriteria.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();

            switch (key) {
                case "inventoryType" -> {
                    whereClause.append("AND i.inventoryType = ? ");
                    parameters.add(value);
                }
                case "description" -> {
                    whereClause.append("AND i.description LIKE ? ");
                    parameters.add("%" + value + "%");
                }
                case "dateOfPurchase" -> {
                    whereClause.append("AND i.dateOfPurchase LIKE ? ");
                    parameters.add("%" + value + "%");
                }
                case "category" -> {
                    whereClause.append("AND i.category LIKE ? ");
                    parameters.add("%" + value + "%");
                }
                case "minPrice" -> {
                    whereClause.append("AND i.price >= ? ");
                    parameters.add(value);
                }
                case "maxPrice" -> {
                    whereClause.append("AND i.price <= ? ");
                    parameters.add(value);
                }
                case "minLifeExpectancy" -> {
                    whereClause.append("AND i.lifeexpectancy >= ? ");
                    parameters.add(value);
                }
                case "maxLifeExpectancy" -> {
                    whereClause.append("AND i.lifeexpectancy <= ? ");
                    parameters.add(value);
                }
                case "minDisposeYear" -> {
                    whereClause.append("AND strftime('%Y', i.dateOfPurchase) + i.lifeexpectancy >= ? ");
                    parameters.add(value);
                }
                case "maxDisposeYear" -> {
                    whereClause.append("AND strftime('%Y', i.dateOfPurchase) + i.lifeexpectancy <= ? ");
                    parameters.add(value);
                }
                case "placement" -> {
                    whereClause.append("AND i.placement LIKE ? ");
                    parameters.add("%" + value + "%");
                }
                case "minNumberOfPurchase" -> {
                    whereClause.append("AND i.numberofpurchase >= ? ");
                    parameters.add(value);
                }
                case "maxNumberOfPurchase" -> {
                    whereClause.append("AND i.numberofpurchase <= ? ");
                    parameters.add(value);
                }
                case "statusCode" -> {
                    whereClause.append("AND s.statusCode = ? ");
                    parameters.add(value);
                }
                case "statusDescription" -> {
                    whereClause.append("AND s.statusDescription LIKE ? ");
                    parameters.add("%" + value + "%");
                }
                case "status" -> {
                    whereClause.append("AND s.status = ? ");
                    parameters.add(value);
                }
                case "outOfUseYear" -> {
                    whereClause.append("AND s.status = 'Out of Use' AND s.dateChanged LIKE ? ");
                    parameters.add("%" + value + "%");
                }
                default -> throw new IllegalArgumentException("Unknown search criteria: " + key);
            }
        }

        sqlQuery.append(whereClause);

        // Execute the query
        try (Connection conn = SQLiteSingletonHandler.getInstance().getConnection();
             PreparedStatement pstm = conn.prepareStatement(sqlQuery.toString())) {

            // Set the parameters in the PreparedStatement
            for (int i = 0; i < parameters.size(); i++) {
                pstm.setObject(i + 1, parameters.get(i));
            }

            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                Inventory inventory = resultset.apply(rs);
                inventoryList.add(inventory);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        }

        return inventoryList;
    }


    //###CHATGPT


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

    private Function<ResultSet, Inventory> resultset = rs -> {
        try {
            String inventoryType = rs.getString("inventoryType");

            if ("Mobel".equals(inventoryType)) {
                Mobel mobel = new Mobel();
                extractedInventory(rs, mobel);
                return mobel;
            } else if ("tekniskutstyr".equals(inventoryType)) {
                Inventory tekniskUtstyr = new TekniskUtstyr();
                extractedInventory(rs, tekniskUtstyr);
                return tekniskUtstyr;
            } else {
                Inventory utsmykning = new Utsmykning();
                extractedInventory(rs, utsmykning);
                return utsmykning;
            }
        } catch (SQLException e) {
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
