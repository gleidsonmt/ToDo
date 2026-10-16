

package io.github.gleidsonmt.todo.view.panel.input.menu.input_menu_items.custom_panels;

import io.github.gleidsonmt.todo.model.RecurrenceViewModel;
import javafx.geometry.HPos;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

import java.time.DayOfWeek;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Gleidson Neves da Silveira | <a href="mailto:gleidisonmt@gmail.com">gleidisonmt@gmail.com</a> <br> <br>
 * Created on  13/04/2026
 */
public class WeekBox extends GridPane {

//    public WeekBox(Weekly weekly) {
    public WeekBox(RecurrenceViewModel recurrence) {
        getStyleClass().add("week-box");
        int col = 0;
        int row = 0;
        this.setVgap(10);
        List<DayOfWeek> arr = Arrays.stream(DayOfWeek.values()).sorted().toList();
        recurrence.getDaysOfWeek().clear();
        for (int i = 0; i < arr.size(); i++) {
            ToggleWeek toggleWeek = new ToggleWeek(arr.get(i).name().substring(0, 2));
            int finalI = i;
            toggleWeek.selectedProperty().addListener((_, _, newValue) -> {
               if (newValue) {
                   recurrence.getDaysOfWeek().add(arr.get(finalI));
               } else {
                   recurrence.getDaysOfWeek().remove(arr.get(finalI));
               }
            });
            // prevents - none toggle selected
            toggleWeek.addEventFilter(MouseEvent.MOUSE_PRESSED, e -> {
                if (recurrence.getDaysOfWeek().size() == 1 && toggleWeek.isSelected()) e.consume();
            });

            if (i == 0) {
                toggleWeek.setSelected(true);
            }
            toggleWeek.getStyleClass().add("toggle-week-day");
            this.getChildren().add(toggleWeek);
            GridPane.setConstraints(toggleWeek, col++, row);
            GridPane.setHgrow(toggleWeek, Priority.ALWAYS);
            GridPane.setHalignment(toggleWeek, HPos.CENTER);
            if (col == 4 ) {
                col = 0;
                row++;
            }
        }
    }
}
