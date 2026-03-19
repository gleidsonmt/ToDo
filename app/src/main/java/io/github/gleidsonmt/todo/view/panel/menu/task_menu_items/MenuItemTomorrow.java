package io.github.gleidsonmt.todo.view.panel.menu.task_menu_items;

import java.time.LocalDate;

import io.github.gleidsonmt.glad.controls.icon.Icon;
import io.github.gleidsonmt.glad.controls.icon.SVGIcon;
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
public class MenuItemTomorrow extends TaskMenuItemBase {

    public MenuItemTomorrow(TaskItemViewModel item) {
        super(item);
    }

    @Override
    protected void updateState(TaskItemViewModel item) {
        this.setGraphic(new SVGIcon(Icon.DATE_RANGE));
        this.setText("Due Tomorrow");
    }

    @Override
    protected EventHandler<ActionEvent> createEvent(TaskItemViewModel item) {
        return e -> {
            item.setDueDate(LocalDate.now().plusDays(1));
            item.update();
        };
    }

}
