package io.github.gleidsonmt.todo.view.aside;

import io.github.gleidsonmt.glad.controls.icon.Icon;
import io.github.gleidsonmt.glad.controls.icon.SVGIcon;
import javafx.scene.control.Button;

/**
 * @author Gleidson Neves da Silveira | <a href="mailto:gleidisonmt@gmail.com">gleidisonmt@gmail.com</a> <br>
 * Created on 15/10/2026
 */
public class CloseButton extends Button {

    public CloseButton() {
        getStyleClass().addAll("bg-transparent hover:bg-[grey-100] hover:cursor-hand h-50 max-h-40 pd-10 display-graphic".split("\\s+"));
        setGraphic(new SVGIcon(Icon.CLEAR));
        setCancelButton(true);
        setOnMouseClicked(_ -> {
            var aside = (Aside) getParent();
            aside.setOpened(false);
        });
    }
}
