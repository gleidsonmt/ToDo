package io.github.gleidsonmt.todo.bd.mysql;

import io.github.gleidsonmt.todo.App;
import io.github.gleidsonmt.todo.bd.sqlite.SQLiteConnection;
import io.github.gleidsonmt.todo.events.DialogEvent;
import io.github.gleidsonmt.todo.events.LoginEvent;
import io.github.gleidsonmt.todo.global.Global;
import io.github.gleidsonmt.todo.global.ListPresenter;
import io.github.gleidsonmt.todo.global.TaskPresenter;
import io.github.gleidsonmt.todo.model.List;
import io.github.gleidsonmt.todo.model.ToDoTask;
import io.github.gleidsonmt.todo.model.User;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.Event;
import javafx.scene.Node;

import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;
import java.util.prefs.Preferences;

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
        Preferences prefs = Preferences.userNodeForPackage(Setup.class);

//        MySQLFolder folder = new MySQLFolder();


        CompletableFuture.supplyAsync(() -> {
            try {
                Logger.getGlobal().info("Starting Loader..");
                SQLiteConnection connection = SQLiteConnection.INSTANCE;
                Logger.getGlobal().info("Iniciando o banco de dados..." + connection.getConnection());
                updateMessage("Iniciando o banco de dados..." + (connection.hasConnection() ? " [ OK ] " : " [ FAILED ]"));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return null;
        }).thenApply(result -> {
            return null;
        }).exceptionally(e -> {
                    // Tratamento de erro para qualquer uma das etapas
                    fireError("Error!", "Error on setting app.", (Exception) e);
                    throw new RuntimeException(e);
                }
        );

//        // These steps configure or just run the app
//        CompletableFuture.supplyAsync(() -> {
//                    // Tarefa 1 verifica os assets do banco de dados ou cria eles
//                    // if is executing as .exe create a logger file.
//
//                    PrintStream out;
//                    try {
//                        out = new PrintStream(new FileOutputStream("log.txt"));
//                    } catch (FileNotFoundException e) {
//                        throw new RuntimeException(e);
//                    }
//                    System.setOut(out);
//                    System.setErr(out);
//
//                    updateMessage("Criando arquivo de log..");
//
//                    if (Global.isRunningOnExecutable()) {
//
//                        updateMessage("Criando registro no windows..");
//                        String path = getExecutable().toString();
//
//                        String regCommand = "reg add \"HKCU\\Software\\Microsoft\\Windows\\CurrentVersion\\Run\" /v \"JavaFx ToDo\" /t REG_SZ /d \"\\\""
//                                            + path + "\\\" --background\" /f";
//                        updateMessage("Executando comando: " + regCommand);
//
//                        try {
//                            ProcessBuilder pb = new ProcessBuilder("cmd", "/c", regCommand);
//                            Process pro = pb.start();
//                            pro.waitFor();
//                            updateMessage("Registro no windows " + (pro.exitValue() == 0 ? "realizado com sucesso!" : "falhou!"));
//                        } catch (IOException e) {
//                            fireError("Erro!", "Erro ao registrar serviço. \n", e);
//                            return false;
//                        } catch (InterruptedException e) {
//                            throw new RuntimeException(e);
//                        }
//                    }
//                    return true;
//                })
//                .thenApply(_ -> {
//
//                    updateMessage("Validando arquivos..");
//                    MySQLAssets assets = new MySQLAssets();
//                    assets.validateFilesAndDirectories();
//
//                    return assets.filesAlreadyExist();
//                })
//                .thenApply(result -> {
//
//                    updateMessage("Arquivos " + (result ? "validados" : " Criados ") + " com sucesso!");
//
//                    if (!result) {
//                        updateMessage("Montando servidor MySQL...");
//                        MySQLMountServer mySQLMountServer = new MySQLMountServer(folder);
//                        mySQLMountServer.run();
//                    } else {
//                        updateMessage("Servidor MySQL pronto..");
//                    }
//                    return null;
//                })
//                .thenApply(_ -> {
//                    MySQLStartServer startService = new MySQLStartServer(folder);
//                    startService.run();
//                    return startService;
//                })
//                .thenAccept(result -> {
//
//                    updateMessage("Conectando ao Banco de Dados...");
//
//                    try (BufferedReader reader = new BufferedReader(new InputStreamReader(result.get().getInputStream()))) {
//                        String line;
//                        while ((line = reader.readLine()) != null) {
//                            if (line.contains("[ERROR]")) {
//                                fireError("Erro!", "Erro ao iniciar banco de dados.", new Exception(line));
//                                return;
//                            }
//                            if (line.contains("mysqld.exe: ready for connections.")) {
//                                Logger.getGlobal().info("MySQL Server is ready");
//                                Event.fireEvent(destiny, new LoginEvent(LoginEvent.LOGIN));
//                            }
//                        }
//                    } catch (IOException | ExecutionException | InterruptedException e) {
//                        throw new RuntimeException(e);
//                    }
//                })
//                .exceptionally(e -> {
//                    // Tratamento de erro para qualquer uma das etapas
//                    Logger.getGlobal().severe("Error starting MySQL: " + e.getMessage());
//                    fireError("Error!", "Error on setting app.", (Exception) e);
//                    throw new RuntimeException(e);
//                });
//
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