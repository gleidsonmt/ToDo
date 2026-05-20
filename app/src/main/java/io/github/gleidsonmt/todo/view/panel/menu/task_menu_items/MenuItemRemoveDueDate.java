

package io.github.gleidsonmt.todo.view.panel.menu.task_menu_items;

import io.github.gleidsonmt.glad.controls.icon.Icon;
import io.github.gleidsonmt.todo.view.panel.menu.TaskMenuItemBase;
import io.github.gleidsonmt.todo.view_model.TaskViewModel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
 * Description:
 *
 * @author Gleidson Neves da Silveira | <a href="mailto:gleidisonmt@gmail.com">gleidisonmt@gmail.com</a> <br>
 * Created On: Mar 08, 2026
 * <p>
 * Version History: Initial version
 */
public class MenuItemRemoveDueDate extends TaskMenuItemBase {

    public MenuItemRemoveDueDate(TaskViewModel item) {
        super(item);
    }

    @Override
    protected void updateState(TaskViewModel item) {
        update("menu.due.date.remove", Icon.EVENT_BUSY);
    }

    @Override
    protected EventHandler<ActionEvent> createEvent(TaskViewModel item) {
        return _ -> item.setDueDate(null);
    }
}
