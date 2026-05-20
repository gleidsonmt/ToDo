

package io.github.gleidsonmt.todo.view.panel.input;

import io.github.gleidsonmt.glad.controls.icon.Icon;
import io.github.gleidsonmt.glad.controls.icon.SVGIcon;
import io.github.gleidsonmt.todo.model.recurrence.Recurrence;
import io.github.gleidsonmt.todo.view.panel.Panel;
import io.github.gleidsonmt.todo.view.panel.events.TaskChangeEvent;
import io.github.gleidsonmt.todo.view_model.TaskViewModel;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;

import java.time.LocalDate;

/**
 * Description: The main input field in the panel.
 * This component is used to create a new task.
 * Creates a big field box with a graphic and a text input field, next to options to that task.
 * The options variety to remind, add a due date or recurrence date, and the task name.
 *
 * @author Gleidson Neves da Silveira | <a href="mailto:gleidisonmt@gmail.com">gleidisonmt@gmail.com</a> <br> <br>
 * Created On: Mar 02, 2026
 */
public class InputField extends GridPane {

    private final TextField textInput;

    private final SVGIcon icon = new SVGIcon(Icon.ADD);
    private final InputFieldOptions options = new InputFieldOptions();
    private double boxHeight = 50;

    public InputField() {
        this.textInput = createTextField();
        init();
        configLayout();
        registerListeners();

    }

    private void configLayout() {
        this.setPrefHeight(50);
        this.setMaxHeight(50);
        this.getChildren().addAll(icon, textInput);

        StackPane.setAlignment(this, Pos.BOTTOM_CENTER);
        StackPane.setMargin(this, new Insets(50, 50, 50, 50));
    }

    private void init() {
        this.setId("input-container");
        this.setMaxWidth(Region.USE_PREF_SIZE);
    }

    private void mediumLayout() {
        options.smallLayout(false);
        options.mediumLayout(true);
        singleBoxLayout();
    }

    private void smallLayout() {
        options.smallLayout(true);

        if (!getChildren().contains(options)) {
            singleBoxLayout();
        } else multiBoxLayout();

    }

    private void wideLayout() {
        options.smallLayout(false);
        options.mediumLayout(false);
        singleBoxLayout();
    }

    private void multiBoxLayout() {
        GridPane.setColumnIndex(icon, 0);
        GridPane.setColumnIndex(textInput, 1);
        GridPane.setColumnIndex(options, 0);
        GridPane.setRowIndex(options, 1);

        boxHeight = 120;
        setMaxHeight(boxHeight);

        GridPane.setHgrow(textInput, Priority.ALWAYS);

        GridPane.setVgrow(textInput, Priority.ALWAYS);
        GridPane.setColumnSpan(options, REMAINING);
        GridPane.setHalignment(options, HPos.CENTER);
        options.setAlignment(Pos.CENTER);
    }

    private void singleBoxLayout() {
        getColumnConstraints().clear();
        getRowConstraints().clear();

        GridPane.setColumnIndex(icon, 0);
        GridPane.setColumnIndex(textInput, 1);
        GridPane.setColumnIndex(options, 2);
        GridPane.setRowIndex(options, 0);

        boxHeight = 50;
        setMaxHeight(boxHeight);

        GridPane.setHgrow(textInput, Priority.ALWAYS);
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setMinWidth(30);
        this.getColumnConstraints().add(col1);
        GridPane.setVgrow(textInput, Priority.ALWAYS);
    }

    public void reset() {
        options.reset();
        textInput.clear();
    }

    private void updateLayout(double width) {
        if (width <= 480) smallLayout();
        else if (width <= 730) mediumLayout();
        else wideLayout();
    }

    private void registerListeners() {
        widthProperty().addListener((_, _, val) -> updateLayout(val.doubleValue()));

        textInput.textProperty().addListener((_, _, val) -> {
            System.out.println("getWidth() = " + getWidth());

            ((Panel) getParent()).getListRoot().getActualList().getId();

            options.needsTaskItem(((Panel) getParent()).getListRoot().getActualList().isFixed());

            if (!val.isEmpty()) {
                if (!getChildren().contains(options))
                    getChildren().add(options);

            } else {
                getChildren().remove(options);
            }

            updateLayout(getWidth());


        });

        textInput.focusedProperty()
                .addListener((_, _, newValue) -> icon.setIcon(newValue ? Icon.CIRCLE : Icon.ADD_CIRCLE));

        this.parentProperty().addListener((_, _, newValue) -> {
            if (newValue != null) {
                prefWidthProperty().bind(((Region) newValue).widthProperty().subtract(boxHeight));
            }
        });

        textInput.addEventFilter(KeyEvent.KEY_RELEASED, createStoreEvent());
    }

    private EventHandler<KeyEvent> createStoreEvent() {
        return (KeyEvent event) -> {
            if (event.getCode() == KeyCode.ENTER) {
                if (this.getParent() instanceof Panel panel) {
                    if (textInput.getText().isBlank())
                        return;
                    if (textInput.getText() == null)
                        return;
                    if (textInput.getText().isEmpty())
                        return;

                    // Create another using the prepared here.
                    TaskViewModel viewModel = new TaskViewModel();
                    viewModel.setName(textInput.getText());
                    viewModel.setCreatedAt(LocalDate.now());
//
                    viewModel.setRemind(options.getRemind());
                    viewModel.setDueDate(options.getDueDate());

                    // set the list
                    if (!panel.getListRoot().getActualList().isFixed()) {
                        viewModel.setListId(panel.getListRoot().getActualList().getId());
                    } else {
                        viewModel.setListId(options.getTask().getId());
                    }
                    viewModel.save();

                    // add on the list UI
//
                    Recurrence recurrence = options.getRecurrence();

                    if (recurrence != null) {
                        recurrence.setTaskID(viewModel.getId());
                        viewModel.storeRecurrence(recurrence);
                    }

                    TaskChangeEvent e = new TaskChangeEvent(TaskChangeEvent.ADD, viewModel);
                    panel.getListRoot().fireEvent(e);

                    panel.getListRoot().getContainer().add(viewModel);

                }
            }
        };
    }

    private TextField createTextField() {
        var field = new TextField();
        field.setPromptText("Add a task");
        field.setId("input-task");
        field.setPrefHeight(50);
        field.getStyleClass().addAll("h5", "inside-text-field");
        return field;
    }
}