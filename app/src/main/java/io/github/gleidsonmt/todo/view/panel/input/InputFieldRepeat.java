

package io.github.gleidsonmt.todo.view.panel.input;

import io.github.gleidsonmt.glad.controls.icon.Icon;
import io.github.gleidsonmt.todo.model.RecurrenceViewModel;
import io.github.gleidsonmt.todo.view.panel.input.menu.input_menu_items.RepeatContextMenu;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author Gleidson Neves da Silveira | <a href="mailto:gleidisonmt@gmail.com">gleidisonmt@gmail.com</a> <br> <br>
 * Created on  21/04/2026
 */
public class InputFieldRepeat extends InputFieldItem<RecurrenceViewModel> {

    public InputFieldRepeat() {
        super(Icon.EVENT_REPEAT, "Repeat");

        this.addEventHandler(MouseEvent.MOUSE_CLICKED, _ -> {
            this.contextMenu = new RepeatContextMenu(this);
            this.contextMenu.show(this);
        });
    }
}
