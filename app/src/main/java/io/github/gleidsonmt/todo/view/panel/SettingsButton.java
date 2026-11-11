package io.github.gleidsonmt.todo.view.panel;

import io.github.gleidsonmt.glad.base.Root;
import io.github.gleidsonmt.glad.controls.button.Button;
import io.github.gleidsonmt.glad.controls.button.IconButton;
import io.github.gleidsonmt.glad.controls.icon.Icon;
import io.github.gleidsonmt.glad.controls.icon.SVGIcon;
import io.github.gleidsonmt.todo.view.panel.containers.ThemeBox;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

/**
 * @author Gleidson Neves da Silveira | <a href="mailto:gleidisonmt@gmail.com">gleidisonmt@gmail.com</a> <br>
 * Created on 10/11/2026
 */
public class SettingsButton extends IconButton {

    public SettingsButton(Panel panel) {
        super(Icon.MORE_HOZ);
        setCancelButton(true);

        setOnMouseClicked(event -> {
            Root root = (Root) getScene().getRoot();
            root.flow()
                    .height(500)
                    .width(300)
                    .pos(Pos.BOTTOM_RIGHT)
                    .content(new ThemeBox(panel))
                    .show(this);
        });
    }
}
