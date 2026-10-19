

package io.github.gleidsonmt.todo.view.panel.items;

import io.github.gleidsonmt.glad.base.Root;
import io.github.gleidsonmt.todo.view.MainView;
import io.github.gleidsonmt.todo.view.aside.Aside;
import io.github.gleidsonmt.todo.view.panel.events.TaskChangeEvent;
import io.github.gleidsonmt.todo.view.panel.items.options.Options;
import io.github.gleidsonmt.todo.view.panel.input.menu.TaskItemContextMenu;
import io.github.gleidsonmt.todo.view_model.ListViewModel;
import io.github.gleidsonmt.todo.view_model.TaskViewModel;
import javafx.beans.property.*;
import javafx.css.PseudoClass;
import javafx.geometry.VPos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import org.jspecify.annotations.NonNull;

/**
 * Description: UI component. Represents a task in the panel.
 *
 * @author Gleidson Neves da Silveira | <a href="mailto:gleidisonmt@gmail.com">gleidisonmt@gmail.com</a> <br> <br>
 * Created On: Feb 26, 2026
 * <p>
 * Version History: Initial version
 */
public class TaskItem extends GridToggle {
    // Components
    private final CheckBox circleIcon;
    private final Label text;
    private final FavoriteButton favorite;

    private final BooleanProperty needDetails = new SimpleBooleanProperty();

    private final Options options;

    private final TaskViewModel viewModel;
    private final ListViewModel listViewModel;

    private static final PseudoClass DETAILS = PseudoClass.getPseudoClass("details");

    public TaskItem(@NonNull TaskViewModel viewModel, ListViewModel listViewModel) {

        this.viewModel = viewModel;
        this.listViewModel = listViewModel;

        this.setId(String.valueOf(viewModel.getId()));
        this.setUserData(viewModel);

        this.circleIcon = new CheckBox();
        this.favorite = new FavoriteButton();
        this.text = new Title();

        this.options = new Options(this);
        needDetails.bindBidirectional(options.hasProperty());

        init();
        bind();
        setActions();
        registerListeners();

        this.addEventFilter(MouseEvent.MOUSE_RELEASED, _ -> setSelected(true));
    }

    private void init() {

        this.circleIcon.getStyleClass().add("check-circle");
        this.getStyleClass().add("task-item");
        this.getChildren().addAll(circleIcon, text, favorite);
        this.setPrefWidth(Double.MAX_VALUE);
        this.setHgap(5);

        pseudoClassStateChanged(DETAILS, needDetails.get());

        if (needDetails.get()) {
            detailsLayout();
        } else {
            minLayout();
        }
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
            if (!inScene())
                return;
            if (newVal) {
                this.text.getStyleClass().add("strike");
            } else {
                this.text.getStyleClass().remove("strike");
            }
            this.getViewModel().update();
            this.fireEvent(new TaskChangeEvent(TaskChangeEvent.COMPLETE, this.getViewModel()));
        });

        this.text.getStyleClass().add(completedProperty().get() ? "strike" : "");

        needDetails.addListener((_, _, newVal) -> {
            this.pseudoClassStateChanged(PseudoClass.getPseudoClass("details"), !newVal);
            if (!newVal) {
                minLayout();
            } else {
                detailsLayout();
            }
        });

        this.favoriteProperty().addListener((_, _, _) -> {
            this.getViewModel().update();
            this.fireEvent(new TaskChangeEvent(TaskChangeEvent.FAVORITE_CHANGED, this.getViewModel()));
        });

        this.getViewModel().myDayProperty().addListener((_, _, val) -> {
            this.getViewModel().update();
            this.fireEvent(new TaskChangeEvent(TaskChangeEvent.MY_DAY_CHANGED, this.getViewModel()));
        });

        this.getViewModel().dueDateProperty().addListener((_, _, _) -> {
            this.getViewModel().update();
//            this.fireEvent(new TaskChangeEvent(TaskChangeEvent.DUE_DATE_CHANGED, this.getViewModel()));
        });

        this.viewModel.listIDProperty().addListener((_, old, val) -> {
            if (old.longValue() != val.longValue()) {
                this.getViewModel().update();
                this.fireEvent(new TaskChangeEvent(TaskChangeEvent.MOVED, viewModel, old.longValue(), val.longValue()));
            }
        });

        this.selectedProperty().addListener((_,_,val) -> {

            if (getScene() == null) return;

            Root root = (Root) this.getScene().getRoot();
            MainView mainView = (MainView) root.getContent();
            this.getViewModel().dispose();
            mainView.setRight(new Aside(this.getViewModel()));
        });
    }

    private boolean inScene() {
        return getScene() != null;
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

    public BooleanProperty favoriteProperty() {
        return this.favorite.selectedProperty();
    }

    public BooleanProperty completedProperty() {
        return this.circleIcon.selectedProperty();
    }

    public TaskViewModel getViewModel() {
        return this.viewModel;
    }



    public ListViewModel getListViewModel() {
        return this.listViewModel;
    }

    @Override
    public String toString() {
        return "TaskItem[" +
               "{id=" + viewModel.getId() + ", " +
               "name=" + viewModel.getName() + ", " +
               "}]";
    }
}