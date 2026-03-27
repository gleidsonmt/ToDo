package io.github.gleidsonmt.todo.view.panel.containers;

import java.util.Comparator;

import io.github.gleidsonmt.todo.model.ToDoTask;
import io.github.gleidsonmt.todo.view.panel.items.TaskItem;
import io.github.gleidsonmt.todo.view.panel.sections.Comparators;
import io.github.gleidsonmt.todo.view.panel.sections.EmptySection;
import io.github.gleidsonmt.todo.view.panel.sections.SingleSection;
import io.github.gleidsonmt.todo.view_model.ListViewModel;
import io.github.gleidsonmt.todo.view_model.TaskViewModel;
import javafx.beans.Observable;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;

/**
 * Description: The core of the list container.
 *
 * @author Gleidson Neves da Silveira | <gleidisonmt@gmail.com>
 *         Created On: Feb 26, 2026
 * 
 *         Version History: Initial version
 */
public abstract class ListContainer extends VBox {

    protected ObservableList<TaskViewModel> data = FXCollections
            .observableArrayList(viewModel -> new Observable[] { viewModel.completedProperty() });

    private final ObjectProperty<TaskItem> selected = new SimpleObjectProperty<>();
    private final ObjectProperty<Comparators> comparator = new SimpleObjectProperty<>(Comparators.NONE);

    private final IntegerProperty size = new SimpleIntegerProperty(0);

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
        this.setStyle("-fx-border-width: 2px; -fx-border-color: gray;");

        this.getChildren().add(new EmptySection());

        // create a bind to maintain the selected property update from the
        // froup.
        selected.bind(group.selectedToggleProperty().map(e -> (TaskItem) e));

        // update the comparator for the all sections
        comparator.addListener((_, _, val) -> {
            this.getChildren().stream().filter(el -> el instanceof SingleSection).map(el -> (SingleSection) el)
                    .forEach(section -> {
                        section.getSortedList().setComparator(switchComparator(val));
                    });
        });

    }

    private Comparator<TaskViewModel> switchComparator(Comparators comparator) {
        switch (comparator) {
        case ALPHABETICALLY -> {
            return createAlphaticallyComporator();
        }
        case IMPORTANCE -> {
            return createImportanceComparator();
        }
        default -> throw new AssertionError();
        }

    }

    private Comparator<TaskViewModel> createAlphaticallyComporator() {
        return (TaskViewModel o1, TaskViewModel o2) -> o2.getName().compareToIgnoreCase(o1.getName());
    }

    private Comparator<TaskViewModel> createImportanceComparator() {
        return (TaskViewModel o1, TaskViewModel o2) -> o2.isImportant() && o1.isImportant() ? 0
                : o2.isImportant() && !o1.isImportant() ? -1 : 1;
    }

    public ToggleGroup getGroup() {
        return this.group;
    }

    public TaskItem getSelected() {
        return this.selected.get();
    }

    public void select(TaskItem value) {
        this.group.selectToggle(value);
    }

    @Deprecated
    protected abstract TaskItem createTaskItem(TaskViewModel task);

    public abstract void load();

    protected void loadTasks(EventHandler<ActionEvent> event) {
        new Thread(new Task<Object>() {
            @Override
            protected Object call() throws Exception {
                event.handle(new ActionEvent());
                return null;
            }
        }).start();
    }

    public ObservableList<TaskViewModel> getData() {
        return this.data;
    }

    public void setComparator(Comparators comparator) {
        this.comparator.set(comparator);
    }

    /**
     * Create UI component based on domain object.
     * Store this object in db using view model.
     * Add the task with id settled to the data list.
     * 
     * @param task The object model to create an UI Component.
     */
    public void add(TaskViewModel viewModel) {
        viewModel.save();
        data.add(viewModel);
    }

    /**
     * Delete the object from database.
     * And remove this object from this UI container.
     * 
     * @param task The task to delete.
     */
    public void remove(TaskItem task) {
        data.remove(task.getViewModel());
    }

    public IntegerProperty sizeProperty() {
        return this.size;
    }

}
