package io.github.gleidsonmt.todo.view.panel.input;


import io.github.gleidsonmt.glad.controls.icon.Icon;
import io.github.gleidsonmt.todo.global.Global;
import io.github.gleidsonmt.todo.global.Presenter;
import io.github.gleidsonmt.todo.model.List;
import io.github.gleidsonmt.todo.utils.StringUtils;
import io.github.gleidsonmt.todo.view.panel.menu.input_menu_items.CustomContextMenu;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;

import java.util.Optional;

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

            ObservableList<MenuItem> options = FXCollections.observableArrayList();
            task.getValue().forEach(list -> {
                if (!list.isFixed() || list.getId() == 0) {
                    MenuItem menuItem = new MenuItem(StringUtils.name(list.getName()));
                    menuItem.setUserData(list);
                    options.add(menuItem);

                    menuItem.setOnAction(_ -> {
                        getSelected(contextMenu.getItems(), menuItem).ifPresent(el -> contextMenu.getItems().remove(el));
                        contextMenu.getItems().remove(menuItem);
                        value.set(list);
                    });

                }
            });

            this.contextMenu.getItems().setAll(options.filtered(el -> ( (List) el.getUserData()).getId() != 0));
            setValue(task.getValue().getFirst());
        });

        this.addEventHandler(MouseEvent.MOUSE_CLICKED, _ -> {
            this.contextMenu.show(this);
        });
    }

    private Optional<MenuItem> getSelected(ObservableList<MenuItem> options, MenuItem menuItem) {
        return options.stream().filter(el -> value.get().getId() == ( (List) el.getUserData()).getId() ).findAny();
    }
}
