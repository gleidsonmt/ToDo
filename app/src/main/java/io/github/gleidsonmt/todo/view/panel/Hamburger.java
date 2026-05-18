

package io.github.gleidsonmt.todo.view.panel;

import io.github.gleidsonmt.glad.base.Anchor;
import io.github.gleidsonmt.glad.base.Layout;
import io.github.gleidsonmt.glad.base.Root;
import io.github.gleidsonmt.glad.base.dialog.WrapperEffect;
import io.github.gleidsonmt.glad.controls.button.Button;
import io.github.gleidsonmt.glad.controls.icon.Icon;
import io.github.gleidsonmt.glad.controls.icon.SVGIcon;
import javafx.animation.TranslateTransition;
import javafx.geometry.Pos;
import javafx.scene.control.ContentDisplay;
import javafx.scene.layout.Region;
import javafx.util.Duration;

/**
 * Description:
 *
 * @author Gleidson Neves da Silveira | <a href="mailto:gleidisonmt@gmail.com">gleidisonmt@gmail.com</a>
 * Created On: Mar 13, 2026
 * <p>
 * Version History: Initial version
 */
public class Hamburger extends Button {

    public Hamburger() {
        this("Button");
    }

    public Hamburger(String text) {
        super(text);
        getStyleClass().addAll("flat", "hamb");
        setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        setGraphic(new SVGIcon(Icon.MENU));

        // Bindings.select(text, steps)
        this.setOnAction(e -> {
            Root root = (Root) getScene().getRoot();

            Layout layout = root.getLayout();

            root.behavior().dialog().effect(WrapperEffect.GRAY).anchor(Anchor.LEFT).pos(Pos.CENTER_LEFT)
                    .content((Region) root.getLayout().getLeft()).width(300).show();

            TranslateTransition transition = new TranslateTransition(Duration.millis(200),
                    (Region) root.getLayout().getLeft().getParent());

            transition.setFromX(-300);
            transition.setToX(0);
            transition.play();

        });
    }

}
