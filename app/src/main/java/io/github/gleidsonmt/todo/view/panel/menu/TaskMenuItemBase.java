package io.github.gleidsonmt.todo.view.panel.menu;

import io.github.gleidsonmt.glad.controls.icon.Icon;
import io.github.gleidsonmt.glad.controls.icon.SVGIcon;
import io.github.gleidsonmt.todo.utils.I18n;
import io.github.gleidsonmt.todo.view.panel.containers.ListContainer;
import io.github.gleidsonmt.todo.view.panel.items.TaskItemViewModel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.MenuItem;

/**
 * Description:
 *
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 *         Created On: Mar 08, 2026
 * 
 *         Version History: Initial version
 */
public abstract class TaskMenuItemBase extends MenuItem {

    public TaskMenuItemBase(TaskItemViewModel taskItem) {
        this.setOnAction(e -> {
            createEvent(taskItem).handle(new ActionEvent());
            updateState(taskItem);
        });
        updateState(taskItem);
    }

    protected void update(String text, Icon icon) {
        setText(I18n.get(text));
        setGraphic(new SVGIcon(icon));
    }

    protected ListContainer getListContainer() {
        return (ListContainer) this.getParentPopup().getOwnerWindow().getScene().lookup("#list-container");
    }

    protected abstract void updateState(TaskItemViewModel item);

    protected abstract EventHandler<ActionEvent> createEvent(TaskItemViewModel item);

}
