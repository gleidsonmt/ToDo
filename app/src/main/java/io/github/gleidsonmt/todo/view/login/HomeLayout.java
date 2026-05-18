

package io.github.gleidsonmt.todo.view.login;

import java.util.Optional;

import io.github.gleidsonmt.glad.base.Layout;
import io.github.gleidsonmt.glad.base.responsive.Container;
import io.github.gleidsonmt.todo.bd.dao.DaoUser;
import io.github.gleidsonmt.todo.model.User;
import io.github.gleidsonmt.todo.view.presentation.Presentation;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

/**
 * Description:
 *
 * @author Gleidson Neves da Silveira | <a href="mailto:gleidisonmt@gmail.com">gleidisonmt@gmail.com</a> <br> <br>
 * Created On: Feb 22, 2026
 * <p>
 * Version History: Initial version
 */
public class HomeLayout extends Container implements Layout {
    // Data Access Object -> User
    private DaoUser daoUser;
    private Optional<User> user;
    private HBox body;

    public HomeLayout() {
        this.daoUser = new DaoUser();
        this.body = new HBox();
        updateLayout();
        getChildren().setAll(body);

    }

    /**
     * Update layout based on user is logged or not.
     */
    private void updateLayout() {
        // if (!hasUser()) {

        // } else if (!isLogged()) {
        // // Welcome view
        // body.getChildren().setAll(new Presentation(), new LoginView());
        // } else {
        // // Si
        // body.getChildren().setAll(new Presentation(), new SignupView());
        // }
        // body.getChildren().setAll(new Presentation(), new SignupView());

        // if (isLogged()) {
        // System.out.println("user = " + user);
        // } else {
        // if (!hasUser()) {
        // // Welcome view
        // // body.getChildren().add(new Presentation());
        // } else {
        // System.out.println("user = " + user);
        // // // Si
        // body.getChildren().setAll(new Presentation(), new
        // Separator(Orientation.VERTICAL), new SignupView());
        // HBox.setHgrow(body.getChildren().get(0), Priority.ALWAYS);
        // HBox.setHgrow(body.getChildren().get(1), Priority.NEVER);
        // HBox.setHgrow(body.getChildren().get(2), Priority.ALWAYS);
        // }
        // }
        var content = new LoginView();
        var presentation = new Presentation();
        addBreakpoint(e -> {
            body.getChildren().setAll(content);
        }, "md");

        addBreakpoint(e -> {
            body.getChildren().setAll(presentation, content);
        }, ">md");

        HBox.setHgrow(content, Priority.ALWAYS);
        HBox.setHgrow(presentation, Priority.ALWAYS);
    }

    /**
     * Verify if user is logged.
     *
     * @return if the user is logged.
     */
    private boolean isLogged() {
        user = this.daoUser.getBy("logged = 1");
        return user.isPresent();
    }

    public boolean hasUser() {
        user = this.daoUser.getFirst();
        return user.isPresent();
    }
}
