package io.github.gleidsonmt.todo.view.aside.sections;

import io.github.gleidsonmt.todo.view.aside.Item;
import io.github.gleidsonmt.todo.view.aside.labels.MyDayLabel;
import io.github.gleidsonmt.todo.view.panel.events.TaskChangeEvent;
import io.github.gleidsonmt.todo.view_model.TaskViewModel;
import javafx.beans.property.BooleanProperty;

/**
 * @author Gleidson Neves da Silveira | <a href="mailto:gleidisonmt@gmail.com">gleidisonmt@gmail.com</a> <br>
 * Created on 15/10/2026
 */
public class MyDaySection extends Section {

    private final Item<Boolean> item;

    public MyDaySection(TaskViewModel viewModel) {
        this.item = new Item<>(new MyDayLabel()) {
            @Override
            public void bind() {
//                getNode().setValue(viewModel.isMyDay());
                getNode().valueProperty().bindBidirectional(viewModel.myDayProperty());
                getNode().valueProperty().addListener((_, _, newValue) -> {
//                    System.out.println("newValue = " + newValue);
//                    viewModel.setMyDay(newValue != null);
//////                    viewModel.setDueDate(LocalDate.now());
                    this.fireEvent(new TaskChangeEvent(TaskChangeEvent.MY_DAY_CHANGED, viewModel));
                });
            }
        };
        getChildren().add(item);
    }

    public BooleanProperty selectedProperty() {
        return item.selectedProperty();
    }

    public Item<Boolean> getItem() {
        return item;
    }
}