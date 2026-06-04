package io.github.gleidsonmt.todo.view.panel;

import io.github.gleidsonmt.glad.base.Anchor;
import io.github.gleidsonmt.glad.base.Root;
import io.github.gleidsonmt.glad.base.dialog.WrapperEffect;
import io.github.gleidsonmt.glad.controls.button.Button;
import io.github.gleidsonmt.glad.controls.icon.Icon;
import io.github.gleidsonmt.glad.controls.icon.SVGIcon;
import io.github.gleidsonmt.todo.view.MainView;
import javafx.geometry.Pos;
import javafx.scene.control.ContentDisplay;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;

/**
 * Description:
 *
 * @author Gleidson Neves da Silveira | <a href="mailto:gleidisonmt@gmail.com">gleidisonmt@gmail.com</a> <br>
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

            MainView layout = (MainView) root.getContent();

            root.behavior().drawer()
                    .with("gray")
                    .content(layout.getNav())
                    .show();

        });
    }
}
