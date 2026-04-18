package io.github.gleidsonmt.todo.view.login.signup;

import io.github.gleidsonmt.glad.controls.icon.Icon;
import io.github.gleidsonmt.todo.view.login.Footer;
import io.github.gleidsonmt.todo.view.login.Header;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on 16/02/2026
 */
public class SignupView extends StackPane {

    private final Header header;
    private final Footer footer;

    public SignupView() {
        this(true);
    }

    public SignupView(boolean needsFooter) {
        var body = new GridPane();
        body.setPrefWidth(600);
        body.setMaxWidth(600);
        body.setPrefHeight(550);
        body.setMaxHeight(550);

        getChildren().add(body);
        body.setPadding(new Insets(20));
        this.header = new Header(Icon.APP_REGISTRATION, "Sign In",
                "Enter your details below to create your perfil and get started.");
        body.getChildren().add(this.header);
        //
        footer = new Footer("Sign In", needsFooter);
        footer.setLinkText("Log In");
        footer.setInfoText("Already have an account?");
        body.getChildren().add(this.footer);
        //
        FormSignUp form = createForm();

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

    private FormSignUp createForm() {
        FormSignUp form = new FormSignUp();
        footer.setButtonAction(e -> {
            if (form.validate()) {
                if (form.persist()) {

                }
            }
        });
        // footer.setButtonAction(e -> {
        // if (form.validate()) {
        // if (form.persist()) {
        // Root root = (Root) this.getScene().lookup("#root");
        // root.setLayout(new MainView(form.get()));
        // } else {
        // DialogUtils.openErrorDialog((View) this.getScene().getRoot(),
        // form.getError());
        // }
        //
        // }
        // });
        // footer.setLinkAction(e -> {
        // View view = (View) this.getScene().getRoot();
        // view.setLayout(new LoginWrapper(new LoginContainer()));
        // });
        return form;
    }
}
