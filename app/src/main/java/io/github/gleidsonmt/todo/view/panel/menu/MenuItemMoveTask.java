package io.github.gleidsonmt.todo.view.panel.menu;

import io.github.gleidsonmt.glad.controls.icon.Icon;
import io.github.gleidsonmt.glad.controls.icon.SVGIcon;
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

        getItems().addAll(new MenuItem("Option 01"), new MenuItem("Option 02"));
    }

}
