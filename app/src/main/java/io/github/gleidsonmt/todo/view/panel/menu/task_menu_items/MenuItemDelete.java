package io.github.gleidsonmt.todo.view.panel.menu.task_menu_items;

import io.github.gleidsonmt.glad.controls.icon.Icon;
import io.github.gleidsonmt.glad.controls.icon.SVGIcon;
import io.github.gleidsonmt.todo.utils.I18n;
import io.github.gleidsonmt.todo.view.panel.items.TaskItemViewModel;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;

/**
 * Description:
 *
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 *         Created On: Mar 06, 2026
 * 
 *         Version History: Initial version
 */
public class MenuItemDelete extends MenuItem {

    public MenuItemDelete(TaskItemViewModel viewModel) {

        setText("Delete");
        this.setAccelerator(new KeyCodeCombination(KeyCode.DELETE));
        this.getStyleClass().addAll("menu-item-last", "menu-item-delete");
        this.setGraphic(new SVGIcon(Icon.DELETE));
        this.setText(I18n.get("menu.delete.task"));

        this.setOnAction(e -> {
            viewModel.delete();
        });
    }

}
