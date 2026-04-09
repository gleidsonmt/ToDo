package io.github.gleidsonmt.todo.view.panel.input;

import java.time.LocalDate;

import io.github.gleidsonmt.glad.controls.icon.Icon;
import io.github.gleidsonmt.glad.controls.icon.SVGIcon;
import io.github.gleidsonmt.todo.model.ToDoTask;
import io.github.gleidsonmt.todo.view.panel.ListRootNew;
import io.github.gleidsonmt.todo.view.panel.Panel;
import io.github.gleidsonmt.todo.view_model.TaskViewModel;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

/**
 * Description:
 *
 * @author Gleidson Neves da Silveira | <gleidisonmt@gmail.com>
 *         Created On: Mar 02, 2026
 * 
 *         Version History: Initial version
 */
public class InputField extends GridPane {

    private final TextField textInput;
    private final SVGIcon icon;
    private final HBox items;
    private final InputFieldItem tasks;

    private ToDoTask task;

    public InputField() {
        // this.taskItem = taskItem;
        this.task = new ToDoTask("");
        this.textInput = createTextField();
        icon = new SVGIcon(Icon.ADD);

        tasks = InputFieldFactory.createItem(InputFieldType.TASK, task);
        items = createActions(task);

        init();
        configLayout();
        registerListeners();
    }

    public void addTasksItem(boolean add) {
        if (add) {
            if (!items.getChildren().contains(tasks))
                items.getChildren().add(0, tasks);
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

        textInput.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.isEmpty()) {
                if (!getChildren().contains(items))
                    getChildren().add(items);
            } else {
                getChildren().remove(items);
            }
        });

        textInput.focusedProperty()
                .addListener((observable, oldValue, newValue) -> icon.setIcon(newValue ? Icon.CIRCLE : Icon.ADD));

        this.parentProperty().addListener((observable, oldValue, newValue) -> {
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

                    ListRootNew listRoot = (ListRootNew) panel.getScene().lookup("#list-root");
                    var list = listRoot.getContainer().getList();

                    // Create another using the prepared here.
                    task = new ToDoTask(0, textInput.getText(), false, false, false, null, null, LocalDate.now(),
                            list.getId()

                    );

                    TaskViewModel taskViewModel = new TaskViewModel(task);
                    taskViewModel.save();
                    listRoot.getContainer().getData().add(taskViewModel);
                    list.addNumberOfTasks(1);
                    list.update();

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

    private HBox createActions(ToDoTask task) {
        var _items = new HBox();
        _items.setAlignment(Pos.CENTER);

        _items = new HBox(InputFieldFactory.createItem(InputFieldType.DUE_DATE, task),
                InputFieldFactory.createItem(InputFieldType.REMIND, task),
                InputFieldFactory.createItem(InputFieldType.REPEAT, task));
        return _items;
    }

    private long getListId() {
        var item = items.getChildren().stream().filter(el -> el instanceof InputFieldItemTask)
                .map(el -> (InputFieldItemTask) el).findFirst();
        return item.isPresent() ? item.get().getListId() : 0;
    }
}
