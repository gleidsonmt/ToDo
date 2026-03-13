package io.github.gleidsonmt.todo.view.panel;

import io.github.gleidsonmt.todo.model.List;
import io.github.gleidsonmt.todo.model.Model;
import io.github.gleidsonmt.todo.model.ToDoTask;
import io.github.gleidsonmt.todo.view.panel.containers.DoubleListContainer;
import io.github.gleidsonmt.todo.view.panel.containers.EmptyContainer;
import io.github.gleidsonmt.todo.view.panel.containers.ListContainer;
import io.github.gleidsonmt.todo.view.panel.containers.MultipleListContainer;
import io.github.gleidsonmt.todo.view.panel.containers.SingleListContainer;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

/**
 * Description:
 *
 * @author Gleidson Neves da Silveira | <gleidisonmt@gmail.com>
 *         Created On: Feb 26, 2026
 * 
 *         Version History: Initial version
 */
public class ListRoot extends VBox {

    private ListContainer container;
    private final EmptyContainer emptyContainer;
    private final ObservableList<ToDoTask> data;

    private final ObjectProperty<List> actualList = new SimpleObjectProperty<>();

    private final IntegerProperty size = new SimpleIntegerProperty(0);

    public ListRoot(ObservableList<ToDoTask> data) {
        this.data = data;
        this.emptyContainer = new EmptyContainer();

        this.setId("list-root");
        VBox.setVgrow(this, Priority.ALWAYS);

        this.actualList.addListener((observable, oldValue, newValue) -> {
            this.getChildren().clear();
            // layout.setRight(null);
            updateContainer(newValue);
        });
        //
        VBox.setVgrow(emptyContainer, Priority.ALWAYS);
    }

    /**
     * Update the container/node type based of the type of the list.
     *
     * @param list The list of tasks to view in the container.
     */
    private void updateContainer(List list) {
        switch (list.getType()) {
        case IMPORTANT -> container = new SingleListContainer(list, data);
        case DEFAULT, DAILY, TASKS -> container = new DoubleListContainer(list, data);
        case COMPLETED, ALL -> container = new MultipleListContainer(list, data);
        default -> throw new IllegalArgumentException("Unexpected value: " + list.getType());
        }

        VBox.setVgrow(container, Priority.ALWAYS);

        if (container instanceof MultipleListContainer multipleListContainer) {
            multipleListContainer.hasChildProperty().addListener((observable, oldValue, newValue) -> {
                getChildren().setAll(!newValue ? emptyContainer : container);
            });
            this.getChildren().setAll(multipleListContainer.hasChildProperty().get() ? container : emptyContainer);
        } else {
            addListenerSize(list.getItems());
        }

        container.load();
    }

    /**
     * This listener is responsible to show a message indicating the list is
     * empty.
     * if the list is empty show a message/image indicating the list is empty.
     * if the list is not empty show the items.
     *
     * @param items The items of the list.
     */
    private void addListenerSize(ObservableList<? extends Model> items) {
        size.unbind();
        size.bind(Bindings.size(items));

        size.addListener((observable, oldValue, newValue) -> {
            getChildren().setAll(newValue.intValue() == 0 ? emptyContainer : container);
        });

        this.getChildren().setAll(!items.isEmpty() ? container : emptyContainer);
    }

    public ObjectProperty<List> actualListProperty() {
        return actualList;
    }

    public List getActuaList() {
        return this.actualList.get();
    }

    public ObservableList<ToDoTask> getData() {
        return this.data;
    }
}
