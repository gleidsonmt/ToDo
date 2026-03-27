package io.github.gleidsonmt.todo.view.panel.menu.task_menu_items;

import io.github.gleidsonmt.glad.controls.icon.Icon;
import io.github.gleidsonmt.glad.controls.icon.SVGIcon;
import io.github.gleidsonmt.todo.global.Global;
import io.github.gleidsonmt.todo.global.Presenter;
import io.github.gleidsonmt.todo.model.List;
import io.github.gleidsonmt.todo.utils.I18n;
import io.github.gleidsonmt.todo.view_model.TaskViewModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;

/**
 * Description: It's a menu in task item that moves the task from a list to
 * another.
 *
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 *         Created On: Mar 18, 2026
 * 
 */
public class MenuItemMoveTask extends Menu {

    public MenuItemMoveTask(TaskViewModel item) {
        setText(I18n.get("menu.moveTo"));
        setGraphic(new SVGIcon(Icon.FLEX_DIRECTION));

        Presenter<List> presenter = Global.get(List.class);
        Task<ObservableList<List>> task = presenter.fetch(FXCollections.observableArrayList());
        new Thread(task).start();

        task.setOnSucceeded(_ -> {

            task.getValue().forEach((list) -> {
                // don't add the option the task is already in
                if (list.getId() != item.getListId()) {
                    // Platform.runLater(() -> {

                    getItems().add(new MenuList(list, item));
                    // });

                }
            });

        });
    }

}

class MenuList extends MenuItem {

    public MenuList(List list, TaskViewModel item) {
        setText(list.getName());
        setGraphic(new SVGIcon(list.getIcon()));

        setOnAction(e -> {
            item.setListId(list.getId());
        });
    }

}