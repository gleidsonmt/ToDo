package io.github.gleidsonmt.todo.view.panel.menu;

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

    protected ListContainer getListContainer() {
        return (ListContainer) this.getParentPopup().getOwnerWindow().getScene().lookup("#list-container");
    }

    protected abstract void updateState(TaskItemViewModel item);

    protected abstract EventHandler<ActionEvent> createEvent(TaskItemViewModel item);

}
