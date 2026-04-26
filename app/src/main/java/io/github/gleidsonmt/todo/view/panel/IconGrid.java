package io.github.gleidsonmt.todo.view.panel;

import io.github.gleidsonmt.todo.utils.Assets;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Node;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 *
 * @author Gleidson Neves da Silveira | <gleidisonmt@gmail.com>
 * Created on  23/04/2026
 */
public class IconGrid extends GridPane {

    private final ObjectProperty<Image> selected = new SimpleObjectProperty<>();

    int cols = 0;
    int rows = 0;

    public IconGrid() {
        getStyleClass().add("icon-grid");
        try {

            // 6
            Assets.getAllIcons(22).forEach(image -> {
                Node iconView = new ImageView(image);
                Label item = new Label(iconView.toString());
                item.setGraphic(iconView);
                item.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                getChildren().add(item);
                GridPane.setColumnIndex(item, cols);
                GridPane.setRowIndex(item, rows);

                item.setOnMouseClicked(_ -> selected.set(image));

                cols++;
                if (cols == 6) {
                    cols = 0;
                    rows++;
                }
            });
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public Image getSelected() {
        return selected.get();
    }

    public ObjectProperty<Image> selectedProperty() {
        return selected;
    }
}
