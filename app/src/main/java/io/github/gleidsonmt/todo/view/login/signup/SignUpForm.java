

package io.github.gleidsonmt.todo.view.login.signup;

import io.github.gleidsonmt.glad.base.Root;
import io.github.gleidsonmt.glad.base.dialog.alert.AlertType;
import io.github.gleidsonmt.glad.controls.form.AbstractForm;
import io.github.gleidsonmt.glad.controls.form.FormField;
import io.github.gleidsonmt.glad.controls.icon.Icon;
import io.github.gleidsonmt.glad.controls.icon.SVGIcon;
import io.github.gleidsonmt.glad.controls.text_box.PasswordBox;
import io.github.gleidsonmt.glad.controls.text_box.TextBox;
import io.github.gleidsonmt.todo.events.DialogEvent;
import io.github.gleidsonmt.todo.events.LoginEvent;
import io.github.gleidsonmt.todo.view_model.UserConverter;
import io.github.gleidsonmt.todo.view_model.UserModel;
import io.github.gleidsonmt.todo.view_model.UserViewModel;
import io.github.gleidsonmt.todo.utils.Mask;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.event.Event;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.util.Duration;

/**
 * @author Gleidson Neves da Silveira | <a href="mailto:gleidisonmt@gmail.com">gleidisonmt@gmail.com</a> <br>
 * Create on 28/10/2024
 */
//public class SignUpForm extends VBox implements AbstractForm<UserViewModel> {

public class SignUpForm extends AbstractForm<UserViewModel> {

//    private final BooleanProperty valid = new SimpleBooleanProperty();

    private int steps = 0;
    //    private final UserViewModel user;
    private final TextBox nameField = new TextBox();
    private final TextBox emailField = new TextBox();
    private final PasswordBox passField = new PasswordBox();

    public SignUpForm() {
        super(new UserViewModel());

        StatusLabel statusLabel = new StatusLabel(3);
        statusLabel.setMinHeight(20);
//
//        HBox box = new HBox();
//        box.setPrefHeight(20);
//        box.setMaxHeight(20);
//        box.setFillHeight(true);
//        box.setAlignment(Pos.CENTER_LEFT);
//        CheckBox checkBox = new CheckBox("I agree with Terms");
//        checkBox.getStyleClass().addAll("text-12");
//        box.getChildren().addAll(checkBox);
//        box.setDisable(true);
//

        createNameSection();
        createEmailSection();
        createPassSection();
        setVgap(2);
        render();
        getFields().stream().filter(el -> el instanceof Node).map(el -> (Node) el).forEach(el -> {
            marginBottom(el);
            ((FormField) el).validProperty().addListener((_, _, val) -> {
                if (val) {
                    steps++;
                    animateOn(((ProgressBar) statusLabel.getChildren().get(steps - 1)));
                } else {
                    steps--;
                    animateOff(((ProgressBar) statusLabel.getChildren().get(steps)));
                }
            });
        });
        add(statusLabel, 0, 6);
        bindToViewModel();
    }

    private void bindToViewModel() {
        getModel().nameProperty().bind(nameField.getEditor().textProperty());
        getModel().usernameProperty().bind(emailField.getEditor().textProperty());
        getModel().passwordProperty().bind(passField.getEditor().textProperty());
    }

    private void createNameSection() {
        nameField.setPromptText("Your perfil name");
        nameField.setIcon(new SVGIcon(Icon.FACE));
        var labelName = new Label("Perfil name");
        labelName.setLabelFor(nameField);
        addLabel(labelName, 0, 0);
        addField(nameField, 0, 1);

        nameField.validProperty().bind(nameField.getEditor().lengthProperty().greaterThan(3));
        nameField.setHelperText("At least 4 characters long.");
        nameField.setMinHeight(50);

    }

    private void marginBottom(Node node) {
        GridPane.setMargin(node, new Insets(0, 0, 15, 0));
    }

    private void createEmailSection() {
        emailField.setPromptText("Your email");
        emailField.setIcon(new SVGIcon(Icon.MAIL));
        var labelName = new Label("Email");
        labelName.setLabelFor(emailField);

        emailField.setHelperText("It needs to look like this example@email.com");
        emailField.setPromptText("Your email");

        addLabel(labelName, 0, 2);
        addField(emailField, 0, 3);

        BooleanBinding isEmail = Bindings.createBooleanBinding(() -> Mask.isEmail(emailField.getEditor()),
                emailField.getEditor().textProperty());

        emailField.validProperty().bind(emailField.getEditor().lengthProperty().greaterThan(4).and(isEmail));

    }

    private void createPassSection() {
        var labelName = new Label("Password");
        labelName.setLabelFor(emailField);

        passField.setIcon(new SVGIcon(Icon.VPN_KEY_FILLED));
        passField.setHelperText("At least 4 characters");
//        passField.getStyleClass().add("input-form");
        passField.setPromptText("Your password");
        passField.setAction(true);

        passField.setHelperText("At least 4 characters");
        passField.validProperty().bind(passField.getEditor().lengthProperty().greaterThan(3));

        addLabel(labelName, 0, 4);
        addField(passField, 0, 5);
    }

    private void animateOn(ProgressBar progressBar) {
        Timeline timeline = new Timeline();
        timeline.getKeyFrames().setAll(
                new KeyFrame(Duration.ZERO, new KeyValue(progressBar.progressProperty(), progressBar.getProgress())),
                new KeyFrame(Duration.millis(200), new KeyValue(progressBar.progressProperty(), (double) 1)));
        timeline.play();
    }

    private void animateOff(ProgressBar progressBar) {
        Timeline timeline = new Timeline();
        timeline.getKeyFrames().setAll(
                new KeyFrame(Duration.ZERO, new KeyValue(progressBar.progressProperty(), progressBar.getProgress())),
                new KeyFrame(Duration.millis(200), new KeyValue(progressBar.progressProperty(), (double) 0)));
        timeline.play();
    }

    @Override
    public void persist() {

//        UserModel model = new UserModel();

        if (getModel().save()) {
            Event.fireEvent(getScene().getRoot(), new LoginEvent(LoginEvent.LOGIN, new UserConverter().toEntity(getModel())));
        } else {
            var text = new Text("A user with this email has already been registered.\n Try another one.");
            text.getStyleClass().addAll("h5");
            ((Root) getScene().getRoot())
                    .behavior()
                    .alert()
                    .title("Ops..")
                    .content(new TextFlow(text))
                    .type(AlertType.WARNING)
                    .show();
        }


//        model.save();


//        Event.fireEvent(getScene().getRoot(), new LoginEvent(LoginEvent.LOGIN, (UserViewModel) get().save()));
//        get().save();
//        Usernew user = new Usernew(0, perfilName.getText());
//        user.setUsername(emailField.getText());
//        user.setPassword(passField.getText());
//        user.setImageUrl("default_avatar.jpg");
//        dao.store(user);


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
    }
}
