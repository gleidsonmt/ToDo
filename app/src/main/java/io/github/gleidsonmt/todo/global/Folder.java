package io.github.gleidsonmt.todo.global;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import io.github.gleidsonmt.todo.bd.mysql.Setup;
import io.github.gleidsonmt.todo.bd.DatabaseConnection;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import org.jetbrains.annotations.ApiStatus;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on 19/11/2024
 */
@ApiStatus.Experimental
public class Folder {


    private final File avatarFolder;
    private final String separator;
    private String defaultFolder;

    private static final String APP_DIR = System.getProperty("user.home") + File.separator + "app";

    private final String[] folders = {"avatar", "db"};

    private final String mysqlBaseDir;

    public String getAppDir() {
        return APP_DIR;
    }

    public String getSeparator() {
        return separator;
    }

    public Folder() {
        separator = FileSystems.getDefault().getSeparator();
        defaultFolder = System.getProperty("user.dir");
        avatarFolder = new File(APP_DIR + separator + "avatars");
        mysqlBaseDir = APP_DIR + separator + "db" + separator + "mysql-8.0.16-winx64";

//        createAvatarFolder();
        try {
            createFolders();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void createFolders() throws IOException {
        for (String folder : folders) {
            Path p = Paths.get(APP_DIR, folder);
            if (!Files.exists(p)) {
                Files.createDirectories(p);
            }
        }
    }

    public void getAvatarFolder() {
        File file = new File(APP_DIR + separator + "db");
        if (!file.exists()) {
            file.mkdir();
        }
    }

    private Path resolveDbSource() {
        List<Path> candidates = List.of(
                Paths.get(defaultFolder, "app", "db"),
                Paths.get(defaultFolder, "db"),
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


    public Runnable createMySQLd() {
        return () -> {
            Path origin = Paths.get(mysqlBaseDir + separator + "my.ini");
            createFileScript(origin,
                    "[mysqld]",
                    "# Pasta onde você extraiu o MySQL",
                    "basedir=" + mysqlBaseDir,
                    "# Pasta onde os dados (bancos) ficarão (será criada automaticamente)",
                    "port=3308",
                    "bind-address=127.0.0.1",
                    "init-file=" + mysqlBaseDir + separator + "init.sql",
                    "innodb_buffer_pool_size=128M",
                    "# Charset do banco de dados",
                    "character-set-server=utf8mb4"
            );
        };
    }

    public void setupDatabase() {

        Path caminho = Paths.get(mysqlBaseDir + separator + "my.ini");

        createFileScript(caminho,
                "[mysqld]",
                "# Pasta onde você extraiu o MySQL",
                "basedir=" + mysqlBaseDir,
                "# Pasta onde os dados (bancos) ficarão (será criada automaticamente)",
                "port=3308",
                "bind-address=127.0.0.1",
                "init-file=" + mysqlBaseDir + separator + "init.sql",
                "innodb_buffer_pool_size=128M",
                "# Charset do banco de dados",
                "character-set-server=utf8mb4"
        );
    }

    private void createFileScript(Path path, String... lines) {
        try {
            // Cria e escreve o arquivo (substitui se já existir)
            Files.write(path, List.of(lines));
            Logger.getGlobal().info("File create successfully: " + path.getFileName());
        } catch (IOException e) {
            Logger.getGlobal().severe("Error on creating file: " + path.getFileName() + "\n" + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void configDB() {
        Path caminho = Paths.get("my.ini");
        File myIni = new File(caminho.toAbsolutePath().toString());
        executeCommand("", "");
    }

    public void limparPastaData(File pasta) {
        if (pasta.exists()) {
            File[] arquivos = pasta.listFiles();
            if (arquivos != null) {
                for (File f : arquivos) {
                    if (f.isDirectory()) limparPastaData(f);
                    else f.delete();
                }
            }
            pasta.delete(); // Agora que está vazia, ela morre
        }
    }

    public void initDB() {
        Path mysqld = Paths.get(mysqlBaseDir, "bin", "mysql.exe");

        System.out.println("mysqld = " + mysqld);

        if (!Files.exists(mysqld)) {
            throw new IllegalStateException("mysqld.exe não encontrado em: " + mysqld);
        }


        var test = mysqld + " -u root --skip-password";
        System.out.println("test = " + test);

        ProcessBuilder pb = new ProcessBuilder(
//                "taskkill /F /IM mysqld.exe /T",
                test

        );

        pb.directory(new File(mysqlBaseDir));
        pb.redirectErrorStream(true);

        try {
            Process p = pb.start();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println("[mysqld] " + line);
                }
            }

            int exitCode = p.waitFor();
            if (exitCode != 0) {
                throw new RuntimeException("Falha ao inicializar MySQL. ExitCode=" + exitCode);
            }

            System.out.println("MySQL inicializado com sucesso.");
            setupUser();
        } catch (IOException | InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Erro ao executar mysqld", e);
        }
    }

    public void executeCommand(String origin, String command) {

        Path mysqld = Paths.get(mysqlBaseDir, "bin", "mysqld.exe");
        Path ini = Paths.get(mysqlBaseDir, "my.ini");

        Path path = Path.of(mysqlBaseDir, "data");

        File file = new File(path.toAbsolutePath().toString());
        limparPastaData(file);

        System.out.println("ini = " + ini);
        System.out.println("mysqld = " + mysqld);

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

        System.out.println("mysqld = " + mysqld);

        new Thread(() -> {
            execute(mysqld.toString());
        }).start();

//        new Thread(() -> {
//            execute(mysqld.toString(), " -u root --skip-password");
//        }).start();
        System.out.println("ulu");

//        execute(mysqld.toString(), " -u root --skip-password=");

        DatabaseConnection connection = DatabaseConnection.INSTANCE;
        System.out.println("connection = " + connection.connect());
    }

    public void execute(String... commands) {
        ProcessBuilder pb = new ProcessBuilder(List.of(commands));

        pb.directory(new File(mysqlBaseDir));
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

    public void setupUser() {
        String url = "jdbc:mysql://localhost:3306/?useSSL=false";

        try (Connection conn = DriverManager.getConnection(url, "root", "radar");
             Statement stmt = conn.createStatement()) {

            // 1. Cria o banco de dados
            stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS meu_aplicativo_db");
            System.out.println("Banco de dados verificado/criado com sucesso.");

            // 2. Agora você pode chamar seus scripts de criação de tabelas
//            executeSqlScripts(conn);

        } catch (SQLException e) {
            System.err.println("Erro ao configurar MySQL: " + e.getMessage());
            // Aqui você deve tratar: senha errada, host inacessível, etc.
        }
    }

    public Runnable createDBFolder() {
        Path destiny = Paths.get(APP_DIR, "db");
        Logger.getGlobal().info("Starting to create database folder...");
        return () -> {
            try {
                Files.createDirectories(destiny);

                Path origin = resolveDbSource();
                if (origin == null) {

                    Logger.getGlobal().severe("Error on finding database folder:\n" +
                                              Paths.get(defaultFolder, "app", "db") + ", " +
                                              Paths.get(defaultFolder, "db") + ", " +
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
                            }
                        } catch (IOException e) {
                            Logger.getGlobal().log(Level.SEVERE, "Error on copying" + source + " to " + target, e);
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
        };
    }

    public boolean has(String imageUrl) {
        if (imageUrl == null)
            return false;
        return Arrays.stream(Objects.requireNonNull(avatarFolder.list())).anyMatch(imageUrl::equalsIgnoreCase);
    }

    private boolean createAvatarFolder() {
        if (!avatarFolder.exists()) {
            return avatarFolder.mkdir();
        }
        return false;
    }

    public Image getAvatar(String url) {
        Image image;
        try {
            File file = new File(avatarFolder.getCanonicalFile() + separator + url);
            image = new Image(file.toURI().toURL().toExternalForm());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return image;
        // return null;
    }

    public Image getAvatar(String url, int size) {
        Image image;
        try {
            File file = new File(avatarFolder.getCanonicalFile() + separator + url);
            image = new Image(file.toURI().toURL().toExternalForm(), size, size, true, true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return image;
    }

    public void saveAvatar(Image image, String name) {

        File file = new File(avatarFolder + separator + name + ".png");

        BufferedImage bImage = SwingFXUtils.fromFXImage(image, null);
        try {
            ImageIO.write(bImage, "png", file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
