package io.github.gleidsonmt.todo.view.aside;

import io.github.gleidsonmt.todo.utils.I18n;
import io.github.gleidsonmt.todo.view_model.TaskViewModel;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * @author Gleidson Neves da Silveira | <a href="mailto:gleidisonmt@gmail.com">gleidisonmt@gmail.com</a> <br>
 * Created on 17/10/2026
 */
public class Footer extends GridPane {
    public Footer(TaskViewModel viewModel) {
        getStyleClass().addAll("align-center w-100 pd-5 border-t-1 border-medium-gray".split("\\s+"));
//        Label label = new Label("Created on qui, 18 de jun");
        Label label = new Label(I18n.get("aside.footer.title") + " " + DateTimeFormatter.ofPattern("EE, dd LLLL").format(LocalDate.now()));
        label.setAlignment(Pos.BASELINE_CENTER);
        label.setMaxWidth(Double.MAX_VALUE);
        add(label, 0, 0);
        add(new DeleteButton(viewModel), 1, 0);
        GridPane.setHgrow(label, Priority.ALWAYS);
    }
}
