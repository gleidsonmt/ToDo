

package io.github.gleidsonmt.todo.view.panel.input;


import io.github.gleidsonmt.glad.controls.icon.Icon;
import io.github.gleidsonmt.todo.bd.dao.DaoList;
import io.github.gleidsonmt.todo.global.Global;
import io.github.gleidsonmt.todo.global.Presenter;
import io.github.gleidsonmt.todo.model.List;
import io.github.gleidsonmt.todo.view.panel.input.menu.input_menu_items.CustomContextMenu;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.scene.input.MouseEvent;

/**
 * @author Gleidson Neves da Silveira | <a href="mailto:gleidisonmt@gmail.com">gleidisonmt@gmail.com</a> <br> <br>
 * Created On: Mar 02, 2026
 */
public class InputFieldItemTask extends InputFieldItem<List> {

    public InputFieldItemTask() {
        super(Icon.HOME, "Select a list");

        Task<ObservableList<List>> task = new DaoList().fetch(FXCollections.observableArrayList());
        new Thread(task).start();

        task.setOnSucceeded(_ -> {
            this.contextMenu = new CustomContextMenu<>(this);

            ObservableList<MenuTaskItem> options = FXCollections.observableArrayList();
            task.getValue().forEach(list -> {
                if (!list.fixed() || list.id() == 1) {
                    MenuTaskItem menuItem = new MenuTaskItem(list);
                    options.add(menuItem);

                    menuItem.setOnAction(_ -> {
                        value.set(menuItem.getList());
                        contextMenu.getItems().setAll(options.filtered(el -> el.getList().id() != menuItem.getList().id()));
                    });
                }
            });

            this.contextMenu.getItems().setAll(options.filtered(el -> el.getList().id() != 1));
            setValue(task.getValue().getFirst());
        });

        this.addEventHandler(MouseEvent.MOUSE_CLICKED, _ -> this.contextMenu.show(this));
    }
}