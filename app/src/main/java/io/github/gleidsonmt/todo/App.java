package io.github.gleidsonmt.todo;

import io.github.gleidsonmt.glad.HotTools;
import io.github.gleidsonmt.glad.Launch;
import io.github.gleidsonmt.glad.base.Root;
import io.github.gleidsonmt.glad.theme.Css;
import io.github.gleidsonmt.glad.theme.Font;
import io.github.gleidsonmt.glad.theme.ThemeProvider;
import io.github.gleidsonmt.todo.bd.sqlite.SQLiteConnection;
import io.github.gleidsonmt.todo.global.Global;
import io.github.gleidsonmt.todo.logger.LogFormatter;
import io.github.gleidsonmt.todo.utils.Assets;
import io.github.gleidsonmt.todo.utils.I18n;
import io.github.gleidsonmt.todo.view.login.LoginView;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.List;
import java.util.Locale;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Description:
 *
 * @author Gleidson Neves da Silveira | <a href="mailto:gleidisonmt@gmail.com">gleidisonmt@gmail.com</a> <br>
 * Created On: Feb 22, 2026
 */
public class App extends Application {

    // Chaves para identificar os valores no registro/arquivo
    private static final String WIDTH_KEY = "window_width";
    private static final String HEIGHT_KEY = "window_height";

    // Valores padrão caso seja a primeira vez que o app abre
    private static final double DEFAULT_WIDTH = 1200;
    private static final double DEFAULT_HEIGHT = 728;

    @Override
    public void init() throws Exception {
        ConsoleHandler handler = new ConsoleHandler();
        LogFormatter formatter = new LogFormatter();

        Logger.getGlobal().addHandler(handler);
        Logger.getGlobal().setUseParentHandlers(false);

        handler.setFormatter(formatter);

        Global.hostServices = getHostServices();

        if (Launcher.mode == Launch.MODE.DEBUG) {
            Logger.getGlobal().setLevel(Level.ALL);
            handler.setLevel(Level.ALL);
        }
    }

    @Override
    public void stop() {

        SQLiteConnection.INSTANCE.close();
        Global.removeWindowsRegister();
        Logger.getGlobal().info("Application is closed...");
        System.exit(0);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Platform.setImplicitExit(true); // mudar para falso quando nao estiver em modo desenvolviemnto
        I18n.setLocale(Locale.of("en"));
        Preferences prefs = Preferences.userNodeForPackage(App.class);
        double width = prefs.getDouble(WIDTH_KEY, DEFAULT_WIDTH);
        double height = prefs.getDouble(HEIGHT_KEY, DEFAULT_HEIGHT);

        LoaderView loaderView = new LoaderView();
        Root root = new Root(loaderView);

        Scene scene = new MainScene(root, width, height);
        ThemeProvider.install(scene, Css.ALL, Font.INSTAGRAM, Font.POPPINS);

        scene.getStylesheets().add(Assets.getCss("app.css"));
        scene.getStylesheets().add(Assets.getCss("tailwind.css"));

        stage.getIcons().setAll(Assets.getImage("logo_128.png"));

        stage.setOnCloseRequest(_ -> {
            prefs.putDouble(WIDTH_KEY, stage.getWidth());
            prefs.putDouble(HEIGHT_KEY, stage.getHeight());
            prefs.putBoolean("MAXIMIZED", stage.isMaximized());
        });

        stage.setMaximized(prefs.getBoolean("MAXIMIZED", false));
        stage.setFullScreen(false);

        stage.setMinWidth(400);
        stage.setMinHeight(600);

        stage.setScene(scene);
        stage.setTitle("JavaFx ToDo");

        List<String> args = getParameters().getRaw();

        if (args.contains("--background")) {
            // Não faz nada, o app fica rodando só no tray
            System.out.println("Iniciado em segundo plano...");
        } else {
            stage.show(); // Abre a janela se o usuário clicou no ícone
        }

        HotTools.init(scene, Launcher.mode);

//        String[] var = "align-top-center pd-[10 0 10 0]".split(" (?=\\w+-\\[)");
////        "(?<=\\]) "
//        for (String s : var)
//            System.out.println("s = " + s);

    }
}