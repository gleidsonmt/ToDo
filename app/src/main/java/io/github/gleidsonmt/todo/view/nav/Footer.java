package io.github.gleidsonmt.todo.view.nav;

import io.github.gleidsonmt.glad.controls.icon.Icon;
import io.github.gleidsonmt.glad.controls.icon.SVGIcon;
import io.github.gleidsonmt.todo.global.TaskRepository;
import io.github.gleidsonmt.todo.model.List;
import io.github.gleidsonmt.todo.utils.I18n;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

/**
 * Description:
 *
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 *         Created On: Mar 14, 2026
 * 
 *         Version History: Initial version
 */
public class Footer extends GridPane {

    public Footer(ObservableList<List> lists) {
        Label label = new Label(I18n.get("drawer.newTask"));
        label.setAlignment(Pos.CENTER);
        label.setPrefWidth(300);
        label.getStyleClass().addAll("hover-item", "h5", "bold");
        label.setPadding(new Insets(10, 10, 10, 0));
        label.setCursor(Cursor.HAND);
        label.setGraphicTextGap(10);
        label.setGraphic(new SVGIcon(Icon.ADD));
        this.getChildren().setAll(label);

        GridPane.setColumnIndex(label, 0);

        GridPane.setHgrow(label, Priority.ALWAYS);

        setOnMouseClicked(e -> {
            var actualIncrementValue = lists.stream()
                    // filter with the patter name (Untitled [some number])
                    .filter(list -> list.getName().matches("Untitled [0-9]+"))
                    // transform this number in an integer
                    .map(list -> Integer.valueOf(list.getName().substring(list.getName().indexOf(" ") + 1)))
                    // get the maximun value
                    .reduce(0, (a, b) -> Integer.max(a, b));

            List list;

            if (actualIncrementValue == 0) {
                list = new List("Untitled 1");
            } else {
                list = new List("Untitled " + (actualIncrementValue + 1));
            }

            lists.add(list);

            TaskRepository repo = (TaskRepository) System.getProperties().get("repository");
            repo.store(list);
        });
    }

}
