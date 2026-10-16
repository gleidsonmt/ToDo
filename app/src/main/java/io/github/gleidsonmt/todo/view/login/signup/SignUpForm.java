

package io.github.gleidsonmt.todo.view.login.signup;

import io.github.gleidsonmt.glad.base.Root;
import io.github.gleidsonmt.glad.base.dialog.alert.AlertType;
import io.github.gleidsonmt.glad.controls.form.AbstractForm;
import io.github.gleidsonmt.glad.controls.form.FormField;
import io.github.gleidsonmt.glad.controls.icon.Icon;
import io.github.gleidsonmt.glad.controls.icon.SVGIcon;
import io.github.gleidsonmt.glad.controls.text_box.PasswordBox;
import io.github.gleidsonmt.glad.controls.text_box.TextBox;
import io.github.gleidsonmt.todo.events.LoginEvent;
import io.github.gleidsonmt.todo.utils.Mask;
import io.github.gleidsonmt.todo.view.MainView;
import io.github.gleidsonmt.todo.view_model.UserViewModel;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.event.Event;
import javafx.scene.Node;
import javafx.scene.control.ProgressBar;
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
//        setVgap(2);
        render();
        getFields().stream().filter(el -> el instanceof Node).map(el -> (Node) el).forEach(el -> {
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
        add(statusLabel, 0, 4);
        bind();
//        setGridLinesVisible(true);
    }

    private void bind() {
        getModel().nameProperty().bind(nameField.textProperty());
        getModel().usernameProperty().bind(emailField.textProperty());
        getModel().passwordProperty().bind(passField.textProperty());
    }

    private void createNameSection() {
        nameField.getEditor().setPromptText("Your perfil name");
        nameField.setIcon(new SVGIcon(Icon.FACE));
        nameField.setLabel("Perfil name");
//        var labelName = new Label("Perfil name");
//        labelName.setLabelFor(nameField);
//        addLabel(labelName, 0, 0);
        addField(nameField, 0, 0);

        nameField.validProperty().bind(nameField.getEditor().lengthProperty().greaterThan(3));
        nameField.setErrorText("At least 4 characters long.");
//        nameField.setMinHeight(50);

    }

    private void createEmailSection() {
        emailField.getEditor().setPromptText("Your email");
        emailField.setIcon(new SVGIcon(Icon.MAIL));
        emailField.setLabel("Email");
//        var labelName = new Label("Email");
//        labelName.setLabelFor(emailField);

        emailField.setErrorText("It needs to look like this example@email.com");
        emailField.getEditor().setPromptText("Your email");

//        addLabel(labelName, 0, 2);
        addField(emailField, 0, 1);

        BooleanBinding isEmail = Bindings.createBooleanBinding(() -> Mask.isEmail(emailField.getEditor()),
                emailField.textProperty());

        emailField.validProperty().bind(emailField.getEditor().lengthProperty().greaterThan(4).and(isEmail));

    }

    private void createPassSection() {
//        var labelName = new Label("Password");
//        labelName.setLabelFor(emailField);

        passField.setIcon(new SVGIcon(Icon.VPN_KEY_FILLED));
        passField.setErrorText("At least 4 characters");
        passField.setLabel("Password");
//        passField.getStyleClass().add("input-form");
        passField.getEditor().setPromptText("Your password");
        passField.setActionable(true);

        passField.setErrorText("At least 4 characters");
        passField.validProperty().bind(passField.getEditor().lengthProperty().greaterThan(3));

//        addLabel(labelName, 0, 4);
        addField(passField, 0, 3);
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

        getModel().setLogged(true);
        getModel().save();

        Event.fireEvent(getScene().getRoot(), new LoginEvent(LoginEvent.LOGIN, getModel()));
        var root = (Root) getScene().getRoot();
        root.setContent(new MainView(getModel()));

//        if (getModel().save()) {
////            Event.fireEvent(getScene().getRoot(), new LoginEvent(LoginEvent.LOGIN, new UserConverter().toEntity(getModel())));
////            Event.fireEvent(getScene().getRoot(), new LoginEvent(LoginEvent.LOGIN, new UserConverter().toEntity(getModel())));
//            System.out.println("UserMapper.INSTANCE.toDTO(getModel()) = " + UserMapper.INSTANCE.toDTO(getModel()));
//            var root = (Root) getScene().getRoot();
//            root.setContent(new MainView(getModel()));
//        } else {
//            var text = new Text("A user with this email has already been registered.\n Try another one.");
//            text.getStyleClass().addAll("h5");
//            ((Root) getScene().getRoot())
//                    .behavior()
//                    .alert()
//                    .title("Ops..")
//                    .content(new TextFlow(text))
//                    .type(AlertType.WARNING)
//                    .show();
//        }


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
