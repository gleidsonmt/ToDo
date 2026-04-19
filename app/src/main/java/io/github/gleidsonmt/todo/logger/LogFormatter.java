package io.github.gleidsonmt.todo.logger;

import org.jspecify.annotations.NonNull;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;

import static io.github.gleidsonmt.todo.logger.AnsiColors.*;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on 18/01/2025
 */
public class LogFormatter extends Formatter {

    // format is called for every console log message
    @Override
    public String format(LogRecord record) {
        StringBuilder builder = new StringBuilder();

        if (record.getLevel().equals(Level.CONFIG)) {
            builder.append(ANSI_GREEN);
        } else if (record.getLevel().equals(Level.FINE)) {
            builder.append(ANSI_PURPLE);
        } else if (record.getLevel().equals(Level.WARNING)) {
            builder.append(ANSI_YELLOW);
        } else if (record.getLevel().equals(Level.INFO)) {
            builder.append(ANSI_CYAN);
        } else if (record.getLevel().equals(Level.SEVERE)) {
            builder.append(ANSI_RED);
        } else if (record.getLevel().equals(Level.FINEST)) {
            builder.append(ANSI_BLUE);
        } else {
            throw new AssertionError();
        }

        builder.append("\n");
        builder.append(record.getMessage());
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
        System.out.print("\033[H\033[2J\n");
        System.out.flush();
        // builder.append("\033[H\033[2J");
//        builder.append("\n");

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
            System.out.println(e);
        }
    }

    private @NonNull String calcDate(long millisecs) {
        SimpleDateFormat date_format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date resultdate = new Date(millisecs);
        return date_format.format(resultdate);
    }

}