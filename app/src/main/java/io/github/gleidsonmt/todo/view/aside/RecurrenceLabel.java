package io.github.gleidsonmt.todo.view.aside;

import io.github.gleidsonmt.glad.controls.icon.Icon;
import io.github.gleidsonmt.todo.model.RecurrenceViewModel;
import io.github.gleidsonmt.todo.view.panel.input.menu.input_menu_items.DueDateContextMenu;
import io.github.gleidsonmt.todo.view.panel.input.menu.input_menu_items.RemindContextMenu;
import io.github.gleidsonmt.todo.view.panel.input.menu.input_menu_items.RepeatContextMenu;
import javafx.geometry.Side;
import javafx.scene.control.ContextMenu;

import java.time.LocalDate;

/**
 * @author Gleidson Neves da Silveira | <a href="mailto:gleidisonmt@gmail.com">gleidisonmt@gmail.com</a> <br>
 * Created on 17/10/2026
 */
public class RecurrenceLabel extends CustomLabel<RecurrenceViewModel> {

    public RecurrenceLabel() {
        super("Repeat", Icon.EVENT_REPEAT, "");

        setOnMouseClicked(_ -> {
            ContextMenu contextMenu = new RepeatContextMenu(this);
            contextMenu.show(this, Side.BOTTOM, 0,0);
        });
    }
}
