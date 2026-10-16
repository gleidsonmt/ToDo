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
 * Description: The footer on the bottom of the sidebar.
 * @author Gleidson Neves da Silveira | <a href="mailto:gleidisonmt@gmail.com">gleidisonmt@gmail.com</a> <br>
 * Created On: Mar 21, 2026
 * <p>
 * Version History: Initial version
 */
public class Footer extends GridPane {

    private final Label label;

    public Footer() {
        label = new Label(I18n.get("drawer.new.list"));
        configLayout();
        setOnMouseClicked(_ -> createNewList());
    }

    private void configLayout() {
        label.setPrefWidth(300);
        label.getStyleClass().addAll("hover-item h5 bold align-center padding-10 cursor-hand".split(" "));
        label.setGraphicTextGap(10);
        label.setGraphic(new SVGIcon(Icon.ADD));
        this.getChildren().setAll(label);

        GridPane.setColumnIndex(label, 0);
        GridPane.setHgrow(label, Priority.ALWAYS);
    }

    /**
     * When this component is clicked, this method is triggered to add a new list.
     * This list will be titled as Untitled 1.
     * The name will depend on if the list is in the lists or not.
     * If there's no list untitled, add.
     * If there are on or more untitled lists, increment
     * Ex. If a list has a list called Untitled 1, the next list to add has to be Untitled 2.
     */
    private void createNewList() {

        var nav = (SideNav) getScene().lookup("#drawer");

        int actualVal = nav.getModels().stream()
                // filter with the patter name (Untitled [some number])
                .filter(el -> el.getName().matches("Untitled [0-9]+"))
                // transform this number in an integer
                .map(list -> StringUtils.getLastNumber(list.getName()))
                // get the maximun value
                .reduce(0, Integer::max);

        nav.addAndSelect( new ListViewModel(actualVal == 0 ? "Untitled 1" : "Untitled " + (actualVal + 1)).save() );
    }
}