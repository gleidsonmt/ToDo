

package io.github.gleidsonmt.todo.view.panel.menu.input_menu_items;

import io.github.gleidsonmt.glad.base.Root;
import io.github.gleidsonmt.glad.controls.icon.Icon;
import io.github.gleidsonmt.todo.model.List;
import io.github.gleidsonmt.todo.view.panel.input.InputFieldItem;
import io.github.gleidsonmt.todo.view.panel.menu.input_menu_items.custom_panels.CalendarPane;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.geometry.Pos;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;

import java.time.DayOfWeek;
import java.time.LocalDate;

/**
 * @author Gleidson Neves da Silveira | <a href="mailto:gleidisonmt@gmail.com">gleidisonmt@gmail.com</a> <br>
 * Create on  09/04/2026
 */
public class DueDateContextMenu extends CustomContextMenu<LocalDate> {

    public DueDateContextMenu(InputFieldItem<LocalDate> target) {
        super(target);
        
        GridMenuItem menuItemToday = new GridMenuItem("Today", Icon.TODAY, DateUtils.formatDay(LocalDate.now()));
        GridMenuItem menuItemTomorrow = new GridMenuItem("Tomorrow", Icon.DATE_RANGE, DateUtils.formatDay(LocalDate.now().plusDays(1)));
        GridMenuItem menuItemNextWeek = new GridMenuItem("Next Week", Icon.EVENT_UPCOMING, DateUtils.formatDay(LocalDate.now().with(DayOfWeek.MONDAY)));
        GridMenuItem menuItemPickADate = new GridMenuItem("Pick a date", Icon.CALENDAR_MONTH);
        GridMenuItem menuItemDelete = new GridMenuItem("Remove due date", Icon.DELETE);

        getItems().addAll(menuItemToday, menuItemTomorrow, menuItemNextWeek, menuItemPickADate);

        menuItemToday.setOnAction(_ -> target.setValue(LocalDate.now()));
        menuItemTomorrow.setOnAction(_ -> target.setValue(LocalDate.now().plusDays(1)));
        menuItemNextWeek.setOnAction(_ -> target.setValue(LocalDate.now().plusWeeks(1)));
        menuItemDelete.setOnAction(_ -> target.setValue(null));

        menuItemPickADate.setOnAction(_ -> {
            CalendarPane container = new CalendarPane();
            Root root = (Root) getOwnerWindow().getScene().getRoot();

            root.flow()
                    .content(container)
                    .width(300)
                    .pos(Pos.TOP_LEFT)
                    .show(target);

            container.setOnSave(_ -> {
                if (container.getSelected() == null) target.setValue(LocalDate.now());
                else target.setValue(container.getSelected());
                root.flow().remove(container);
            });

        });

        menuItemDelete.getStyleClass().addAll("menu-item-delete");

        if (target.getValue() != null) {
            var separator = new SeparatorMenuItem();
            getItems().addAll(separator, menuItemDelete);
        }
    }
}