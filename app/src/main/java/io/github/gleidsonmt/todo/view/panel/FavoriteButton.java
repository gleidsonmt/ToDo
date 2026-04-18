package io.github.gleidsonmt.todo.view.panel;

import io.github.gleidsonmt.glad.controls.icon.Icon;
import io.github.gleidsonmt.glad.controls.icon.SVGIcon;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ToggleButton;

/**
 * @author Gleidson Neves da Silveira | <gleidisonmt@gmail.com>
 * Created On: Feb 26, 2026
 */
public class FavoriteButton extends ToggleButton {

    public FavoriteButton() {

        this.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        this.setPrefSize(30, 30);
        this.getStyleClass().add("btn-favorite");
        setGraphic(new SVGIcon(Icon.STAR));
        //
        this.selectedProperty().addListener((_, _, newValue) -> {
            if (newValue) {
                setGraphic(new SVGIcon(Icon.STAR_FILLED));
            } else {
                setGraphic(new SVGIcon(Icon.STAR));
            }
        });
    }
}
