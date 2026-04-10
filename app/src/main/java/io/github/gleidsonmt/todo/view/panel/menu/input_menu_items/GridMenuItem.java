package io.github.gleidsonmt.todo.view.panel.menu.input_menu_items;

import io.github.gleidsonmt.glad.controls.icon.Icon;
import io.github.gleidsonmt.glad.controls.icon.SVGIcon;
import javafx.geometry.HPos;
import javafx.scene.Node;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  09/04/2026
 */
public class GridMenuItem extends MenuItem {


    public GridMenuItem(String _text, Icon _icon) {
        this(_text, _icon, null);
    }

    public GridMenuItem(String _text, Icon _icon, String _legend) {
        this(_text, new SVGIcon(_icon), _legend);
    }

    public GridMenuItem(String _text, Node icon, String _legend) {
        GridPane container = new GridPane();
        container.setMinWidth(200);
        container.setMouseTransparent(true);
//        container.getStyleClass().add("container");

        var text = new Text(_text);

//        setText(_text);
//        textProperty().bindBidirectional(text.textProperty());

        container.getChildren().setAll(icon, text);
//        System.out.println("icon = " + icon.getStyleClass());

        GridPane.setColumnIndex(icon, 0);
        GridPane.setColumnIndex(text, 1);
        GridPane.setHgrow(text, Priority.ALWAYS);

        container.setMaxWidth(Region.USE_PREF_SIZE);
        container.setHgap(10);

        if (_legend != null) {
            var legend = new Text(_legend);
            container.getChildren().add(legend);
            GridPane.setColumnIndex(legend, 2);
            GridPane.setHgrow(legend, Priority.ALWAYS);
            GridPane.setHalignment(legend, HPos.RIGHT);
        }

        ColumnConstraints col1 = new ColumnConstraints();
        col1.setMinWidth(25);
        container.getColumnConstraints().add(col1);

        setGraphic(container);
//        super(text, new SVGIconOld(icon));
    }
}
