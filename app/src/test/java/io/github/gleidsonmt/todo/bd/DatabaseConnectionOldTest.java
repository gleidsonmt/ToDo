package io.github.gleidsonmt.todo.bd;

import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Gleidson Neves da Silveira | <gleidisonmt@gmail.com>
 * Created on  19/04/2026
 */
class DatabaseConnectionOldTest {

    @Test
    void is_constructor_working() {
        DatabaseConnection proto = DatabaseConnection.INSTANCE;
        assertNotNull(proto);
    }

    @Test
    void verify_connection_is_working() {
        DatabaseConnection proto = DatabaseConnection.INSTANCE;
        assertNotNull(proto);
        assertFalse(proto.hasConnection());
        assertTrue(proto.connect());
        assertTrue(proto.hasConnection());
        proto.close();
    }



    @Test
    void connecting_with_database() {
        DatabaseConnection proto = DatabaseConnection.INSTANCE;
        assertTrue(proto.connect());
        proto.close();
    }
  
    @Test
    void has_connection() {
        DatabaseConnection proto = DatabaseConnection.INSTANCE;
        assertFalse(proto.hasConnection());
        assertTrue(proto.connect());
        assertTrue(proto.hasConnection());
        proto.close();
    }
  
    @Test
    void is_query_working() throws SQLException {
        DatabaseConnection proto = DatabaseConnection.INSTANCE;
        proto.connect();
        ResultSet resultSet = proto.executeQuery("select * from list;");
        assertTrue(resultSet.next());
        proto.close();
    }

    @Test
    void is_connection_closing() throws SQLException {
        DatabaseConnection proto = DatabaseConnection.INSTANCE;
        proto.connect();
        assertTrue(proto.hasConnection());
        assertTrue(proto.close());
        assertFalse(proto.hasConnection());
        assertTrue(proto.getConnection().isClosed());
    }
}