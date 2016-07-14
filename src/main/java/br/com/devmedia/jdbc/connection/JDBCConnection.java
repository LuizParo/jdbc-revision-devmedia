package br.com.devmedia.jdbc.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
    
    public static void close(Connection connection) {
        try {
            if(connection != null && connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
    
    public static void close(Connection connection, PreparedStatement statement) {
        try {
            if(statement != null && statement.isClosed()) {
                statement.close();
            }
            close(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
    
    public static void close(Connection connection, PreparedStatement statement, ResultSet resultSet) {
        try {
            if(resultSet != null && resultSet.isClosed()) {
                resultSet.close();
            }
            close(connection, statement);
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}