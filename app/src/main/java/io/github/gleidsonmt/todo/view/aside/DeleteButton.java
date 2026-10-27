package io.github.gleidsonmt.todo.view.aside;

import io.github.gleidsonmt.glad.base.Root;
import io.github.gleidsonmt.glad.controls.icon.Icon;
import io.github.gleidsonmt.glad.controls.icon.SVGIcon;
import io.github.gleidsonmt.todo.global.Global;
import io.github.gleidsonmt.todo.view.panel.events.TaskChangeEvent;
import io.github.gleidsonmt.todo.view_model.TaskViewModel;
import javafx.scene.control.Button;

/**
 * @author Gleidson Neves da Silveira | <a href="mailto:gleidisonmt@gmail.com">gleidisonmt@gmail.com</a> <br>
 * Created on 17/10/2026
 */
public class DeleteButton extends Button {
    public DeleteButton(TaskViewModel viewModel) {
        getStyleClass().addAll("bg-transparent display-graphic".split("\\s+"));
        setGraphic(new SVGIcon(Icon.DELETE));

        setOnAction(_ -> Global.fireEvent(this, new TaskChangeEvent(TaskChangeEvent.DELETE_TASK, viewModel)));
    }
}
