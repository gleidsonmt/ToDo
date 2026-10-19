package io.github.gleidsonmt.todo.view.aside;

import io.github.gleidsonmt.todo.view_model.TaskViewModel;

/**
 * @author Gleidson Neves da Silveira | <a href="mailto:gleidisonmt@gmail.com">gleidisonmt@gmail.com</a> <br>
 * Created on 15/10/2026
 */
public class MyDaySection extends Section {

    public MyDaySection(TaskViewModel viewModel) {
        getChildren().setAll(new Item<>(new MyDayLabel()) {
            @Override
            public void bind() {
                getNode().setValue(viewModel.isMyDay());
                getNode().valueProperty().addListener((_, _, newValue) -> viewModel.setMyDay(newValue != null));
            }
        });
    }
}