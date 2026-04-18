package io.github.gleidsonmt.todo.view.panel.input;

import io.github.gleidsonmt.glad.controls.icon.Icon;
import io.github.gleidsonmt.glad.controls.icon.SVGIcon;
import io.github.gleidsonmt.todo.model.ToDoTask;
import io.github.gleidsonmt.todo.view.panel.menu.input_menu_items.CustomContextMenu;
import io.github.gleidsonmt.todo.view.panel.menu.input_menu_items.DateUtils;
import io.github.gleidsonmt.todo.view.panel.menu.input_menu_items.DueDateContextMenu;
import io.github.gleidsonmt.todo.view.panel.menu.input_menu_items.GridMenuItem;
import javafx.application.Platform;
import javafx.geometry.Side;
import javafx.scene.control.ContextMenu;

import java.time.DayOfWeek;
import java.time.LocalDate;

/**
 * Description:
 *
 * @author Gleidson Neves da Silveira | <gleidisonmt@gmail.com>
 * Created On: Mar 02, 2026
 * <p>
 * Version History: Initial version
 */
@Deprecated(forRemoval = true)
public class InputFieldItemDueDate extends InputFieldItem<LocalDate> {

    public InputFieldItemDueDate() {
        super(Icon.CALENDAR_MONTH);
        setId("input-field-due-date");
    }
}
