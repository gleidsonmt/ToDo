

package io.github.gleidsonmt.todo.bd.mysql;

import org.jetbrains.annotations.ApiStatus;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

/**
 *
 * @author Gleidson Neves da Silveira | <a href="mailto:gleidisonmt@gmail.com">gleidisonmt@gmail.com</a> <br>
 * Created on  29/04/2026
 */
@Deprecated
public class MySQLFolder {

    private final String APP_DIR = System.getProperty("user.home") + File.separator + "todo" + File.separator + "app";
    private final String PROJECT_DIR = System.getProperty("user.dir");

    private final String MYSQL_DIR_ORIGIN = PROJECT_DIR + File.separator + "db";
    private final String MYSQL_DIR = APP_DIR + File.separator + "db";

    public MySQLFolder() {
    }

    public String APP_DIR() {
        return APP_DIR;
    }

    public String PROJECT_DIR() {
        return PROJECT_DIR;
    }

    public String MYSQL_DIR() {
        return MYSQL_DIR;
    }

    /**
     * Only tests if there's a folder called "mysql" in the project's db folder.
     */
    @ApiStatus.Internal
    public boolean mysqlDirExists() {
//        Path path = resolveDbSource();
        Path path = Paths.get(PROJECT_DIR, "app", "db");
        try (Stream<Path> stream = Files.list(path)) {
            Optional<Path> find = stream.filter(el -> el.getFileName().toString().startsWith("mysql")).findAny();
            return find.isPresent();
        } catch (Exception e) {
            throw new RuntimeException("Error on find MySQL folder", e);
        }
    }

    /**
     * Tests if there's a folder called "mysql" in the user's db folder.
     * @return if theres a folder called "mysql" in the user's db folder.
     */
    public boolean exists() {
        Path path = Paths.get(MYSQL_DIR);
        try (Stream<Path> stream = Files.list(path)) {
            Optional<Path> find = stream.filter(el -> el.getFileName().toString().startsWith("mysql")).findAny();
            return find.isPresent();
        } catch (Exception e) {
            if (e instanceof IOException) {
                return false;
            } else {
                Logger.getGlobal().severe("Error on find MySQL folder: " + e);
                throw new RuntimeException("Error on find MySQL folder", e);
            }
        }
    }

    /**
     * Find any directory in db folder, that start with "mysql".
     * @return the path of the folder called "mysql" in the user's db folder.
     */
    public String getDBFolder() {
        Path path = Paths.get(MYSQL_DIR);
        try (Stream<Path> stream = Files.list(path)) {
            Optional<Path> find = stream.filter(el -> el.getFileName().toString().startsWith("mysql")).findAny();
            if (find.isPresent()) {
                return find.get().toString();
            } else {
                Logger.getGlobal().severe("MySQL folder not found.");
            }
        } catch (Exception e) {
            Logger.getGlobal().severe("Error on find MySQL folder: " + e);
            throw new RuntimeException("Error on find MySQL folder", e);
        }
        return null;
    };

    public String getProjectFolder() {
        return PROJECT_DIR;
    }

//    private Path resolveDbSource() {
//        List<Path> candidates = List.of(
//                Paths.get(APP_DIR, "app", "db"),
//                Paths.get(APP_DIR, "db"),
//                Paths.get("app", "db"),
//                Paths.get("db")
//        );
//
//        for (Path candidate : candidates) {
//            if (Files.exists(candidate) && Files.isDirectory(candidate)) {
//                return candidate;
//            }
//        }
//        return null;
//    }


    public void delete() {
        Path dbDir = Paths.get(APP_DIR, "db");

        if (!Files.exists(dbDir)) return;

        try (var paths = Files.walk(dbDir)) {

            paths
                    .sorted(Comparator.reverseOrder())
                    .forEach(path -> {
                        try {
                            Files.delete(path);
                        } catch (IOException e) {
                            throw new RuntimeException("Error deleting " + path, e);
                        }
                    });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean create() {
        Path destiny = Paths.get(MYSQL_DIR);
        Logger.getGlobal().info("Starting to create database folder...");
        try {

            Files.createDirectories(destiny);
            Path origin = Paths.get(MYSQL_DIR_ORIGIN);

            Logger.getGlobal().info("Copying files...");
//
            try (var stream = Files.walk(origin)) {
                stream.forEach(source -> {
//
                    Path target = destiny.resolve(origin.relativize(source));
                    try {
                        if (Files.isDirectory(source)) {
                            Files.createDirectories(target);
                        } else {
                            Files.createDirectories(target.getParent());
                            Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
                        }
                        Logger.getGlobal().finest("Copied " + source + " to " + target);
                    } catch (IOException e) {
                        Logger.getGlobal().log(Level.SEVERE, "Error on copying " + source + " to " + target, e);
                        throw new RuntimeException("Error on copying" + source + " to " + target, e);
                    }
                });
//                return true;
            } catch (IOException e) {
                Logger.getGlobal().severe("Error on fill database directory:" + e);
                throw new RuntimeException("Error on filling database directory:" + e);
            }
        } catch (IOException e) {
            Logger.getGlobal().severe("Error on filling database folder:" + e);
            throw new RuntimeException("Error on filling database folder:", e);
        }
//        }
        return false;
    }


}
