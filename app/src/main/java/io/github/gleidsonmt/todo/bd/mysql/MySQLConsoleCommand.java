package io.github.gleidsonmt.todo.bd.mysql;

import org.jspecify.annotations.NonNull;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static java.io.File.separator;

/**
 *
 * @author Gleidson Neves da Silveira | <gleidisonmt@gmail.com>
 * Created on  29/04/2026
 */
public class MySQLConsoleCommand {

    private static final String APP_DIR = System.getProperty("user.home") + separator + "app";
    private static final String DEFAULT_DIR = System.getProperty("user.dir");
    private static final String MYSQL_DIR = APP_DIR + separator + "db" + separator + "mysql-8.0.16-winx64";

    public void mountServer() {
        Path mysqld = Paths.get(MYSQL_DIR, "bin", "mysqld.exe");
        Path ini = Paths.get(MYSQL_DIR, "my.ini");

        Path path = Path.of(MYSQL_DIR, "data");

        File file = new File(path.toAbsolutePath().toString());
        cleanData(file);


        if (!Files.exists(mysqld)) {
            throw new IllegalStateException("mysqld.exe não encontrado em: " + mysqld);
        }
        if (!Files.exists(ini)) {
            throw new IllegalStateException("my.ini não encontrado em: " + ini);
        }

        execute(
                mysqld.toString(),
                "--defaults-file=" + ini,
                "--initialize-insecure",
                "--console"
        );
    }

    public void cleanData(@NonNull File directory) {
        if (directory.exists()) {
            File[] arquivos = directory.listFiles();
            if (arquivos != null) {
                for (File f : arquivos) {
                    if (f.isDirectory()) cleanData(f);
                    else f.delete();
                }
            }
            directory.delete(); // Agora que está vazia, ela morre
        }
    }

    public void execute(String... commands) {
        ProcessBuilder pb = new ProcessBuilder(List.of(commands));

        pb.directory(new File(MYSQL_DIR));
        pb.redirectErrorStream(true);
//
        try {
            Process p = pb.start();
            pb.inheritIO();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println("[mysqld] " + line);
                }
            }

            int exitCode = p.waitFor();
            if (exitCode != 0) {
                throw new RuntimeException("Erro ao executar comando MySQL. ExitCode=" + exitCode);
            }

        } catch (IOException | InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Erro ao executar mysqld", e);
        }
    }
}
