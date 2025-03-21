package org.example.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;

public class PostgresConfig {
    private static final String username = "postgres";
    private static final String password = "12345";
    private static final String url = "jdbc:postgresql://localhost:5432/postgres?currentSchema=bank_customer_schema";
    private static Connection connection;

    public static Connection getConnection(){
        try {
            if(Objects.isNull(connection)){
                connection=DriverManager.getConnection(url,username,password);
            }
           return connection;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
