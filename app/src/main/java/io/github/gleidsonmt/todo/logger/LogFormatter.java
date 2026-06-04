

package io.github.gleidsonmt.todo.logger;

import org.jspecify.annotations.NonNull;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;

import static io.github.gleidsonmt.todo.logger.AnsiColors.*;

/**
 * @author Gleidson Neves da Silveira | <a href="mailto:gleidisonmt@gmail.com">gleidisonmt@gmail.com</a> <br>
 * Create on 18/01/2025
 */
public class LogFormatter extends Formatter {

    // SEVERE / ERROR: Critical failures that stop a task or the entire application
    // WARNING / WARN: Potential issues that might need attention later.
    // INFO: General progress messages, like "Application started".
    // CONFIG: that may be associated with particular configurations.
    // For example, CONFIG message might include the CPU type,
    // the graphics depth, the GUI look-and-feel, etc.
    // DEBUG / FINE: Detailed info for developers to see what the code is doing during development.
    // FINER: FINER indicates a fairly detailed tracing message. By default logging calls for entering, returning, or throwing an exception are traced at this level.
    // TRACE / FINEST: Extremely detailed step-by-step information.

    // [severe, red]
    // [warning, yellow]
    // [info, blue]
    // [config, purple]
    // [green, cyan]

    // format is called for every console log message
    @Override
    public String format(LogRecord record) {
        StringBuilder builder = new StringBuilder();

        builder.append(ANSI_WHITE);

        if (record.getLevel().equals(Level.CONFIG)) {
            builder.append(ANSI_PURPLE);
        } else if (record.getLevel().equals(Level.FINE)) {
            builder.append(ANSI_CYAN);
        } else if (record.getLevel().equals(Level.WARNING)) {
            builder.append(ANSI_YELLOW);
        } else if (record.getLevel().equals(Level.INFO)) {
            builder.append(ANSI_BLUE);
        } else if (record.getLevel().equals(Level.SEVERE)) {
            builder.append(ANSI_RED);
        } else if (record.getLevel().equals(Level.FINEST)) {
            builder.append(ANSI_WHITE);
        } else if (record.getLevel().equals(Level.FINER)) {
            builder.append(ANSI_GREEN);
        } else {
            throw new AssertionError();
        }

        builder.append("\n");
        builder.append("[ ").append(record.getLevel()).append(" ]");

        if (record.getLevel() == Level.SEVERE) {
            builder.append(" ( ")
                    .append("class=")
                    .append(record.getSourceClassName().substring(record.getSourceClassName().lastIndexOf(".") + 1))
                    .append(", method=").append(record.getSourceMethodName()).append(" ) ");
        }
        builder.append(ANSI_WHITE).append(" => ");

        builder.append(record.getMessage());

        if (record.getLevel().equals(Level.CONFIG)) {
            builder.append(ANSI_GREEN).append(" [ OK ]").append(ANSI_WHITE);
        } else if (record.getLevel().equals(Level.SEVERE)) {
            builder.append(ANSI_RED).append(" [ FAILED ]").append(ANSI_WHITE);
        }

        builder.append(" ").append(calcDate(record.getMillis()));
        builder.append(ANSI_WHITE);

        Object[] params = record.getParameters();

        if (params != null) {
            builder.append("\t");
            for (int i = 0; i < params.length; i++) {
                builder.append(params[i]);
                if (i < params.length - 1)
                    builder.append(", ");
            }
        }

        builder.append(ANSI_RESET);
//        System.out.print("\033[H\033[2J");
//        System.out.print(ANSI_RESET);
        System.out.flush();

        return builder.toString();
    }

    @Deprecated(since = "1.0", forRemoval = true)
    public void clearConsole() {
        try {
            String operatingSystem = System.getProperty("os.name"); // Check the
            // current
            // operating
            // system
            if (operatingSystem.contains("Windows")) {
                ProcessBuilder pb = new ProcessBuilder("cmd", "/c", "cls");
                Process startProcess = pb.inheritIO().start();
                startProcess.waitFor();
            } else {
                ProcessBuilder pb = new ProcessBuilder("clear");
                Process startProcess = pb.inheritIO().start();

                startProcess.waitFor();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private @NonNull String calcDate(long millisecs) {
        SimpleDateFormat date_format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date resultdate = new Date(millisecs);
        return date_format.format(resultdate);
    }

}