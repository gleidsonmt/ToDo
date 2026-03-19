package io.github.gleidsonmt.todo.view.panel.menu.list_menu_items;

import io.github.gleidsonmt.glad.controls.icon.Icon;
import io.github.gleidsonmt.glad.controls.icon.SVGIcon;
import io.github.gleidsonmt.todo.global.Global;
import io.github.gleidsonmt.todo.global.ListPresenter;
import io.github.gleidsonmt.todo.model.List;
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
            SideNav nav = (SideNav) item.getScene().lookup("#drawer");
            // nav.getChildren().remove(item);
            nav.getItems().remove(item.getViewList());
            ListPresenter presenter = (ListPresenter) Global.get(List.class);
            presenter.delete(item.getViewList().getList());
            // repo.of(List.class).getData().remove(index);
            // repo.delete(item.getViewList().getList());
        });
    }

}
