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
    void is_constructor_working() {
        DatabaseConnectionProto proto = DatabaseConnectionProto.INSTANCE;
        assertNotNull(proto);
        proto.close();
    }
  
  
    @Test
    void connecting_with_database() {
        DatabaseConnectionProto proto = DatabaseConnectionProto.INSTANCE;
        assertTrue(proto.connect());
        proto.close();
    }
  
    @Test
    void has_connection() {
        DatabaseConnectionProto proto = DatabaseConnectionProto.INSTANCE;
        assertFalse(proto.hasConnection());
        assertTrue(proto.connect());
        assertTrue(proto.hasConnection());
        proto.close();
    }

  
    @Test
    void is_query_working() throws SQLException {
        DatabaseConnectionProto proto = DatabaseConnectionProto.INSTANCE;
        proto.connect();
        ResultSet resultSet = connection.executeQuery("select * from list;");
        assertTrue(resultSet.next());
        proto.close();
    }

    @Test
    void is_connection_closing() throws SQLException {
        DatabaseConnectionProto proto = DatabaseConnectionProto.INSTANCE;
        proto.connect();
        assertTrue(proto.hasConnection());
        assertTrue(proto.close());
        assertFalse(proto.hasConnection());
        assertTrue(proto.getConnection().isClosed());
    }
}