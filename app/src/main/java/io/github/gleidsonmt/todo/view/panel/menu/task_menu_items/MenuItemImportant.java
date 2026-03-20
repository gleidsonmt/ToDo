package io.github.gleidsonmt.todo.view.panel.menu.task_menu_items;

import io.github.gleidsonmt.glad.controls.icon.Icon;
import io.github.gleidsonmt.todo.view.panel.menu.TaskMenuItemBase;
import io.github.gleidsonmt.todo.view_model.TaskViewModel;
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
public class MenuItemImportant extends TaskMenuItemBase {

    public MenuItemImportant(TaskViewModel item) {
        super(item);
    }

    @Override
    protected void updateState(TaskViewModel model) {
        update(model.isImportant() ? "menu.important.off" : "menu.important.on",
                !model.isImportant() ? Icon.STAR_HALF : Icon.STAR);
    }

    @Override
    protected EventHandler<ActionEvent> createEvent(TaskViewModel item) {
        return _ -> {
            item.setImportant(!item.isImportant());
            item.update();
        };
    }
}
