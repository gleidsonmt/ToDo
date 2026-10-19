package io.github.gleidsonmt.todo.view.aside;

import io.github.gleidsonmt.glad.controls.icon.Icon;
import io.github.gleidsonmt.todo.view.panel.input.menu.input_menu_items.DueDateContextMenu;
import javafx.geometry.Side;

import java.time.LocalDate;

/**
 * @author Gleidson Neves da Silveira | <a href="mailto:gleidisonmt@gmail.com">gleidisonmt@gmail.com</a> <br>
 * Created on 17/10/2026
 */
public class DueDateLabel extends CustomLabel<LocalDate> {

    public DueDateLabel() {
        super("Add due date", Icon.CALENDAR_MONTH, "");
        setOnMouseClicked(_ -> {
            DueDateContextMenu contextMenu = new DueDateContextMenu(this);
            contextMenu.show(this, Side.BOTTOM, 0,0);
        });
    }
}
