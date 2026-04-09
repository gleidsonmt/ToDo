package io.github.gleidsonmt.todo.view.panel.menu.task_menu_items;

import io.github.gleidsonmt.glad.controls.icon.Icon;
import io.github.gleidsonmt.glad.controls.icon.SVGIcon;
import io.github.gleidsonmt.todo.utils.I18n;
import io.github.gleidsonmt.todo.view.panel.ListRootNew;
import io.github.gleidsonmt.todo.view_model.TaskViewModel;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.stage.Stage;

/**
 * Description:
 *
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 *         Created On: Mar 06, 2026
 * 
 *         Version History: Initial version
 */
public class MenuItemDelete extends MenuItem {

    public MenuItemDelete(TaskViewModel viewModel) {

        setText("Delete");
        this.setAccelerator(new KeyCodeCombination(KeyCode.DELETE));
        this.getStyleClass().addAll("menu-item-last", "menu-item-delete");
        this.setGraphic(new SVGIcon(Icon.DELETE));
        this.setText(I18n.get("menu.delete.task"));

        this.setOnAction(e -> {
            Stage stage = (Stage) this.getParentPopup().getOwnerWindow();
            ListRootNew listRoot = (ListRootNew) stage.getScene().lookup("#list-root");
            listRoot.getData().removeIf(el -> el.getId() == viewModel.getId());
            viewModel.delete();
        });
    }

}
