package io.github.gleidsonmt.todo.view.panel;

import io.github.gleidsonmt.todo.model.List;
import io.github.gleidsonmt.todo.model.Model;
import io.github.gleidsonmt.todo.model.ToDoTask;
import io.github.gleidsonmt.todo.view.panel.containers.EmptyContainer;
import io.github.gleidsonmt.todo.view.panel.containers.ListContainer;
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
 * Created On: Feb 26, 2026
 * <p>
 * Version History: Initial version
 */
public class ListRoot extends VBox {

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
//            updateContainer(newValue);
        });
        //
        VBox.setVgrow(emptyContainer, Priority.ALWAYS);
    }



    public ObservableList<ToDoTask> getData() {
        return this.data;
    }
}
