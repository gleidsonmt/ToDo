package io.github.gleidsonmt.todo.view.panel.menu;

import java.time.LocalDate;

import io.github.gleidsonmt.todo.view.panel.items.TaskItemViewModel;
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
    private final MenuItemChangeImportance menuItemChangeImportance;
    private final MenuItemComplete menuItemComplete;
    private final MenuItemDelete menuItemDelete;

    // private MenuItemDueDate menuItemDueDate;
    private final MenuItemToday menuItemToday;
    private final MenuItemTomorrow menuItemTomorrow;

    private final MenuItemRemoveDueDate menuItemRemoveDueDate;

    private final MenuItemMoveTask menuItemMoveTask;

    private final ObservableList<MenuItem> moveable;

    public TaskItemContextMenu(TaskItemViewModel item) {
        // fixed
        this.menuItemMyDay = new MenuItemMyDay(item);
        this.menuItemChangeImportance = new MenuItemChangeImportance(item);
        this.menuItemComplete = new MenuItemComplete(item);
        // moveable
        this.menuItemToday = new MenuItemToday(item);
        this.menuItemTomorrow = new MenuItemTomorrow(item);
        // this.menuItemDueDate = new MenuItemDueDate();

        this.menuItemRemoveDueDate = new MenuItemRemoveDueDate(item);
        this.menuItemMoveTask = new MenuItemMoveTask();
        this.menuItemDelete = new MenuItemDelete(item);

        moveable = FXCollections.observableArrayList();

        getItems().addAll(menuItemMyDay, menuItemChangeImportance, menuItemComplete, new SeparatorMenuItem());
        getItems().addAll(moveable);
        getItems().addAll(new SeparatorMenuItem(), menuItemMoveTask, new SeparatorMenuItem(), menuItemDelete);

        item.dueDateProperty().addListener((_, _, newVal) -> {
            switchContextItems(newVal);
        });

        switchContextItems(item.getDueDate());
    }

    private void switchContextItems(LocalDate value) {
        if (value != null) {
            if (value.equals(LocalDate.now())) {
                layoutOne();
            } else if (value.equals(LocalDate.now().plusDays(1))) {
                layoutTwo();
            } else {
                layoutThree();
            }
        } else {
            nullLayout();
        }
    }

    private void nullLayout() {
        moveable.setAll(menuItemToday, menuItemTomorrow);
        getItems().addAll(4, moveable);
    }

    private void layoutOne() {
        getItems().removeAll(moveable);
        moveable.addAll(menuItemTomorrow, menuItemRemoveDueDate);
        getItems().addAll(4, moveable);
    }

    private void layoutTwo() {
        getItems().removeAll(moveable);
        moveable.setAll(menuItemToday, menuItemRemoveDueDate);
        getItems().addAll(4, moveable);
    }

    private void layoutThree() {
        getItems().removeAll(moveable);
        moveable.setAll(menuItemToday, menuItemRemoveDueDate);
        getItems().addAll(4, moveable);
    }
}
