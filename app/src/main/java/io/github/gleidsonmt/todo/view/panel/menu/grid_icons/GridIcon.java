

package io.github.gleidsonmt.todo.view.panel.menu.grid_icons;

import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * @author Gleidson Neves da Silveira | <a href="mailto:gleidisonmt@gmail.com">gleidisonmt@gmail.com</a> <br>
 * Create on  12/12/2024
 */
public class GridIcon extends GridPane {

    public GridIcon() {
        setMinHeight(20);
        setMinWidth(20);
    }

    protected Rectangle createRectangle() {
        Rectangle rectangle = createDefault();
        rectangle.setWidth(4);
        rectangle.setHeight(4);
        return rectangle;
    }

    protected Rectangle createDefault() {
        Rectangle rectangle = new Rectangle();
        rectangle.setStyle("-fx-stroke: -fx-accent;");
        rectangle.setStrokeWidth(1.2);
        rectangle.setFill(Color.TRANSPARENT);
        rectangle.setStroke(Color.BLACK);
        rectangle.setArcHeight(2);
        rectangle.setArcWidth(2);
        return rectangle;
    }

    protected Rectangle createBullet() {
        Rectangle rectangle = createDefault();
        rectangle.setWidth(2);
        rectangle.setHeight(2);
        return rectangle;
    }
}
