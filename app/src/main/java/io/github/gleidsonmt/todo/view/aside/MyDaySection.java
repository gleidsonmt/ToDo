package io.github.gleidsonmt.todo.view.aside;

import io.github.gleidsonmt.todo.view_model.ViewModel;

/**
 * @author Gleidson Neves da Silveira | <a href="mailto:gleidisonmt@gmail.com">gleidisonmt@gmail.com</a> <br>
 * Created on 15/10/2026
 */
public class MyDaySection extends Section {

    public MyDaySection(ViewModel viewModel) {
        Item item = new Item(new MyDayLabel(), viewModel);
        getChildren().setAll(item);

    }
}