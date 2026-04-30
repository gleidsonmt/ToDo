package io.github.gleidsonmt.todo.bd.mysql;

import io.github.gleidsonmt.todo.global.Folder;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Gleidson Neves da Silveira | <gleidisonmt@gmail.com>
 * Created on  28/04/2026
 */
public class MySQLCommandExecutorOld {

    private Folder folder;

    public MySQLCommandExecutorOld(Folder folder) {
        this.folder = folder;
    }

    public Runnable createEngine() {
        return () -> {
            Path mysqld = Paths.get(folder.getMysqlBaseDir(), "bin", "mysqld.exe");
            Path ini = Paths.get(folder.getMysqlBaseDir(), "my.ini");

            cleanData(folder);

            if (!Files.exists(mysqld)) {
                throw new IllegalStateException("mysqld.exe não encontrado em: " + mysqld);
            }
            if (!Files.exists(ini)) {
                throw new IllegalStateException("my.ini não encontrado em: " + ini);
            }

            Logger.getGlobal().info("Starting to create database engine...");

            execute(
                    mysqld.toString(),
                    "--defaults-file=" + ini,
                    "--initialize-insecure",
                    "--console"
            );
        };

//        execute(folder, "mysqld", "--console");
    }

    public Runnable startEngine2()  {
        return () -> {
            Path mysqld = Paths.get(folder.getMysqlBaseDir(), "bin", "mysqld.exe");
            var process = createProcess(mysqld.toString(), "--console");
            printProcess(process);
        };
    };

    public Service<Boolean> startEngine() {

        return new Service<>() {
            @Override
            protected Task<Boolean> createTask() {
                return new Task<>() {
                    @Override
                    protected Boolean call() {
                        Path mysqld = Paths.get(folder.getMysqlBaseDir(), "bin", "mysqld.exe");
                        Logger.getGlobal().config("Teste passando ");

                        ProcessBuilder pb = new ProcessBuilder(List.of( mysqld.toString(), "--console"));

                        pb.directory(new File(folder.getMysqlBaseDir()));
                        pb.redirectErrorStream(true);
                        pb.inheritIO();

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
        };
    }

    private void cleanAll(File file) {
        File[] arquivos = file.listFiles();
        if (arquivos != null) {
            for (File f : arquivos) {
                if (f.isDirectory()) cleanAll(f);
                else f.delete();
            }
        }
        file.delete();
    }

    private void cleanData(Folder folder) {
        Path path = Path.of(folder.getMysqlBaseDir(), "data");
        File file = new File(path.toAbsolutePath().toString());
        cleanAll(file);
    }

    private synchronized Process createProcess(String... commands) {
        ProcessBuilder pb = new ProcessBuilder(List.of(commands));

        pb.directory(new File(folder.getMysqlBaseDir()));
        pb.redirectErrorStream(true);

        Process process = null;
        try {
            process = pb.start();
            pb.inheritIO();
        } catch (IOException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
        return process;
    }

    ;

    private void printProcess(Process process) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains("[Warning]")) {
                    Logger.getGlobal().log(Level.WARNING, line);
                } else {
                    Logger.getGlobal().config(line);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Erro ao executar mysqld", e);
        }
    }

    public void execute(String... commands) {
        ProcessBuilder pb = new ProcessBuilder(List.of(commands));

        pb.directory(new File(folder.getMysqlBaseDir()));
        pb.redirectErrorStream(true);
//
        try {
            Process p = pb.start();
            pb.inheritIO();

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.contains("[Warning]")) {
                        Logger.getGlobal().log(Level.WARNING, line);
                    } else {
                        Logger.getGlobal().config(line);
                    }
                }
            }

            int exitCode = p.waitFor();
            if (exitCode != 0) {
                throw new RuntimeException("Erro ao executar comando MySQL. ExitCode=" + exitCode);
            }

            System.out.println("exitCode = " + exitCode);
        } catch (IOException | InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Erro ao executar mysqld", e);
        }
    }
}