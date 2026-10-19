package io.github.gleidsonmt.todo.view.aside;

import io.github.gleidsonmt.glad.base.Root;
import io.github.gleidsonmt.glad.controls.icon.Icon;
import io.github.gleidsonmt.glad.controls.icon.SVGIcon;
import io.github.gleidsonmt.glad.tail.Converter;
import io.github.gleidsonmt.todo.view.MainView;
import javafx.scene.control.Button;

/**
 * @author Gleidson Neves da Silveira | <a href="mailto:gleidisonmt@gmail.com">gleidisonmt@gmail.com</a> <br>
 * Created on 15/10/2026
 */
public class CloseButton extends Button {

    public CloseButton() {
        getStyleClass().addAll("bg-transparent hover:bg-[grey-100] h-50 pd-10 display-graphic".split("\\s+"));
        setGraphic(new SVGIcon(Icon.CLEAR));
        setOnMouseClicked(_ -> {
            if (getParent().getParent() instanceof MainView mainView) {
                mainView.setRight(null);
            } else {
                Root root = (Root) getParent().getParent();
                root.behavior().drawer().hide();
            }

        });
    }
}
