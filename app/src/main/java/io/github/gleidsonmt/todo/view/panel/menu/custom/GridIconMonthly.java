package io.github.gleidsonmt.todo.view.panel.menu.custom;

import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.shape.Rectangle;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  12/12/2024
 */
public class GridIconMonthly extends GridIcon {
    public GridIconMonthly() {
        Rectangle recOne = createRectangle();
        Rectangle recTwo = createBullet();
        Rectangle recThree = createBullet();
        Rectangle recFour = createBullet();
        Rectangle recFive = createBullet();
        Rectangle recSix = createBullet();
        Rectangle recSeven = createBullet();


        this.getChildren().setAll(recOne, recTwo, recThree, recFour, recFive, recSix, recSeven);

        GridPane.setConstraints(recOne, 1,0, 1,1, HPos.CENTER, VPos.CENTER);
        GridPane.setConstraints(recTwo, 2,0, 1,1, HPos.CENTER, VPos.CENTER);
        GridPane.setConstraints(recThree, 0,1, 1,1, HPos.CENTER, VPos.CENTER);
        GridPane.setConstraints(recFour, 1,1, 1,1, HPos.CENTER, VPos.CENTER);
        GridPane.setConstraints(recFive, 2,1, 1,1, HPos.CENTER, VPos.CENTER);
        GridPane.setConstraints(recSix, 0,2, 1,1, HPos.CENTER, VPos.CENTER);
        GridPane.setConstraints(recSeven, 1,2, 1,1, HPos.CENTER, VPos.CENTER);

        this.setHgap(2);
        this.setVgap(2);
        this.setMaxWidth(Region.USE_PREF_SIZE);
        this.setMaxHeight(Region.USE_PREF_SIZE);
    }

}
