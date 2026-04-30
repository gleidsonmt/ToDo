package io.github.gleidsonmt.todo.bd.mysql;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Logger;

import static java.io.File.separator;

/**
 *
 * @author Gleidson Neves da Silveira | <gleidisonmt@gmail.com>
 * Created on  29/04/2026
 */
public class MySQLStartService extends Service<Boolean> {

    private static final String APP_DIR = System.getProperty("user.home") + separator + "app";
    private static final String MYSQL_DIR = APP_DIR + separator + "db" + separator + "mysql-8.0.16-winx64";

    @Override
    protected Task<Boolean> createTask() {
        return new Task<>() {
            @Override
            protected Boolean call() {
                Path mysqld = Paths.get(MYSQL_DIR, "bin", "mysqld.exe");
                Logger.getGlobal().config("Teste passando ");

                ProcessBuilder pb = new ProcessBuilder(List.of( mysqld.toString(), "--console"));

                pb.directory(new File(MYSQL_DIR));
                pb.redirectErrorStream(true);
//                pb.inheritIO();

                Process process = null;
                try {
                    process = pb.start();
                } catch (IOException e) {
                    Thread.currentThread().interrupt();
                    throw new RuntimeException(e);
                }
                return process.isAlive();
            }
        };
    }


}
