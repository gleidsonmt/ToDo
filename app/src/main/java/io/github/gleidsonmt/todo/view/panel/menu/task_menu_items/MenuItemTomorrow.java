package io.github.gleidsonmt.todo.view.panel.menu.task_menu_items;

import java.time.LocalDate;

import io.github.gleidsonmt.glad.controls.icon.Icon;
import io.github.gleidsonmt.todo.view.panel.menu.TaskMenuItemBase;
import io.github.gleidsonmt.todo.view_model.TaskViewModel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
 * Description:
 *
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Created On: Mar 08, 2026
 * <p>
 * Version History: Initial version
 */
public class MenuItemTomorrow extends TaskMenuItemBase {

    public MenuItemTomorrow(TaskViewModel item) {
        super(item);
    }

    @Override
    protected void updateState(TaskViewModel item) {
        update("menu.due.tomorrow", Icon.DATE_RANGE);
    }

    @Override
    protected EventHandler<ActionEvent> createEvent(TaskViewModel item) {
        return e -> {
            item.setDueDate(LocalDate.now().plusDays(1));
            item.update();
        };
    }

}
