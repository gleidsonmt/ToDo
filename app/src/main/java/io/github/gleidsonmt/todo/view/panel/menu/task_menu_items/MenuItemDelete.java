

package io.github.gleidsonmt.todo.view.panel.menu.task_menu_items;

import io.github.gleidsonmt.glad.controls.icon.Icon;
import io.github.gleidsonmt.glad.controls.icon.SVGIcon;
import io.github.gleidsonmt.todo.utils.I18n;
import io.github.gleidsonmt.todo.view.panel.containers.ListContainer;
import io.github.gleidsonmt.todo.view.panel.events.TaskChangeEvent;
import io.github.gleidsonmt.todo.view.panel.items.TaskItem;
import io.github.gleidsonmt.todo.view_model.TaskViewModel;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;

/**
 * Description:
 *
 * @author Gleidson Neves da Silveira | <a href="mailto:gleidisonmt@gmail.com">gleidisonmt@gmail.com</a> <br>
 * Created On: Mar 06, 2026
 * <p>
 * Version History: Initial version
 */
public class MenuItemDelete extends MenuItem {

    public MenuItemDelete(TaskViewModel viewModel) {

        setText("Delete");
        this.setAccelerator(new KeyCodeCombination(KeyCode.DELETE));
        this.getStyleClass().addAll("menu-item-last", "menu-item-delete");
        this.setGraphic(new SVGIcon(Icon.DELETE));
        this.setText(I18n.get("menu.delete.task"));

        this.setOnAction(_ -> {
            TaskItem item = (TaskItem) getParentPopup().getOwnerNode();
            // first getParent is the Section and the second the ListContainer
            ListContainer listContainer = (ListContainer) item.getParent().getParent();
            listContainer.fireEvent(new TaskChangeEvent(TaskChangeEvent.DELETE_TASK, viewModel));
        });
    }

}
