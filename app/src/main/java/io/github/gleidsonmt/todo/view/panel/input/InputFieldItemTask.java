package io.github.gleidsonmt.todo.view.panel.input;


import io.github.gleidsonmt.glad.controls.icon.Icon;
import io.github.gleidsonmt.todo.global.Global;
import io.github.gleidsonmt.todo.global.Presenter;
import io.github.gleidsonmt.todo.model.List;
import io.github.gleidsonmt.todo.utils.StringUtils;
import io.github.gleidsonmt.todo.view.panel.menu.input_menu_items.CustomContextMenu;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;

import java.util.Objects;

/**
 * @author Gleidson Neves da Silveira | <gleidisonmt@gmail.com>
 * Created On: Mar 02, 2026
 */
public class InputFieldItemTask extends InputFieldItem<List> {

    public InputFieldItemTask() {
        super(Icon.HOME, "Select a list");

        Presenter<List> presenter = Global.get(List.class);
        Task<ObservableList<List>> task = presenter.fetch(FXCollections.observableArrayList());
        new Thread(task).start();

        task.setOnSucceeded(_ -> {
            this.contextMenu = new CustomContextMenu<>(this);

            task.getValue().forEach(list -> Platform.runLater(() -> {
                if ((getValue() != null && !Objects.equals(getValue().getName(), list.getName())) && (!list.isFixed() || list.getId() == 0) ) {
                    MenuItem menuItem = new MenuItem(StringUtils.name(list.getName()));
                    menuItem.setOnAction(_ -> setValue(list));

                    this.contextMenu.getItems().add(menuItem);
                }
            }));
            setValue(task.getValue().getFirst());
        });

        this.addEventHandler(MouseEvent.MOUSE_CLICKED, _ -> this.contextMenu.show(this));

    }
}
