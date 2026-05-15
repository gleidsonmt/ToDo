package io.github.gleidsonmt.todo.bd.mysql;

import io.github.gleidsonmt.todo.logger.AnsiColors;
import javafx.concurrent.Task;
import org.jspecify.annotations.NonNull;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
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
@Deprecated
public class MySQLMountServer extends Task<Process> {

    private final MySQLFolder folder;

    public MySQLMountServer(MySQLFolder folder) {
        this.folder = folder;
    }

    @Override
    protected Process call() {
        Path mysqld = Paths.get(folder.getDBFolder(), "bin", "mysqld.exe");
        Path iniFile = Paths.get(folder.getDBFolder(), "my.ini");

        if (!clean()) {
            Logger.getGlobal().severe("Error on clean data.");
            throw new IllegalStateException("Error on clean data.");
        }

        if (!Files.exists(mysqld)) {
            Logger.getGlobal().severe("mysqld.exe not found.");
            throw new IllegalStateException("mysqld.exe  not found " + mysqld);
        }
        if (!Files.exists(iniFile)) {
            Logger.getGlobal().severe("my.ini not found.");
            throw new IllegalStateException("my.ini not found: " + iniFile);
        }

       return execute(
                mysqld.toString(),
                "--defaults-file=" + iniFile,
                "--initialize-insecure",
                "--console"
        );
    }

    public boolean clean() {

        Path path = Path.of(folder.getDBFolder(), "data");

        File file = new File(path.toAbsolutePath().toString());
        return cleanData(file);
    }

    private boolean cleanData(@NonNull File directory) {
        if (directory.exists()) {
            File[] arquivos = directory.listFiles();
            if (arquivos != null) {
                for (File f : arquivos) {
                    if (f.isDirectory()) cleanData(f);
                    else return f.delete();
                }
            }
            return directory.delete(); // Agora que está vazia, ela morre
        }
        return true;
    }

    public Process execute(String... commands) {
        ProcessBuilder pb = new ProcessBuilder(List.of(commands));

        pb.directory(new File(folder.getDBFolder()));
        pb.redirectErrorStream(true);
//        pb.inheritIO();

        try {
            Process p = pb.start();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    Logger.getGlobal().info(line);

                }
            }
            int exitCode = p.waitFor();
            if (exitCode != 0) {
                throw new RuntimeException("Error on execute MySQL command . ExitCode=" + exitCode);
            }
            return p;
        } catch (IOException | InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Error on execute mysqld", e);
        }
    }
}
