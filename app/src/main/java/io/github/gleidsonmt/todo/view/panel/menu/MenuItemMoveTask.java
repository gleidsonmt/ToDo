package io.github.gleidsonmt.todo.view.panel.menu;

import io.github.gleidsonmt.glad.controls.icon.Icon;
import io.github.gleidsonmt.glad.controls.icon.SVGIcon;
import io.github.gleidsonmt.todo.global.Global;
import io.github.gleidsonmt.todo.global.Presenter;
import io.github.gleidsonmt.todo.model.List;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;

/**
 * Description:
 *
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 *         Created On: Mar 18, 2026
 * 
 *         Version History: Initial version
 */
public class MenuItemMoveTask extends Menu {

    public MenuItemMoveTask() {
        setText("Move task to");
        setGraphic(new SVGIcon(Icon.FLEX_DIRECTION));

        Presenter<List> presenter = Global.get(List.class);

        presenter.getData().forEach(list -> {
            getItems().add(new MenuList(list));
        });
    }

}

class MenuList extends MenuItem {

    public MenuList(List list) {
        setText(list.getName());
    }

}