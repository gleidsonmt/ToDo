package io.github.gleidsonmt.todo.view.panel.menu;

import java.util.List;

import io.github.gleidsonmt.todo.view.nav.CustomDrawerItemNew;
import io.github.gleidsonmt.todo.view.panel.menu.list_menu_items.MenuItemDeleteList;
import io.github.gleidsonmt.todo.view.panel.menu.list_menu_items.MenuItemReanameList;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;

/**
 * Description:
 *
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Created On: Mar 16, 2026
 * <p>
 * Version History: Initial version
 */
public class ListContextMenu extends ContextMenu {

    // private MenuItemDeleteList menuItemDeleteList;
    private CustomDrawerItemNew item;

    public ListContextMenu(CustomDrawerItemNew item) {
        this.item = item;
        getItems().setAll(item.getViewModel().isFixed() ? layoutFixed() : layoutUnfixed());
    }

    @SuppressWarnings("null")
    private List<MenuItem> layoutFixed() {
        // return List<MenuItem>.of(new MenuItemDeleteList());
        return List.of();
    }

    @SuppressWarnings("null")
    private List<MenuItem> layoutUnfixed() {
        return List.of(new MenuItemReanameList(item), new SeparatorMenuItem(), new MenuItemDeleteList(item));
    }

}
