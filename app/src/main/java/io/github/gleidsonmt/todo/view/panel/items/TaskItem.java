package io.github.gleidsonmt.todo.view.panel.items;

import io.github.gleidsonmt.todo.model.ToDoTask;
import io.github.gleidsonmt.todo.view.panel.FavoriteButton;
import io.github.gleidsonmt.todo.view.panel.actions.CompleteAction;
import io.github.gleidsonmt.todo.view.panel.actions.ImportantAction;
import io.github.gleidsonmt.todo.view.panel.containers.ListContainer;
import io.github.gleidsonmt.todo.view.panel.menu.TaskItemContextMenu;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Bounds;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

/**
 * Description:
 *
 * @author Gleidson Neves da Silveira | <gleidisonmt@gmail.com>
 *         Created On: Feb 26, 2026
 * 
 *         Version History: Initial version
 */
public class TaskItem extends ToggleButton {

    private final GridPane body = new GridPane();

    private final CheckBox circleIcon;
    private final Label text;
    private final FavoriteButton favorite;

    private ToDoTask task;
    private ListContainer container;

    private boolean needDetails;

    private CompleteAction completed;
    private ImportantAction importantAction;

    private TaskItemViewModel viewModel;

    @Deprecated
    public TaskItem(ListContainer container, ToDoTask task) {
        this(container, task, true);
    }

    public TaskItem(ToDoTask task) {
        this.circleIcon = new CheckBox();
        this.circleIcon.setSelected(task.isCompleted());
        this.favorite = new FavoriteButton(task.isImportant());
        this.text = new Title(task.getName());
        this.setId(String.valueOf(task.getId()));

        this.viewModel = new TaskItemViewModel(task, this);

        init();
        configLayout();
        bind();
        registerListeners();
    }

    private void bind() {
        // text.strikethroughProperty().bind(this.completedProperty());
    }

    @Deprecated
    public TaskItem(ListContainer container, ToDoTask task, boolean needDetails) {
        this.container = container;
        this.task = task;
        this.needDetails = needDetails;

        this.viewModel = new TaskItemViewModel(task, this);

        this.circleIcon = new CheckBox();
        this.getStyleClass().add("check-circle");
        this.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        this.circleIcon.setSelected(task.isCompleted());

        // // for test
        // dueDate.set(task.getDueDate());
        // remind.set(task.getRemind());

        this.favorite = new FavoriteButton(task.isImportant());
        this.text = new Title(task.getName());

        if (task.isCompleted()) {
            this.text.getStyleClass().add("strike");
        }

        init();
        configLayout();
        registerListeners();
    }

    private void configLayout() {

        this.setOnMousePressed(e -> {

            Bounds bounds = this.localToScene(this.getLayoutBounds());

        });

        this.setOnContextMenuRequested(e -> {
            var contextMenu = new TaskItemContextMenu(viewModel);

            contextMenu.show(this, Side.BOTTOM, e.getX(), e.getY() - 50);
        });

    }

    private void registerListeners() {
        completedProperty().addListener((_, _, newVal) -> {
            if (newVal) {
                this.text.getStyleClass().add("strike");
            } else {
                this.text.getStyleClass().remove("strike");
            }
        });

        this.text.getStyleClass().add(completedProperty().get() ? "strike" : "");

    }

    private void init() {

        this.circleIcon.getStyleClass().add("check-circle");

        this.setId("task-item");
        this.body.setId("task-container");
        this.body.setHgap(10);
        this.body.setVgap(2);
        this.body.setAlignment(Pos.CENTER_LEFT);
        this.setMinHeight(50);

        this.body.getChildren().addAll(circleIcon, text, favorite);
        this.body.setMinHeight(USE_PREF_SIZE); // this enables label to use wrap
                                               // text
        this.setMinHeight(50);
        this.setPrefHeight(50);

        this.setGraphic(body);

        this.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        this.setPrefWidth(Double.MAX_VALUE);

        minLayout();
    }

    // private void registerListeners() {
    // this.favorite.selectedProperty()
    // .addListener((ObservableValue<? extends Boolean> observable, Boolean
    // oldValue, Boolean newValue) -> {
    // task.setImportant(newValue);
    // container.update(task); // update the data
    // importantAction.handle(); // trigger an ui action
    // });

    // circleIcon.selectedProperty()
    // .addListener((ObservableValue<? extends Boolean> observable, Boolean
    // oldValue, Boolean newValue) -> {
    // task.setCompleted(newValue);
    // container.update(task);
    // completed.handle(this, task);
    // });
    // }

    public void minLayout() {

        GridPane.setHgrow(text, Priority.ALWAYS);
        GridPane.setVgrow(text, Priority.ALWAYS);

        GridPane.setColumnIndex(circleIcon, 0);
        GridPane.setColumnIndex(text, 1);

        GridPane.setColumnIndex(favorite, 2);
        GridPane.setRowSpan(circleIcon, GridPane.REMAINING);
        GridPane.setRowSpan(favorite, GridPane.REMAINING);

        // VBox.setVgrow(this, Priority.ALWAYS);

    }

    @Deprecated
    public ToDoTask getTask() {
        return this.task;
    }

    public void setOnCompletedChange(CompleteAction completeAction) {
        this.completed = completeAction;
    }

    public void setOnImportantChange(ImportantAction action) {
        this.importantAction = action;
    }

    public BooleanProperty favoriteProperty() {
        return this.favorite.selectedProperty();
    }

    public StringProperty nameProperty() {
        return this.text.textProperty();
    }

    public BooleanProperty completedProperty() {
        return this.circleIcon.selectedProperty();
    }

    public CompleteAction onCompletedChange() {
        return completed;
    }

    public ImportantAction onImportantChange() {
        return importantAction;
    }

    public TaskItemViewModel getViewModel() {
        return this.viewModel;
    }

    @Override
    public String toString() {
        StringBuilder build = new StringBuilder();
        build.append("TaskItem[");
        build.append("{id=").append(viewModel.getId()).append(", ");
        build.append("name=").append(viewModel.getName()).append(", ");
        build.append("}]");
        return build.toString();
    }

}
