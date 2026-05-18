

package io.github.gleidsonmt.todo.view.panel.menu.list_menu_items;

import io.github.gleidsonmt.glad.controls.icon.Icon;
import io.github.gleidsonmt.glad.controls.icon.SVGIcon;
import io.github.gleidsonmt.todo.view.nav.DrawerItem;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;

/**
 * Description:
 *
 * @author Gleidson Neves da Silveira | <a href="mailto:gleidisonmt@gmail.com">gleidisonmt@gmail.com</a>
 * Created On: Mar 16, 2026
 * <p>
 * Version History: Initial version
 */
public class MenuItemReanameList extends MenuItem {

    public MenuItemReanameList(DrawerItem item) {
        this.getStyleClass().addAll("menu-item-rename");
        this.setGraphic(new SVGIcon(Icon.EDIT_NOTE));
        this.setAccelerator(new KeyCodeCombination(KeyCode.F2));
        this.setText("Rename List");
        this.setOnAction(e -> {
            // var nav = (SideNav) item.getScene().lookup("#drawer");
            // nav.select(item.getViewList());
            item.setEditable(true);
        });
    }

}
