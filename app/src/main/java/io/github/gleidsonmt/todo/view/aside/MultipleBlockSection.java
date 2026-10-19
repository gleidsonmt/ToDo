package io.github.gleidsonmt.todo.view.aside;

import io.github.gleidsonmt.todo.bd.dao.DaoRecurrence;
import io.github.gleidsonmt.todo.model.RecurrenceViewModel;
import io.github.gleidsonmt.todo.view_model.TaskViewModel;
import io.github.gleidsonmt.todo.view_model.converter.RecurrenceConverter;
import io.github.gleidsonmt.todo.view_model.map.RecurrenceMapper;
import io.github.gleidsonmt.todo.view_model.map.TaskMapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author Gleidson Neves da Silveira | <a href="mailto:gleidisonmt@gmail.com">gleidisonmt@gmail.com</a> <br>
 * Created on 17/10/2026
 */
public class MultipleBlockSection extends Section {

    public MultipleBlockSection(TaskViewModel viewModel) {
        Item<LocalDateTime> itemRemind = new Item<>(new RemindLabel(), viewModel) {
            @Override
            public void bind() {
                getNode().valueProperty().addListener((_, _, newValue) -> viewModel.setRemind(newValue));
                getNode().setValue(viewModel.getRemind());
            }
        };
        Item<LocalDate> itemDueDate = new Item<>(new DueDateLabel(), viewModel) {
            @Override
            public void bind() {
                getNode().valueProperty().addListener((_, _, newValue) -> viewModel.setDueDate(newValue));
                getNode().setValue(viewModel.getDueDate());
            }
        };
        Item<RecurrenceViewModel> itemRecurrence = new Item<>(new RecurrenceLabel(), viewModel) {

            @Override
            public void bind() {
                final DaoRecurrence dao = new DaoRecurrence();

                var finded = new DaoRecurrence().findWhere("task_id = " + viewModel.getId());
                RecurrenceMapper converter = RecurrenceMapper.INSTANCE;
                finded.ifPresent(recurrence -> getNode().setValue(converter.toViewModel(recurrence, new RecurrenceViewModel())));

                getNode().valueProperty().addListener((_, _, newValue) -> {
                    if (newValue != null) {
                        newValue.setTaskID(viewModel.getId());
                        viewModel.storeRecurrence(converter.toDTO(newValue));
                    } else {
                        dao.delete("task_id = " + viewModel.getId());
                    }
                });
            }
        };

        itemRemind.getStyleClass().addAll("border-b-1 border-light-gray pd-2 ".split("\\s+"));
        itemDueDate.getStyleClass().addAll("border-b-1 border-light-gray pd-2 ".split("\\s+"));
        getChildren().setAll(itemRemind, itemDueDate, itemRecurrence);
    }
}
