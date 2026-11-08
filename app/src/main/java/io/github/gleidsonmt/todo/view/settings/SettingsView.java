package io.github.gleidsonmt.todo.view.settings;

import io.github.gleidsonmt.todo.view_model.UserViewModel;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.VBox;

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
        body.getChildren().addAll(new Header(viewModel), new Separator(), new SmartListSection());
    }
}

