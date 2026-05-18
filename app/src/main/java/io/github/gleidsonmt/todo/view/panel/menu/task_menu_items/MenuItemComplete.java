

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
 * Created On: Mar 06, 2026
 */
public class MenuItemComplete extends TaskMenuItemBase {

    public MenuItemComplete(TaskViewModel taskItem) {
        super(taskItem);
    }

    @Override
    protected void updateState(TaskViewModel item) {
        update(!item.isCompleted() ? "menu.completed.on" : "menu.completed.off",
                item.isCompleted() ? Icon.CIRCLE : Icon.CHECK_CIRCLE);
    }

    @Override
    protected EventHandler<ActionEvent> createEvent(TaskViewModel item) {
        return _ -> item.setCompleted(!item.isCompleted());
    }
}
