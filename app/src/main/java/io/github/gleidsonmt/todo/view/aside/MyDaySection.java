package io.github.gleidsonmt.todo.view.aside;

import io.github.gleidsonmt.todo.view_model.TaskViewModel;
import io.github.gleidsonmt.todo.view_model.ViewModel;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

/**
 * @author Gleidson Neves da Silveira | <a href="mailto:gleidisonmt@gmail.com">gleidisonmt@gmail.com</a> <br>
 * Created on 15/10/2026
 */
public class MyDaySection extends Section {

    public MyDaySection(TaskViewModel viewModel) {
        getChildren().setAll(new Item<>(new MyDayLabel(), viewModel) {
            @Override
            public void bind() {
                getNode().setValue(viewModel.isMyDay());
                getNode().valueProperty().addListener((_, _, newValue) -> viewModel.setMyDay(newValue != null));
            }
        });
    }
}