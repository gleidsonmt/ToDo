package io.github.gleidsonmt.todo.view.panel;

import io.github.gleidsonmt.todo.view.panel.containers.DoubleListContainer;
import io.github.gleidsonmt.todo.view.panel.containers.EmptyContainer;
import io.github.gleidsonmt.todo.view.panel.containers.ListContainer;
import io.github.gleidsonmt.todo.view.panel.events.TaskChangeEvent;
import io.github.gleidsonmt.todo.view.panel.items.TaskItem;
import io.github.gleidsonmt.todo.view_model.ListViewModel;
import io.github.gleidsonmt.todo.view_model.TaskViewModel;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.logging.Logger;

/**
 * Description:
 *
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Created On: Mar 23, 2026
 * <p>
 * Version History: Initial version
 */
public class ListRootNew extends VBox {

    private ListContainer container;
    private EmptyContainer emptyContainer;

    private final ObjectProperty<ListViewModel> actualList = new SimpleObjectProperty<>();

    public ListRootNew() {
        init();

        this.actualList.addListener((_, _, newValue) -> {
            this.getChildren().clear();
            updateContainer(newValue);
        });
    }

    private void init() {
        this.setId("list-root");
        this.emptyContainer = new EmptyContainer();
        VBox.setVgrow(emptyContainer, Priority.ALWAYS);
        VBox.setVgrow(this, Priority.ALWAYS);
    }

    /**
     * Update the container/node type based of the type of the list.
     *
     * @param list The list of tasks to view in the container.
     */
    public void updateContainer(ListViewModel list) {
        var query = "";

        query = switch (list.getType()) {
            case DAILY -> "my_day = 1";
            case IMPORTANT -> "important = 1";
            case TASKS -> "list_id = 0";
            default -> "list_id = " + list.getId();
        };

        container = new DoubleListContainer(list, query);

        container.load();

        container.sizeProperty().addListener((_, _, val) -> {
            update(val.intValue() == 0);
        });
        update(container.sizeProperty().get() == 0);
    }

    /**
     * If there's no task on the list root, so the image with a message will
     * show.
     *
     * @param empty If it neeeds update the list root node.
     */
    private void update(boolean empty) {
        this.getChildren().setAll(empty ? emptyContainer : container);
    }

    public ListContainer getContainer() {
        return this.container;
    }

    public TaskItem getSelected() {
        return this.getContainer().getSelected();
    }

    public ObjectProperty<ListViewModel> actualListProperty() {
        return actualList;
    }

    public ListViewModel getActualList() {
        return this.actualList.get();
    }

    public ObservableList<TaskViewModel> getData() {
        return container.getData();
    }
}
