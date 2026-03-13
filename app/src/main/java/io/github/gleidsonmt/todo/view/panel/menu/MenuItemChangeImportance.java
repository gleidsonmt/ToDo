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
public class MenuItemChangeImportance extends TaskMenuItemBase {

    public MenuItemChangeImportance(TaskItemViewModel item) {
        super(item);
    }

    @Override
    protected void updateState(TaskItemViewModel taskItem) {
        if (!taskItem.isImportant()) {
            this.setGraphic(new SVGIcon(Icon.STAR_HALF));
            this.setText("Mark as Important");
        } else {
            this.setText("Remove Importance");
            this.setGraphic(new SVGIcon(Icon.STAR));
        }
    }

    @Override
    protected EventHandler<ActionEvent> createEvent(TaskItemViewModel item) {
        return _ -> {
            item.setImportant(!item.isImportant());
            item.update();
        };
    }
}
