

package io.github.gleidsonmt.todo.view.panel.input;

import io.github.gleidsonmt.glad.controls.icon.Icon;
import io.github.gleidsonmt.todo.model.recurrence.Recurrence;
import io.github.gleidsonmt.todo.view.panel.menu.input_menu_items.RemindContextMenu;
import io.github.gleidsonmt.todo.view.panel.menu.input_menu_items.RepeatContextMenu;
import javafx.scene.input.MouseEvent;

import java.time.LocalDateTime;

/**
 *
 * @author Gleidson Neves da Silveira | <a href="mailto:gleidisonmt@gmail.com">gleidisonmt@gmail.com</a> <br>
 * Created on  21/04/2026
 */
public class InputFieldRepeat extends InputFieldItem<Recurrence> {

    public InputFieldRepeat() {
        super(Icon.EVENT_REPEAT, "Repeat");

        this.addEventHandler(MouseEvent.MOUSE_CLICKED, _ -> {
            this.contextMenu = new RepeatContextMenu(this);
            this.contextMenu.show(this);
        });
    }
}
