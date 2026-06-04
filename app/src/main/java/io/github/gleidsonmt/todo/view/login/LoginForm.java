

package io.github.gleidsonmt.todo.view.login;

import java.util.Optional;

import io.github.gleidsonmt.glad.controls.form.AbstractForm;
import io.github.gleidsonmt.glad.controls.text_box.PasswordBox;
import io.github.gleidsonmt.glad.controls.text_box.TextBox;
import io.github.gleidsonmt.todo.bd.dao.DaoUser;
import io.github.gleidsonmt.todo.view_model.UserViewModel;
import io.github.gleidsonmt.todo.model.Usernew;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.*;
import javafx.scene.control.Label;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

/**
 * Description:
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
        super(new UserViewModel());

//        // userNameField.setAction(true);
//        userNameField.setPromptText("Input your username");
//        userNameField.setIcon(new SVGIcon(Icon.ACCOUNT_CIRCLE));
//        userNameField.setMinHeight(50);
////        userNameField.setHelperText("Please provide a username");
//
//
//        // user.nameProperty().bind(userNameField.getEditor().textProperty());
//
//        passField.setPromptText("Input your pass");
//        passField.setIcon(new SVGIcon(Icon.LOCK));
//        // passField.setAction(true);
//        passField.setMinHeight(50);
//        passField.setHelperText("At least 4 characters.");
//
//        CheckBox rememberCheck = new CheckBox();
//        rememberCheck.setText("Remember me");
//
//        // user.passwordProperty().bind(passField.getEditor().textProperty());
//        BooleanBinding greaterThan4 = Bindings.greaterThan(passField.getEditor().lengthProperty(), 4);
//
//        GridPane rememberBox = new GridPane();
//
//        rememberBox.setDisable(true);
//
//
//        this.getChildren().addAll(userNameField, passField, rememberBox);
//
//        valid.bind(userNameField.validProperty().and(passField.validProperty()).and(passField.validProperty()));
//
//        passField.disableProperty().bind(rememberCheck.selectedProperty());
//
//        Hyperlink forgotPass = new Hyperlink();
//        forgotPass.setText("Forgot password?");
//
//        rememberBox.getChildren().addAll(rememberCheck, forgotPass);
//
//        rememberBox.setPadding(new Insets(10));
//
//        GridPane.setConstraints(rememberCheck, 0, 0, 1, 1, HPos.LEFT, VPos.CENTER, Priority.SOMETIMES, Priority.ALWAYS);
//
//        GridPane.setConstraints(forgotPass, 1, 0, 1, 1, HPos.RIGHT, VPos.CENTER, Priority.SOMETIMES, Priority.ALWAYS);
        // rememberCheck.selectedProperty().addListener((observable, oldValue,
        // newValue) -> {
        // if (!newValue) {
        // needPassword = true;
        // }
        // });
        createInputFields();
    }


    private final PasswordBox passwordField = new PasswordBox();

    private void createNameFieldSection() {
        var labelName = new Label("Username");
        labelName.setLabelFor(nameField);
        nameField.setAction(true);
        nameField.setPromptText("Your username");

        BooleanProperty wrongName = new SimpleBooleanProperty(false);

        BooleanBinding greaterThan = nameField.getEditor().lengthProperty().greaterThan(4);

        greaterThan.addListener((_, _, val) -> nameField.setHelperText(!val ? "Username must be at least 4 characters long." : "Please provide a correct username."));

        nameField.getEditor().focusedProperty().addListener((_, _, val) -> {
            if (!val) {
                wrongName.set(findUser(nameField.getText()) == null);
            } else {
                wrongName.set(false);
            }
        });
        nameField.validProperty().bind(greaterThan.and(wrongName.not()));
        nameField.setHelperText("Username must be at least 4 characters long.");

        addLabel(labelName, 0, 0);
        addField(nameField, 0, 1);
    }

    private void createPasswordSection() {
        BooleanProperty wrongPassword = new SimpleBooleanProperty(false);

        var labelPass = new Label("Password");
        passwordField.setPromptText("Your password");

        labelPass.setLabelFor(passwordField);
        BooleanBinding greaterThan = passwordField.getEditor().lengthProperty().greaterThan(4);
        passwordField.setAction(true);

        passwordField.validProperty().bind(greaterThan.and(wrongPassword.not()));
        passwordField.setHelperText("At least 4 characters long.");
        greaterThan.addListener((_, _, val) -> passwordField.setHelperText(!val ? "At least 4 characters long." : "Incorrect password."));
        passwordField.getEditor().focusedProperty().addListener((_, _, val) -> {
            if (!val && greaterThan.get()) {
                wrongPassword.set(!verifyPassword());
            } else {
                wrongPassword.set(false);
            }
        });

        addLabel(labelPass, 0, 2);
        addField(passwordField, 0, 3);
    }

    private void createInputFields() {
        createNameFieldSection();
        createPasswordSection();
        VBox.setVgrow(this, Priority.ALWAYS);
        setVgap(15);
        render();
    }

    private Usernew findUser(String name) {
        Optional<Usernew> optional = dao.findWhere("username = \"" + name + "\"");
        return optional.orElse(null);
    }

    private boolean verifyPassword() {
        Usernew user = findUser(nameField.getText());

        if (user != null) {
            return dao.validatePassword(user, passwordField.getText());
        } else {
            return false;
        }
    }

    @Override
    public void persist() {
        System.out.println(" = " );
//        UserViewModel model = new UserViewModel(findUser(nameField.getText()));
//        Event.fireEvent(getScene().getRoot(), new LoginEvent(LoginEvent.LOGIN, model));
    }
}
