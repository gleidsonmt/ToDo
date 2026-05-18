

package io.github.gleidsonmt.todo.view.login.signup;

import javafx.geometry.Pos;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.HBox;

/**
 * @author Gleidson Neves da Silveira | <a href="mailto:gleidisonmt@gmail.com">gleidisonmt@gmail.com</a>
 * Create on 27/10/2024
 */
public class StatusLabel extends HBox {

    public StatusLabel(int totalSteps) {

        for (int i = 0; i < totalSteps; i++) {
            ProgressBar pane = new ProgressBar();
            // pane.setStyle("-fx-background-color: -light-gray-2;
            // -fx-background-radius: 2px;");
            pane.setProgress(0);
            pane.setPrefWidth(200);
            pane.setPrefHeight(5);
            setSpacing(20);
            this.getChildren().add(pane);
        }
        this.setAlignment(Pos.BOTTOM_CENTER);
    }
}
