package io.github.gleidsonmt.todo;

import io.github.gleidsonmt.glad.base.Root;
import io.github.gleidsonmt.glad.theme.Css;
import io.github.gleidsonmt.glad.theme.Font;
import io.github.gleidsonmt.glad.theme.ThemeProvider;
import io.github.gleidsonmt.todo.bd.DatabaseConnection;
import io.github.gleidsonmt.todo.global.Global;
import io.github.gleidsonmt.todo.logger.LogFormatter;
import io.github.gleidsonmt.todo.utils.Assets;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Description:
 *
 * @author Gleidson Neves da Silveira | <gleidisonmt@gmail.com>
 * Created On: Feb 22, 2026
 */
public class App extends Application {

    @Override
    public void init() throws Exception {

        ConsoleHandler handler = new ConsoleHandler();
        LogFormatter formatter = new LogFormatter();

        Logger.getGlobal().addHandler(handler);
        Logger.getGlobal().setUseParentHandlers(false);

        handler.setFormatter(formatter);


        if (Launcher.mode == Mode.LOG) {
            Logger.getGlobal().setLevel(Level.ALL);
            handler.setLevel(Level.ALL);
        }

    }

    @Override
    public void stop() {

        DatabaseConnection.INSTANCE.close();

        var test = ProcessHandle.allProcesses()
                .filter(processHandle -> processHandle.info().command().filter(cmd -> cmd.contains("mysqld")).isPresent()).findAny();
        test.ifPresent(processHandle -> processHandle.descendants().forEach(el -> System.out.println(el.info().command().orElse(""))));
        test.ifPresent(processHandle -> processHandle.descendants().forEach(ProcessHandle::destroy));
        Logger.getGlobal().info("Application is closed...");
        Platform.exit();
    }

    @Override
    public void start(Stage stage) throws Exception {

        LoaderView loaderView = new LoaderView();
        Root root = new Root(loaderView);

        Scene scene = new Scene(root, 1200, 728);
        ThemeProvider.install(scene, Css.ALL, Font.INSTAGRAM);
        scene.getStylesheets().add(Assets.getCss("app.css"));

        stage.getIcons().setAll(Assets.getImage("logo_128.png"));

        stage.setMinWidth(400);
        stage.setMinHeight(600);
        stage.setScene(scene);
        stage.show();

        if (Launcher.mode == Mode.DEBUG) {
            if (Global.getPreferences().getBoolean("nodeAnalyze", true)) {
                Tools.analyzeNodes(scene);
            }
            if (Global.getPreferences().getBoolean("listenCss", false)) {
                Tools.listenCss(scene);
            }
        }


    }
}
