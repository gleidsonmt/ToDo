

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
 * @author Gleidson Neves da Silveira | <a href="mailto:gleidisonmt@gmail.com">gleidisonmt@gmail.com</a> <br> <br>
 * Created on  29/04/2026
 */
@Deprecated
public class MySQLMountServer extends Task<Process> {

    public MySQLMountServer() {
    }

    @Override
    protected Process call() {
        return null;
    }

    private boolean cleanData(@NonNull File directory) {
        return true;
    }
}
