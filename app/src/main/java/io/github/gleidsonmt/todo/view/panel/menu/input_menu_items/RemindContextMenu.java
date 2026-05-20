

package io.github.gleidsonmt.todo.view.panel.menu.input_menu_items;

import io.github.gleidsonmt.glad.base.Root;
import io.github.gleidsonmt.glad.controls.icon.Icon;
import io.github.gleidsonmt.todo.model.List;
import io.github.gleidsonmt.todo.view.panel.input.InputFieldItem;
import io.github.gleidsonmt.todo.view.panel.menu.input_menu_items.custom_panels.CalendarTimePane;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.geometry.Pos;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * @author Gleidson Neves da Silveira | <a href="mailto:gleidisonmt@gmail.com">gleidisonmt@gmail.com</a> <br>
 * Create on  09/04/2026
 */
public class RemindContextMenu extends CustomContextMenu<LocalDateTime> {

    public RemindContextMenu(InputFieldItem<LocalDateTime> target) {
        super(target);
        GridMenuItem menuItemToday = new GridMenuItem("Later Today", Icon.SHARE_ETA, DateUtils.format(LocalTime.of(19, 0)));
        GridMenuItem menuItemTomorrow = new GridMenuItem("Tomorrow", Icon.SHARE_ETA, DateUtils.format(LocalDate.now().plusDays(1), LocalTime.of(9, 0)));
        GridMenuItem menuItemNextWeek = new GridMenuItem("Next Week", Icon.SHARE_ETA, DateUtils.format(LocalDate.now().with(DayOfWeek.SUNDAY), LocalTime.of(9, 0)));
        GridMenuItem menuItemPickDateAndTime = new GridMenuItem("Pick a Date and Time", Icon.CALENDAR_CLOCK);
        GridMenuItem menuItemDelete = new GridMenuItem("Remove reminder", Icon.DELETE);
        menuItemDelete.getStyleClass().addAll("menu-item-delete");

        this.getItems().addAll(menuItemToday, menuItemTomorrow, menuItemNextWeek, menuItemPickDateAndTime);

        menuItemToday.setOnAction(_ -> target.setValue(compose(LocalDate.now(), LocalTime.of(19, 0))));
        menuItemTomorrow.setOnAction(_ -> target.setValue(compose(LocalDate.now().plusDays(1), LocalTime.now())));
        menuItemNextWeek.setOnAction(_ -> target.setValue(compose(LocalDate.now().with(DayOfWeek.SUNDAY), LocalTime.now())));
        menuItemDelete.setOnAction(_ -> target.setValue(null));

        menuItemPickDateAndTime.setOnAction(_ -> {

            CalendarTimePane container = new CalendarTimePane();
            Root root = (Root) getOwnerWindow().getScene().getRoot();

            root.flow()
                    .content(container)
                    .width(280)
                    .pos(target.isCenter() ? Pos.TOP_CENTER : Pos.TOP_LEFT)
                    .show(target);

            container.setOnSave(_ -> {
                if (container.getSelected() == null) {
                    target.setValue(compose(LocalDate.now(), container.getTime()));
                } else target.setValue(compose(container.getSelected(), container.getTime()));
                root.flow().remove(container);
            });
        });

        if (target.getValue() != null) {
            var separator = new SeparatorMenuItem();
            getItems().addAll(separator, menuItemDelete);
        }
    }

    private LocalDateTime compose(LocalDate date, LocalTime time) {
        return LocalDateTime.of(date, time);
    }
}
