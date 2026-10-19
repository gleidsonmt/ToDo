

package io.github.gleidsonmt.todo.view.panel.items;

import io.github.gleidsonmt.glad.controls.icon.Icon;
import io.github.gleidsonmt.glad.controls.icon.SVGIcon;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ToggleButton;

/**
 * @author Gleidson Neves da Silveira | <a href="mailto:gleidisonmt@gmail.com">gleidisonmt@gmail.com</a> <br> <br>
 * Created On: Feb 26, 2026
 */
public class FavoriteButton extends ToggleButton {

    public FavoriteButton() {

        this.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        this.getStyleClass().addAll("btn-favorite min-w-30 min-h-30".split("\\s+"));
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
