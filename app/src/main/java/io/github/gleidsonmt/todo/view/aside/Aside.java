package io.github.gleidsonmt.todo.view.aside;

import io.github.gleidsonmt.glad.base.drawer.View;
import io.github.gleidsonmt.glad.controls.button.IconButton;
import io.github.gleidsonmt.todo.view.panel.items.FavoriteButton;
import io.github.gleidsonmt.todo.view_model.TaskViewModel;
import io.github.gleidsonmt.todo.view_model.ViewModel;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

/**
 * @author Gleidson Neves da Silveira | <a href="mailto:gleidisonmt@gmail.com">gleidisonmt@gmail.com</a> <br>
 * Created on 12/10/2026
 */
public class Aside extends ScrollPane {

    public Aside(TaskViewModel viewModel) {
        setContent(new Body(viewModel));
        setFitToHeight(true);
    }
}



