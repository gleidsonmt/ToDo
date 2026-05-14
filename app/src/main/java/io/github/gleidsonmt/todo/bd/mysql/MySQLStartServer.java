package io.github.gleidsonmt.todo.bd.mysql;

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
public class MySQLStartServer extends Task<Process> {

    private final MySQLFolder folder;

    public MySQLStartServer(MySQLFolder folder) {
        this.folder = folder;
    }

    public boolean isProcessRunning(String processName) {
        return ProcessHandle.allProcesses()
                .map(ProcessHandle::info)
                .anyMatch(info -> info.command()
                        .map(cmd -> cmd.contains(processName))
                        .orElse(false));
    }

    @Override
    protected Process call() throws Exception {

        Path mysqld = Paths.get(folder.getDBFolder(), "bin", "mysqld.exe");

        ProcessBuilder pb = new ProcessBuilder(List.of(mysqld.toString(), "--console"));

        pb.directory(new File(folder.getDBFolder()));
        pb.redirectErrorStream(true);
//        pb.inheritIO();
//
        Process process;
        try {
            process = pb.start();
            return process;
        } catch (IOException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
    }
}
