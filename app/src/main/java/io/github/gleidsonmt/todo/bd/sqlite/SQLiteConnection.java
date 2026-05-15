package io.github.gleidsonmt.todo.bd.sqlite;

import io.github.gleidsonmt.todo.utils.Assets;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.logging.Logger;

/**
 *
 * @author Gleidson Neves da Silveira | <gleidisonmt@gmail.com>
 * Created on  14/05/2026
 */
public enum SQLiteConnection {
    INSTANCE;

    // URL com parâmetro que ativa Foreign Keys automaticamente em toda nova conexão
    private static final String DB_URL = "jdbc:sqlite:todo.db?foreign_keys=on";
    private static final String DB_FILE_PATH = "todo.db";

    private Connection connection;
    private Statement statement;
    private ResultSet result;

    public Connection getConnection() throws SQLException {
        File dbFile = new File(DB_FILE_PATH);
        System.out.println("dbFile = " + dbFile.delete());

        boolean isFirstRun = !dbFile.exists();

        connection = DriverManager.getConnection(DB_URL);

//        try (Statement st = connection.createStatement()) {
//            st.execute("PRAGMA foreign_keys = ON");
//        }

        if (isFirstRun) {
            System.out.println("Banco de dados não encontrado. Iniciando configuração inicial...");
            createDatabase(connection);
        }

        return connection;
    }

    private void createDatabase(Connection conn) throws SQLException {

        URL url = Assets.getResource("sql/schema.sql");

        if (url == null) {
            throw new SQLException("Arquivo SQL não encontrado: sql/schema.sql");
        }

        try (Statement _ = conn.createStatement()) {

            String sqlScript = new String(Files.readAllBytes(Paths.get(url.toURI())));
            executeSqlScript(conn, sqlScript);
        } catch (URISyntaxException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void executeSqlScript(Connection conn, String sqlScript) throws SQLException {
        String[] commands = sqlScript.split(";");

        try (Statement stmt = conn.createStatement()) {
            for (String command : commands) {
                System.out.println("command = " + command);
                String sql = command.trim();

                if (!sql.isEmpty()) {
                    System.out.println("sql = " + sql);
                    stmt.execute(sql);
                }
            }
        }
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
            Logger.getGlobal().severe("Error on updating. SQL { " + SQL + " }");
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
            Logger.getGlobal().severe("Error on executing query. { " + SQL + " }");
        }
        return null;
    }

    @Contract(pure = true)
    public boolean hasConnection() {
        try {
            return connection != null && !getConnection().isClosed();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @ApiStatus.Experimental
    public boolean close() {
        if (!hasConnection()) return true;
        try {
            if (this.result != null && this.statement != null) {
                this.result.close();
                this.statement.close();
            }
            connection.close();
            Logger.getGlobal().config("Database connection has closed. ");
            return true;
        } catch (SQLException ex) {
            Logger.getGlobal().severe("Error on closing database connection. ");
            return false;
        }
    }
}
