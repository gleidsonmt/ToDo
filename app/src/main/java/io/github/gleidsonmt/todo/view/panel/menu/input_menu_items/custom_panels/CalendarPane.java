

package io.github.gleidsonmt.todo.view.panel.menu.input_menu_items.custom_panels;

import com.dlsc.gemsfx.CalendarView;
import io.github.gleidsonmt.glad.base.Root;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.time.LocalDate;

/**
 * @author Gleidson Neves da Silveira | <a href="mailto:gleidisonmt@gmail.com">gleidisonmt@gmail.com</a> <br>
 * Create on  10/04/2026
 */
public class CalendarPane extends VBox {

    protected Button save = new Button("Save");
    protected CalendarView calendarView = new CalendarView();
    protected Button cancel = new Button("Cancel");

    public CalendarPane() {
        this.getStyleClass().add("calendar-container");

        save.setPrefWidth(120);
        cancel.setPrefWidth(120);
        cancel.setCancelButton(true);

        HBox buttons = new HBox(save, cancel);
        buttons.setMinHeight(50);
        buttons.setAlignment(Pos.CENTER);

        cancel.setOnAction(_ -> {
            Root root = (Root) getScene().getRoot();
            root.flow().remove(this);
        });

        this.getChildren().setAll(calendarView, buttons);

        buttons.setSpacing(10);
        this.setSpacing(10);
    }

    public void setOnSave(EventHandler<ActionEvent> eventHandler) {
        this.save.setOnAction(eventHandler);
    }

    public LocalDate getSelected() {
        return calendarView.getSelectionModel().getSelectedDate();
    }
}
