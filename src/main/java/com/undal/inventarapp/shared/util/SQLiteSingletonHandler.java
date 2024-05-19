package com.undal.inventarapp.shared.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLiteSingletonHandler {
    private static final String JDBC_URL = Util.JDBC_URL;
    private static SQLiteSingletonHandler instance;
    private Connection connection;

    private SQLiteSingletonHandler(){
        try{
            this.connection = DriverManager.getConnection(JDBC_URL);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static SQLiteSingletonHandler getInstance(){
        if(instance == null){
            instance = new SQLiteSingletonHandler();
        }else {
            try {
                if(instance.getConnection().isClosed()){
                    instance = new SQLiteSingletonHandler();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return instance;
    }
    public Connection getConnection() {
        return connection;
    }
}
