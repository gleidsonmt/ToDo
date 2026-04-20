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
        DatabaseConnectionProto proto = DatabaseConnectionProto.INSTANCE;
        assertNotNull(proto);
        proto.close();
    }

    @Test
    void connect() {
        DatabaseConnectionProto proto = DatabaseConnectionProto.INSTANCE;
        assertTrue(proto.connect());
        proto.close();
    }

    @Test
    void hasConnection() {
        DatabaseConnectionProto proto = DatabaseConnectionProto.INSTANCE;
        assertFalse(proto.hasConnection());
        assertTrue(proto.connect());
        assertTrue(proto.hasConnection());
        proto.close();
    }

    @Test
    void getResult() throws SQLException {
        DatabaseConnectionProto proto = DatabaseConnectionProto.INSTANCE;
        proto.connect();
        assertNotNull(proto.executeQuery("select * from list;"));
        proto.close();
    }

    @Test
    void executeQuery() throws SQLException {
        DatabaseConnectionProto proto = DatabaseConnectionProto.INSTANCE;
        proto.connect();
        ResultSet resultSet = proto.executeQuery("select * from list;");
        assertNotNull(resultSet);
        assertTrue(resultSet.next());
        proto.close();
    }
}