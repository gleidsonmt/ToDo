package io.github.gleidsonmt.todo.bd;

import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Gleidson Neves da Silveira | <gleidisonmt@gmail.com>
 * Created on  19/04/2026
 */
class DatabaseConnectionTest {

    @Test
    void constructor_is_Working() {
        DatabaseConnection connection = new DatabaseConnection();
        assertNotNull(connection);
    }

    @Test
    void connecting_with_database() {
        DatabaseConnection connection = new DatabaseConnection();
        assertTrue(connection.connect());
    }

    @Test
    void is_query_working() throws SQLException {
        DatabaseConnection connection = new DatabaseConnection();
        connection.connect();
        ResultSet resultSet = connection.executeQuery("select * from list;");
        assertTrue(resultSet.next());
        resultSet.close();
    }

    @Test
    void is_connection_closed() throws SQLException {
        DatabaseConnection connection = new DatabaseConnection();
        connection.connect();
        assertTrue(connection.hasConnection());
        assertTrue(connection.close());
        assertFalse(connection.hasConnection());
        assertTrue(connection.getConnection().isClosed());
    }
}