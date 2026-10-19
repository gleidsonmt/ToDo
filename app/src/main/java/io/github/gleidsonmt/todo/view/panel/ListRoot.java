

package io.github.gleidsonmt.todo.view.panel;

import io.github.gleidsonmt.glad.base.Anchor;
import io.github.gleidsonmt.glad.base.Root;
import io.github.gleidsonmt.glad.base.dialog.WrapperEffect;
import io.github.gleidsonmt.todo.view.MainView;
import io.github.gleidsonmt.todo.view.aside.Aside;
import io.github.gleidsonmt.todo.view.panel.containers.DoubleListContainer;
import io.github.gleidsonmt.todo.view.panel.containers.EmptyContainer;
import io.github.gleidsonmt.todo.view.panel.containers.ListContainer;
import io.github.gleidsonmt.todo.view.panel.items.TaskItem;
import io.github.gleidsonmt.todo.view_model.ListViewModel;
import io.github.gleidsonmt.todo.view_model.TaskViewModel;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.time.LocalDate;

/**
 * Description:
 *
 * @author Gleidson Neves da Silveira | <a href="mailto:gleidisonmt@gmail.com">gleidisonmt@gmail.com</a> <br>
 * Created On: Mar 23, 2026
 * <p>
 * Version History: Initial version
 */
public class ListRoot extends VBox {

    private ListContainer container;
    private EmptyContainer emptyContainer;
    private Node aside;

    private final ObjectProperty<ListViewModel> actualList = new SimpleObjectProperty<>();

    private final ChangeListener<Number> updateListener = (_, _, val) -> {
        update(val.intValue() == 0);
    };

    private final ChangeListener<Number> updateAsideViewer = (_, _, newVal) -> {
        if (aside == null) return;
        changeRight(newVal.doubleValue());
    };

    private final ChangeListener<TaskItem> updateAsideModel = (_, _, newVal) -> {
        if (newVal == null) return;
        Root root = (Root) getScene().getRoot();
        aside = new Aside(newVal.getViewModel());
        changeRight(root.getWidth());
    };

    private void changeRight(double val) {
        Root root = (Root) getScene().getRoot();
        MainView mainView = (MainView) root.getContent();

        double breakPoint = 1000;
        if (val > breakPoint) {
            root.behavior()
                    .drawer()
                    .hide();
            mainView.setRight(aside);
        } else {
            if (root.behavior().drawer().isShowing()) return;
            mainView.setRight(null);
            root.behavior()
                    .drawer()
                    .content(aside)
                    .with(WrapperEffect.GRAY)
                    .show(Pos.CENTER_RIGHT, Anchor.RIGHT);
        }
    }

    public ListRoot() {
        init();

        this.actualList.addListener((_, _, newValue) -> {
            if (container != null) {
                container.sizeProperty().removeListener(updateListener);
                container.selectedProperty().removeListener(updateAsideModel);
            }
            this.getChildren().clear();
            updateContainer(newValue);
            container.sizeProperty().addListener(updateListener);

        });

        Platform.runLater(() -> {
            Root root = (Root) getScene().getRoot();
            root.widthProperty().addListener(updateAsideViewer);
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
//        return dao.fetchWhere(FXCollections.observableArrayList(), "WHERE completed = 0 AND DATE(remind) <= CURRENT_DATE AND TIME(remind) <= CURRENT_TIME");

        query = switch (list.getType()) {
            case DAILY -> "my_day = 1 and DATE(due_date) == '" + LocalDate.now() + "'";
            case IMPORTANT -> "important = 1";
            case TASKS -> "list_id = 1";
            default -> "list_id = " + list.getId();
        };

        container = new DoubleListContainer(list, query);

        container.load();

        update(container.sizeProperty().get() == 0);
        container.selectedProperty().addListener(updateAsideModel);

//        panel.createLines(true);
    }


    /**
     * If there's no task on the list root, so the image with a message will
     * show.
     *
     * @param empty If it neeeds update the list root node.
     */
    private void update(boolean empty) {
        if (getScene() == null) return;
        this.getChildren().setAll(empty ? emptyContainer : container);
        var panel = (Panel) getScene().lookup("#panel");

        if (!empty) panel.createBackgroundLines();
        else panel.removeBackgroundLines();
    }

    public ListContainer getContainer() {
        return this.container;
    }

    public TaskItem getSelected() {
        return this.getContainer().getSelected();
    }

    public ObjectProperty<TaskItem> selectedProperty() {
        return this.getContainer().selectedProperty();
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