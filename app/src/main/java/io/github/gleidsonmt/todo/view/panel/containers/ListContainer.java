package io.github.gleidsonmt.todo.view.panel.containers;

import java.util.Optional;

import io.github.gleidsonmt.todo.model.List;
import io.github.gleidsonmt.todo.model.ToDoTask;
import io.github.gleidsonmt.todo.view.panel.items.TaskItem;
import io.github.gleidsonmt.todo.view.panel.sections.EmptySection;
import io.github.gleidsonmt.todo.view_model.ListViewModel;
import io.github.gleidsonmt.todo.view_model.TaskViewModelConverter;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;

/**
 * Description:
 *
 * @author Gleidson Neves da Silveira | <gleidisonmt@gmail.com>
 *         Created On: Feb 26, 2026
 * 
 *         Version History: Initial version
 */
public abstract class ListContainer extends VBox {

    protected ObservableList<ToDoTask> data;
    protected ListViewModel list;

    protected ToggleGroup group;

    /**
     * Constructor
     * 
     * @param list The filtered list.
     * @param data The core list.
     */
    // public ListContainer(List list, ObservableList<ToDoTask> data) {
    public ListContainer(ListViewModel list) {
        this.list = list;
        // this.data = data;
        this.group = new ToggleGroup();
        this.setId("list-container");

        this.getChildren().add(new EmptySection());
    }

    protected abstract TaskItem createTaskItem(ToDoTask task);

    public abstract void load();

    protected void loadTasks(EventHandler<ActionEvent> event) {
        new Thread(new Task<Object>() {
            @Override
            protected Object call() throws Exception {
                event.handle(new ActionEvent());
                return null;
            }
        }).start();
        // Platform.runLater(() -> {
        // event.handle(new ActionEvent());
        // });
    }

    /**
     * Update a core list of tasks.
     * If the task has update one of the properties this will be reflect
     * in the main data, so the ui will be adapt to it.
     * 
     * @param task The task to update.
     */
    @Deprecated
    public void update(ToDoTask task) {
        // var index = data.indexOf(task);
        // data.set(index, task);
        var finded = find(task);
        if (finded.isPresent()) {
            var index = data.indexOf(finded.get());
            data.set(index, task);
        }
    }

    /**
     * Add a task to a core.
     * 
     * @param task The task to add.
     */
    @Deprecated
    public void add(ToDoTask task) {
        data.add(task);
    }

    /**
     * Remove a task from a list core of tasks.
     * 
     * @param task The task to delete.
     */
    @Deprecated
    public void remove(ToDoTask task) {
        // data.remove(task);
        var finded = find(task);
        if (finded.isPresent()) {
            data.remove(finded.get());
        }
    }

    @Deprecated
    private Optional<ToDoTask> find(ToDoTask task) {
        return data.stream().filter(el -> el.getId() == task.getId()).findAny();
    }
}
