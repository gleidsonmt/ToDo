package io.github.gleidsonmt.todo;

import io.github.gleidsonmt.glad.base.Root;
import io.github.gleidsonmt.glad.theme.Css;
import io.github.gleidsonmt.glad.theme.Font;
import io.github.gleidsonmt.glad.theme.ThemeProvider;
import io.github.gleidsonmt.todo.bd.DatabaseConnection;
import io.github.gleidsonmt.todo.global.Global;
import io.github.gleidsonmt.todo.global.UserPresenter;
import io.github.gleidsonmt.todo.logger.LogFormatter;
import io.github.gleidsonmt.todo.model.User;
import io.github.gleidsonmt.todo.utils.Assets;
import io.github.gleidsonmt.todo.view.MainView;
import io.github.gleidsonmt.todo.view.login.HomeLayout;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Optional;
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

    private final LogFormatter formatter = new LogFormatter();
    private DatabaseConnection connection;
    @Override
    public void init() throws Exception {
        ConsoleHandler handler = new ConsoleHandler();

        handler.setFormatter(formatter);

        Logger.getGlobal().addHandler(handler);
        Logger.getGlobal().setUseParentHandlers(false);
        Logger.getGlobal().setLevel(Level.CONFIG);
        handler.setLevel(Level.CONFIG);

        // UI Notifications info
        // Config Database Configuration
        // JavaFx Actions
        // Database Actions
    }

    @Override
    public void stop() {
        Logger.getGlobal().info("Application is stopping...");
        if (connection.hasConnection())
            connection.close();
    }

    @Override
    public void start(Stage stage) throws Exception {
        this.connection = new DatabaseConnection();
        Logger.getGlobal().finest(() -> "Established connection... [" + (connection.connect() ? "OK" : "FAILED") + "]");

        UserPresenter presenter = (UserPresenter) Global.get(User.class);
        Optional<User> user = presenter.getLogged();

        Root root = new Root(user.isPresent() ? new MainView(user.get()) : new HomeLayout());
        Scene scene = new Scene(root, 1200, 728);
        ThemeProvider.install(scene, Css.ALL, Font.INSTAGRAM);
        scene.getStylesheets().add(Assets.getCss("app.css"));

        stage.getIcons().setAll(Assets.getImage("logo_128.png"));

        stage.setMinWidth(400);
        stage.setMinHeight(600);
        stage.setScene(scene);
        stage.show();

//        Tools.showUp(scene);
        Tools.analyzeNodes(scene);
        Tools.listenCss(scene);

    }
    public static void main(String[] args) {
        launch(args);
    }
}
