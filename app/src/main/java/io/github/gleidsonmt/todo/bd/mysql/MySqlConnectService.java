package io.github.gleidsonmt.todo.bd.mysql;

import io.github.gleidsonmt.todo.bd.DatabaseConnection;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import javafx.util.Duration;

/**
 *
 * @author Gleidson Neves da Silveira | <gleidisonmt@gmail.com>
 * Created on  29/04/2026
 */
public class MySqlConnectService extends ScheduledService<Void> {

    private boolean connected = false;

    public MySqlConnectService() {
        setPeriod(Duration.seconds(1));
    }

    @Override
    protected Task<Void> createTask() {
        return new Task<>() {
            @Override
            protected Void call() {
                DatabaseConnection connection = DatabaseConnection.INSTANCE;
                if (!connection.hasConnection()) {
                    connection.connect();
                    connected = false;
                    updateMessage("Trying to connect to database...");
                } else {
                    connected = true;
                    setPeriod(Duration.seconds(20));
                    updateMessage("Connected to database.");
                }
                return null;
            }
        };
    }

    public boolean isConnected() {
        return connected;
    }
}
