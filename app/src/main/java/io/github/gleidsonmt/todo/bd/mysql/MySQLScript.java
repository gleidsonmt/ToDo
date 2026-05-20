

package io.github.gleidsonmt.todo.bd.mysql;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.logging.Logger;

/**
 *
 * @author Gleidson Neves da Silveira | <a href="mailto:gleidisonmt@gmail.com">gleidisonmt@gmail.com</a> <br> <br>
 * Created on  29/04/2026
 */
@Deprecated
public class MySQLScript {

    public void createFileScript(Path path, String... lines) {
        try {
            Files.deleteIfExists(path);
            Files.write(path, List.of(lines));

            Logger.getGlobal().info("File create successfully: " + path.toAbsolutePath());
        } catch (IOException e) {
            Logger.getGlobal().severe("Error on creating file: " + path.toAbsolutePath() + "\n" + e.getMessage());
            throw new RuntimeException(e);
        }
    }

}
