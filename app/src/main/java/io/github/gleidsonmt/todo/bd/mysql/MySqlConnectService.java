package io.github.gleidsonmt.todo.bd.mysql;

import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import javafx.util.Duration;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 *
 * @author Gleidson Neves da Silveira | <gleidisonmt@gmail.com>
 * Created on  29/04/2026
 */
@Deprecated
public class MySqlConnectService extends ScheduledService<Boolean> {


    public MySqlConnectService() {
        setPeriod(Duration.seconds(1));
    }

    @Override
    protected Task<Boolean> createTask() {
        return new Task<>() {
            @Override
            protected Boolean call() {
                if (isPortOpen()) {
                    return true;
                } else {
                    return false;
                }
            }
        };
    }


    private boolean isPortOpen() {
        try (Socket socket = new Socket()) {
            // Tenta conectar com um timeout de 1 segundo
            socket.connect(new InetSocketAddress("localhost", 3308), 1000);
            return true;
        } catch (IOException e) {
            return false; // Porta fechada ou banco offline
        }
    }

}
