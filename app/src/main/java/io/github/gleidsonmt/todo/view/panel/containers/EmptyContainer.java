

package io.github.gleidsonmt.todo.view.panel.containers;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.text.TextAlignment;

/**
 * Description: Creates a single Pane with an image to show the user there's no task to see.
 *
 * @author Gleidson Neves da Silveira | <a href="mailto:gleidisonmt@gmail.com">gleidisonmt@gmail.com</a> <br> <br>
 * Created On: Feb 26, 2026
 */
public class EmptyContainer extends StackPane {

    public EmptyContainer() {
        this.setStyle("-fx-background-color: -fx-background;");
        Region img = new Region();
        img.getStyleClass().add("empty-list-image");
        img.setMaxSize(400, 400);
        img.setPrefSize(400, 400);
        this.setAlignment(Pos.CENTER);
        Label info = new Label("Empty list, let's try to put some information :D");
        info.setWrapText(true);
        info.setTextAlignment(TextAlignment.CENTER);
        this.getChildren().setAll(img, info);

        StackPane.setMargin(info, new Insets(100, 0, 0, 0));
    }

}
