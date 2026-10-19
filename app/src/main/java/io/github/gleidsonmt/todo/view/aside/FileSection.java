package io.github.gleidsonmt.todo.view.aside;

import io.github.gleidsonmt.todo.view_model.TaskViewModel;

/**
 * @author Gleidson Neves da Silveira | <a href="mailto:gleidisonmt@gmail.com">gleidisonmt@gmail.com</a> <br>
 * Created on 18/10/2026
 */
public class FileSection extends Section {
    public FileSection(TaskViewModel model) {
        Item item = new Item(new FileLabel()) {
            @Override
            public void bind() {
            }
        };
        getChildren().setAll(item);
    }
}
