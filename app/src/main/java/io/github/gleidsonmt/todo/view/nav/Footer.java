package io.github.gleidsonmt.todo.view.nav;

import io.github.gleidsonmt.glad.controls.icon.Icon;
import io.github.gleidsonmt.glad.controls.icon.SVGIcon;
import io.github.gleidsonmt.todo.global.TaskRepository;
import io.github.gleidsonmt.todo.model.List;
import io.github.gleidsonmt.todo.utils.I18n;
import io.github.gleidsonmt.todo.utils.StringUtils;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

/**
 * Description: The Drawer Footer.
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

        setOnMouseClicked(e -> createNewList(lists));
    }

    /**
     * When this component is clicked, this method is triggered to add a new
     * list.
     * This list will be titled as Untitled 1.
     * The name will depends if the list are in the lists or not.
     * If there's no list untitled, add.
     * If there's on or more untitled lists, increment
     * ex. if a lists has a list called Untitled 1, the next list to add has to
     * be Untitled 2.
     * 
     * @param lists
     */
    private void createNewList(ObservableList<List> lists) {
        // first step, get the last new list with the pattern [untitled [0-9]]
        // and get his number
        var actualIncrementValue = lists.stream()
                // filter with the patter name (Untitled [some number])
                .filter(list -> list.getName().matches("Untitled [0-9]+"))
                // transform this number in an integer
                .map(list -> StringUtils.getLastNumber(list.getName()))
                // get the maximun value
                .reduce(0, (a, b) -> Integer.max(a, b));

        List list;

        if (actualIncrementValue == 0) {
            // if there's no patttern added to lists create
            list = new List("Untitled 1");
        } else {
            // if there's a list update the increment
            list = new List("Untitled " + (actualIncrementValue + 1));
        }

        // add to
        lists.add(list);

        TaskRepository repo = (TaskRepository) System.getProperties().get("repository");
        // add to dabases
        repo.store(list);
    }
}
