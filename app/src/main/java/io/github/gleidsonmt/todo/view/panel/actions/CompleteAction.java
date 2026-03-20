package io.github.gleidsonmt.todo.view.panel.actions;

import java.util.EventListener;

import io.github.gleidsonmt.todo.view_model.TaskViewModel;

/**
 * Description:
 *
 * @author Gleidson Neves da Silveira | <gleidisonmt@gmail.com>
 *         Created On: Feb 27, 2026
 * 
 *         Version History: Initial version
 */
@FunctionalInterface
public interface CompleteAction extends EventListener {
    // void handle(TaskItem item, ToDoTask task);
    void handle(TaskViewModel viewModel);
}
