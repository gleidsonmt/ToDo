

package io.github.gleidsonmt.todo.view.panel.input;

import io.github.gleidsonmt.todo.model.List;
import io.github.gleidsonmt.todo.model.recurrence.Recurrence;
import io.github.gleidsonmt.todo.model.recurrence.RecurrenceType;
import io.github.gleidsonmt.todo.view.panel.menu.input_menu_items.DateUtils;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.css.PseudoClass;
import javafx.scene.Node;
import javafx.scene.control.ContentDisplay;
import javafx.scene.layout.HBox;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 *
 * @author Gleidson Neves da Silveira | <a href="mailto:gleidisonmt@gmail.com">gleidisonmt@gmail.com</a> <br> <br>
 * Created on  21/04/2026
 */
public class InputFieldOptions extends HBox {

    private final InputFieldItem<List> tasks;
    private final InputFieldItem<LocalDate> dueDate;
    private final InputFieldItem<LocalDateTime> remind;
    private final InputFieldItem<Recurrence> repeat;

    public InputFieldOptions() {
        tasks = new InputFieldItemTask();
        dueDate = new InputFieldItemDueDate();
        remind = new InputFieldRemind();
        repeat = new InputFieldRepeat();

        getChildren().addAll(tasks, dueDate, remind, repeat);

        dueDate.valueProperty().addListener((_,_,val) -> {
            if (val == null) {
                repeat.setValue(null);
            }
        });

        repeat.valueProperty().addListener((_, _, newValue) -> {
            var date = LocalDate.now();
            if (newValue != null) {
                if (newValue.getType() == RecurrenceType.WEEKDAYS) {
                    if (DateUtils.isWeekend(date)) {
                        date = date.getDayOfWeek().equals(DayOfWeek.SUNDAY) ? date.plusDays(1) : date.plusDays(2);
                    }
                }
                dueDate.setValue(date);
                dueDate.setText(DateUtils.format(date));
            }
        });
    }

    public void mediumLayout(boolean val) {
        pseudoClassStateChanged(PseudoClass.getPseudoClass("medium"), val);
        minIcons(val);
    }

    public void smallLayout(boolean val) {
        pseudoClassStateChanged(PseudoClass.getPseudoClass("small"), val);
        minIcons(val);
    }

    private void minIcons(boolean val) {
        getChildren().stream().filter(el -> el instanceof InputFieldItem).forEach(el -> {
                    ((InputFieldItem<?>) el).setContentDisplay(val ? ContentDisplay.GRAPHIC_ONLY : ContentDisplay.LEFT);
                    ((InputFieldItem<?>) el).setCenter(val ? true : false);
                }
        );
    }

    public LocalDate getDueDate() {
        return dueDate.getValue();
    }

    public LocalDateTime getRemind() {
        return remind.getValue();
    }

    public Recurrence getRecurrence() {
        return repeat.getValue();
    }

    public List getTask() {
        return tasks.getValue();
    }

    public List getList() {
        return tasks.getValue();
    }

    public void needsTaskItem(boolean needs) {
        if (needs) {
            if (!getChildren().contains(tasks))
                getChildren().addFirst(tasks);
        } else {
            getChildren().remove(tasks);
        }
    }

    public void reset() {

    }
}
