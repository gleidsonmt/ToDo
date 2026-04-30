package io.github.gleidsonmt.todo.bd.mysql;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Logger;

/**
 *
 * @author Gleidson Neves da Silveira | <gleidisonmt@gmail.com>
 * Created on  29/04/2026
 */
public class MySQLManagerService extends Service<Void> {

    private static final String APP_DIR = System.getProperty("user.home") + File.separator + "app";
    private final String MYSQL_BASE_DIR = APP_DIR + File.separator + "db" + File.separator + "mysql-8.0.16-winx64";


    @Override
    protected Task<Void> createTask() {
        return new Task<>() {
            @Override
            protected Void call() {

                MySQLFolder mySQLFolder = new MySQLFolder();

                System.out.println("mySQLFolder = " + mySQLFolder.exists());

                if (!mySQLFolder.exists()) {
                    mySQLFolder.create();
                    updateMessage("Criando pasta de banco de dados...");

                    MySQLFile mySQLFile = new MySQLFile();
                    mySQLFile.createInitFile();
                    mySQLFile.createMyIniFile();
                    updateMessage("Criando arquivos de configuração...");

                    MySQLConsoleCommand mySQLConsoleCommand = new MySQLConsoleCommand();
                    mySQLConsoleCommand.mountServer();
                    updateMessage("Criando servidor MySQL...");
                }

                System.out.println("toma no cu");


//                 3. Iniciar o Servidor em Thread separada
//                updateMessage("Iniciando servidor MySQL...");
//                startMySQlServer();
//                updateProgress(80, 100);
                return null;
            }
        };
    }

    private void startMySQlServer() {
        Path mysqld = Paths.get(MYSQL_BASE_DIR, "bin", "mysqld.exe");
        Logger.getGlobal().config("Teste passando ");

//        ProcessBuilder pb = new ProcessBuilder(List.of( mysqld.toString(), "--console"));
        ProcessBuilder pb = new ProcessBuilder(List.of(mysqld.toString()));

        pb.directory(new File(MYSQL_BASE_DIR));
        pb.redirectErrorStream(true);
        pb.inheritIO();

        Process process = null;
        try {
            process = pb.start();

        } catch (IOException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
    }
}
