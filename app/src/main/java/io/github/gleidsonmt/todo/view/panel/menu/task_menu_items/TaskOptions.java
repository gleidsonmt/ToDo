

package io.github.gleidsonmt.todo.view.panel.menu.task_menu_items;

import io.github.gleidsonmt.todo.global.Global;
import io.github.gleidsonmt.todo.global.Presenter;
import io.github.gleidsonmt.todo.model.List;
import io.github.gleidsonmt.todo.utils.StringUtils;
import io.github.gleidsonmt.todo.view.panel.Panel;
import io.github.gleidsonmt.todo.view.panel.menu.input_menu_items.CustomContextMenu;
import io.github.gleidsonmt.todo.view_model.ListViewModel;
import io.github.gleidsonmt.todo.view_model.TaskViewModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.scene.control.MenuItem;

/**
 *
 * @author Gleidson Neves da Silveira | <a href="mailto:gleidisonmt@gmail.com">gleidisonmt@gmail.com</a> <br> <br>
 * Created on  16/04/2026
 */
public class TaskOptions {


    private final ObservableList<MenuItem> items = FXCollections.observableArrayList();
    private final CustomContextMenu<ListViewModel> contextMenu;

    public TaskOptions(CustomContextMenu<ListViewModel> contextMenu) {
        this.contextMenu = contextMenu;
    }

    public Task<ObservableList<List>> fetch() {
        Presenter<List> presenter = Global.get(List.class);
        Task<ObservableList<List>> task = presenter.fetch(FXCollections.observableArrayList());
        new Thread(task).start();

        task.setOnSucceeded(_ -> {
            task.getValue().forEach((list) -> {
                // don't add the option the task is already in
//                Panel panel = (Panel) contextMenu.getOwnerNode();
//                var currentId = panel.getListRoot().getActualList().getId();
//                if (list.getId() != item.getListId() && (!list.isFixed() || list.getId() == 0)) {
//                if (list.getId() != currentId && !(list.isFixed() || list.getId() == 0)) {
                    // Platform.runLater(() -> {
                    contextMenu.getItems().add(createMenuList(list));
                    // });
//                }
            });
        });
        return task;
    };

    public ObservableList<MenuItem> getItems() {
        return items;
    }

    private MenuItem createMenuList(List list) {
        MenuItem menuItem = new MenuItem();
        menuItem.setText(StringUtils.name(list.getName()));
        return menuItem;
    }
}