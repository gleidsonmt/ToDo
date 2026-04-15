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
public class MenuItemToday extends TaskMenuItemBase {

    public MenuItemToday(TaskViewModel taskItem) {
        super(taskItem);
    }

    @Override
    protected void updateState(TaskViewModel item) {
        update("menu.due.today", Icon.TODAY);
    }

    @Override
    protected EventHandler<ActionEvent> createEvent(TaskViewModel viewModel) {
        return _ -> {
            viewModel.setMyDay(true);
            viewModel.setDueDate(LocalDate.now());
        };
    }

}
