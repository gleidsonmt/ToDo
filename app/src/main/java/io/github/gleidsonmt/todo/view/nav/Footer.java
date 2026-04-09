package io.github.gleidsonmt.todo.view.nav;

import io.github.gleidsonmt.glad.controls.icon.Icon;
import io.github.gleidsonmt.glad.controls.icon.SVGIcon;
import io.github.gleidsonmt.todo.utils.I18n;
import io.github.gleidsonmt.todo.utils.StringUtils;
import io.github.gleidsonmt.todo.view_model.ListViewModel;
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
 *         Created On: Mar 21, 2026
 * 
 *         Version History: Initial version
 */
public class Footer extends GridPane {

    private final Label label;

    public Footer() {
        label = new Label(I18n.get("drawer.new.list"));
        configLayout();
        setOnMouseClicked(e -> createNewList());
    }

    private void configLayout() {
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
    private void createNewList() {

        var nav = (SideNavNew) getScene().lookup("#drawer");

        var actualVal = nav.getModels().stream()
                // filter with the patter name (Untitled [some number])
                .filter(el -> el.getName().matches("Untitled [0-9]+"))
                // transform this number in an integer
                .map(list -> StringUtils.getLastNumber(list.getName()))
                // get the maximun value
                .reduce(0, (a, b) -> Integer.max(a, b));

        ListViewModel model;

        if (actualVal == 0) {
            // if there's no patttern added to lists create
            model = new ListViewModel("Untitled 1");
        } else {
            // if there's a list update the increment
            model = new ListViewModel("Untitled " + (actualVal + 1));
        }

        nav.add(model.save());
    }
}