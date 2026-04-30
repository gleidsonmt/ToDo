package io.github.gleidsonmt.todo.bd.mysql;

import io.github.gleidsonmt.todo.App;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

import static java.io.File.separator;

/**
 *
 * @author Gleidson Neves da Silveira | <gleidisonmt@gmail.com>
 * Created on  29/04/2026
 */
public class MySQLFile {

    private static final String APP_DIR = System.getProperty("user.home") + separator + "app";
    private static final String DEFAULT_DIR = System.getProperty("user.dir");
    private static final String MYSQL_DIR = APP_DIR + separator + "db" + separator + "mysql-8.0.16-winx64";

    private final MySQLScript mysqlScript = new MySQLScript();

    public void createInitFile() {

        Path path = Paths.get(MYSQL_DIR + separator + "init.sql");
        var script = readScript();
        var header = createHeader();
        mysqlScript.createFileScript(path,  header, script);

    }

    private String createHeader() {
        Properties properties = Setup.getDatabaseProperties();

        return "CREATE USER '" + properties.get("user") + "'@'127.0.0.1' IDENTIFIED BY '" + properties.get("password") + "';\n" +
               "GRANT ALL PRIVILEGES ON todo.* TO '" + properties.get("user") + "'@'127.0.0.1';\n" +
               "FLUSH PRIVILEGES;\n";
    }

    private String readScript() {
        var asset = App.class.getResource("sql/bd_script.sql");
        if (asset == null) {
            return "";
        }
        StringBuilder query = new StringBuilder();

        try {
            BufferedReader br = new BufferedReader(new FileReader(asset.getFile()));
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().startsWith("-- ")) {
                    continue;
                }
                query.append(line).append(" \n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return query.toString();
    }

    public void createMyIniFile() {
        Path path = Paths.get(MYSQL_DIR + separator + "my.ini");
        mysqlScript.createFileScript(path, "[mysqld]",
                "basedir=" + MYSQL_DIR,
                "port=3308",
                "bind-address=127.0.0.1",
                "init-file=" + MYSQL_DIR + separator + "init.sql",
                "innodb_buffer_pool_size=128M",
                "# Charset do banco de dados",
                "character-set-server=utf8mb4"
        );
    }
}
