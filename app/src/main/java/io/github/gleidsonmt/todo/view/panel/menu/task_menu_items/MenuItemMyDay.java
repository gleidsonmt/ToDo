

package io.github.gleidsonmt.todo.view.panel.menu.task_menu_items;

import io.github.gleidsonmt.glad.controls.icon.Icon;
import io.github.gleidsonmt.todo.view.panel.menu.TaskMenuItemBase;
import io.github.gleidsonmt.todo.view_model.TaskViewModel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
 * Description: Change the state of an task item.
 * This in specif puts the task as an task to do in my day list.
 *
 * @author Gleidson Neves da Silveira | <a href="mailto:gleidisonmt@gmail.com">gleidisonmt@gmail.com</a> <br>
 * Created On: Mar 06, 2026
 *
 */
public class MenuItemMyDay extends TaskMenuItemBase {

    public MenuItemMyDay(TaskViewModel item) {
        super(item);
        this.getStyleClass().add("menu-item-first");
    }

    @Override
    protected void updateState(TaskViewModel item) {
        update(!item.isMyDay() ? "menu.myDay.on" : "menu.myDay.off", item.isMyDay() ? Icon.FLARE : Icon.SUN);
    }

    @Override
    protected EventHandler<ActionEvent> createEvent(TaskViewModel item) {
        return _ -> item.setMyDay(!item.isMyDay());
    }
}