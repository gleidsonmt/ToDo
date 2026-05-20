

package io.github.gleidsonmt.todo.view.login.signup;

import io.github.gleidsonmt.glad.controls.form.Form;
import io.github.gleidsonmt.glad.controls.form.FormField;
import io.github.gleidsonmt.glad.controls.icon.Icon;
import io.github.gleidsonmt.glad.controls.icon.SVGIcon;
import io.github.gleidsonmt.glad.controls.text_box.PasswordBox;
import io.github.gleidsonmt.glad.controls.text_box.TextBox;
import io.github.gleidsonmt.todo.bd.dao.DaoUser;
import io.github.gleidsonmt.todo.model.User;
import io.github.gleidsonmt.todo.utils.Mask;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

/**
 * @author Gleidson Neves da Silveira | <a href="mailto:gleidisonmt@gmail.com">gleidisonmt@gmail.com</a> <br>
 * Create on 28/10/2024
 */
public class FormSignUp extends VBox implements Form<User> {

    private final BooleanProperty valid = new SimpleBooleanProperty();

    private int steps = 0;
    private final User user;
    private final DaoUser daoUser;

    public FormSignUp() {
        this.user = new User(0, "");
        this.daoUser = new DaoUser();
        this.setAlignment(Pos.CENTER);
        this.setSpacing(20D);

        StatusLabel statusLabel = new StatusLabel(3);
        statusLabel.setMinHeight(20);

        TextBox perfilName = new TextBox();
        perfilName.setPromptText("Your perfil name");
        perfilName.setIcon(new SVGIcon(Icon.FACE));
        perfilName.setHelperText("Minimal size 4");
        perfilName.setMinHeight(50);
        // user.nameProperty().bind(perfilName.textProperty());

        perfilName.validProperty().bind(perfilName.getEditor().lengthProperty().greaterThan(3));

        TextBox emailField = new TextBox();
        emailField.setPromptText("Your email");
        emailField.setHelperText("It needs to look like this example@email.com");
        emailField.setIcon(new SVGIcon(Icon.MAIL));
        emailField.setMinHeight(50);
        user.usernameProperty().bind(emailField.textProperty());

        BooleanBinding isEmail = Bindings.createBooleanBinding(() -> Mask.isEmail(emailField.getEditor()),
                emailField.getEditor().textProperty());

        emailField.validProperty().bind(emailField.getEditor().lengthProperty().greaterThan(4).and(isEmail));

        PasswordBox passField = new PasswordBox();
        passField.setIcon(new SVGIcon(Icon.VPN_KEY_FILLED));
        passField.getStyleClass().add("input-form");
        passField.setPromptText("Your password");
        // passField.setIcon(new SVGIcon(Icon.LOCK));
        passField.setAction(true);
        passField.setMinHeight(50);
        passField.setHelperText("At least 4 characters");
        passField.validProperty().bind(passField.getEditor().lengthProperty().greaterThan(3));
        user.passwordProperty().bind(passField.textProperty());

        HBox box = new HBox();
        box.setPrefHeight(20);
        box.setMaxHeight(20);
        box.setFillHeight(true);
        box.setAlignment(Pos.CENTER_LEFT);
        CheckBox checkBox = new CheckBox("I agree with Terms");
        checkBox.getStyleClass().addAll("text-12");
        box.getChildren().addAll(checkBox);
        box.setDisable(true);

        this.getChildren().addAll(perfilName, emailField, passField, box, statusLabel);
        // this.getChildren().addAll(perfilName, emailField, passField,
        // statusLabel);

        this.getChildren().stream().filter(el -> el instanceof FormField).map(el -> (FormField) el).forEach(el -> {
            el.validProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue) {
                    steps++;
                    animateOn(((ProgressBar) statusLabel.getChildren().get(steps - 1)), 1);
                } else {
                    steps--;
                    animateOff(((ProgressBar) statusLabel.getChildren().get(steps)), 0);
                }

            });
        });

        valid.bind(perfilName.validProperty().and(emailField.validProperty()).and(passField.validProperty()));
    }

    private void animateOn(ProgressBar progressBar, double to) {
        Timeline timeline = new Timeline();
        timeline.getKeyFrames().setAll(
                new KeyFrame(Duration.ZERO, new KeyValue(progressBar.progressProperty(), progressBar.getProgress())),
                new KeyFrame(Duration.millis(200), new KeyValue(progressBar.progressProperty(), to)));
        timeline.play();
    }

    private void animateOff(ProgressBar progressBar, double to) {
        Timeline timeline = new Timeline();
        timeline.getKeyFrames().setAll(
                new KeyFrame(Duration.ZERO, new KeyValue(progressBar.progressProperty(), progressBar.getProgress())),
                new KeyFrame(Duration.millis(200), new KeyValue(progressBar.progressProperty(), to)));
        timeline.play();
    }

    @Override
    public boolean validate() {
        this.getChildren().stream().filter(el -> el instanceof FormField).map(el -> (FormField) el)
                .forEach(FormField::validate);
        return valid.get();
    }

    @Override
    public User get() {
        return user;
    }

    private String error;

    public String getError() {
        return error;
    }

    @Override
    public boolean persist() {
        // user.setLogged(true);

        // DaoPreferences daoPreferences = new DaoPreferences();
        // daoUser.begin();

        // if (daoUser.store(user) && daoPreferences.store(new
        // Preferences(user.getId()))) {
        // daoUser.commit();
        // return true;
        // } else {
        // error = daoUser.getErrorMessage();
        // daoUser.rollback();
        // return false;
        // }
        return false;
    }
}
