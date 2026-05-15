package io.github.gleidsonmt.todo.bd.mysql;

import io.github.gleidsonmt.todo.App;
import org.jetbrains.annotations.Contract;
import org.jspecify.annotations.NonNull;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.io.File.separator;

/**
 *
 * @author Gleidson Neves da Silveira | <gleidisonmt@gmail.com>
 * Created on  29/04/2026
 */
@Deprecated
public class MySQLFile {

    private final MySQLScript mysqlScript = new MySQLScript();
    private final MySQLProperties properties =  MySQLProperties.INSTANCE;

    public void createInitFile(String mysqlDir) {

        var initFileWindows = (mysqlDir + separator).replace("\\", "/");
        Path path = Paths.get(initFileWindows + "init.sql");
        var script = readScript();
        var header = createHeader();
        mysqlScript.createFileScript(path, header, script);

    }

    private String createHeader() {
        properties.read();
        return "CREATE USER '" + properties.get("user") + "'@'" + properties.get("host") + "' IDENTIFIED BY '" + properties.get("password") + "';\n" +
               "GRANT ALL PRIVILEGES ON todo.* TO '" + properties.get("user") + "'@'" + properties.get("host") + "';\n" +
               "FLUSH PRIVILEGES;\n";
    }

    private String readScript() {
        var asset = App.class.getResourceAsStream("sql/bd_script.sql");

        if (asset == null) {
            return "";
        }

        StringBuilder query = new StringBuilder();

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(asset));
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().startsWith("-- ") || line.trim().startsWith("/*")) {
                    continue;
                }
                query.append(line).append(" \n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return query.toString();
    }

    public void createMyIniFile(String mysqlDir) {
        Path path = Paths.get(mysqlDir + separator + "my.ini");

        mysqlScript.createFileScript(path, "[mysqld]",
                "basedir=" + resolveMySql(mysqlDir),
                "port=" + properties.get("port"),
                "bind-address=" + properties.get("host"),
                "init-file=" + resolveMySql(mysqlDir + separator + "init.sql"),
                "innodb_buffer_pool_size=128M",
                "# Charset do banco de dados",
                "character-set-server=utf8mb4"
        );
    }

    @Contract(pure = true)
    private @NonNull String resolveMySql(String string) {
        return string.replace("\\", "/");
    }
}
