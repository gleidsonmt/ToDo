package io.github.gleidsonmt.todo.view.panel.menu;

import io.github.gleidsonmt.glad.controls.icon.Icon;
import io.github.gleidsonmt.glad.controls.icon.SVGIcon;
import io.github.gleidsonmt.todo.global.Repository;
import io.github.gleidsonmt.todo.view.nav.CustomDrawerItem;
import io.github.gleidsonmt.todo.view.nav.SideNav;
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
public class MenuItemDeleteList extends MenuItem {

    public MenuItemDeleteList(CustomDrawerItem item) {

        setText("Delete");
        this.setAccelerator(new KeyCodeCombination(KeyCode.DELETE));
        this.getStyleClass().addAll("menu-item-last", "menu-item-delete");
        this.setGraphic(new SVGIcon(Icon.DELETE));
        this.setText("Delete Task");

        this.setOnAction(e -> {
            // viewModel.delete();
            Repository repo = (Repository) System.getProperties().get("repository");
            SideNav nav = (SideNav) item.getScene().lookup("#drawer");
            nav.getChildren().remove(item);
            repo.delete(item.getViewList().getList());
        });
    }

}
