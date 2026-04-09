package io.github.gleidsonmt.todo.view.panel.items;

import java.time.LocalDate;

import io.github.gleidsonmt.todo.view.panel.events.TaskChangeEvent;
import javafx.beans.property.*;
import org.jetbrains.annotations.ApiStatus.Experimental;

import io.github.gleidsonmt.todo.model.List;
import io.github.gleidsonmt.todo.model.ListType;
import io.github.gleidsonmt.todo.view.nav.SideNavNew;
import io.github.gleidsonmt.todo.view.panel.FavoriteButton;
import io.github.gleidsonmt.todo.view.panel.ListRootNew;
import io.github.gleidsonmt.todo.view.panel.actions.CompleteAction;
import io.github.gleidsonmt.todo.view.panel.actions.ImportantAction;
import io.github.gleidsonmt.todo.view.panel.menu.TaskItemContextMenu;
import io.github.gleidsonmt.todo.view_model.ListViewModel;
import io.github.gleidsonmt.todo.view_model.TaskViewModel;
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

    // Components
    private final CheckBox circleIcon;
    private final Label text;
    private final FavoriteButton favorite;
    //
    private BooleanProperty myDay;
    private ObjectProperty<LocalDate> dueDate;
    private LongProperty listID;

    private BooleanProperty needDetails = new SimpleBooleanProperty();

    private CompleteAction completed;
    private ImportantAction importantAction;

    private Options options;

    private final TaskViewModel viewModel;
    private final ListViewModel listViewModel;

    public TaskItem(TaskViewModel viewModel, ListViewModel listViewModel) {
        this.viewModel = viewModel;
        this.listViewModel = listViewModel;
        this.setId(String.valueOf(viewModel.getId()));
        this.setUserData(viewModel);

        this.circleIcon = new CheckBox();
        this.favorite = new FavoriteButton();
        this.text = new Title();

        this.myDay = new SimpleBooleanProperty(viewModel.isMyDay());
        this.dueDate = new SimpleObjectProperty<>(viewModel.getDueDate());
        this.listID = new SimpleLongProperty(viewModel.getListId());

        this.options = new Options(viewModel, !listViewModel.isFixed() || listViewModel.getId() != 0);
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
        this.myDay.bind(viewModel.myDayProperty());
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
            if (!inScene())
                return;
            if (newVal) {
                this.text.getStyleClass().add("strike");
            } else {
                this.text.getStyleClass().remove("strike");
            }
            this.fireEvent(new TaskChangeEvent(TaskChangeEvent.COMPLETE, this.getViewModel()));
        });

        this.text.getStyleClass().add(completedProperty().get() ? "strike" : "");

        needDetails.addListener((_, _, newVal) -> {
            if (!newVal) {
                minLayout();
            } else {
                detailsLayout();
            }
        });


        this.favoriteProperty().addListener((_, _, _) -> this.fireEvent(new TaskChangeEvent(TaskChangeEvent.FAVORITE_CHANGED, this.getViewModel())));
        this.myDay.addListener((_, _, _) -> this.fireEvent(new TaskChangeEvent(TaskChangeEvent.MY_DAY_CHANGED, this.getViewModel())));

        this.viewModel.listIdProperty().addListener((_,old,val) -> {
            if (old.longValue() != val.longValue()) {
                this.fireEvent(new TaskChangeEvent(TaskChangeEvent.MOVED, viewModel, old.longValue(), val.longValue()));
            }
        });
    }

    private boolean inScene() {
        return getScene() != null;
    }

    private SideNavNew getNav() {
        return (SideNavNew) getScene().lookup("#drawer");
    }

    private ListRootNew getListRoot() {
        return (ListRootNew) getScene().lookup("#list-root");
    }

    private void init() {

        this.circleIcon.getStyleClass().add("check-circle");
        this.getStyleClass().add("task-item");
        this.getChildren().addAll(circleIcon, text, favorite);
        this.setPrefWidth(Double.MAX_VALUE);
        this.setHgap(5);

        if (needDetails.get()) {
            detailsLayout();
        } else {
            minLayout();
        }
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