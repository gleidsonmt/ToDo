package io.github.gleidsonmt.todo.view.settings;

import io.github.gleidsonmt.glad.base.Root;
import io.github.gleidsonmt.glad.controls.avatar.AvatarView;
import io.github.gleidsonmt.glad.controls.avatar_crop.AvatarCrop;
import io.github.gleidsonmt.glad.controls.icon.Icon;
import io.github.gleidsonmt.todo.events.LoginEvent;
import io.github.gleidsonmt.todo.view.login.LoginView;
import io.github.gleidsonmt.todo.view_model.UserViewModel;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

/**
 * @author Gleidson Neves da Silveira | <a href="mailto:gleidisonmt@gmail.com">gleidisonmt@gmail.com</a> <br>
 * Created on 06/11/2026
 */
class Header extends GridPane {
    public Header(UserViewModel viewModel) {
        getStyleClass().addAll("align-center".split("\\s+"));
//        Icon.DONE_ALL
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
            Root root = (Root) getScene().getRoot();
            root.setContent(new LoginView());
        });


        setVgap(5);
        setHgap(15);
        setPadding(new Insets(15));

        avatarView.setOnMouseClicked(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Resource File");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif",  "*.jpeg"));

            File selectedFile = fileChooser.showOpenDialog(new Stage());

            if (selectedFile == null) return;
            AvatarCrop avatarCrop = new AvatarCrop(new Image(String.valueOf(selectedFile.toURI())));
            Root root = (Root) getScene().getRoot();
            root.block();
            root.behavior().dialog()
                    .with("gray")
                    .content(avatarCrop)
                    .show();

            avatarCrop.setOnSave(image -> {
                try {
                    File file = new File("./avatars/");
                    if (!file.exists()) if (!file.mkdir()) return;
                    ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", new File("./avatars/" + viewModel.getUsername().replace(" ", "") + ".png"));
                    root.behavior().dialog().hide();
                    avatarView.setImage(image);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });

            avatarCrop.setOnClose(() -> {
                root.behavior().dialog().hide();
                root.unblock();
            });
        });
    }
}
