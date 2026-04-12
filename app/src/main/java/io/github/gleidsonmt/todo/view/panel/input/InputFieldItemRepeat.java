package io.github.gleidsonmt.todo.view.panel.input;

import io.github.gleidsonmt.glad.controls.icon.Icon;
import io.github.gleidsonmt.todo.model.ToDoTask;
import io.github.gleidsonmt.todo.view.panel.menu.input_menu_items.RepeatContextMenu;
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
public class InputFieldItemRepeat extends InputFieldItem {

    private final RepeatContextMenu contextMenu;

    public InputFieldItemRepeat() {
        super(Icon.EVENT_REPEAT);

        this.contextMenu = new RepeatContextMenu();

        Tooltip tooltip = new Tooltip("Repeat \nOpen a recurrence picker.");
        this.setTooltip(tooltip);

        this.setOnMouseClicked(e -> {
//            if (contextMenu.isShowing()) return;
//
//            // Calling when first to avoid the erro on placing the value..
//            contextMenu.show(this, Side.TOP, -(contextMenu.getWidth() / 2), 0);
//            contextMenu.hide();
//
//            // second show places in the right position
            contextMenu.show(this, Side.TOP, -(contextMenu.getWidth() / 2), 0);

        });
//
    }
}
