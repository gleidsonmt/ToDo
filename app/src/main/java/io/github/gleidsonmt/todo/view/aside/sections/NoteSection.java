package io.github.gleidsonmt.todo.view.aside.sections;

import io.github.gleidsonmt.todo.bd.dao.DaoTask;
import io.github.gleidsonmt.todo.view.aside.AreaInsert;
import io.github.gleidsonmt.todo.view_model.TaskViewModel;
import io.github.gleidsonmt.todo.view_model.map.TaskMapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

/**
 * @author Gleidson Neves da Silveira | <a href="mailto:gleidisonmt@gmail.com">gleidisonmt@gmail.com</a> <br>
 * Created on 18/10/2026
 */
public class NoteSection extends Section {

    public NoteSection(TaskViewModel viewModel) {
        var area = new AreaInsert(viewModel.getNote());
        getChildren().add(area);

        area.focusedProperty().addListener((_, _, newValue) -> {
            if (!newValue) {
                viewModel.setNote(area.getText());
                viewModel.update();
            }
        });
    }
}
