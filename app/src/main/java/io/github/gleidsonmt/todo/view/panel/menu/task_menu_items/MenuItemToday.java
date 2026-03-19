package io.github.gleidsonmt.todo.view.panel.menu.task_menu_items;

import java.time.LocalDate;

import io.github.gleidsonmt.glad.controls.icon.Icon;
import io.github.gleidsonmt.todo.view.panel.items.TaskItemViewModel;
import io.github.gleidsonmt.todo.view.panel.menu.TaskMenuItemBase;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
 * Description:
 *
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 *         Created On: Mar 08, 2026
 * 
 *         Version History: Initial version
 */
public class MenuItemToday extends TaskMenuItemBase {

    public MenuItemToday(TaskItemViewModel taskItem) {
        super(taskItem);
    }

    @Override
    protected void updateState(TaskItemViewModel item) {
        update("menu.due.today", Icon.TODAY);
    }

    @Override
    protected EventHandler<ActionEvent> createEvent(TaskItemViewModel viewModel) {
        return _ -> {
            viewModel.setMyDay(true);
            viewModel.setDueDate(LocalDate.now());
            viewModel.update();
        };
    }

}
