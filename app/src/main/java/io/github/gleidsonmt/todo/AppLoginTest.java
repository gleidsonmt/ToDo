package io.github.gleidsonmt.todo;

import io.github.gleidsonmt.glad.base.Root;
import io.github.gleidsonmt.glad.theme.ThemeProvider;
import io.github.gleidsonmt.todo.bd.dao.DaoUser;
import io.github.gleidsonmt.todo.global.Global;
import io.github.gleidsonmt.todo.model.Usernew;
import io.github.gleidsonmt.todo.utils.Assets;
import io.github.gleidsonmt.todo.view.login.HomeLayout;
import io.github.gleidsonmt.todo.view.login.LoginView;
import io.github.gleidsonmt.todo.view.login.signup.SignupView;
import io.github.gleidsonmt.todo.view.nav.Header;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Optional;

/**
 * Description:
 *
 * @author Gleidson Neves da Silveira | <a href="mailto:gleidisonmt@gmail.com">gleidisonmt@gmail.com</a> <br>
 * Create on 21/05/2026
 */
public class AppLoginTest extends Application {

    private Optional<Usernew> getUser() {
        DaoUser daoUser = new DaoUser();
        return daoUser.get(1);
    }

    @Override
    public void init() throws Exception {
        Global.hostServices = getHostServices();
    }

    @Override
    public void start(Stage stage) throws Exception {

        LoginView  loginView = new LoginView();
        SignupView signupView = new SignupView();
        HomeLayout homeLayout = new HomeLayout();

        VBox drawer = new VBox();

        drawer.setStyle("-fx-border-width: 0px 0px 2px 0px; -fx-border-color: -medium-gray; ");

        Header header = new Header(getUser().get());
        drawer.getChildren().add(header);

        Root root = new Root(homeLayout);

        Scene scene = new Scene(root);
        ThemeProvider.install(scene);
        stage.setScene(scene);
        stage.show();

        scene.getStylesheets().add(Assets.getCss("app.css"));

        Tools.analyzeNodes(scene);
        Tools.listenCss(scene);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
