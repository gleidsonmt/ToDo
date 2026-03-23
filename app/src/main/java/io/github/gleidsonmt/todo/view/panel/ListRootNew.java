package io.github.gleidsonmt.todo.view.panel;

import io.github.gleidsonmt.todo.model.Model;
import io.github.gleidsonmt.todo.view.panel.containers.DoubleListContainer;
import io.github.gleidsonmt.todo.view.panel.containers.EmptyContainer;
import io.github.gleidsonmt.todo.view.panel.containers.ListContainer;
import io.github.gleidsonmt.todo.view_model.ListViewModel;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

/**
 * Description:
 *
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 *         Created On: Mar 23, 2026
 * 
 *         Version History: Initial version
 */
public class ListRootNew extends VBox {

    // private ListContainer container;
    private EmptyContainer emptyContainer;

    private final ObjectProperty<ListViewModel> actualList = new SimpleObjectProperty<>();

    public ListRootNew() {
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
    private void updateContainer(ListViewModel list) {
        System.out.println("list = " + list.getType());
        ListContainer container = null;
        // switch (list.getType()) {
        // case IMPORTANT -> container = new SingleListContainer(list, data);
        // case DEFAULT, DAILY, TASKS -> 
        container = new DoubleListContainer(list);
        // case COMPLETED, ALL -> container = new MultipleListContainer(list,
        // data);
        // default -> throw new IllegalArgumentException("Unexpected value: " +
        // list.getType());
        // }

        // VBox.setVgrow(container, Priority.ALWAYS);

        // if (container instanceof MultipleListContainer multipleListContainer)
        // {
        // multipleListContainer.hasChildProperty().addListener((observable,
        // oldValue, newValue) -> {
        // getChildren().setAll(!newValue ? emptyContainer : container);
        // });
        // this.getChildren().setAll(multipleListContainer.hasChildProperty().get()
        // ? container : emptyContainer);
        // } else {
        // addListenerSize(list.getItems());
        // }

        container.load();

        this.getChildren().setAll(container);

        // Repo<Dao<User>>, User> repo = Repository.<User>of();
        // repo.store(list);
    }

    private void addListenerSize(ObservableList<? extends Model> items) {
        // size.unbind();
        // size.bind(Bindings.size(items));

        // size.addListener((observable, oldValue, newValue) -> {
        // getChildren().setAll(newValue.intValue() == 0 ? emptyContainer :
        // container);
        // });

        // this.getChildren().setAll(!items.isEmpty() ? container :
        // emptyContainer);
    }

    public ObjectProperty<ListViewModel> actualListProperty() {
        return actualList;
    }

    public ListViewModel getActuaList() {
        return this.actualList.get();
    }
}
