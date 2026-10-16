

package io.github.gleidsonmt.todo.view.panel.input.menu.input_menu_items.custom_panels;

import io.github.gleidsonmt.todo.model.RecurrenceViewModel;
import io.github.gleidsonmt.todo.model.recurrence.*;
import io.github.gleidsonmt.todo.view.panel.input.menu.input_menu_items.DateUtils;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.scene.control.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.util.StringConverter;
import javafx.util.converter.NumberStringConverter;

/**
 *
 * @author Gleidson Neves da Silveira | <a href="mailto:gleidisonmt@gmail.com">gleidisonmt@gmail.com</a> <br> <br>
 * Created on  13/04/2026
 */
public class RecurrenceBox extends GridPane {

    private Label title;
    private TextField input;
    private ButtonBar buttonBar;
    private ComboBox<RecurrenceViewModel> comBox;
    private WeekBox weekDays;

    private Button save;
    private Button cancel;

    private ObjectProperty<RecurrenceViewModel> selected = new SimpleObjectProperty<>();

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

    private ComboBox<RecurrenceViewModel> createComboBox() {
        comBox = new ComboBox<>();
        comBox.setMaxWidth(300);

        StringConverter<RecurrenceViewModel> converter = new StringConverter<>() {
            @Override
            public String toString(RecurrenceViewModel object) {
                return object != null ? DateUtils.shortName(object.getType()) : "";
            }

            @Override
            public RecurrenceViewModel fromString(String string) {
                return null;
            }
        };
        comBox.setConverter(converter);

        comBox.getItems().setAll(
                new RecurrenceViewModel(RecurrenceType.DAILY),
                new RecurrenceViewModel(RecurrenceType.WEEKLY),
                new RecurrenceViewModel(RecurrenceType.MONTHLY),
                new RecurrenceViewModel(RecurrenceType.YEARLY)
                );

        selected.addListener((_, oldValue, newValue) -> {
            if (oldValue != null) {
                Bindings.unbindBidirectional(input.textProperty(), oldValue.gapProperty());
            }
            if (newValue != null) {
                Bindings.bindBidirectional(input.textProperty(), newValue.gapProperty(), new NumberStringConverter());
            }
        });
        selected.bindBidirectional(comBox.valueProperty());

        comBox.getSelectionModel().select(0);

        comBox.valueProperty().addListener((_, _, newValue) -> {
            updateLayout(newValue);
        });

        return comBox;
    }


    public void updateLayout(RecurrenceViewModel newValue) {
        if (newValue.getType().equals(RecurrenceType.WEEKLY)) {
//            if (weekDays == null) weekDays = new WeekBox((Weekly) newValue);
            if (weekDays == null) weekDays = new WeekBox(newValue);
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

    public RecurrenceViewModel getSelected() {
        return comBox.getSelectionModel().getSelectedItem();
    }
}