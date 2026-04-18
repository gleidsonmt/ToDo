package io.github.gleidsonmt.todo.view.panel.menu;

import io.github.gleidsonmt.todo.global.Global;
import io.github.gleidsonmt.todo.global.Presenter;
import io.github.gleidsonmt.todo.model.List;
import io.github.gleidsonmt.todo.utils.Assets;
import io.github.gleidsonmt.todo.utils.StringUtils;
import io.github.gleidsonmt.todo.view.panel.input.InputFieldItem;
import io.github.gleidsonmt.todo.view.panel.menu.input_menu_items.CustomContextMenu;
import io.github.gleidsonmt.todo.view_model.ListViewModel;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;

/**
 *
 * @author Gleidson Neves da Silveira | <gleidisonmt@gmail.com>
 * Created on  16/04/2026
 */
public class TaskContextMenu extends CustomContextMenu<List> {

    public TaskContextMenu(InputFieldItem<List> inputFieldItem) {
        Presenter<List> presenter = Global.get(List.class);
        Task<ObservableList<List>> task = presenter.fetch(FXCollections.observableArrayList());
        new Thread(task).start();

        task.setOnSucceeded(_ -> {
            task.getValue().forEach(list -> Platform.runLater(() -> {
                if ((!list.isFixed() || list.getId() == 0)) {
                    MenuItem menuItem = new MenuItem(StringUtils.name(list.getName()));
                    menuItem.setOnAction(_ -> setValue(list));





                    getItems().add(menuItem);
                }
            }));
            Platform.runLater(() -> {
                show(inputFieldItem);
            });
        });
    }
}