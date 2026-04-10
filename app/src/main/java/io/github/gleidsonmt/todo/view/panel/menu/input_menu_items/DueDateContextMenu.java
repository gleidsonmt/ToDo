package io.github.gleidsonmt.todo.view.panel.menu.input_menu_items;

import com.dlsc.gemsfx.CalendarView;
import io.github.gleidsonmt.glad.base.Root;
import io.github.gleidsonmt.glad.controls.icon.Icon;
import io.github.gleidsonmt.todo.view.panel.input.InputFieldItem;
import javafx.geometry.Pos;
import javafx.scene.control.SeparatorMenuItem;

import java.time.DayOfWeek;
import java.time.LocalDate;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  09/04/2026
 */
public class DueDateContextMenu extends CustomContextMenu<LocalDate> {

    public DueDateContextMenu() {

        GridMenuItem menuItemToday = new GridMenuItem("Today", Icon.TODAY, DateUtils.formatDay(LocalDate.now()));
        GridMenuItem menuItemTomorrow = new GridMenuItem("Tomorrow", Icon.DATE_RANGE, DateUtils.formatDay(LocalDate.now().plusDays(1)));
        GridMenuItem menuItemNextWeek = new GridMenuItem("Next Week", Icon.EVENT_UPCOMING, DateUtils.formatDay(LocalDate.now().with(DayOfWeek.MONDAY)));
        GridMenuItem menuItemPickADate = new GridMenuItem("Pick a date", Icon.CALENDAR_MONTH);
        GridMenuItem menuItemDelete = new GridMenuItem("Remove due date", Icon.DELETE);

        getItems().addAll(menuItemToday, menuItemTomorrow, menuItemNextWeek, menuItemPickADate);

        menuItemToday.setOnAction(_ -> update(LocalDate.now()));
        menuItemTomorrow.setOnAction(_ -> update(LocalDate.now().plusDays(1)));
        menuItemNextWeek.setOnAction(_ -> update(LocalDate.now().with(DayOfWeek.SUNDAY)));
        menuItemDelete.setOnAction(_ -> update(null));

        menuItemPickADate.setOnAction(e -> {
            CalendarPane container = new CalendarPane();
            var node = (InputFieldItem) getOwnerWindow().getScene().lookup("#input-field-due-date");
            Root root = (Root) getOwnerWindow().getScene().getRoot();

            root.flow()
                    .content(container)
                    .width(280)
                    .pos(Pos.TOP_LEFT)
                    .show(node);

            container.setOnSave(_ -> {
                if (container.getSelected() == null) update(LocalDate.now());
                else update(container.getSelected());
                root.flow().remove(container);
            });

        });

        menuItemDelete.getStyleClass().addAll("menu-item-delete");
        selected.addListener((_, _, newValue) -> {
            var separator = new SeparatorMenuItem();
            if (newValue) {
                getItems().addAll(separator, menuItemDelete);
            } else {
                getItems().remove(getItems().size() - 2, getItems().size());
            }
        });

    }

    @Override
    public void update(LocalDate date) {
        var node = (InputFieldItem) getOwnerWindow().getScene().lookup("#input-field-due-date");
        node.setText(date == null ? "" : DateUtils.format(date));
        setSelected(date != null);
        setValue(date);
    }
}
