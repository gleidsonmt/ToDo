package io.github.gleidsonmt.todo.view_model.converter;

import io.github.gleidsonmt.todo.model.ToDoTask;
import io.github.gleidsonmt.todo.view_model.TaskViewModel;

/**
 * Description:
 *
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
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
    public ToDoTask toModel(TaskViewModel model) {
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
