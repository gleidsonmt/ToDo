package io.github.gleidsonmt.todo.view.panel.input;

import io.github.gleidsonmt.todo.model.List;
import io.github.gleidsonmt.todo.model.recurrence.Recurrence;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.HBox;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 *
 * @author Gleidson Neves da Silveira | <gleidisonmt@gmail.com>
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
    }

    public InputFieldItem<LocalDate> getDueDate() {
        return dueDate;
    }

    public InputFieldItem<List> getTasks() {
        return tasks;
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


}
