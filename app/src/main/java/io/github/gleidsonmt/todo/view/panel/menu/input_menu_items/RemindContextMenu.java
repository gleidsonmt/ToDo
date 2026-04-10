package io.github.gleidsonmt.todo.view.panel.menu.input_menu_items;

import io.github.gleidsonmt.glad.base.Root;
import io.github.gleidsonmt.glad.controls.icon.Icon;
import io.github.gleidsonmt.todo.view.panel.input.InputFieldItem;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Pos;
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

    private final ObjectProperty<LocalDateTime> remind = new SimpleObjectProperty<>();

    public RemindContextMenu() {

        GridMenuItem menuItemToday = new GridMenuItem("Later Today", Icon.SHARE_ETA, DateUtils.format(LocalTime.of(19, 0)));
        GridMenuItem menuItemTomorrow = new GridMenuItem("Tomorrow", Icon.SHARE_ETA, DateUtils.format(LocalDate.now().plusDays(1), LocalTime.of(9, 0)));
        GridMenuItem menuItemNextWeek = new GridMenuItem("Next Week", Icon.SHARE_ETA, DateUtils.format(LocalDate.now().with(DayOfWeek.SUNDAY), LocalTime.of(9, 0)));
        GridMenuItem menuItemPickDateAndTime = new GridMenuItem("Pick a Date and Time", Icon.CALENDAR_CLOCK);
        GridMenuItem menuItemDelete = new GridMenuItem("Remove reminder", Icon.REMOVE);
        menuItemDelete.getStyleClass().addAll("menu-item-delete");

        this.getItems().addAll(menuItemToday, menuItemTomorrow, menuItemNextWeek, new SeparatorMenuItem(), menuItemPickDateAndTime);
//
        menuItemToday.setOnAction(_ -> update(compose(LocalDate.now(), LocalTime.of(19, 0))));
        menuItemTomorrow.setOnAction(_ -> update(compose(LocalDate.now().plusDays(1), LocalTime.now())));
        menuItemNextWeek.setOnAction(_ -> update(compose(LocalDate.now().with(DayOfWeek.SUNDAY), LocalTime.now())));
        menuItemDelete.setOnAction(_ -> update(null));

        menuItemPickDateAndTime.setOnAction(e -> {

            CalendarTimePane container = new CalendarTimePane();
            var node = (InputFieldItem) getOwnerWindow().getScene().lookup("#input-field-due-date");
            Root root = (Root) getOwnerWindow().getScene().getRoot();

            root.flow()
                    .content(container)
                    .width(280)
                    .pos(Pos.TOP_LEFT)
                    .show(node);

            container.setOnSave(_ -> {
                if (container.getSelected() == null) {
                    update(compose(LocalDate.now(), container.getTime()));
                }
                else  update(compose(container.getSelected(), container.getTime()));
                root.flow().remove(container);
            });
        });
    }

    private LocalDateTime compose(LocalDate date, LocalTime time) {
        return LocalDateTime.of(date, time);
    }

    @Override
    protected void update(LocalDateTime value) {
        var node = (InputFieldItem) getOwnerWindow().getScene().lookup("#input-field-remind");
        node.setText(value == null ? "" : DateUtils.format(value));
        setSelected(value != null);
        setValue(value);
    }
}
