package io.github.gleidsonmt.todo.view.panel.menu.input_menu_items;

import io.github.gleidsonmt.todo.model.recurrence.Daily;
import io.github.gleidsonmt.todo.model.recurrence.Recurrence;
import io.github.gleidsonmt.todo.model.recurrence.RecurrenceType;
import io.github.gleidsonmt.todo.model.recurrence.Weekly;
import io.github.gleidsonmt.todo.view.panel.menu.custom.Monthly;
import io.github.gleidsonmt.todo.view.panel.menu.custom.Yearly;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.IntegerBinding;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.scene.control.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.util.StringConverter;
import org.jetbrains.annotations.NotNull;

/**
 *
 * @author Gleidson Neves da Silveira | <gleidisonmt@gmail.com>
 * Created on  13/04/2026
 */
public class RecurrenceBox extends GridPane {

    private Label title;
    private TextField input;
    private ButtonBar buttonBar;
    private ComboBox<Recurrence> comBox;
    private WeekBox weekDays;

    private Button save;
    private Button cancel;

    public RecurrenceBox() {
//        setMaxSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
//        setPrefHeight(300);
        getStyleClass().add("recurrence-box");
        init();
        configLayout();
    }

    private void init() {
        title = new Label("Repeat every...");
        input = new TextField("1");
        save = new Button("Save");
        cancel = new Button("Cancel");
        save.setDefaultButton(true);
        comBox = createComboBox();
        buttonBar = createButtonBar();
//
        getChildren().addAll(title, input, comBox, buttonBar);
    }

    private ComboBox<Recurrence> createComboBox() {
        comBox = new ComboBox<>();
        comBox.setMaxWidth(300);

        StringConverter<Recurrence> converter = new StringConverter<>() {
            @Override
            public String toString(Recurrence object) {
                return object != null ? object.getShortName() : "";
            }

            @Override
            public Recurrence fromString(String string) {
                return null;
            }
        };
        comBox.setConverter(converter);

        comBox.getItems().setAll(new Daily(), new Weekly(), new Monthly(), new Yearly(1));
        comBox.getSelectionModel().select(0);

        comBox.valueProperty().addListener((_, _, newValue) -> {
            updateLayout(newValue);
        });

        comBox.getSelectionModel().selectedItemProperty().addListener((_, oldValue, newValue) -> {
            if (newValue != null) {
                IntegerBinding number = Bindings.createIntegerBinding(
                        () -> {
                            try {
                                return Integer.parseInt(input.getText());
                            } catch (NumberFormatException e) {
                                return 0;
                            }
                        },
                        input.textProperty()
                );
                newValue.gapProperty().bind(number);
                if (oldValue != null) oldValue.gapProperty().unbind();
            }
        });

        return comBox;
    }

    public void updateLayout(Recurrence newValue) {
        if (newValue.getType().equals(RecurrenceType.WEEKLY)) {
            if (weekDays == null) weekDays = new WeekBox((Weekly) newValue);
            this.getChildren().add(weekDays);
//            setPrefSize(230, 200);
//            prefHeight(500);
            GridPane.setConstraints(weekDays, 0, 2);
            GridPane.setColumnSpan(weekDays, REMAINING);
            GridPane.setConstraints(this.buttonBar, 0, 3);
        } else {
            this.getChildren().remove(weekDays);
            GridPane.setConstraints(this.buttonBar, 0, 2);
            // resize to default
            resize(prefWidth(-1), prefHeight(-1));
        }
    }

    private void configLayout() {
        GridPane.setConstraints(this.title, 0, 0);
        GridPane.setConstraints(this.input, 0, 1);
        GridPane.setConstraints(this.comBox, 1, 1);
        GridPane.setConstraints(this.buttonBar, 0, 2);
        GridPane.setHgrow(this.comBox, Priority.ALWAYS);

        GridPane.setColumnSpan(this.comBox, REMAINING);
        GridPane.setColumnSpan(this.buttonBar, REMAINING);
        GridPane.setColumnSpan(this.title, REMAINING);
        GridPane.setHalignment(this.buttonBar, HPos.CENTER);

        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(25);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(75);

        getColumnConstraints().addAll(col1, col2);
    }

    private ButtonBar createButtonBar() {
        ButtonBar bar = new ButtonBar();
        bar.setButtonMinWidth(120);
        bar.setMinHeight(50);
        cancel.setCancelButton(true);
        ButtonBar.setButtonData(save, ButtonBar.ButtonData.OK_DONE);
        ButtonBar.setButtonData(cancel, ButtonBar.ButtonData.CANCEL_CLOSE);
        bar.getButtons().setAll(cancel, save);
        return bar;
    }

    public void setOnSave(EventHandler<ActionEvent> eventHandler) {
        save.setOnAction(eventHandler);
    }

    public void setOnCancel(EventHandler<ActionEvent> eventHandler) {
        cancel.setOnAction(eventHandler);
    }

    public EventHandler<ActionEvent> getOnCancel() {
        return cancel.getOnAction();
    }

    public Recurrence getSelected() {
        return comBox.getSelectionModel().getSelectedItem();
    }
}
