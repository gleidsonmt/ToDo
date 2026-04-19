package io.github.gleidsonmt.todo.bd;

import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Gleidson Neves da Silveira | <gleidisonmt@gmail.com>
 * Created on  19/04/2026
 */
class DatabaseConnectionTest {

    @Test
    void constructor() {
        DatabaseConnection connection = new DatabaseConnection();
        assertNotNull(connection);
    }

    @Test
    void connect() {
        DatabaseConnection connection = new DatabaseConnection();
        assertTrue(connection.connect());
    }

    @Test
    void hasConnection() {
        DatabaseConnection connection = new DatabaseConnection();
        connection.connect();
        assertTrue(connection.hasConnection());
    }

    @Test
    void getResult() throws SQLException {
        DatabaseConnection connection = new DatabaseConnection();
        connection.connect();
        assertNotNull(connection.executeQuery("select * from list;"));
    }

    @Test
    void executeQuery() throws SQLException {
        DatabaseConnection connection = new DatabaseConnection();
        connection.connect();
        ResultSet resultSet = connection.executeQuery("select * from list;");
        assertTrue(resultSet.next());
    }

    @Test
    void getLastID() {
    }

    @Test
    void close() {
    }



    @Test
    void getConnection() {
    }

    @Test
    void getDatabase() {
    }

    @Test
    void getErrorMessage() {
    }
}