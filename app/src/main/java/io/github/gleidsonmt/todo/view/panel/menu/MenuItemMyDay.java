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
public class MenuItemMyDay extends TaskMenuItemBase {

    public MenuItemMyDay(TaskItemViewModel item) {
        super(item);
        this.getStyleClass().add("menu-item-first");
    }

    @Override
    protected void updateState(TaskItemViewModel item) {
        if (item.isMyDay()) {
            this.setText("Remove to My Day");
            this.setGraphic(new SVGIcon(Icon.FLARE));
        } else {
            this.setText("Add to My Day");
            this.setGraphic(new SVGIcon(Icon.SUN));
        }
    }

    @Override
    protected EventHandler<ActionEvent> createEvent(TaskItemViewModel item) {
        return _ -> {
            // update the model
            item.setMyDay(true);
            item.update();
        };
    }

}
