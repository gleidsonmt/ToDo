package io.github.gleidsonmt.todo.view.panel.actions;

import java.util.EventListener;

import io.github.gleidsonmt.todo.view.panel.items.TaskItemViewModel;

/**
 * Description:
 *
 * @author Gleidson Neves da Silveira | <gleidisonmt@gmail.com>
 *         Created On: Mar 03, 2026
 * 
 *         Version History: Initial version
 */
@FunctionalInterface
public interface ImportantAction extends EventListener {
    void handle(TaskItemViewModel viewModel);
}
