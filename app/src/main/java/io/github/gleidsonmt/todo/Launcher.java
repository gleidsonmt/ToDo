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
 * @author Gleidson Neves da Silveira | <gleidisonmt@gmail.com>
 * Created on  21/04/2026
 */
public class Launcher extends App {


    public Launcher() {
//        JavaFx actions INFO
//        Database actions CONFIG
//        finer criação de diretórios, configuacaoes da aplicação final
//        UI actions finest
    }

    public static void main(String[] args) {
        for (String arg : args) {
            if (arg.startsWith("level")) Global.getPreferences().put("level", arg.substring("level-".length()));
            Global.getPreferences().putBoolean("nodeAnalyze", arg.equals("nodeAnalyze"));
            Global.getPreferences().putBoolean("listenCss", arg.equals("listenCss"));
        }
        launch(args);
    }
}
