

package io.github.gleidsonmt.todo.view_model.converter;

import io.github.gleidsonmt.todo.model.ToDoTask;
import io.github.gleidsonmt.todo.view_model.TaskViewModel;
import org.jspecify.annotations.NonNull;

/**
 * Description:
 *
 * @author Gleidson Neves da Silveira | <a href="mailto:gleidisonmt@gmail.com">gleidisonmt@gmail.com</a> <br>
 * Created On: Mar 20, 2026
 * <p>
 * Version History: Initial version
 */
public class TaskViewModelConverter extends ViewModelConverter<ToDoTask, TaskViewModel> {

    @Override
    public TaskViewModel toViewModel(ToDoTask model) {
        return new TaskViewModel(model);
    }

    @Override
    public ToDoTask toModel(@NonNull TaskViewModel model) {
        return new ToDoTask(
                model.getId(),
                model.getName(),
                model.isCompleted(),
                model.isImportant(),
                model.isMyDay(),
                model.getDueDate(),
                model.getRemind(),
                model.getCreatedAt(),
                model.getListId()
        );
    }
}
