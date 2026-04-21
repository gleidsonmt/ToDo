package io.github.gleidsonmt.todo.view.panel.input;

import io.github.gleidsonmt.glad.controls.icon.Icon;
import io.github.gleidsonmt.todo.view.panel.menu.input_menu_items.DueDateContextMenu;
import io.github.gleidsonmt.todo.view.panel.menu.input_menu_items.RemindContextMenu;
import javafx.scene.input.MouseEvent;

import java.time.LocalDateTime;

/**
 *
 * @author Gleidson Neves da Silveira | <gleidisonmt@gmail.com>
 * Created on  21/04/2026
 */
public class InputFieldRemind extends InputFieldItem<LocalDateTime> {

    public InputFieldRemind() {
        super(Icon.CLOCK, "Remind me");

        this.addEventHandler(MouseEvent.MOUSE_CLICKED, _ -> {
            this.contextMenu = new RemindContextMenu(this);
            this.contextMenu.show(this);
        });
    }
}
