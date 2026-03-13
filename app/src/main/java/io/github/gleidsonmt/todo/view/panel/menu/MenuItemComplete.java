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
        if (item.isCompleted()) {
            this.setGraphic(new SVGIcon(Icon.CIRCLE));
            setText("Mark as not completed");
        } else {
            this.setGraphic(new SVGIcon(Icon.CHECK_CIRCLE));
            setText("Mark as completed");
        }
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
