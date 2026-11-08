package io.github.gleidsonmt.todo.view.settings;

import io.github.gleidsonmt.glad.base.Root;
import io.github.gleidsonmt.glad.controls.button.IconButton;
import io.github.gleidsonmt.glad.controls.icon.Icon;
import io.github.gleidsonmt.glad.controls.icon.SVGIcon;
import io.github.gleidsonmt.todo.view.MainView;
import io.github.gleidsonmt.todo.view_model.UserViewModel;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

/**
 * @author Gleidson Neves da Silveira | <a href="mailto:gleidisonmt@gmail.com">gleidisonmt@gmail.com</a> <br>
 * Created on 06/11/2026
 */
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
            Root root = (Root) getScene().getRoot();
            root.setContent(new MainView(viewModel));
        });
    }
}
