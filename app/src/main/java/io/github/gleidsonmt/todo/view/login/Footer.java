package io.github.gleidsonmt.todo.view.login;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Separator;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 *         Create on 27/10/2024
 */
public class Footer extends VBox {

    private final Button loginButton = new Button();
    private final Hyperlink hyperLink = new Hyperlink();
    private final Text information = new Text("");

    public Footer() {
        this(null, true);
    }

    public Footer(String _buttonName, boolean needsFooter) {
        this.setAlignment(Pos.CENTER);
        this.setSpacing(15);
        this.loginButton.setText(_buttonName);

        // loginButton.setPrefWidth(Double.MAX_VALUE);
        loginButton.setMinHeight(50);
        loginButton.setMaxWidth(Double.MAX_VALUE);
        loginButton.getStyleClass().addAll("text-14");

        GridPane rememberBox = new GridPane();

        CheckBox rememberCheck = new CheckBox();
        rememberCheck.setText("Remember me");

        Hyperlink forgotPass = new Hyperlink();
        forgotPass.setText("Forgot password?");

        rememberBox.getChildren().addAll(rememberCheck, forgotPass);

        rememberBox.setPadding(new Insets(10));
        GridPane.setConstraints(rememberCheck, 0, 0, 1, 1, HPos.LEFT, VPos.CENTER, Priority.SOMETIMES, Priority.ALWAYS);
        GridPane.setConstraints(forgotPass, 1, 0, 1, 1, HPos.RIGHT, VPos.CENTER, Priority.SOMETIMES, Priority.ALWAYS);

        // content.getChildren().addAll(userNameField, passField, rememberBox,
        // loginButton);
        hyperLink.setText("Sign up");
        hyperLink.getStyleClass().addAll("text-18");
        // hyperLink.setOnAction(event);

        information.getStyleClass().addAll("text-14");

        VBox infoBox = new VBox();
        infoBox.setAlignment(Pos.CENTER);
        infoBox.getChildren().setAll(information, hyperLink);
        VBox.setVgrow(infoBox, Priority.ALWAYS);

        this.getChildren().addAll(loginButton, new Separator());

        if (needsFooter) {
            this.getChildren().add(infoBox);
        }
        VBox.setVgrow(this, Priority.ALWAYS);
    }

    public void setButtonText(String buttonText) {
        this.loginButton.setText(buttonText);
    }

    public void setLinkText(String linkName) {
        this.hyperLink.setText(linkName);
    }

    public void setInfoText(String infoText) {
        this.information.setText(infoText);
    }

    public void setButtonAction(EventHandler<ActionEvent> eventHandler) {
        this.loginButton.setOnAction(eventHandler);
    }

    public void setLinkAction(EventHandler<ActionEvent> eventHandler) {
        this.hyperLink.setOnAction(eventHandler);
    }
}
