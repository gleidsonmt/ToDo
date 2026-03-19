package io.github.gleidsonmt.todo.view.panel.menu;

import io.github.gleidsonmt.glad.controls.icon.Icon;
import io.github.gleidsonmt.glad.controls.icon.SVGIcon;
import io.github.gleidsonmt.todo.view.panel.items.TaskItemViewModel;
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
public class MenuItemRemoveDueDate extends TaskMenuItemBase {

    public MenuItemRemoveDueDate(TaskItemViewModel item) {
        super(item);
    }

    @Override
    protected void updateState(TaskItemViewModel item) {
        this.setGraphic(new SVGIcon(Icon.CALENDAR_MONTH));
        this.setText("Remove due date");
    }

    @Override
    protected EventHandler<ActionEvent> createEvent(TaskItemViewModel item) {
        return _ -> {
            item.setDueDate(null);
            item.update();
        };
    }
}
