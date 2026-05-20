

package io.github.gleidsonmt.todo.view.panel.actions;

import java.util.EventListener;

import io.github.gleidsonmt.todo.view_model.TaskViewModel;

/**
 * Description:
 *
 * @author Gleidson Neves da Silveira | <a href="mailto:gleidisonmt@gmail.com">gleidisonmt@gmail.com</a> <br> <br>
 * Created On: Feb 27, 2026
 */
@Deprecated(forRemoval = true)
@FunctionalInterface
public interface CompleteAction extends EventListener {
    // void handle(TaskItem item, ToDoTask task);
    void handle(TaskViewModel viewModel);
}
