package io.github.gleidsonmt.todo.view.panel.containers;

import io.github.gleidsonmt.todo.model.List;
import io.github.gleidsonmt.todo.model.ListType;
import io.github.gleidsonmt.todo.model.ToDoTask;
import io.github.gleidsonmt.todo.view.panel.items.TaskItem;
import io.github.gleidsonmt.todo.view.panel.sections.AnimatedSection;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;

/**
 * Description:
 *
 * @author Gleidson Neves da Silveira | <gleidisonmt@gmail.com>
 *         Created On: Feb 27, 2026
 * 
 *         Version History: Initial version
 */
public class MultipleListContainer extends ListContainer {

    private final BooleanProperty hasChild = new SimpleBooleanProperty(false);

    public MultipleListContainer(List list, ObservableList<ToDoTask> data) {
        super(list, data);
        this.setSpacing(5);

        hasChild.bind(Bindings.size(this.getChildren()).greaterThan(1));

        // list.getLists().forEach(_list -> {
        // if (!_list.getItems().isEmpty()) {
        // AnimatedSection section = new AnimatedSection(_list, true);

        // _list.getItems().addListener((ListChangeListener<ToDoTask>) c -> {
        // if (c.next()) {
        // if (c.wasReplaced()) {
        // return;
        // }

        // if (c.wasAdded()) {
        // c.getAddedSubList().forEach(task -> {
        // TaskItem taskItem = createTaskItem(task);
        // section.getChildren().add(taskItem);
        // });
        // }
        // }
        // });

        // Task<Object> ts = new Task<>() {
        // @Override
        // protected Object call() {
        // _list.getItems().forEach(item -> {
        // try {
        // Thread.sleep((long) (section.getSpeed() / 2));
        // } catch (InterruptedException e) {
        // throw new RuntimeException(e);
        // }

        // Platform.runLater(() -> {
        // TaskItem taskItem = createTaskItem(item);
        // if (Objects.requireNonNull(list.getType()) == ListType.COMPLETED) {
        // if (item.isCompleted()) {
        // section.getItems().add(taskItem);
        // if (!section.getChildren().isEmpty() &&
        // !getChildren().contains(section)) {
        // getChildren().add(0, section);
        // }
        // }
        // } else {
        // if (!item.isCompleted()) {
        // section.getItems().add(taskItem);
        // if (!section.getChildren().isEmpty() &&
        // !getChildren().contains(section))
        // getChildren().add(0, section);
        // }
        // }
        // });
        // });

        // return null;
        // }
        // };

        // Thread thread = new Thread(ts);
        // thread.setDaemon(true);
        // thread.setName("Loading tasks.");
        // thread.start();
        // }

        // });
    }

    @Override
    protected TaskItem createTaskItem(ToDoTask task) {
        TaskItem item = new TaskItem(task);
        item.setOnCompletedChange(viewModel -> {
            AnimatedSection section = (AnimatedSection) item.getParent();
            section.getItems().remove(section.get(task));
            viewModel.update();
        });
        item.setOnImportantChange(viewModel -> {
            viewModel.update();
        });
        return item;
    }

    public BooleanProperty hasChildProperty() {
        return hasChild;
    }

    @Override
    public void load() {
        // loadTasks(_ -> list.getLists().forEach(this::loadLists));
        list.getLists().forEach(this::loadLists);
    }

    private void loadLists(List list) {

        AnimatedSection section = new AnimatedSection(list);

        list.getItems().forEach(el -> {
            loadTask(section, el, list.getType());
            list.getItems().addListener(changeListener(section));
            // hasChild.bind(Bindings.size(list.getItems()).greaterThan(0));
        });

        Platform.runLater(() -> {
            if (!list.getItems().isEmpty()) {
                if (!getChildren().contains(section))
                    getChildren().add(0, section);
            }
        });
    }

    /**
     * If the list as update behind the scenes.
     * 
     * @param section
     * @return
     */
    private ListChangeListener<ToDoTask> changeListener(AnimatedSection section) {
        return (ListChangeListener<ToDoTask>) c -> {
            if (c.next()) {
                if (c.wasReplaced()) {
                    return;
                }
                if (c.wasAdded()) {
                }
                if (c.wasRemoved()) {
                    c.getRemoved().forEach(task -> section.getItems().remove(section.get(task)));
                }
            }
        };
    }

    private void loadTask(AnimatedSection section, ToDoTask task, ListType type) {

        // try {
        // Thread.sleep((long) (section.getSpeed() / 2));
        // } catch (InterruptedException e1) {

        // }

        // if (list.getType() == ListType.COMPLETED) {

        // }
        new Thread(new Task<Object>() {
            @Override
            protected Object call() {
                TaskItem taskItem = createTaskItem(task);
                Platform.runLater(() -> {

                    section.getItems().add(taskItem);

                    // if (task.isCompleted()) {
                    // sectionCompleted.getItems().add(taskItem);
                    // } else {
                    // sectionIncomplete.getChildren().add(0, taskItem);
                    // }
                    if (!getChildren().contains(section))
                        getChildren().add(0, section);
                });

                return taskItem;
            }

        }).start();
        // Platform.runLater(() -> {
        // section.getItems().add(taskItem);

        // // if (task.isCompleted()) {
        // // sectionCompleted.getItems().add(taskItem);
        // // } else {
        // // sectionIncomplete.getChildren().add(0, taskItem);
        // // }
        // if (!getChildren().contains(section))
        // getChildren().add(0, section);
        // });
    }
}
