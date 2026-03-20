package io.github.gleidsonmt.todo.view.panel.containers;

import io.github.gleidsonmt.todo.model.List;
import io.github.gleidsonmt.todo.model.ToDoTask;
import io.github.gleidsonmt.todo.view.panel.items.TaskItem;
import io.github.gleidsonmt.todo.view.panel.sections.AnimatedSection;
import io.github.gleidsonmt.todo.view.panel.sections.SingleSection;
import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

/**
 * Description:
 *
 * @author Gleidson Neves da Silveira | <gleidisonmt@gmail.com>
 *         Created On: Feb 26, 2026
 * 
 *         Version History: Initial version
 */
public class DoubleListContainer extends ListContainer {

    private final SingleSection sectionIncomplete = new SingleSection();
    private final AnimatedSection sectionCompleted;

    public DoubleListContainer(List list, ObservableList<ToDoTask> data) {
        super(list, data);

        sectionCompleted = new AnimatedSection();

        this.getChildren().add(0, sectionIncomplete);
        this.getChildren().add(1, sectionCompleted);

        registerListeners();
    }

    private void registerListeners() {

        list.getItems().addListener((ListChangeListener<ToDoTask>) c -> {
            if (c.next()) {
                if (c.wasReplaced()) {
                    // Todo: do not do something if the data is changed
                    return;
                }
                if (c.wasAdded()) {
                    c.getAddedSubList().forEach(task -> {
                        loadTask(task);
                    });
                }

                if (c.wasRemoved()) {
                    c.getRemoved().forEach(el -> {
                        Platform.runLater(() -> {
                            if (el.isCompleted()) {
                                sectionCompleted.getItems().remove(sectionCompleted.get(el));
                            } else {
                                sectionIncomplete.getChildren().remove(sectionIncomplete.get(el));
                            }
                        });

                    });
                }
            }
        });
    }

    @Override
    public void load() {
        loadTasks(_ -> list.getItems().forEach(this::loadTask));
    }

    private void loadTask(ToDoTask task) {
        try {
            Thread.sleep((long) (sectionIncomplete.getSpeed() / 2));
        } catch (InterruptedException e1) {

        }

        TaskItem taskItem = createTaskItem(task);
        Platform.runLater(() -> {
            if (task.isCompleted()) {
                sectionCompleted.getItems().add(taskItem);
            } else {
                sectionIncomplete.getChildren().add(0, taskItem);
            }
        });
    }

    @Override
    protected TaskItem createTaskItem(ToDoTask task) {

        TaskItem item = new TaskItem(task);
        group.getToggles().add(item);

        item.setOnImportantChange(viewModel -> {
            viewModel.update();
        });

        item.setOnCompletedChange(viewModel -> {

            if (viewModel.isCompleted()) {
                sectionIncomplete.getChildren().removeAll(item);
                sectionCompleted.getItems().addAll(item);
            } else {
                sectionCompleted.getItems().removeAll(item);
                sectionIncomplete.getChildren().add(0, item);
            }

            viewModel.update();

        });

        return item;
    }
}