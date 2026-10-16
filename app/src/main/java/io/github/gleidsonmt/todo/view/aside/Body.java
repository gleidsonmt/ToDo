package io.github.gleidsonmt.todo.view.aside;

import io.github.gleidsonmt.todo.view_model.ViewModel;
import javafx.scene.layout.VBox;

/**
 * @author Gleidson Neves da Silveira | <a href="mailto:gleidisonmt@gmail.com">gleidisonmt@gmail.com</a> <br>
 * Created on 15/10/2026
 */
public class Body extends VBox {
    public Body(ViewModel viewModel) {
        getStyleClass().addAll("w-300 border-light-gray border-1 spacing-10 pd-10".split("\\s+"));
        getChildren().addAll(new TitleSection(), new MyDaySection(viewModel));

    }
}