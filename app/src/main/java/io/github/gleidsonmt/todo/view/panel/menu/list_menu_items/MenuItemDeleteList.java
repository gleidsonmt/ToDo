package io.github.gleidsonmt.todo.view.panel.menu.list_menu_items;

import io.github.gleidsonmt.glad.controls.icon.Icon;
import io.github.gleidsonmt.glad.controls.icon.SVGIcon;
import io.github.gleidsonmt.todo.view.nav.CustomDrawerItemNew;
import io.github.gleidsonmt.todo.view.nav.SideNav;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;

/**
 * Description:
 *
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Created On: Mar 06, 2026
 * <p>
 * Version History: Initial version
 */
public class MenuItemDeleteList extends MenuItem {

    public MenuItemDeleteList(CustomDrawerItemNew item) {

        setText("Delete");
        this.setAccelerator(new KeyCodeCombination(KeyCode.DELETE));
        this.getStyleClass().addAll("menu-item-last", "menu-item-delete");
        this.setGraphic(new SVGIcon(Icon.DELETE));
        this.setText("Delete Task");

        this.setOnAction(e -> {
            item.getViewModel().delete();
            SideNav nav = (SideNav) item.getScene().lookup("#drawer");
            nav.remove(item.getViewModel());
        });
    }

}
