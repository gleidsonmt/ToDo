package io.github.gleidsonmt.todo.bd.mysql;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Gleidson Neves da Silveira | <gleidisonmt@gmail.com>
 * Created on  29/04/2026
 */
public class MySQLFolder {

    private static final String APP_DIR = System.getProperty("user.home") + File.separator + "app";
    private static final String DEFAULT_DIR = System.getProperty("user.dir");
    private String MYSQL_BASE_DIR = APP_DIR + File.separator + "db" + File.separator + "mysql-8.0.16-winx64";

    public MySQLFolder() {
    }

    private Path resolveDbSource() {
        List<Path> candidates = List.of(
                Paths.get(DEFAULT_DIR, "app", "db"),
                Paths.get(DEFAULT_DIR, "db"),
                Paths.get("app", "db"),
                Paths.get("db")
        );

        for (Path candidate : candidates) {
            if (Files.exists(candidate) && Files.isDirectory(candidate)) {
                return candidate;
            }
        }
        return null;
    }

    public boolean exists() {
        return Files.exists(Paths.get(APP_DIR, "db"));
    }

    public void create() {
        Path destiny = Paths.get(APP_DIR, "db");
        Logger.getGlobal().info("Starting to create database folder...");

        try {
            Files.createDirectories(destiny);
            Path origin = resolveDbSource();
            if (origin == null) {

                Logger.getGlobal().severe("Error on finding database folder:\n" +
                                          Paths.get(APP_DIR, "app", "db") + ", " +
                                          destiny + ", " +
                                          Paths.get("app", "db") + ", " +
                                          Paths.get("db"));
                return;

            }
            Logger.getGlobal().finest("Copying files...");

            try (var stream = Files.walk(origin)) {
                stream.forEach(source -> {

                    Path target = destiny.resolve(origin.relativize(source));
                    try {
                        if (Files.isDirectory(source)) {
                            Files.createDirectories(target);
                        } else {
                            Files.createDirectories(target.getParent());
                            Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
                        }
                    } catch (IOException e) {
                        Logger.getGlobal().log(Level.SEVERE, "Error on copying " + source + " to " + target, e);
                        throw new RuntimeException("Error on copying" + source + " to " + target, e);
                    }
                });
            } catch (IOException e) {
                Logger.getGlobal().severe("Error on fill database directory:" + e);
                throw new RuntimeException("Error on filling database directory:" + e);
            }
        } catch (IOException e) {
            Logger.getGlobal().severe("Error on filling database folder:" + e);
            throw new RuntimeException("Error on filling database folder:", e);
        }
    }


}
