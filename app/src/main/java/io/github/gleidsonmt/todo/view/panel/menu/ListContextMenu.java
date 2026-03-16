package io.github.gleidsonmt.todo.view.panel.menu;

import java.util.List;

import io.github.gleidsonmt.todo.view.nav.CustomDrawerItem;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;

/**
 * Description:
 *
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 *         Created On: Mar 16, 2026
 * 
 *         Version History: Initial version
 */
public class ListContextMenu extends ContextMenu {

    // private MenuItemDeleteList menuItemDeleteList;
    private CustomDrawerItem item;

    public ListContextMenu(CustomDrawerItem item) {
        this.item = item;
        getItems().setAll(item.getViewList().getList().isFixed() ? layoutFixed() : layoutUnfixed());
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
