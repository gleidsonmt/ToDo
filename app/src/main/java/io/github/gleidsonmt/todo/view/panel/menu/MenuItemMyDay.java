package io.github.gleidsonmt.todo.view.panel.menu;

import io.github.gleidsonmt.glad.controls.icon.Icon;
import io.github.gleidsonmt.glad.controls.icon.SVGIcon;
import io.github.gleidsonmt.todo.utils.I18n;
import io.github.gleidsonmt.todo.view.panel.items.TaskItemViewModel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
 * Description:
 *
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 *         Created On: Mar 06, 2026
 * 
 */
public class MenuItemMyDay extends TaskMenuItemBase {

    public MenuItemMyDay(TaskItemViewModel item) {
        super(item);
        this.getStyleClass().add("menu-item-first");
    }

    @Override
    protected void updateState(TaskItemViewModel item) {
        this.setText(I18n.get(!item.isMyDay() ? "menu.myDay.on" : "menu.myDay.off"));
        this.setGraphic(new SVGIcon(item.isMyDay() ? Icon.FLARE : Icon.SUN));
    }

    @Override
    protected EventHandler<ActionEvent> createEvent(TaskItemViewModel item) {
        return _ -> {
            // update the model
            item.setMyDay(!item.isMyDay());
            item.update();
        };
    }

}
