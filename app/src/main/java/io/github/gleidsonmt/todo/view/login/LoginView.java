package io.github.gleidsonmt.todo.view.login;

import java.util.logging.Logger;

import io.github.gleidsonmt.glad.base.Root;
import io.github.gleidsonmt.glad.controls.icon.Icon;
import io.github.gleidsonmt.todo.view.MainView;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;

/**
 * Description:
 *
 * @author Gleidson Neves da Silveira | <gleidisonmt@gmail.com>
 * Created On: Feb 24, 2026
 * <p>
 * Version History: Initial version
 */
public class LoginView extends StackPane {

    private final Header header;
    private final Footer footer;

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

        FormLogin form = createForm();

        body.getChildren().add(form);
        GridPane.setVgrow(form, Priority.ALWAYS);
        GridPane.setMargin(form, new Insets(10));
        //
        GridPane.setRowIndex(this.header, 0);
        GridPane.setRowIndex(form, 1);
        GridPane.setRowIndex(this.footer, 2);
        //
        GridPane.setHgrow(header, Priority.ALWAYS);
    }

    private FormLogin createForm() {
        FormLogin form = new FormLogin();
        footer.setButtonAction(e -> {
            // if (form.validate()) {
            // if (form.persist()) {
            Logger.getGlobal().info("User logged in successfully!");
            Root root = (Root) getScene().getRoot();
            root.setLayout(new MainView(form.get()));
            // }
            // }
        });
        return form;
    }

}
