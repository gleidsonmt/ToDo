package io.github.gleidsonmt.todo.view.aside.sections;

import io.github.gleidsonmt.todo.bd.dao.DaoRecurrence;
import io.github.gleidsonmt.todo.model.RecurrenceViewModel;
import io.github.gleidsonmt.todo.view.aside.*;
import io.github.gleidsonmt.todo.view.aside.labels.DueDateLabel;
import io.github.gleidsonmt.todo.view.aside.labels.RecurrenceLabel;
import io.github.gleidsonmt.todo.view.aside.labels.RemindLabel;
import io.github.gleidsonmt.todo.view_model.TaskViewModel;
import io.github.gleidsonmt.todo.view_model.map.RecurrenceMapper;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author Gleidson Neves da Silveira | <a href="mailto:gleidisonmt@gmail.com">gleidisonmt@gmail.com</a> <br>
 * Created on 17/10/2026
 */
public class MultipleBlockSection extends Section {

    private final Item<LocalDateTime> itemRemind;
    private final Item<LocalDate> itemDueDate;
    private final Item<RecurrenceViewModel> itemRecurrence;

    public MultipleBlockSection(TaskViewModel viewModel) {

        this.itemRemind = new Item<>(new RemindLabel()) {
            @Override
            public void bind() {
//                getNode().valueProperty().addListener((_, _, newValue) -> viewModel.setRemind(newValue));
                getNode().valueProperty().bindBidirectional(viewModel.remindProperty());
                getNode().setValue(viewModel.getRemind());
            }
        };

        this.itemDueDate = new Item<>(new DueDateLabel()) {
            @Override
            public void bind() {
//                getNode().valueProperty().addListener((_, _, newValue) -> {
//                    viewModel.setDueDate(newValue);
//                    viewModel.setMyDay(newValue != null);
//                });
                getNode().valueProperty().bindBidirectional(viewModel.dueDateProperty());
//                getNode().setValue(viewModel.getDueDate());
            }
        };

        this.itemRecurrence = new Item<>(new RecurrenceLabel()) {

            @Override
            public void bind() {
                final DaoRecurrence dao = new DaoRecurrence();

                var found = new DaoRecurrence().findWhere("task_id = " + viewModel.getId());
                RecurrenceMapper converter = RecurrenceMapper.INSTANCE;


                getNode().valueProperty().addListener((_, _, newValue) -> {
                    if (newValue != null) {
                        newValue.setTaskID(viewModel.getId());
                        viewModel.storeRecurrence(converter.toDTO(newValue));
                    } else {
                        dao.delete("task_id = " + viewModel.getId());
                    }
                });

                found.ifPresent(recurrence -> getNode().setValue(converter.toViewModel(recurrence, new RecurrenceViewModel())));
            }
        };

        var defaultStyle = "border-b-1 border-light-gray pd-2";
        itemRemind.getStyleClass().addAll(defaultStyle.split("\\s+"));
        itemDueDate.getStyleClass().addAll(defaultStyle.split("\\s+"));
        getChildren().setAll(itemRemind, itemDueDate, itemRecurrence);
    }

    public BooleanProperty selectedRemind() {
        return this.itemRemind.selectedProperty();
    }

    public Item<LocalDate> getItemDueDate() {
        return this.itemDueDate;
    }

    public BooleanProperty selectedDueDate() {
        return this.itemDueDate.selectedProperty();
    }

    public BooleanProperty selectedRecurrence() {
        return this.itemRecurrence.selectedProperty();
    }

    public ObjectProperty<LocalDate> dueDateProperty() {
        return this.itemDueDate.getNode().valueProperty();
    }
}
