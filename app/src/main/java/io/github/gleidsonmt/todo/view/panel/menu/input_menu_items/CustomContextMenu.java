package io.github.gleidsonmt.todo.view.panel.menu.input_menu_items;

import io.github.gleidsonmt.todo.model.List;
import io.github.gleidsonmt.todo.model.recurrence.Recurrence;
import io.github.gleidsonmt.todo.view.panel.input.InputFieldItem;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.geometry.Side;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.function.Predicate;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  09/04/2026
 */
public abstract class CustomContextMenu<T> extends ContextMenu {


    protected InputFieldItem<T> target;

    public CustomContextMenu(InputFieldItem<T> target) {
        this.target = target;

        target.valueProperty().addListener((_, _, val) -> {

            if (val != null) {
                if (val instanceof LocalDate date) {
                    target.setText(DateUtils.format(date));
                    target.getTooltip().setText("");
                }
                if (val instanceof LocalDateTime dateTime) {
                    target.setText(DateUtils.format("Remind at ", dateTime.toLocalTime(), " at ", dateTime.toLocalDate()));
                }
                if (val instanceof Recurrence recurrence) {
                    target.setText(DateUtils.format(recurrence));
                }
                if (val instanceof List list) {
                    target.setText(list.getName());
                }
            } else {
                target.setText("");
            }
        });
    }

    public void show(InputFieldItem<T> target) {
        show(target, Side.TOP, 0, 0);
    }

    public void show(InputFieldItem<T> target, Side side, double dx, double dy) {
        this.target = target;

        if (isShowing()) return;
        // I just used to fixed a error
        // always in the first show the target appears in wrong position
        // but calling twice it placed right.
        // is it a bug? idk

        super.show(target, side, dx, dy);
        super.hide();

        // second show places in the right position
        super.show(target, side, dx, dy);
        Platform.requestNextPulse(); // makes sure the is on the right position
    }
}
