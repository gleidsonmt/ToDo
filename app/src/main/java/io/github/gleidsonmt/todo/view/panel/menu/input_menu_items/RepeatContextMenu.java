package io.github.gleidsonmt.todo.view.panel.menu.input_menu_items;

import io.github.gleidsonmt.glad.base.Root;
import io.github.gleidsonmt.glad.controls.icon.Icon;
import io.github.gleidsonmt.glad.controls.icon.SVGIcon;
import io.github.gleidsonmt.todo.model.List;
import io.github.gleidsonmt.todo.model.recurrence.*;
import io.github.gleidsonmt.todo.view.panel.menu.grid_icons.*;
import io.github.gleidsonmt.todo.view.panel.menu.input_menu_items.custom_panels.RecurrenceBox;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  11/04/2026
 */
public class RepeatContextMenu extends CustomContextMenu<Recurrence> {

    public RepeatContextMenu() {
        MenuItem menuItemDaily = new MenuItem("Daily", new GridIconDaily());
        MenuItem menuItemWeekDays = new MenuItem("WeekDays", new GridIconWeekDays());
        MenuItem menuItemWeekly = new MenuItem("Weekly", new GridIconWeekly());
        MenuItem menuItemMonthly = new MenuItem("Monthly", new GridIconMonthly());
        MenuItem menuItemYearly = new MenuItem("Yearly", new GridIconYearly());
        MenuItem menuItemCustom = new MenuItem("Custom", new SVGIcon(Icon.EVENT_REPEAT));

        MenuItem menuItemDelete = new MenuItem("Never repeat", new SVGIcon(Icon.DELETE));
        menuItemDelete.getStyleClass().addAll("menu-item-delete");

        this.getItems().addAll(menuItemDaily, menuItemWeekDays, menuItemWeekly, menuItemMonthly, menuItemYearly, menuItemCustom);

        menuItemDaily.setOnAction(_ -> setValue(new Daily()));
        menuItemWeekDays.setOnAction(_ -> setValue(new Weekly(true)));
        menuItemWeekly.setOnAction(_ -> setValue(new Weekly()));
        menuItemMonthly.setOnAction(_ -> setValue(new Monthly()));
        menuItemYearly.setOnAction(_ -> setValue(new Yearly()));
        menuItemDelete.setOnAction(_ -> setValue(null));

        selected.addListener((_, _, newValue) -> {
            var separator = new SeparatorMenuItem();
            if (newValue) {
                getItems().addAll(separator, menuItemDelete);
            } else {
                getItems().remove(getItems().size() - 2, getItems().size());
            }
        });

        RecurrenceBox container = new RecurrenceBox();

        container.setOnSave(e -> {
            setValue(container.getSelected());
            container.getOnCancel().handle(new ActionEvent());
        });

        container.setOnCancel(_ -> ((Root) getOwnerWindow().getScene().getRoot())
                .flow().hide());

        menuItemCustom.setOnAction(_ -> {
            ((Root) getOwnerWindow().getScene().getRoot())
                    .flow()
                    .width(300)
                    .content(container)
                    .pos(Pos.TOP_LEFT)
                    .show(target);
        });
    }
}