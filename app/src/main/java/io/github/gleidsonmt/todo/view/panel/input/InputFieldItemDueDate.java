package io.github.gleidsonmt.todo.view.panel.input;

import io.github.gleidsonmt.glad.controls.icon.Icon;
import io.github.gleidsonmt.glad.controls.icon.SVGIcon;
import io.github.gleidsonmt.todo.model.ToDoTask;
import io.github.gleidsonmt.todo.view.panel.menu.input_menu_items.CustomContextMenu;
import io.github.gleidsonmt.todo.view.panel.menu.input_menu_items.DateUtils;
import io.github.gleidsonmt.todo.view.panel.menu.input_menu_items.DueDateContextMenu;
import io.github.gleidsonmt.todo.view.panel.menu.input_menu_items.GridMenuItem;
import javafx.geometry.Side;
import javafx.scene.control.ContextMenu;

import java.time.DayOfWeek;
import java.time.LocalDate;

/**
 * Description:
 *
 * @author Gleidson Neves da Silveira | <gleidisonmt@gmail.com>
 *         Created On: Mar 02, 2026
 * 
 *         Version History: Initial version
 */
public class InputFieldItemDueDate extends InputFieldItem {

    private final CustomContextMenu<LocalDate> contextMenu;

    public InputFieldItemDueDate() {
        super(Icon.CALENDAR_MONTH);

        setId("input-field-due-date");

        contextMenu = new DueDateContextMenu();

        this.setOnMouseClicked(e -> {
            if (contextMenu.isShowing()) return;

            // Calling when first to avoid the erro on placing the value..
//            contextMenu.show(this, Side.TOP, -(contextMenu.getWidth() / 2), 0);
//            contextMenu.hide();

            // second show places in the right position
            contextMenu.show(this, Side.TOP, -(contextMenu.getWidth() / 2), 0);

        });


    }

    public LocalDate getValue() {
        return contextMenu.getValue();
    }
}
