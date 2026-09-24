

package io.github.gleidsonmt.todo.view.login;

import java.util.Optional;

import io.github.gleidsonmt.glad.base.Root;
import io.github.gleidsonmt.glad.base.responsive.Container;
import io.github.gleidsonmt.todo.bd.dao.DaoUser;
import io.github.gleidsonmt.todo.events.LoginEvent;
import io.github.gleidsonmt.todo.model.User;
import io.github.gleidsonmt.todo.model.Usernew;
import io.github.gleidsonmt.todo.view.MainView;
import io.github.gleidsonmt.todo.view.login.signup.SignupView;
import io.github.gleidsonmt.todo.view.presentation.Presentation;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;

/**
 * Description:
 *
 * @author Gleidson Neves da Silveira | <a href="mailto:gleidisonmt@gmail.com">gleidisonmt@gmail.com</a> <br> <br>
 * Created On: Feb 22, 2026
 * <p>
 * Version History: Initial version
 */
public class HomeLayout extends Container {
    // Data Access Object -> User
    private DaoUser daoUser;
    private User user;
    private HBox body;

    public HomeLayout(User user) {
        setId("home-layout");
        this.daoUser = new DaoUser();
        this.body = new HBox();
        getChildren().setAll(body);
        this.user = user;
        updateLayout();

    }

    public void setLeft(Node node) {
        body.getChildren().set(body.getChildren().size() > 1 ? 1 : 0, node);

        HBox.setHgrow(node, Priority.ALWAYS);
    }

    /**
     * Update layout based on user is logged or not.
     */
    private void updateLayout() {
        System.out.println("user = " + user);

//        body.getChildren().setAll(new Presentation(), new SignupView());

        // if (isLogged()) {
        // System.out.println("user = " + user);
        // } else {
        // if (!hasUser()) {
        // // Welcome view
        // // body.getChildren().add(new Presentation());
        // } else {
        // System.out.println("user = " + user);
        // // // Si
//         body.getChildren().setAll(new Presentation(), new
//                 Separator(Orientation.VERTICAL), new SignupView());
        // HBox.setHgrow(body.getChildren().get(0), Priority.ALWAYS);
        // HBox.setHgrow(body.getChildren().get(1), Priority.NEVER);
        // HBox.setHgrow(body.getChildren().get(2), Priority.ALWAYS);
        // }
        // }
        StackPane content = user == null ? new LoginView() : new SignupView();
        var presentation = new Presentation();

//        body.getChildren().setAll(presentation, content);

        addBreakpoint(e -> {
            body.getChildren().setAll(content);
        }, "sm md");

        addBreakpoint(e -> {
            body.getChildren().setAll(presentation, content);
        }, ">md");
//
        HBox.setHgrow(content, Priority.ALWAYS);
        HBox.setHgrow(presentation, Priority.ALWAYS);

    }

    /**
     * Verify if user is logged.
     *
     * @return if the user is logged.
     */
    private boolean isLogged() {
//        user = this.daoUser.getBy("logged = 1");
//        return user.isPresent();\
        return false;
    }

    public boolean hasUser() {
        return false;
//        user = this.daoUser.getFirst();
//        return user.isPresent();
    }
}
