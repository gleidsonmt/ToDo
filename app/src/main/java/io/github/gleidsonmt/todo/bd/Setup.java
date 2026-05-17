package io.github.gleidsonmt.todo.bd;

import io.github.gleidsonmt.todo.bd.sqlite.SQLiteConnection;
import io.github.gleidsonmt.todo.events.DialogEvent;
import io.github.gleidsonmt.todo.events.LoginEvent;
import io.github.gleidsonmt.todo.global.Global;
import io.github.gleidsonmt.todo.model.User;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.Event;
import javafx.scene.Node;

import java.io.*;
import java.sql.SQLException;
import java.util.concurrent.CompletableFuture;
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
        CompletableFuture.supplyAsync(() -> {

                    try {

                        Logger.getGlobal().info("Starting Loader..");
                        SQLiteConnection database = SQLiteConnection.INSTANCE;
                        database.connect();
                        Logger.getGlobal().info("Iniciando o banco de dados...");
                        updateMessage("Iniciando o banco de dados..." + (database.hasConnection() ? " [ OK ] " : " [ FAILED ]"));
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    return null;
                }).thenApply(_ -> {
                    if (Global.isRunningOnExecutable()) {
                        updateMessage("Criando arquivo de log..");

                        PrintStream out;
                        try {
                            out = new PrintStream(new FileOutputStream("log.txt"));
                        } catch (FileNotFoundException e) {
                            throw new RuntimeException(e);
                        }
                        System.setOut(out);
                        System.setErr(out);

                        updateMessage("Criando registro no windows..");
                        String path = getExecutable().toString();

                        String regCommand = "reg add \"HKCU\\Software\\Microsoft\\Windows\\CurrentVersion\\Run\" /v \"JavaFx ToDo\" /t REG_SZ /d \"\\\""
                                            + path + "\\\" --background\" /f";
                        updateMessage("Executando comando: " + regCommand);

                        try {
                            ProcessBuilder pb = new ProcessBuilder("cmd", "/c", regCommand);
                            Process pro = pb.start();
                            pro.waitFor();
                            updateMessage("Registro no windows.. " + (pro.exitValue() == 0 ? " [ OK ] " : " [ FAILED ]"));
                        } catch (IOException e) {
                            fireError("Erro!", "Erro ao registrar serviço. \n", e);
                            return false;
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    return null;
                }).thenAccept(_ -> Event.fireEvent(destiny, new LoginEvent(LoginEvent.LOGIN)))
                .exceptionally(e -> {
                            fireError("Error!", "Error on setting app.", (Exception) e);
                            throw new RuntimeException(e);
                        }
                );
        return null;
    }

    /**
     * Show an error dialog on the javafx UI thread. (In this case in LoaderView).
     *
     * @param title   The error title.
     * @param message The error message.
     * @param e       The exception.
     */
    private void fireError(String title, String message, Exception e) {
        Logger.getGlobal().severe(message + e.getMessage());
        Platform.runLater(() -> Event.fireEvent(destiny, new DialogEvent(DialogEvent.DIALOG_ERROR, title, message + "\n" + e)));
    }

    private void updateMessage(String msg) {
        Platform.runLater(() -> message.set(msg));
        System.out.println(msg);
    }

    private File getExecutable() {
        return new File(new File(System.getProperty("user.dir")), "JavaFx ToDo.exe");
    }

    public StringProperty messageProperty() {
        return message;
    }
}