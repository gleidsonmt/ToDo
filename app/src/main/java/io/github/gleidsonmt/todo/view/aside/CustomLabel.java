package io.github.gleidsonmt.todo.view.aside;

import io.github.gleidsonmt.glad.controls.icon.Icon;
import io.github.gleidsonmt.glad.controls.icon.SVGIcon;
import javafx.scene.control.Label;

/**
 * @author Gleidson Neves da Silveira | <a href="mailto:gleidisonmt@gmail.com">gleidisonmt@gmail.com</a> <br>
 * Created on 17/10/2026
 */
public class CustomLabel extends Label {
    public CustomLabel(String text, Icon icon) {
        setText(text);
        setMouseTransparent(false);
        getStyleClass().addAll("pd-10 bg-transparent  ".split("\\s+"));
        setGraphicTextGap(10);
        setGraphic(new SVGIcon(icon));
    }
}
