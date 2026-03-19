package io.github.gleidsonmt.todo.view.panel.menu.task_menu_items;

import io.github.gleidsonmt.glad.controls.icon.Icon;
import io.github.gleidsonmt.todo.view.panel.items.TaskItemViewModel;
import io.github.gleidsonmt.todo.view.panel.menu.TaskMenuItemBase;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
 * Description:
 *
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 *         Created On: Mar 06, 2026
 * 
 *         Version History: Initial version
 */
public class MenuItemComplete extends TaskMenuItemBase {

    public MenuItemComplete(TaskItemViewModel taskItem) {
        super(taskItem);
    }

    @Override
    protected void updateState(TaskItemViewModel item) {
        update(!item.isCompleted() ? "menu.completed.on" : "menu.completed.off",
                item.isCompleted() ? Icon.CIRCLE : Icon.CHECK_CIRCLE);
    }

    @Override
    protected EventHandler<ActionEvent> createEvent(TaskItemViewModel item) {
        return _ -> {
            // update the model
            item.setCompleted(!item.isCompleted());
            item.update();
        };
    }
}
