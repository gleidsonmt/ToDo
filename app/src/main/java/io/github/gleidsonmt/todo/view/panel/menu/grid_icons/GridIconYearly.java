package io.github.gleidsonmt.todo.view.panel.menu.grid_icons;


import io.github.gleidsonmt.glad.controls.icon.SVGPathIcon;
import io.github.gleidsonmt.todo.view.panel.menu.custom.SVGPathFirework;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  12/12/2024
 */
public class GridIconYearly extends GridIcon {

    public GridIconYearly() {

        SVGPathIcon one = new SVGPathIcon(new SVGPathFirework(), Color.RED, 0.18);
        SVGPathIcon two = new SVGPathIcon(new SVGPathFirework(), Color.RED, 0.18);
        SVGPathIcon three = new SVGPathIcon(new SVGPathFirework(), Color.RED, 0.18);

        getChildren().addAll(one, two, three);

        GridPane.setConstraints(one, 0,0, 2,1, HPos.CENTER, VPos.CENTER);

        GridPane.setConstraints(two, 0,1, 1,1, HPos.CENTER, VPos.CENTER);
        GridPane.setConstraints(three, 1,1, 1,1, HPos.CENTER, VPos.CENTER);
    }
}
