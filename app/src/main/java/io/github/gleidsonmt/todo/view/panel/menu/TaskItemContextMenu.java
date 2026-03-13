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

    private MenuItemMyDay menuItemMyDay;
    private MenuItemChangeImportance menuItemChangeImportance;
    private MenuItemComplete menuItemComplete;
    private MenuItemDelete menuItemDelete;

    // private MenuItemDueDate menuItemDueDate;
    private MenuItemToday menuItemToday;
    private MenuItemTomorrow menuItemTomorrow;

    private MenuItemRemoveDueDate menuItemRemoveDueDate;

    private ObservableList<MenuItem> moveable;

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

        this.menuItemDelete = new MenuItemDelete(item);
        moveable = FXCollections.observableArrayList();

        getItems().addAll(menuItemMyDay, menuItemChangeImportance, menuItemComplete, new SeparatorMenuItem());

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
        getItems().removeAll(moveable);
        // moveable.setAll(menuItemToday, menuItemTomorrow, menuItemDueDate, new
        // SeparatorMenuItem(), menuItemDelete);

        moveable.setAll(menuItemToday, menuItemTomorrow, new SeparatorMenuItem(), menuItemDelete);
        getItems().addAll(moveable);
    }

    private void layoutOne() {
        getItems().removeAll(moveable);
        // moveable.setAll(menuItemTomorrow, menuItemDueDate,
        // menuItemRemoveDueDate, new SeparatorMenuItem(),
        // menuItemDelete);
        moveable.setAll(menuItemTomorrow, menuItemRemoveDueDate, new SeparatorMenuItem(), menuItemDelete);
        getItems().addAll(moveable);
    }

    private void layoutTwo() {
        getItems().removeAll(moveable);
        // moveable.setAll(menuItemToday, menuItemDueDate,
        // menuItemRemoveDueDate, new SeparatorMenuItem(), menuItemDelete);
        moveable.setAll(menuItemToday, menuItemRemoveDueDate, new SeparatorMenuItem(), menuItemDelete);
        getItems().addAll(moveable);
    }

    private void layoutThree() {
        getItems().removeAll(moveable);
        // moveable.setAll(menuItemToday, menuItemTomorrow, menuItemDueDate,
        // menuItemRemoveDueDate,
        // new SeparatorMenuItem(), menuItemDelete);
        moveable.setAll(menuItemToday, menuItemRemoveDueDate, new SeparatorMenuItem(), menuItemDelete);
        getItems().addAll(moveable);
    }
}
