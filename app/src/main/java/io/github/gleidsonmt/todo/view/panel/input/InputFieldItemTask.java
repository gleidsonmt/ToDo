package io.github.gleidsonmt.todo.view.panel.input;


import io.github.gleidsonmt.glad.controls.icon.Icon;
import io.github.gleidsonmt.todo.model.List;
import io.github.gleidsonmt.todo.view.panel.menu.TaskContextMenu;
import io.github.gleidsonmt.todo.view_model.ListViewModel;
import javafx.scene.input.MouseEvent;

/**
 * @author Gleidson Neves da Silveira | <gleidisonmt@gmail.com>
 * Created On: Mar 02, 2026
 */
public class InputFieldItemTask extends InputFieldItem<List> {

    public InputFieldItemTask() {
        super(Icon.HOME, "Select a list");

        this.addEventHandler(MouseEvent.MOUSE_CLICKED, _ -> {
            this.contextMenu = new TaskContextMenu(this);
        });
    }
}
