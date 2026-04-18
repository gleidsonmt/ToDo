package io.github.gleidsonmt.todo.view.panel.menu.input_menu_items.custom_panels;

import com.dlsc.gemsfx.TimePicker;
import javafx.scene.control.Separator;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  10/04/2026
 */
public class CalendarTimePane extends CalendarPane {

    private final TimePicker timePicker = new TimePicker();

    public CalendarTimePane() {
        this.getStyleClass().add("calendar-timer");

        timePicker.setPrefWidth(200);
        timePicker.setMinutesSeparator(new Separator());

        this.getChildren().add(1, timePicker);
    }

    public LocalTime getTime() {
        return timePicker.getTime();
    }

    public LocalDateTime getDateTime() {
        return LocalDateTime.of(this.getSelected() == null ? LocalDate.now() : this.getSelected(), getTime());
    }
}
