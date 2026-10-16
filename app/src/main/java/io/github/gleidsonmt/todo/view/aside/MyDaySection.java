package io.github.gleidsonmt.todo.view.aside;

import io.github.gleidsonmt.glad.controls.icon.Icon;
import io.github.gleidsonmt.glad.controls.icon.SVGIcon;
import io.github.gleidsonmt.todo.view_model.ViewModel;
import javafx.scene.control.Label;

/**
 * @author Gleidson Neves da Silveira | <a href="mailto:gleidisonmt@gmail.com">gleidisonmt@gmail.com</a> <br>
 * Created on 15/10/2026
 */
public class MyDaySection extends Section {

    public MyDaySection(ViewModel viewModel) {
        Label label = new Label("My Day");
        label.setMouseTransparent(false);
        label.getStyleClass().addAll("pd-10 bg-transparent  ".split("\\s+"));
        label.setGraphicTextGap(10);
        SVGIcon icon = new SVGIcon(Icon.SUN);
        label.setGraphic(icon);

        Item item = new Item(label, viewModel);

        getChildren().setAll(item);


    }
}