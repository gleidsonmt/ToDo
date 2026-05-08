package io.github.gleidsonmt.todo.bd.mysql;

import io.github.gleidsonmt.todo.events.LoginEvent;
import io.github.gleidsonmt.todo.logger.AnsiColors;
import io.github.gleidsonmt.todo.model.User;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.concurrent.Task;
import javafx.event.Event;
import javafx.scene.Node;
import org.jspecify.annotations.NonNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;

/**
 *
 * @author Gleidson Neves da Silveira | <gleidisonmt@gmail.com>
 * Created on  27/04/2026
 */
public class Setup {


    public StringProperty message = new SimpleStringProperty();

    private final Node destiny;


    public Setup(Node destiny) {
        this.destiny = destiny;
    }

    public User start() {

        MySQLFolder folder = new MySQLFolder();

        CompletableFuture.supplyAsync(() -> {
                    // TAREFA 1 (Background)
                    MySQLMangerAssets mySqlManager = new MySQLMangerAssets();
                    message.bind(mySqlManager.messageProperty());
                    mySqlManager.run();

                    return mySqlManager;
                })
                .thenApply(result -> {
                    try {
                        message.unbind();
                        if (result.get()) {
                            message.set("Montando Banco de Dados..");
                            MySQLMountServer mySQLMountServer = new MySQLMountServer(folder);
                            mySQLMountServer.run();
                        }
                        Logger.getGlobal().config("Starting MySQL Server = " + result);
                        MySQLStartServer startService = new MySQLStartServer(folder);
                        startService.run();
                        return startService;
                    } catch (InterruptedException | ExecutionException e) {
                        throw new RuntimeException(e);
                    }
                })

                .thenAccept(result -> {
                    try {
                        Logger.getGlobal().info("Starting MySQL Connect = " + result.get());
                        message.set("Conectando ao Banco de Dados...");

                            try (BufferedReader reader = new BufferedReader(new InputStreamReader(result.get().getInputStream()))) {
                                String line;
                                while ((line = reader.readLine()) != null) {
                                    if (line.contains("mysqld.exe: ready for connections.")) {
                                        Logger.getGlobal().info("MySQL Server is ready");
                                        Event.fireEvent(destiny, new LoginEvent(LoginEvent.LOGIN));
                                    }
                                }
                            } catch (IOException | ExecutionException | InterruptedException e) {
                                throw new RuntimeException(e);
                            }

                    } catch (ExecutionException | InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                })
                .exceptionally(ex -> {
                    // Tratamento de erro para qualquer uma das etapas
                    Logger.getGlobal().severe("Error starting MySQL: " + ex.getMessage());
                    return null;
                });

        return null;
    }

    public StringProperty messageProperty() {
        return message;
    }
}