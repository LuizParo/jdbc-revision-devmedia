package br.com.devmedia.jdbc.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCConnection {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/jdbcdb";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASS = "root";
    private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    
    public static Connection getConnection() {
        try {
            Class.forName(JDBC_DRIVER);
            return DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}