

package io.github.gleidsonmt.todo.view.login;

import io.github.gleidsonmt.glad.controls.icon.Icon;
import io.github.gleidsonmt.todo.view.login.signup.SignupView;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;

/**
 * Description:
 *
 * @author Gleidson Neves da Silveira | <a href="mailto:gleidisonmt@gmail.com">gleidisonmt@gmail.com</a> <br> <br>
 * Created On: Feb 24, 2026
 * <p>
 * Version History: Initial version
 */
public class LoginView extends StackPane {

    private final Header header;
    private final Footer footer;
    private final LoginForm form;

    public LoginView() {
        this(true);
    }

    public LoginView(boolean needsFooter) {

        var body = new GridPane();
        body.setPrefWidth(600);
        body.setMaxWidth(600);
        body.setPrefHeight(550);
        body.setMaxHeight(550);

        getChildren().add(body);
        body.setPadding(new Insets(20));
        this.header = new Header(Icon.VPN_KEY_FILLED, "Log In", "Glad to see you again, Login to your account bellow.");
        body.getChildren().add(this.header);

        footer = new Footer("Log In", needsFooter);
        footer.setLinkText("Sign Up");
        footer.setInfoText("Don't have an account?");
        body.getChildren().add(this.footer);

        form = createForm();

        footer.setLinkAction(_ -> ((HomeLayout)getScene().lookup("#home-layout")).setLeft(new SignupView()));

        body.getChildren().add(form);
        GridPane.setMargin(form, new Insets(10));
        //
        GridPane.setRowIndex(this.header, 0);
        GridPane.setRowIndex(form, 1);
        GridPane.setRowIndex(this.footer, 2);
        GridPane.setMargin(footer, new Insets(10));
        //
        GridPane.setHgrow(header, Priority.ALWAYS);
    }

    private void find() {
    }

    private LoginForm createForm() {
        LoginForm form = new LoginForm();
        footer.setButtonAction(e -> {
            if (form.validate()) {
               form.persist();
            }
        });
        return form;
    }

}
