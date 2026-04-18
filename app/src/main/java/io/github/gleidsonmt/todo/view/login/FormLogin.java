package io.github.gleidsonmt.todo.view.login;

import java.util.Optional;
import java.util.logging.Logger;

import io.github.gleidsonmt.glad.controls.form.Form;
import io.github.gleidsonmt.glad.controls.form.FormField;
import io.github.gleidsonmt.glad.controls.icon.Icon;
import io.github.gleidsonmt.glad.controls.icon.SVGIcon;
import io.github.gleidsonmt.glad.controls.text_box.PasswordBox;
import io.github.gleidsonmt.glad.controls.text_box.TextBox;
import io.github.gleidsonmt.todo.bd.dao.DaoUser;
import io.github.gleidsonmt.todo.model.User;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

/**
 * Description:
 *
 * @author Gleidson Neves da Silveira | <gleidisonmt@gmail.com>
 * Created On: Feb 24, 2026
 * <p>
 * Version History: Initial version
 */
public class FormLogin extends VBox implements Form<User> {

    private final TextBox userNameField = new TextBox();
    private final PasswordBox passField = new PasswordBox();

    private final BooleanProperty valid = new SimpleBooleanProperty();

    private User user;
    private DaoUser dao;

    public FormLogin() {

        // this.user = new User();
        this.dao = new DaoUser();

        this.setAlignment(Pos.CENTER);
        this.setSpacing(20D);

        // userNameField.setAction(true);
        userNameField.setPromptText("Input your username");
        userNameField.setIcon(new SVGIcon(Icon.ACCOUNT_CIRCLE));
        userNameField.setMinHeight(50);
        userNameField.setHelperText("Please provide a username");

        // user.nameProperty().bind(userNameField.getEditor().textProperty());

        passField.setPromptText("Input your pass");
        passField.setIcon(new SVGIcon(Icon.LOCK));
        // passField.setAction(true);
        passField.setMinHeight(50);
        passField.setHelperText("At least 4 characters.");

        CheckBox rememberCheck = new CheckBox();
        rememberCheck.setText("Remember me");

        // user.passwordProperty().bind(passField.getEditor().textProperty());
        BooleanBinding greaterThan4 = Bindings.greaterThan(passField.getEditor().lengthProperty(), 4);

        GridPane rememberBox = new GridPane();

        rememberBox.setDisable(true);

        userNameField.validProperty().bind(userNameField.getEditor().lengthProperty().greaterThan(4));
        passField.validProperty().bind(greaterThan4);

        this.getChildren().addAll(userNameField, passField, rememberBox);

        valid.bind(userNameField.validProperty().and(passField.validProperty()).and(passField.validProperty()));

        passField.disableProperty().bind(rememberCheck.selectedProperty());

        Hyperlink forgotPass = new Hyperlink();
        forgotPass.setText("Forgot password?");

        rememberBox.getChildren().addAll(rememberCheck, forgotPass);

        rememberBox.setPadding(new Insets(10));

        GridPane.setConstraints(rememberCheck, 0, 0, 1, 1, HPos.LEFT, VPos.CENTER, Priority.SOMETIMES, Priority.ALWAYS);

        GridPane.setConstraints(forgotPass, 1, 0, 1, 1, HPos.RIGHT, VPos.CENTER, Priority.SOMETIMES, Priority.ALWAYS);
        // rememberCheck.selectedProperty().addListener((observable, oldValue,
        // newValue) -> {
        // if (!newValue) {
        // needPassword = true;
        // }
        // });
    }

    @Override
    public User get() {
        return user;
    }

    @Override
    public boolean persist() {
        // this.dao.login(user);

        Optional<User> optional = dao.getByName(userNameField.getText());

        Logger.getGlobal().info(() -> "User found: " + optional);
        if (optional.isPresent()) {
            User found = optional.get();
            if (found.getPassword().equals(passField.getText())) {
                Logger.getGlobal().info(() -> "User logged: " + found);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean validate() {
        this.getChildren().stream().filter(el -> el instanceof FormField).map(el -> (FormField) el)
                .forEach(FormField::validate);
        return valid.get();
    }

}
