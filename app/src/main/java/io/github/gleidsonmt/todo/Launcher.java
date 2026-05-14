package io.github.gleidsonmt.todo;

import io.github.gleidsonmt.todo.global.Global;
import io.github.gleidsonmt.todo.logger.LogFormatter;
import javafx.application.Application;
import javafx.application.Platform;

import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;

/**
 *
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Created on  21/04/2026
 */
public class Launcher extends App {

    public static Mode mode;

    public static void main(String[] args) {
        for (String arg : args) {
            if (arg.equals("debug") ) mode = Mode.DEBUG;
            else if (arg.equals("log")) mode = Mode.LOG;
            else mode = Mode.DEFAULT;
        }
        launch(args);
    }
}
