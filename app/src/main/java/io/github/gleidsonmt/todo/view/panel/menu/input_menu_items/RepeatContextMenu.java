package io.github.gleidsonmt.todo.view.panel.menu.input_menu_items;

import io.github.gleidsonmt.glad.controls.icon.Icon;
import io.github.gleidsonmt.glad.controls.icon.SVGIcon;
import io.github.gleidsonmt.todo.model.recurrence.Recurrence;
import io.github.gleidsonmt.todo.view.panel.menu.custom.*;
import javafx.scene.control.MenuItem;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  11/04/2026
 */
public class RepeatContextMenu extends CustomContextMenu<Recurrence> {

    public RepeatContextMenu() {

        MenuItem menuItemDaily = new MenuItem("Daily", new GridIconDaily());
//        MenuItem menuItemWeekDays = new MenuItem("WeekDays", new GridIconWeekDays());
//        MenuItem menuItemWeekly = new MenuItem("Weekly", new GridIconWeekly());
//        MenuItem menuItemMonthly = new MenuItem("Monthly", new GridIconMonthly());
//        MenuItem menuItemYearly = new MenuItem("Yearly", new YearlyIcon());
//        MenuItem menuItemCustom = new MenuItem("Custom", new SVGIcon(Icon.EVENT_REPEAT));

        MenuItem menuItemDelete = new MenuItem("Never repeat", new SVGIcon(Icon.REMOVE));
        menuItemDelete.getStyleClass().addAll("menu-item-delete");

//        this.getItems().addAll(menuItemDaily, menuItemWeekDays, menuItemWeekly, menuItemMonthly, menuItemYearly, menuItemCustom);
        this.getItems().addAll(menuItemDaily);

//        menuItemDaily.setOnAction(e -> update(new Daily()));
    }

    @Override
    protected void update(Recurrence value) {

    }
}
