package io.github.gleidsonmt.todo.bd;

import io.github.gleidsonmt.todo.App;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.Properties;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Gleidson Neves da Silveira | <gleidisonmt@gmail.com>
 * Created on  20/04/2026
 */
@SuppressWarnings("unused")
public enum DatabaseConnectionProto {

    INSTANCE;

    private Connection connection;
    private Statement statement;
    private ResultSet result;

    private String driver;
    private String user;
    private String password;
    private String url;
    private String database;
    private int port;
    private String host;

    private final Logger logger = Logger.getGlobal();

    DatabaseConnectionProto() {
        Properties properties = new Properties();
        try {
            // Loading properties
            InputStream file = App.class.getResourceAsStream("properties/db.properties");

            if (file == null) {
                Logger.getGlobal().severe("Loading database properties... [FAILED]");
                return;
            }

            properties.load(file);

            if (properties.isEmpty()) {
                logger.severe("[DatabaseConnection, method=Constructor]  ERROR => Loading database properties");
                throw new RuntimeException("Loading database properties");
            }

            this.driver = properties.get("driver").toString();
            database = properties.get("database").toString();
            port = Integer.parseInt(properties.get("port").toString()); // port-number

            host = properties.get("host") + ":" + port; // ex. localhost:3306
            this.user = properties.get("user").toString();
            this.password = properties.get("password").toString();

            String timeZone = String.valueOf(TimeZone.getDefault().toZoneId());
            this.url = "jdbc:mysql://" + host + "/" + database
                       + "?useUnicode=true&allowPublicKeyRetrieval=true&useSSL=false&characterEncoding=utf8&serverTimezone="
                       + timeZone;
            logger.config("[ OK ] => [DatabaseConnection, method=Constructor] SUCCESSFULLY => Loaded database properties. ");
        } catch (IOException e) {
            logger.severe("[DatabaseConnection, method=Constructor]  ERROR => Some of the properties are missing or invalid.");
            throw new RuntimeException(e);
        }
    }

    @Contract(pure = true)
    public boolean hasConnection() {
        return connection != null;
    }

    public boolean connect() {
        try {
            System.setProperty("jdbc.Driver", driver);
            Class.forName(driver).getDeclaredConstructor().newInstance();
            connection = DriverManager.getConnection(url, user, password);
            return connection != null;
        } catch (IllegalAccessException | InstantiationException | ClassNotFoundException | SQLException
                 | InvocationTargetException | NoSuchMethodException e) {
            logger.severe(
                    "[ OK ] => [DatabaseConnection, method=connect] ERROR => Some of the properties are missing or invalid.\n" +
                    "DatabaseConnection {\n" +
                    "   driver = '" + driver + "',\n" +
                    "   host = '" + host + "',\n" +
                    "   port = '" + port + "',\n" +
                    "   user = '" + user + "',\n" +
                    "   password = '" + user + "',\n" +
                    "}\n"
            );
        }
        return false;
    }

    /**
     * This method is used to retrieve data from a database using SELECT query.
     * This method returns the ResultSet object that returns the data according
     * to the query.
     *
     * @param SQL The SQL query.
     */
    public ResultSet executeQuery(String SQL) throws SQLException {
        try {
            this.statement = getConnection().createStatement();
            this.result = this.statement.executeQuery(SQL);
            return this.result;
        } catch (SQLException ex) {
//            msg = "Error on executing query. `" + SQL + "`" + ex;
//            logger.log(Level.SEVERE, msg);
        }
        return null;
    }

    @ApiStatus.Experimental
    public void close() {
        try {
            if ((this.getResult() != null) && (this.statement != null)) {
                this.getResult().close();
                this.statement.close();
            }
            this.getConnection().close();
            connection.close();
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, () -> "Error on closing database connection. " + ex);
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public ResultSet getResult() {
        return result;
    }
}
