package io.github.gleidsonmt.todo.bd.mysql;

import io.github.gleidsonmt.todo.bd.DatabaseConnection;
import javafx.concurrent.Task;

/**
 *
 * @author Gleidson Neves da Silveira | <gleidisonmt@gmail.com>
 * Created on  30/04/2026
 */
public class MySQLConnectTask extends Task<Boolean> {

    @Override
    protected Boolean call() throws Exception {
        DatabaseConnection connection = DatabaseConnection.INSTANCE;
        updateMessage("Trying to connect to database...");
        if (connection.hasConnection()) {
            return true;
        }

        System.out.println("Actually connecting to database...");
        updateMessage("Connecting to database failed...");
        return connection.connect();
    }
}
