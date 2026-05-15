package io.github.gleidsonmt.todo.bd.mysql;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 *
 * @author Gleidson Neves da Silveira | <gleidisonmt@gmail.com>
 * Created on  03/05/2026
 */
@Deprecated
public class MySQLStartServiceTest extends Service<Process> {

    private MySQLFolder folder;

    public MySQLStartServiceTest(MySQLFolder folder) {
        this.folder = folder;
    }

    @Override
    protected Task<Process> createTask() {
        return new Task<Process>() {
            @Override
            protected Process call() {
                Path mysqld = Paths.get(folder.getDBFolder(), "bin", "mysqld.exe");
//
                ProcessBuilder pb = new ProcessBuilder(List.of(mysqld.toString(), "--console"));
//
                pb.directory(new File(folder.getDBFolder()));
                pb.redirectErrorStream(true);
                pb.inheritIO();
//
                Process process = null;
                try {
                    process = pb.start();
                    System.out.println("process.info() = " + process.info());
                    System.out.println("process.isAlive() = " + process.info().arguments());
                    System.out.println("process.isAlive() = " + process.info().command());
                    System.out.println("process.isAlive() = " + process.info().user());
                    System.out.println("process.isAlive() = " + process.info().commandLine());
                    return process;
                } catch (IOException e) {
                    Thread.currentThread().interrupt();
                    throw new RuntimeException(e);
                }
//                return process;
            }
        };
    }
}
