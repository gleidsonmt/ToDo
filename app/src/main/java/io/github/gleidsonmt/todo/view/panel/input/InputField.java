

package io.github.gleidsonmt.todo.view.panel.input;

import io.github.gleidsonmt.glad.controls.icon.Icon;
import io.github.gleidsonmt.glad.controls.icon.SVGIcon;
import io.github.gleidsonmt.todo.model.recurrence.Recurrence;
import io.github.gleidsonmt.todo.view.panel.Panel;
import io.github.gleidsonmt.todo.view.panel.events.TaskChangeEvent;
import io.github.gleidsonmt.todo.view_model.TaskViewModel;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.css.PseudoClass;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;

import java.time.LocalDate;

/**
 * Description:
 *
 * @author Gleidson Neves da Silveira | <a href="mailto:gleidisonmt@gmail.com">gleidisonmt@gmail.com</a> <br> <br>
 * Created On: Mar 02, 2026
 * <p>
 * Version History: Initial version
 */
public class InputField extends GridPane {

    private final TextField textInput;

    private final SVGIcon icon = new SVGIcon(Icon.ADD);
    private  InputFieldOptions options = new InputFieldOptions();

    private double boxHeight = 50;

    public InputField() {
        this.textInput = createTextField();
        init();
        configLayout();
        registerListeners();
        
        widthProperty().addListener((_, _, val) -> {
            System.out.println("val = " + val);
            if (val.doubleValue() <= 480) {
                smallLayout();
            } else {
                wideLayout();
            }
        });
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

    private void smallLayout() {
        getColumnConstraints().clear();
        getRowConstraints().clear();

        options.smallLayout(true);

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

    private void wideLayout() {
        getColumnConstraints().clear();
        getRowConstraints().clear();

        options.smallLayout(false);

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
        getColumnConstraints().clear();
        getRowConstraints().clear();
        this.getChildren().remove(options);
        options = new InputFieldOptions();
        this.getChildren().addAll(options);
        textInput.clear();
        configLayout();

    }

    private void registerListeners() {
        textInput.textProperty().addListener((_, _, newValue) -> {

            ((Panel) getParent()).getListRoot().getActualList().getId();

            options.needsTaskItem(((Panel) getParent()).getListRoot().getActualList().isFixed());

//            if (!newValue.isEmpty()) {
//                if (!getChildren().contains(options))
//                    getChildren().add(options);
//            } else {
//                getChildren().remove(options);
//            }
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