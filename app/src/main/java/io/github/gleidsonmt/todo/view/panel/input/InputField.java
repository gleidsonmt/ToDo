package io.github.gleidsonmt.todo.view.panel.input;

import io.github.gleidsonmt.glad.controls.icon.Icon;
import io.github.gleidsonmt.glad.controls.icon.SVGIcon;
import io.github.gleidsonmt.todo.model.List;
import io.github.gleidsonmt.todo.model.ToDoTask;
import io.github.gleidsonmt.todo.model.recurrence.Recurrence;
import io.github.gleidsonmt.todo.view.panel.Panel;
import io.github.gleidsonmt.todo.view.panel.events.TaskChangeEvent;
import io.github.gleidsonmt.todo.view.panel.menu.TaskContextMenu;
import io.github.gleidsonmt.todo.view.panel.menu.input_menu_items.CustomContextMenu;
import io.github.gleidsonmt.todo.view.panel.menu.input_menu_items.DueDateContextMenu;
import io.github.gleidsonmt.todo.view.panel.menu.input_menu_items.RemindContextMenu;
import io.github.gleidsonmt.todo.view.panel.menu.input_menu_items.RepeatContextMenu;
import io.github.gleidsonmt.todo.view_model.ListViewModel;
import io.github.gleidsonmt.todo.view_model.TaskViewModel;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
    private final SVGIcon icon;
    private final HBox items;

    private InputFieldItem<List> tasks;
    private final InputFieldItem<LocalDate> dueDate;
    private final InputFieldItem<LocalDateTime> remind;
    private final InputFieldItem<Recurrence> repeat;

    private ToDoTask task;

    public InputField() {
        // this.taskItem = taskItem;
        this.task = new ToDoTask("");
        this.textInput = createTextField();
        icon = new SVGIcon(Icon.ADD);

        tasks = new InputFieldItemTask();
        dueDate = new InputFieldItem<>(Icon.CALENDAR_MONTH, "Add a due date");
        remind = new InputFieldItem<>(Icon.CLOCK, "Remind me");
        repeat = new InputFieldItem<>(Icon.EVENT_REPEAT, "Repeat");

//        dueDate = new InputFieldItem<>(Icon.CALENDAR_MONTH, "Add a due date", new DueDateContextMenu());
//        remind = new InputFieldItem<>(Icon.CLOCK, "Remind me", new RemindContextMenu());
//        repeat = new InputFieldItem<>(Icon.EVENT_REPEAT, "Repeat", new RepeatContextMenu());
        items = createActions();

        init();
        configLayout();
        registerListeners();
    }

    public void addTasksItem(boolean add) {
        if (add) {
            if (!items.getChildren().contains(tasks))
                items.getChildren().addFirst(tasks);
        } else {
            items.getChildren().remove(tasks);
        }
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
        GridPane.setColumnIndex(items, 2);

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
//            addTasksItem(!newValue.isEmpty());
            ((Panel) getParent()).getListRoot().getActualList().getId();

            if ( !((Panel) getParent()).getListRoot().getActualList().isFixed() ) {
                addTasksItem(true);
            }

            if (!newValue.isEmpty()) {
                if (!getChildren().contains(items))
                    getChildren().add(items);
            } else {
                getChildren().remove(items);
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

                    System.out.println("tasks.getValue() = " + tasks.getValue());

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

    private HBox createActions() {
        var _items = new HBox();
        _items.setAlignment(Pos.CENTER);
        _items = new HBox(dueDate, remind, repeat);
        return _items;
    }
}
