package br.com.devmedia.jdbc.connection;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.Assert;
import org.junit.Test;

public class JDBCConnectionTest {

    @Test
    public void shouldConnectSuccessfullyWithDatabase() throws SQLException {
        try(Connection connection = JDBCConnection.getConnection()) {
            Assert.assertNotNull(connection);
            Assert.assertFalse(connection.isClosed());
        }
    }
}