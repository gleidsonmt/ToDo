package io.github.gleidsonmt.todo.view.panel.menu.list_menu_items;

import io.github.gleidsonmt.glad.controls.icon.Icon;
import io.github.gleidsonmt.glad.controls.icon.SVGIcon;
import io.github.gleidsonmt.todo.view.nav.CustomDrawerItem;
import io.github.gleidsonmt.todo.view.nav.SideNav;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;

/**
 * Description:
 *
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 *         Created On: Mar 16, 2026
 * 
 *         Version History: Initial version
 */
public class MenuItemReanameList extends MenuItem {

    public MenuItemReanameList(CustomDrawerItem item) {
        this.getStyleClass().addAll("menu-item-rename");
        this.setGraphic(new SVGIcon(Icon.EDIT_NOTE));
        this.setAccelerator(new KeyCodeCombination(KeyCode.F2));
        this.setText("Rename List");
        this.setOnAction(e -> {
            var nav = (SideNav) item.getScene().lookup("#drawer");
            nav.select(item.getViewList());
            item.setEditable(true);
        });
    }

}
