package io.github.gleidsonmt.todo.view.panel.input;

import io.github.gleidsonmt.glad.controls.icon.Icon;
import io.github.gleidsonmt.glad.controls.icon.SVGIcon;
import io.github.gleidsonmt.todo.view.nav.SideNav;
import io.github.gleidsonmt.todo.view.panel.Panel;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;

/**
 * Description:
 *
 * @author Gleidson Neves da Silveira | <gleidisonmt@gmail.com>
 * Created On: Mar 02, 2026
 * <p>
 * Version History: Initial version
 */
public class InputField extends GridPane {

    private final TextField textInput;

    private final SVGIcon icon = new SVGIcon(Icon.ADD);
    private final InputFieldOptions options = new InputFieldOptions();;

    public InputField() {
        this.textInput = createTextField();
        init();
        configLayout();
        registerListeners();
    }

    public InputFieldOptions getOptions() {
        return options;
    }

    private void init() {
        this.setId("input-container");
        this.setMaxWidth(Region.USE_PREF_SIZE);
        this.setPrefHeight(50);
        this.setMaxHeight(50);
        this.getChildren().addAll(icon, textInput);
    }

    private void configLayout() {
        StackPane.setMargin(this, new Insets(0, 15, 0, 0));

        GridPane.setColumnIndex(icon, 0);
        GridPane.setColumnIndex(textInput, 1);
        GridPane.setColumnIndex(options, 2);

        StackPane.setAlignment(this, Pos.BOTTOM_CENTER);
        StackPane.setMargin(this, new Insets(50, 50, 50, 50));

        GridPane.setHgrow(textInput, Priority.ALWAYS);
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setMinWidth(30);
        this.getColumnConstraints().add(col1);
        GridPane.setVgrow(textInput, Priority.ALWAYS);
    }

    private void registerListeners() {
        textInput.textProperty().addListener((_, _, newValue) -> {

            ((Panel) getParent()).getListRoot().getActualList().getId();

            options.needsTaskItem(((Panel) getParent()).getListRoot().getActualList().isFixed());
            var nav = (SideNav) getScene().lookup("#drawer");

            System.out.println("getScene() = " + nav.get(0));
            System.out.println("nav.getCustomLists() = " + nav.getCustomLists());

            if (!newValue.isEmpty()) {
                if (!getChildren().contains(options))
                    getChildren().add(options);
            } else {
                getChildren().remove(options);
            }
        });

        textInput.focusedProperty()
                .addListener((_, _, newValue) -> icon.setIcon(newValue ? Icon.CIRCLE : Icon.ADD));

        this.parentProperty().addListener((_, _, newValue) -> {
            if (newValue != null) {
                prefWidthProperty().bind(((Region) newValue).widthProperty().subtract(50));
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
//                    TaskViewModel viewModel = new TaskViewModel();
//                    viewModel.setCreatedAt(LocalDate.now());
//                    viewModel.setName(textInput.getText());
//                    viewModel.setCompleted(false);
//                    viewModel.setImportant(false);
//                    viewModel.setMyDay(false);
//                    viewModel.setDueDate(dueDate.getValue());
//                    viewModel.setRemind(remind.getValue());
//                    tasks.getValue();
//                    remind.getValue();
////                    viewModel.setListId(panel.getListRoot().getActualList().getId());
//                    viewModel.setListId(tasks.getValue().getId());
//                    viewModel.save();
//
//                    panel.getListRoot().getContainer().fireEvent(new TaskChangeEvent(TaskChangeEvent.ADD, viewModel));
//
//                    Recurrence recurrence = repeat.getValue();
//                    if (recurrence == null) return;
//
//                    recurrence.setTaskID(viewModel.getId());
//                    viewModel.storeRecurrence(recurrence);

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