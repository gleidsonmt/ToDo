

package io.github.gleidsonmt.todo.view.panel;

import com.dlsc.gemsfx.SVGImageView;
import com.github.weisj.jsvg.SVGDocument;
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
 * @author Gleidson Neves da Silveira | <a href="mailto:gleidisonmt@gmail.com">gleidisonmt@gmail.com</a> <br> <br>
 * Created on  23/04/2026
 */
public class IconGrid extends GridPane {

    private final ObjectProperty<SVGImageView> selected = new SimpleObjectProperty<>();

    int cols = 0;
    int rows = 0;

    public IconGrid() {
        getStyleClass().add("icon-grid");
            // 6
            Assets.getAllIcons(32).forEach(svgView -> {
                Label item = new Label();
                item.setGraphic(svgView);
                svgView.setMouseTransparent(true);
                item.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                getChildren().add(item);
                GridPane.setColumnIndex(item, cols);
                GridPane.setRowIndex(item, rows);

                item.setOnMouseClicked(_ -> selected.set(svgView));

                cols++;
                if (cols == 6) {
                    cols = 0;
                    rows++;
                }
            });
    }

    public SVGImageView getSelected() {
        return selected.get();
    }

    public ObjectProperty<SVGImageView> selectedProperty() {
        return selected;
    }
}
