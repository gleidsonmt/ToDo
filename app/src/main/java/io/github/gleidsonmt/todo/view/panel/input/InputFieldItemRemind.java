package io.github.gleidsonmt.todo.view.panel.input;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import io.github.gleidsonmt.glad.controls.icon.Icon;
import io.github.gleidsonmt.todo.model.ToDoTask;
import io.github.gleidsonmt.todo.view.panel.menu.input_menu_items.CustomContextMenu;
import io.github.gleidsonmt.todo.view.panel.menu.input_menu_items.DateUtils;
import io.github.gleidsonmt.todo.view.panel.menu.input_menu_items.RemindContextMenu;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Side;
import javafx.scene.control.Tooltip;

/**
 * Description:
 *
 * @author Gleidson Neves da Silveira | <gleidisonmt@gmail.com>
 * Created On: Mar 02, 2026
 * <p>
 * Version History: Initial version
 */
public class InputFieldItemRemind extends InputFieldItem {

    private final CustomContextMenu<LocalDateTime> contextMenu;

    public InputFieldItemRemind() {
        super(Icon.CLOCK);
        setId("input-field-remind");
        contextMenu = new RemindContextMenu();
//
        Tooltip tooltip = new Tooltip("Add a due date");
        this.setTooltip(tooltip);
//
        contextMenu.valueProperty().addListener((_, _, newValue) -> {
            String init = "Remind at ";
            if (newValue != null) {
                setText(DateUtils.format(init, newValue.toLocalTime(), " at ", newValue.toLocalDate()));
            } else {
                setText(null);
            }
        });

        this.setOnMouseClicked(e -> {
            if (contextMenu.isShowing()) return;

            // Calling when first to avoid the erro on placing the value..
            contextMenu.show(this, Side.TOP, -(contextMenu.getWidth() / 2), 0);
            contextMenu.hide();

            // second show places in the right position
            contextMenu.show(this, Side.TOP, -(contextMenu.getWidth() / 2), 0);

        });
    }

    public LocalDateTime getValue() {
        return contextMenu.getValue();
    }
}
