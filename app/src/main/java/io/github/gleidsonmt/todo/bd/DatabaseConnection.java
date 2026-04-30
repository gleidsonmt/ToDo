package io.github.gleidsonmt.todo.bd;

import io.github.gleidsonmt.todo.bd.mysql.Setup;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;

import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.Properties;
import java.util.TimeZone;
import java.util.logging.Logger;

/**
 *
 * @author Gleidson Neves da Silveira | <gleidisonmt@gmail.com>
 * Created on  20/04/2026
 */
@SuppressWarnings("unused")
public enum DatabaseConnection {

    INSTANCE;

    private Connection connection;
    private Statement statement;
    private ResultSet result;

    private final String driver;
    private final String user;
    private final String password;
    private final String url;
    private final int port;
    private final String host;

    private final Logger logger = Logger.getGlobal();

    DatabaseConnection() {
        Properties properties = Setup.getDatabaseProperties();

        this.driver = properties.get("driver").toString();
        String database = properties.get("database").toString();
        port = Integer.parseInt(properties.get("port").toString()); // port-number

        host = properties.get("host") + ":" + port; // ex. localhost:3306
        this.user = properties.get("user").toString();
        this.password = properties.get("password").toString();

        String timeZone = String.valueOf(TimeZone.getDefault().toZoneId());
        this.url = "jdbc:mysql://" + host + "/" + database
                   + "?useUnicode=true&allowPublicKeyRetrieval=true&useSSL=false&characterEncoding=utf8&serverTimezone="
                   + timeZone;

    }


    public boolean connect() {
        try {
            logger.config("Connecting to database...");
            System.setProperty("jdbc.Driver", driver);
            Class.forName(driver).getDeclaredConstructor().newInstance();
            connection = DriverManager.getConnection(url, user, password);
            if (connection != null) {
                logger.config("Created connection with database");
                return true;
            }
        } catch (IllegalAccessException | InstantiationException | ClassNotFoundException | SQLException
                 | InvocationTargetException | NoSuchMethodException e) {

            if (e instanceof SQLException) {
                logger.warning("Failed to connect with db.");
            } else {
                Logger.getGlobal().severe("Error on connecting to database, some of the properties are missing or invalid:" + e.getMessage() );
            }
        }
        return false;
    }

    @Contract(pure = true)
    public boolean hasConnection() {
        try {
            return connection != null && !getConnection().isClosed();
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
            logger.severe("Error on executing query. { " + SQL + " }");
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
            logger.severe("Error on updating. SQL { " + SQL + " }");
        }
        return false;
    }

    @ApiStatus.Experimental
    public boolean close() {
        if (!hasConnection()) return true;
        try {
            if ((this.getResult() != null) && (this.statement != null)) {
                this.getResult().close();
                this.statement.close();
            }
            this.getConnection().close();
            connection.close();
            logger.config("Database connection has closed. ");
            return true;
        } catch (SQLException ex) {
            logger.severe("Error on closing database connection. ");
            return false;
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public ResultSet getResult() {
        return result;
    }
}
