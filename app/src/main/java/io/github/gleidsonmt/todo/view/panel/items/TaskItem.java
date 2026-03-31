package io.github.gleidsonmt.todo.view.panel.items;

import java.time.LocalDate;

import org.jetbrains.annotations.ApiStatus.Experimental;

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
import javafx.geometry.VPos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
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
public class TaskItem extends GridToggle {

    // private final GridPane body = new GridPane();
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
        setActions();
        bind();
        registerListeners();

        this.addEventFilter(MouseEvent.MOUSE_RELEASED, e -> {
            setSelected(true);
        });
    }

    private void bind() {
        this.text.textProperty().bind(viewModel.nameProperty());
        this.circleIcon.selectedProperty().bindBidirectional(viewModel.completedProperty());
        this.favorite.selectedProperty().bindBidirectional(viewModel.importantProperty());
    }

    private void setActions() {
        this.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> {
            if (e.getButton() == MouseButton.SECONDARY) {
                var contextMenu = new TaskItemContextMenu(viewModel);
                contextMenu.show(this, e.getScreenX(), e.getScreenY());
            }
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

        needDetails.addListener((_, _, newVal) -> {
            if (!newVal) {
                minLayout();
            } else {
                detailsLayout();
            }
        });
    }

    private void init() {

        // this.setAlignment(Pos.TOP_LEFT);
        // this.text.setAlignment(Pos.TOP_LEFT);
        // this.circleIcon.setAlignment(Pos.TOP_LEFT);
        // this.circleIcon.setPadding(new Insets(10,0,0,0));

        this.circleIcon.getStyleClass().add("check-circle");
        this.getStyleClass().add("task-item");
        this.getChildren().addAll(circleIcon, text, favorite);
        this.setPrefWidth(Double.MAX_VALUE);
        this.setHgap(5);
        minLayout();

    }

    public void minLayout() {
        this.getChildren().remove(options);

        GridPane.setHgrow(text, Priority.ALWAYS);

        GridPane.setColumnIndex(circleIcon, 0);
        GridPane.setColumnIndex(text, 1);

        GridPane.setColumnIndex(favorite, 2);

        GridPane.setValignment(circleIcon, VPos.TOP);
    }

    private void detailsLayout() {

        this.getChildren().add(options);

        GridPane.setColumnIndex(circleIcon, 0);
        GridPane.setRowIndex(circleIcon, 0);
        GridPane.setColumnIndex(text, 1);
        GridPane.setRowIndex(text, 0);

        GridPane.setColumnIndex(favorite, 2);
        GridPane.setRowIndex(favorite, 0);

        GridPane.setColumnIndex(options, 1);
        GridPane.setRowIndex(options, 1);

        GridPane.setHgrow(text, Priority.ALWAYS);

        GridPane.setHgrow(options, Priority.ALWAYS);

        // this.setAlignment(Pos.TOP_LEFT);
        // this.text.setAlignment(Pos.TOP_LEFT);
        // this.circleIcon.setAlignment(Pos.TOP_LEFT);
    }

    @Experimental
    public void setOnCompletedChange(CompleteAction completeAction) {
        this.completed = completeAction;
    }

    @Experimental
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

    @Experimental
    public CompleteAction onCompletedChange() {
        return completed;
    }

    @Experimental
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
