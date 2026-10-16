

package io.github.gleidsonmt.todo.view.nav;

import io.github.gleidsonmt.glad.base.Root;
import io.github.gleidsonmt.glad.controls.avatar.AvatarView;
import io.github.gleidsonmt.glad.controls.icon.Icon;
import io.github.gleidsonmt.glad.controls.icon.SVGIcon;
import io.github.gleidsonmt.todo.utils.Assets;
import io.github.gleidsonmt.todo.utils.I18n;
import io.github.gleidsonmt.todo.view.login.LoginView;
import io.github.gleidsonmt.todo.view.settings.SettingsView;
import io.github.gleidsonmt.todo.view_model.UserViewModel;
import javafx.geometry.Insets;
import javafx.geometry.Side;
import javafx.geometry.VPos;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.logging.Logger;

/**
 * Description:
 *
 * @author Gleidson Neves da Silveira | <a href="mailto:gleidisonmt@gmail.com">gleidisonmt@gmail.com</a> <br> <br>
 * Created On: Feb 26, 2026
 * <p>
 * Version History: Initial version
 */
public class Header extends GridPane {

    private AvatarView avatarView;
    private GridPane email;
    private Label name;

    private ContextMenu options;

    public Header(UserViewModel user) {
        setMinHeight(60);
        this.email = createEmailComponent(user.getName());
        this.name = createNameComponent(user.getUsername());
        this.options = createOptions(user);
        this.avatarView = new AvatarView();
//        Folder folder = new Folder();
        Image img = user.getAvatar(80);
//        Image img = folder.getAvatar(user.getImageUrl());
//
        this.avatarView
                .setImage(img == null ? Assets.getImage("default_avatar.jpg") : img);
//                .setImage(img == null || user.getImageUrl().isBlank() ? Assets.getImage("default_avatar.jpg") : img);
//
        init();
    }

    private Image getAvatar(String url) {
        File folder = new File("avatars");
        if (folder.exists()) {
            if (folder.mkdir()) {
                Logger.getGlobal().severe("Error on creating avatar folder");
                return null;
            }
        }

        File file = new File("avatars" + File.separator + url);
        try {
            return new Image(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            return null;
        }
    }

    private void init() {
        this.setPadding(new Insets(5, 5, 0, 5));
        this.getChildren().setAll(avatarView, email, name);
        avatarView.setRadius(23);
        GridPane.setRowSpan(avatarView, 2);
        //
        GridPane.setColumnIndex(avatarView, 0);
        GridPane.setRowIndex(avatarView, 0);
        //
        GridPane.setColumnIndex(name, 1);
        GridPane.setRowIndex(name, 0);
        //
        GridPane.setColumnIndex(email, 1);
        GridPane.setRowIndex(email, 1);
        //
        GridPane.setValignment(name, VPos.BOTTOM);
        GridPane.setValignment(email, VPos.TOP);
        //
        GridPane.setHgrow(email, Priority.ALWAYS);
        GridPane.setVgrow(name, Priority.ALWAYS);
        GridPane.setVgrow(email, Priority.ALWAYS);

        this.setHgap(10);
        this.setVgap(2);

        // VBox.setMargin(this, new Insets(10));
        VBox.setMargin(this, new Insets(0, 5, 20, 5));
    }

    private GridPane createEmailComponent(String _text) {
        Text text = new Text(_text);
        text.getStyleClass().addAll("h6");

        SVGIcon svgIcon = new SVGIcon(Icon.EXPAND_ALL);
        svgIcon.setScale(0.8);

        GridPane container = new GridPane();
        container.setHgap(5);
        container.getChildren().addAll(text, svgIcon);

        GridPane.setColumnIndex(text, 0);
        GridPane.setColumnIndex(svgIcon, 1);

        return container;
    }

    private Label createNameComponent(String _text) {
        Label text = new Label(_text);
        text.setWrapText(true);
        text.getStyleClass().addAll("h5", "bold");
        return text;
    }

    private ContextMenu createOptions(UserViewModel user) {

        ContextMenu options = new ContextMenu();

        MenuItem menuLogout = new MenuItem(I18n.get("menu.logout"));
        menuLogout.getStyleClass().add("menu-item-first");
        menuLogout.setGraphic(new SVGIcon(Icon.LOG_OUT));

        menuLogout.setOnAction(e -> {
            // Adicionar novo evento de view change
             Root root = (Root) this.getScene().getRoot();
             user.setLogged(false);
             user.update();
             root.setContent(new LoginView());
//             root.setContent(new SettingsView(user));
//             root.setLayout(new SettingsView(user));
        });

        MenuItem menuManageAccount = new MenuItem("Manage Account");
        menuManageAccount.getStyleClass().add("menu-item-last");
        menuManageAccount.setGraphic(new SVGIcon(Icon.MANAGE_ACCOUNTS));

        menuManageAccount.setOnAction(e -> {
            Root root = (Root) this.getScene().getRoot();
            root.setContent(new SettingsView(user));
        });

        options.getItems().addAll(menuManageAccount, new SeparatorMenuItem(), menuLogout);
        options.getStyleClass().add("drawer-context-menu");

        this.setOnMouseClicked(e -> {
            if (options.isShowing())
                return;
//            options.show(this, Side.BOTTOM, getWidth() / 4, e.getY() - 20);
            options.show(this, Side.BOTTOM, 0,0);
        });
        return options;
    }
}
