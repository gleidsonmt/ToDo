package io.github.gleidsonmt.todo.view.panel.items;

import java.time.LocalDate;

import io.github.gleidsonmt.todo.model.List;
import io.github.gleidsonmt.todo.view.panel.FavoriteButton;
import io.github.gleidsonmt.todo.view.panel.actions.CompleteAction;
import io.github.gleidsonmt.todo.view.panel.actions.ImportantAction;
import io.github.gleidsonmt.todo.view.panel.containers.ListContainer;
import io.github.gleidsonmt.todo.view.panel.menu.TaskItemContextMenu;
import io.github.gleidsonmt.todo.view_model.TaskViewModel;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

/**
 * Description: UI componentt. Represents a task in the panel.
 *
 * @author Gleidson Neves da Silveira | <gleidisonmt@gmail.com>
 *         Created On: Feb 26, 2026
 * 
 *         Version History: Initial version
 */
public class TaskItem extends ToggleButton {

    private final GridPane body = new GridPane();
    // Components
    private final CheckBox circleIcon;
    private final Label text;
    private final FavoriteButton favorite;
    //
    private BooleanProperty myDay;
    private ObjectProperty<LocalDate> dueDate;
    private ObjectProperty<List> list;

    // private ToDoTask task;
    private ListContainer container;

    private BooleanProperty needDetails = new SimpleBooleanProperty();

    private CompleteAction completed;
    private ImportantAction importantAction;

    private Options options;

    private final TaskViewModel viewModel;

    public TaskItem(TaskViewModel viewModel) {
        this.viewModel = viewModel;
        this.setId(String.valueOf(viewModel.getId()));

        this.circleIcon = new CheckBox();
        this.favorite = new FavoriteButton();
        this.text = new Title();

        this.myDay = new SimpleBooleanProperty(viewModel.isMyDay());
        this.dueDate = new SimpleObjectProperty<>(viewModel.getDueDate());
        this.list = new SimpleObjectProperty<>();

        this.options = new Options(viewModel);
        needDetails.bindBidirectional(options.hasProperty());

        init();
        // setActions();
        bind();
        registerListeners();
    }

    private void bind() {
        this.text.textProperty().bind(viewModel.nameProperty());
        this.circleIcon.selectedProperty().bindBidirectional(viewModel.completedProperty());
        this.favorite.selectedProperty().bindBidirectional(viewModel.importantProperty());
    }

    private void setActions() {
        var contextMenu = new TaskItemContextMenu(viewModel);
        this.setContextMenu(contextMenu);
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

        needDetails.addListener((_, _, newVal) -> {
            if (!newVal) {
                minLayout();
            } else {
                detailsLayout();
            }
        });
    }

    private void init() {

        this.circleIcon.getStyleClass().add("check-circle");

        this.getStyleClass().add("task-item");
        this.body.setId("task-container");
        this.body.setHgap(10);
        this.body.setVgap(2);
        this.body.setAlignment(Pos.CENTER_LEFT);
        this.setGraphic(body);

        this.body.getChildren().addAll(circleIcon, text, favorite);
        this.body.setPrefHeight(50);

        this.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);

        // this.setMinHeight(Region.USE_PREF_SIZE);
        this.body.setMinHeight(USE_PREF_SIZE); 
                                               
        // this.body.prefHeightProperty().bind(this.heightProperty());

        this.setPrefWidth(Double.MAX_VALUE);
        
        // this.minHeightProperty().bind(text.heightProperty().add(options.minHeightProperty()));
        // this.minHeightProperty().bind(this.body.heightProperty());
        this.prefHeightProperty().bind(this.body.heightProperty());

        minLayout();
        this.body.setGridLinesVisible(true);
    }

    public void minLayout() {
        this.body.getChildren().remove(options);

        // this.minHeightProperty().unbind();
        // this.body.getRowConstraints().clear();

        GridPane.setHgrow(text, Priority.ALWAYS);
        GridPane.setVgrow(text, Priority.ALWAYS);

        GridPane.setColumnIndex(circleIcon, 0);
        GridPane.setColumnIndex(text, 1);

        GridPane.setColumnIndex(favorite, 2);
    }

    private void detailsLayout() {

        this.body.getChildren().add(options);

        GridPane.setColumnIndex(circleIcon, 0);
        GridPane.setRowIndex(circleIcon, 0);
        GridPane.setColumnIndex(text, 1);
        GridPane.setRowIndex(text, 0);

        GridPane.setColumnIndex(favorite, 2);
        GridPane.setRowIndex(favorite, 0);

        GridPane.setColumnIndex(options, 1);
        GridPane.setRowIndex(options, 1);

        // GridPane.setVgrow(options, Priority.ALWAYS);
        GridPane.setHgrow(options, Priority.ALWAYS);

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

    public boolean isMyDay() {
        return this.myDay.get();
    }

    public LocalDate getDueDate() {
        return this.dueDate.get();
    }

    public long getListId() {
        return this.viewModel.getListId();
    }

    public CompleteAction onCompletedChange() {
        return completed;
    }

    public ImportantAction onImportantChange() {
        return importantAction;
    }

    public TaskViewModel getViewModel() {
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
