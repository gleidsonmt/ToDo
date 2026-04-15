package io.github.gleidsonmt.todo.view.panel.menu;

import io.github.gleidsonmt.glad.controls.icon.Icon;
import io.github.gleidsonmt.glad.controls.icon.SVGIcon;
import io.github.gleidsonmt.todo.utils.I18n;
import io.github.gleidsonmt.todo.view.panel.containers.ListContainer;
import io.github.gleidsonmt.todo.view_model.TaskViewModel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.MenuItem;

/**
 * Description:
 *
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Created On: Mar 08, 2026
 */
public abstract class TaskMenuItemBase extends MenuItem {

    public TaskMenuItemBase(TaskViewModel taskItem) {
        this.setOnAction(_ -> {
            createEvent(taskItem).handle(new ActionEvent());
            updateState(taskItem);
        });
        updateState(taskItem);
    }

    protected void update(String text, Icon icon) {
        setText(I18n.get(text));
        setGraphic(new SVGIcon(icon));
    }

    protected abstract void updateState(TaskViewModel item);

    protected abstract EventHandler<ActionEvent> createEvent(TaskViewModel item);
}
