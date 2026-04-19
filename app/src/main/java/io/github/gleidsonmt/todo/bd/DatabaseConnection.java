package io.github.gleidsonmt.todo.bd;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jetbrains.annotations.ApiStatus;

import io.github.gleidsonmt.todo.App;

/**
 * @author Gleidson Neves da Silveira | <gleidisonmt@gmail.com>
 * Created On: Feb 22, 2026
 * <p>
 * Version History: Initial version
 */
public class DatabaseConnection {

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

    private static final Logger logger = Logger.getGlobal();
    private String msg;

    public DatabaseConnection() {
        Properties properties = new Properties();
        try {
            // Loading properties
            InputStream file = App.class.getResourceAsStream("properties/db.properties");

            if (file == null) {
                logger.severe("[ ERROR ] => [DatabaseConnection, method=Constructor] => File properties/db.properties not found.");
                throw new RuntimeException("File properties/db.properties not found.");
            }

            properties.load(file);

            if (properties.isEmpty()) {
                logger.severe("[ ERROR ] => [DatabaseConnection, method=Constructor] => File propertis/db.properties is empty.");
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
            logger.config("[ OK ] => [DatabaseConnection, method=Constructor] => Loaded database properties. ");
        } catch (IOException e) {
            logger.severe("[ ERROR ] => [DatabaseConnection, method=Constructor] => Some of the properties are missing or invalid.");
            throw new RuntimeException(e);
        }
    }

    public boolean connect() {
        try {
            System.setProperty("jdbc.Driver", driver);
            Class.forName(driver).getDeclaredConstructor().newInstance();
            connection = DriverManager.getConnection(url, user, password);
            if (connection != null) {
                logger.config("[ SUCCEED ] => [DatabaseConnection, method=connect] => Created connection with database. ");
                return true;
            }
            return false;
        } catch (IllegalAccessException | InstantiationException | ClassNotFoundException | SQLException
                 | InvocationTargetException | NoSuchMethodException e) {
            logger.severe(
                    "[ ERROR ] => [DatabaseConnection, method=connect] => Some of the properties are missing or invalid.\n" +
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

    public boolean hasConnection() {
        try {
            return !connection.isClosed();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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
            logger.severe("[ ERROR ] => [DatabaseConnection, method=executeQuery] => Error on executing query. " + SQL);
        }
        return null;
    }

    /**
     * The method used for all types of SQL statements
     * If the method returns TRUE, return the ResultSet object and FALSE
     * returns the int on.
     *
     * @param SQL The SQL to execute.
     * @return The result of the query.
     */
    public boolean executeUpdate(String SQL) {
        try {
            this.statement = getConnection().createStatement();
            this.statement.executeUpdate(SQL);
            return true;
        } catch (SQLException ex) {
            this.msg = "Error on executing update using `" + SQL + "`" + ex;
            logger.log(Level.SEVERE, msg);
        }
        return false;
    }

    @ApiStatus.Experimental
    public int getLastID() {
        int status = 0;

        try {
            this.result = this.statement.executeQuery("SELECT LAST_INSERT_ID();");
            while (this.result.next()) {
                status = this.result.getInt(1);
            }
        } catch (SQLException e) {
            this.msg = e.getMessage();
            logger.log(Level.SEVERE, msg);
            throw new RuntimeException(e);
        }

        return status;
    }

    /**
     * Close the database connection.
     * @return If the connection was closed successfully.
     */
    public boolean close() {
        try {
            if ((this.getResult() != null) && (this.statement != null)) {
                this.getResult().close();
                this.statement.close();
            }
            this.getConnection().close();
            connection.close();
            logger.config("[ OK ] => [DatabaseConnection, method=close]  => Database connection has closed. ");
            return true;
        } catch (SQLException ex) {
            logger.severe("[ ERROR ] => [DatabaseConnection, method=close]  => Error on closing database connection. ");
            return false;
        }
    }

    public ResultSet getResult() {
        return result;
    }

    public Connection getConnection() {
        return connection;
    }

    public String getDatabase() {
        return database;
    }

    public String getErrorMessage() {
        return this.msg;
    }

}
