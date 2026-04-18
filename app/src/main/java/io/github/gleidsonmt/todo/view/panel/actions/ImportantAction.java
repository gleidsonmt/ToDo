package io.github.gleidsonmt.todo.view.panel.actions;

import java.util.EventListener;

import io.github.gleidsonmt.todo.view_model.TaskViewModel;

/**
 * Description:
 *
 * @author Gleidson Neves da Silveira | <gleidisonmt@gmail.com>
 * Created On: Mar 03, 2026
 * <p>
 * Version History: Initial version
 */
@Deprecated(forRemoval = true)
@FunctionalInterface
public interface ImportantAction extends EventListener {
    void handle(TaskViewModel viewModel);
}
