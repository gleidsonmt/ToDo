package io.github.gleidsonmt.todo.view.aside;

import io.github.gleidsonmt.todo.view_model.TaskViewModel;
import javafx.scene.layout.VBox;

/**
 * @author Gleidson Neves da Silveira | <a href="mailto:gleidisonmt@gmail.com">gleidisonmt@gmail.com</a> <br>
 * Created on 12/10/2026
 */
public class Aside extends VBox {

    public Aside(TaskViewModel viewModel) {
        var close = new CloseButton();
        getStyleClass().addAll("aside".split("\\s+"));
        close.getStyleClass().addAll("hover:bg-medium-gray".split("\\s+"));
        getChildren().setAll(new CloseButton(), new Body(viewModel),  new Footer());
        getStyleClass().addAll("w-300 border-medium-gray border-l-1 pd-10 align-center-right".split("\\s+"));
    }
}



