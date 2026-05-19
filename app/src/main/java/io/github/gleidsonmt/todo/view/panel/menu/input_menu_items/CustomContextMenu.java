

package io.github.gleidsonmt.todo.view.panel.menu.input_menu_items;

import io.github.gleidsonmt.todo.model.List;
import io.github.gleidsonmt.todo.model.recurrence.Recurrence;
import io.github.gleidsonmt.todo.utils.IconUtils;
import io.github.gleidsonmt.todo.utils.StringUtils;
import io.github.gleidsonmt.todo.view.panel.input.InputFieldItem;
import javafx.application.Platform;
import javafx.geometry.Side;
import javafx.scene.control.ContextMenu;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author Gleidson Neves da Silveira | <a href="mailto:gleidisonmt@gmail.com">gleidisonmt@gmail.com</a> <br>
 * Create on  09/04/2026
 */
public class CustomContextMenu<T> extends ContextMenu {

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
                    target.setText(StringUtils.name(list.getName()));
                    target.setGraphic(IconUtils.getIcon(list));
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

        System.out.println("target = " + target.isCenter());
        System.out.println("target = " + target.getWidth());
        System.out.println("target = " + this.getWidth());

        // second show places in the right position
        super.show(target, side, target.isCenter() ? -((this.getWidth() / 2) - (target.getWidth() / 2)) : dx, dy);
        Platform.requestNextPulse(); // makes sure the is on the right position
    }
}
