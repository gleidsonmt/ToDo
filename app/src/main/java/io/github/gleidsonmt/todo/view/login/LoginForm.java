

package io.github.gleidsonmt.todo.view.login;

import io.github.gleidsonmt.glad.controls.form.AbstractForm;
import io.github.gleidsonmt.glad.controls.icon.Icon;
import io.github.gleidsonmt.glad.controls.icon.SVGIcon;
import io.github.gleidsonmt.glad.controls.text_box.PasswordBox;
import io.github.gleidsonmt.glad.controls.text_box.TextBox;
import io.github.gleidsonmt.todo.bd.dao.DaoUser;
import io.github.gleidsonmt.todo.events.LoginEvent;
import io.github.gleidsonmt.todo.model.User;
import io.github.gleidsonmt.todo.view_model.UserViewModel;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Insets;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.Optional;

/**
 * Description: A form for user login.
 *
 * @author Gleidson Neves da Silveira | <a href="mailto:gleidisonmt@gmail.com">gleidisonmt@gmail.com</a> <br> <br>
 * Created On: Feb 24, 2026
 * <p>
 * Version History: Initial version
 */
public class LoginForm extends AbstractForm<UserViewModel> {

    private final TextBox nameField = new TextBox();
    private final DaoUser dao = new DaoUser();

    public LoginForm() {
        this(new UserViewModel());
    }

    public LoginForm(UserViewModel userViewModel) {
        super(userViewModel);
        createInputFields();
    }

    private final PasswordBox passwordField = new PasswordBox();

    private void createNameFieldSection() {
        nameField.setActionable(true);
        nameField.getEditor().setPromptText("Your username");
        nameField.setLabel("Username");
        nameField.setIcon(new SVGIcon(Icon.ACCOUNT));

        BooleanProperty wrongName = new SimpleBooleanProperty(false);

        BooleanBinding greaterThan = nameField.getEditor().lengthProperty().greaterThan(4);
        nameField.setErrorText("Username must be at least 4 characters long.");
        greaterThan.addListener((_, _, val) -> nameField.setErrorText(!val ? "Username must be at least 4 characters long." : "Please provide a correct username."));

        nameField.focusWithinProperty().addListener((_, _, val) -> wrongName.set(!val && findUser(nameField.getText()).isEmpty()));
        nameField.validProperty().bind(greaterThan.and(wrongName.not()));

        addField(nameField, 0, 0);

    }

    private void createPasswordSection() {
        BooleanProperty wrongPassword = new SimpleBooleanProperty(false);

        passwordField.getEditor().setPromptText("Your password");
        passwordField.setLabel("Password");
        passwordField.setIcon(new SVGIcon(Icon.LOCK));

        BooleanBinding greaterThan = passwordField.getEditor().lengthProperty().greaterThan(4);
        passwordField.setActionable(true);

        passwordField.validProperty().bind(greaterThan.and(wrongPassword.not()));
        passwordField.setErrorText("At least 4 characters long.");
        greaterThan.addListener((_, _, val) -> passwordField.setErrorText(!val ? "At least 4 characters long." : "Incorrect password."));
        passwordField.focusWithinProperty().addListener((_, _, val) -> wrongPassword.set(!val && greaterThan.get() && verifyPassword()));

        addField(passwordField, 0, 1);

        VBox.setMargin(passwordField, new Insets(0, 0, 200, 0));
    }

    private void createInputFields() {
        createNameFieldSection();
        createPasswordSection();
        VBox.setVgrow(this, Priority.ALWAYS);
        render();
    }

    private Optional<User> findUser(String name) {
        return dao.findWhere("username = \"" + name + "\"");
    }

    private boolean verifyPassword() {
        return findUser(nameField.getText()).filter(user -> dao.validatePassword(user, passwordField.getText())).isPresent();
    }

    @Override
    public void persist() {
        findUser(nameField.getText()).ifPresent(user -> fireEvent(new LoginEvent(LoginEvent.LOGIN_SUCCESS, user)));
    }
}
