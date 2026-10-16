package io.github.gleidsonmt.todo.view.settings;

import io.github.gleidsonmt.glad.base.Root;
import io.github.gleidsonmt.glad.controls.avatar.AvatarView;
import io.github.gleidsonmt.glad.controls.avatar_crop.AvatarCrop;
import io.github.gleidsonmt.glad.controls.button.IconButton;
import io.github.gleidsonmt.glad.controls.icon.Icon;
import io.github.gleidsonmt.glad.controls.icon.SVGIcon;
import io.github.gleidsonmt.todo.events.LoginEvent;
import io.github.gleidsonmt.todo.view.MainView;
import io.github.gleidsonmt.todo.view.login.LoginView;
import io.github.gleidsonmt.todo.view_model.UserViewModel;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

/**
 * @author Gleidson Neves da Silveira | <a href="mailto:gleidisonmt@gmail.com">gleidisonmt@gmail.com</a> <br>
 * Created on 12/10/2026
 */
public class SettingsView extends VBox {

    public SettingsView(UserViewModel viewModel) {
        getStyleClass().addAll("align-top-center".split("\\s+"));
        VBox body = new VBox();
        body.getStyleClass().addAll("align-top-center max-w-400".split("\\s+"));
        getChildren().addAll(new Nav(viewModel), new Separator(), body);
        body.getChildren().addAll(new Header(viewModel), new Separator());
    }
}

class Nav extends HBox {
    public Nav(UserViewModel viewModel) {
        getStyleClass().addAll("align-center-left pd-10 spacing-10".split("\\s+"));
        Text tittle = new Text("Settings");
        tittle.getStyleClass().addAll("text-20 font-instagram bolder ".split("\\s+"));
        IconButton btn = new IconButton();
        btn.setGraphic(new SVGIcon(Icon.ARROW_BACK));
        btn.getGraphic().getStyleClass().addAll("icon-grey-700");
        btn.getStyleClass().addAll("bg-transparent border-1 rect border-medium-gray".split("\\s+"));
        getChildren().addAll(btn, tittle);
        btn.setOnAction(_ -> {
            Root root = (Root)getScene().getRoot();
            root.setContent(new MainView(viewModel));
        });
    }
}

class Header extends GridPane {
    public Header(UserViewModel viewModel) {
        getStyleClass().addAll("align-center".split("\\s+"));

        AvatarView avatarView = new AvatarView();
        avatarView.setCursor(Cursor.HAND);
        avatarView.setSize(60);
        avatarView.setImage(viewModel.getAvatar());
        add(avatarView, 0, 0, 1, 2);
        Label name = new Label(viewModel.getName());
        name.getStyleClass().addAll("text-20 font-instagram bolder ".split("\\s+"));
        add(name, 1, 0);
        Label username = new Label(viewModel.getUsername());
        username.getStyleClass().addAll("text-12 ".split("\\s+"));
        add(username, 1, 1);
        GridPane.setValignment(name, VPos.BOTTOM);
        GridPane.setValignment(username, VPos.TOP);

        var logout = new Button("Logout");
        logout.getStyleClass().addAll("bg-danger w-100".split("\\s+"));
        logout.setOnAction(_ -> fireEvent(new LoginEvent(LoginEvent.LOGOUT, viewModel)));
        add(logout, 2, 0, 1, 2);

        logout.setOnAction(_ -> {
            Root root = (Root)getScene().getRoot();
            root.setContent(new LoginView());
        });


        setVgap(5);
        setHgap(15);
        setPadding(new Insets(15));

        avatarView.setOnMouseClicked(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Resource File");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"),
                    new FileChooser.ExtensionFilter("Audio Files", "*.wav", "*.mp3", "*.aac"),
                    new FileChooser.ExtensionFilter("All Files", "*.*"));

            File selectedFile = fileChooser.showOpenDialog(new Stage());
            if (selectedFile == null) return;
            AvatarCrop avatarCrop = new AvatarCrop(new Image(String.valueOf(selectedFile.toURI())));
            Root root = (Root) getScene().getRoot();
            root.behavior().dialog()
                    .with("gray")
                    .content(avatarCrop)
                    .block()
                    .show();

            avatarCrop.setOnSave(image -> {
                try {
                    File file = new File("./avatars/");
                    if (!file.exists()) if (!file.mkdir()) return;
                    ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", new File("./avatars/" + viewModel.getUsername().replace(" ", "") + ".png"));
                    root.behavior().dialog().hide();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });

            avatarCrop.setOnClose(() -> root.behavior().dialog().hide());
        });
    }
}

class Section extends VBox {

    public Section() {
        getStyleClass().addAll("align-center".split("\\s+"));
    }

}