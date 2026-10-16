

package io.github.gleidsonmt.todo;

import io.github.gleidsonmt.glad.Launch;
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
 * @author Gleidson Neves da Silveira | <a href="mailto:gleidisonmt@gmail.com">gleidisonmt@gmail.com</a> <br>
 * Created on  21/04/2026
 */
public class Launcher extends App {

    public static Launch.MODE mode = Launch.MODE.RUN;

    public static void main(String[] args) {
        for (String arg : args) {
            mode = Launch.MODE.valueOf(arg.toUpperCase());
        }
        launch(args);
    }
}
