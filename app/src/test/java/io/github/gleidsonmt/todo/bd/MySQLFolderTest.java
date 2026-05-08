package io.github.gleidsonmt.todo.bd;

import io.github.gleidsonmt.todo.Launcher;
import io.github.gleidsonmt.todo.Mode;
import io.github.gleidsonmt.todo.bd.mysql.MySQLFolder;
import io.github.gleidsonmt.todo.logger.LogFormatter;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Gleidson Neves da Silveira | <gleidisonmt@gmail.com>
 * Created on  01/05/2026
 */
class MySQLFolderTest {
    @Test
    void is_app_dir_properly_created() {
        MySQLFolder proto = new MySQLFolder();
        assertEquals(proto.APP_DIR(), System.getProperty("user.home") + File.separator + "app");
    }

    @Test
    void is_mysql_dir_properly_created() {
        MySQLFolder proto = new MySQLFolder();
        assertEquals(proto.MYSQL_DIR(), System.getProperty("user.home") + File.separator + "todo" + File.separator + "app" + File.separator + "db" + File.separator + "mysql-8.0.16-winx64");
    }

    @Test
    void is_mysql_dir_8_or_higher_on_project_dir() {
        MySQLFolder proto = new MySQLFolder();
        assertTrue(proto.mysqlDirExists());
    }

    @Test
    void mysql_dir_exists_on_user_home() {
        MySQLFolder proto = new MySQLFolder();
        assertTrue(proto.exists());
    }

    @Test
    void test_ai() {
        MySQLFolder proto = new MySQLFolder();
        assertEquals(
                proto.APP_DIR(),
                "C:\\Users\\Gleidson\\todo\\app"
        );
        assertEquals(
                proto.MYSQL_DIR(),
                "C:\\Users\\Gleidson\\todo\\app\\db"
        );
    }

    @Test
    void test_logs() {
        ConsoleHandler handler = new ConsoleHandler();
        LogFormatter formatter = new LogFormatter();

        Logger.getGlobal().addHandler(handler);
        Logger.getGlobal().setUseParentHandlers(false);

        handler.setFormatter(formatter);

        Logger.getGlobal().setLevel(Level.ALL);
        handler.setLevel(Level.ALL);


        Logger.getGlobal().severe("Critical failures that stop a task or the entire application");
        Logger.getGlobal().warning(" Potential issues that might need attention later.");
        Logger.getGlobal().info("General progress messages, like \"Application started\".");
        Logger.getGlobal().config("that may be associated with particular configurations. the graphics depth, the GUI look-and-feel, etc");
        Logger.getGlobal().fine("Detailed information about the flow of the application.");
        Logger.getGlobal().finer("Detailed info for developers to see what the code is doing during development.");
        Logger.getGlobal().finest("Extremely detailed step-by-step information.");
    }
}
