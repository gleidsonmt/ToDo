

package io.github.gleidsonmt.todo.view.panel.menu.task_menu_items;

import io.github.gleidsonmt.glad.controls.icon.Icon;
import io.github.gleidsonmt.glad.controls.icon.SVGIcon;
import javafx.scene.control.MenuItem;

/**
 * Description:
 *
 * @author Gleidson Neves da Silveira | <a href="mailto:gleidisonmt@gmail.com">gleidisonmt@gmail.com</a> <br>
 * Created On: Mar 08, 2026
 * <p>
 * Version History: Initial version
 */
public class MenuItemDueDate extends MenuItem {

    public MenuItemDueDate() {
        this.setGraphic(new SVGIcon(Icon.CALENDAR_MONTH));

        this.setText("Pick a date");

        // calendar.getSave().setOnAction(event -> {
        // calendar.getCancel().fire();
        // taskItem.setDueDate(calendar.getSelectedDate());
        // });
    }

}
