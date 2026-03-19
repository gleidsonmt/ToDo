package io.github.gleidsonmt.todo.view.panel.menu;

import java.time.LocalDate;

import io.github.gleidsonmt.todo.view.panel.items.TaskItemViewModel;
import io.github.gleidsonmt.todo.view.panel.menu.task_menu_items.MenuItemComplete;
import io.github.gleidsonmt.todo.view.panel.menu.task_menu_items.MenuItemDelete;
import io.github.gleidsonmt.todo.view.panel.menu.task_menu_items.MenuItemImportant;
import io.github.gleidsonmt.todo.view.panel.menu.task_menu_items.MenuItemMoveTask;
import io.github.gleidsonmt.todo.view.panel.menu.task_menu_items.MenuItemMyDay;
import io.github.gleidsonmt.todo.view.panel.menu.task_menu_items.MenuItemRemoveDueDate;
import io.github.gleidsonmt.todo.view.panel.menu.task_menu_items.MenuItemToday;
import io.github.gleidsonmt.todo.view.panel.menu.task_menu_items.MenuItemTomorrow;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;

/**
 * Description:
 *
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 *         Created On: Mar 06, 2026
 * 
 *         Version History: Initial version
 */
public class TaskItemContextMenu extends ContextMenu {

    private final MenuItemMyDay menuItemMyDay;
    private final MenuItemImportant menuItemChangeImportance;
    private final MenuItemComplete menuItemComplete;
    private final MenuItemDelete menuItemDelete;

    // private MenuItemDueDate menuItemDueDate;
    private final MenuItemToday menuItemToday;
    private final MenuItemTomorrow menuItemTomorrow;

    private final MenuItemRemoveDueDate menuItemRemoveDueDate;

    private final MenuItemMoveTask menuItemMoveTask;

    private final ObservableList<MenuItem> moveable;

    public TaskItemContextMenu(TaskItemViewModel item) {
        // fixed in top
        this.menuItemMyDay = new MenuItemMyDay(item);
        this.menuItemChangeImportance = new MenuItemImportant(item);
        this.menuItemComplete = new MenuItemComplete(item);
        // moveable
        this.menuItemToday = new MenuItemToday(item);
        this.menuItemTomorrow = new MenuItemTomorrow(item);
        this.menuItemRemoveDueDate = new MenuItemRemoveDueDate(item);
        // fiexd in bottom
        this.menuItemMoveTask = new MenuItemMoveTask(item);
        this.menuItemDelete = new MenuItemDelete(item);

        moveable = FXCollections.observableArrayList();

        getItems().addAll(menuItemMyDay, menuItemChangeImportance, menuItemComplete, new SeparatorMenuItem());
        getItems().addAll(new SeparatorMenuItem(), menuItemMoveTask, new SeparatorMenuItem(), menuItemDelete);

        item.dueDateProperty().addListener((_, _, newVal) -> {
            switchContextItems(newVal);
        });

        switchContextItems(item.getDueDate());
    }

    private void switchContextItems(LocalDate value) {
        getItems().removeAll(moveable);

        if (value != null) {
            if (value.equals(LocalDate.now())) {
                layout(menuItemTomorrow, menuItemRemoveDueDate);
            } else if (value.equals(LocalDate.now().plusDays(1))) {
                layout(menuItemToday, menuItemRemoveDueDate);
            } else {
                layout(menuItemToday, menuItemRemoveDueDate);
            }
        } else {
            layout(menuItemToday, menuItemTomorrow);
        }
    }

    private void layout(MenuItem... menuItems) {
        moveable.setAll(menuItems);
        getItems().addAll(4, moveable);
    }
}