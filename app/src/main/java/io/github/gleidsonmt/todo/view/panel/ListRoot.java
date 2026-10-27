

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
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

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
    private Aside aside;

    private final ObjectProperty<ListViewModel> actualList = new SimpleObjectProperty<>();

    private final ChangeListener<Number> updateListener = (_, _, val) -> update(val.intValue() == 0);

    private final ChangeListener<Number> updateAsideViewer = (_, _, newVal) -> {
        if (aside == null) return;
        changeRight(newVal.doubleValue());
    };

    private final EventHandler<MouseEvent> mouseEventHandler = new EventHandler<>() {
        @Override
        public void handle(MouseEvent event) {
            if (event.getButton() == MouseButton.PRIMARY) {
                if (getSelected() == null) return;

                Root root = (Root) getScene().getRoot();
//        if (aside == null)
                aside = new Aside(getSelected().getViewModel());
//        else aside.setViewModel(newVal.getViewModel());
                changeRight(root.getWidth());
            }
        }
    };

    private void changeRight(double val) {
        Root root = (Root) getScene().getRoot();
        MainView mainView = (MainView) root.getContent();

        double breakPoint = 1000;
        if (val > breakPoint) {
            if (root.isBlocked()) return;

            root.behavior()
                    .drawer()
                    .hide();

            mainView.setRight(aside);
        } else {
//            if (root.behavior().drawer().isShowing()) return;
            mainView.setRight(null);
            if (root.isBlocked()) return;
            root.behavior()
                    .drawer()
                    .content(aside)
                    .with(WrapperEffect.GRAY)
                    .show(Pos.CENTER_RIGHT, Anchor.RIGHT);
        }
    }

    public ListRoot(MainView mainView) {
        init();

        this.actualList.addListener((_, _, newValue) -> {
            if (container != null) {
                container.sizeProperty().removeListener(updateListener);
            }
            this.getChildren().clear();
            updateContainer(newValue);
            container.sizeProperty().addListener(updateListener);

        });

        mainView.widthProperty().addListener(updateAsideViewer);
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
//            case DAILY -> "my_day = 1 and DATE(due_date) == '" + LocalDate.now() + "'";
            case DAILY -> "my_day = 1";
            case IMPORTANT -> "important = 1";
            case TASKS -> "list_id = 1";
            default -> "list_id = " + list.getId();
        };

        container = new DoubleListContainer(list, query);

        container.load();

        update(container.sizeProperty().get() == 0);
        container.setOnMouseClicked(mouseEventHandler);

//        Root root = (Root) getScene().getRoot();
//        MainView mainView = (MainView) root.getContent();
//        mainView.setRight(null);


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