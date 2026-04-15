package io.github.gleidsonmt.todo.view.panel.menu.input_menu_items;

import io.github.gleidsonmt.todo.model.recurrence.Recurrence;
import io.github.gleidsonmt.todo.view.panel.input.InputFieldItem;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Side;
import javafx.scene.control.ContextMenu;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  09/04/2026
 */
public abstract class CustomContextMenu<T> extends ContextMenu {

    protected BooleanProperty selected;
    protected ObjectProperty<T> value;

    protected InputFieldItem<T> target;

    public CustomContextMenu() {
        this.selected = new SimpleBooleanProperty(false);
        this.value = new SimpleObjectProperty<>();

        value.addListener((_, _, val) -> {
            setSelected(val != null);

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

    protected void setSelected(boolean selected) {
        this.selected.set(selected);
    }

    public T getValue() {
        return value.getValue();
    }

    protected void setValue(T value) {
        this.value.setValue(value);
    }

}
