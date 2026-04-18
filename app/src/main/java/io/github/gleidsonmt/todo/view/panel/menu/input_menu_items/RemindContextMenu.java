package io.github.gleidsonmt.todo.view.panel.menu.input_menu_items;

import io.github.gleidsonmt.glad.base.Root;
import io.github.gleidsonmt.glad.controls.icon.Icon;
import io.github.gleidsonmt.todo.model.List;
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
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  09/04/2026
 */
public class RemindContextMenu extends CustomContextMenu<LocalDateTime> {

    public RemindContextMenu() {

        GridMenuItem menuItemToday = new GridMenuItem("Later Today", Icon.SHARE_ETA, DateUtils.format(LocalTime.of(19, 0)));
        GridMenuItem menuItemTomorrow = new GridMenuItem("Tomorrow", Icon.SHARE_ETA, DateUtils.format(LocalDate.now().plusDays(1), LocalTime.of(9, 0)));
        GridMenuItem menuItemNextWeek = new GridMenuItem("Next Week", Icon.SHARE_ETA, DateUtils.format(LocalDate.now().with(DayOfWeek.SUNDAY), LocalTime.of(9, 0)));
        GridMenuItem menuItemPickDateAndTime = new GridMenuItem("Pick a Date and Time", Icon.CALENDAR_CLOCK);
        GridMenuItem menuItemDelete = new GridMenuItem("Remove reminder", Icon.DELETE);
        menuItemDelete.getStyleClass().addAll("menu-item-delete");

        this.getItems().addAll(menuItemToday, menuItemTomorrow, menuItemNextWeek, menuItemPickDateAndTime);

        menuItemToday.setOnAction(_ -> setValue(compose(LocalDate.now(), LocalTime.of(19, 0))));
        menuItemTomorrow.setOnAction(_ -> setValue(compose(LocalDate.now().plusDays(1), LocalTime.now())));
        menuItemNextWeek.setOnAction(_ -> setValue(compose(LocalDate.now().with(DayOfWeek.SUNDAY), LocalTime.now())));
        menuItemDelete.setOnAction(_ -> setValue(null));

        menuItemPickDateAndTime.setOnAction(_ -> {

            CalendarTimePane container = new CalendarTimePane();
            Root root = (Root) getOwnerWindow().getScene().getRoot();

            root.flow()
                    .content(container)
                    .width(280)
                    .pos(Pos.TOP_LEFT)
                    .show(target);

            container.setOnSave(_ -> {
                if (container.getSelected() == null) {
                    setValue(compose(LocalDate.now(), container.getTime()));
                } else setValue(compose(container.getSelected(), container.getTime()));
                root.flow().remove(container);
            });
        });

        selected.addListener((_, _, newValue) -> {
            var separator = new SeparatorMenuItem();
            if (newValue) {
                getItems().addAll(separator, menuItemDelete);
            } else {
                getItems().remove(getItems().size() - 2, getItems().size());
            }
        });
    }

    private LocalDateTime compose(LocalDate date, LocalTime time) {
        return LocalDateTime.of(date, time);
    }
}
