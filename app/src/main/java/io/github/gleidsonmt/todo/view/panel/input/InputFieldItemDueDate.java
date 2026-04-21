package io.github.gleidsonmt.todo.view.panel.input;

import io.github.gleidsonmt.glad.controls.icon.Icon;
import io.github.gleidsonmt.todo.view.panel.menu.input_menu_items.DueDateContextMenu;
import javafx.scene.input.MouseEvent;

import java.time.LocalDate;

/**
 * Description:
 *
 * @author Gleidson Neves da Silveira | <gleidisonmt@gmail.com>
 * Created On: Mar 02, 2026
 * <p>
 * Version History: Initial version
 */
public class InputFieldItemDueDate extends InputFieldItem<LocalDate> {



    public InputFieldItemDueDate() {
        super(Icon.CALENDAR_MONTH, "Add a due date");
        setId("input-field-due-date");

        this.addEventHandler(MouseEvent.MOUSE_CLICKED, _ -> {
            this.contextMenu = new DueDateContextMenu(this);
            this.contextMenu.show(this);
        });
    }
}
