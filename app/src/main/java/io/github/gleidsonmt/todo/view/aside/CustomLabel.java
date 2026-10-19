package io.github.gleidsonmt.todo.view.aside;

import io.github.gleidsonmt.glad.controls.icon.Icon;
import io.github.gleidsonmt.glad.controls.icon.SVGIcon;
import io.github.gleidsonmt.todo.view.panel.input.InputFieldItem;
import io.github.gleidsonmt.todo.view_model.TaskViewModel;
import javafx.scene.control.Label;

/**
 * @author Gleidson Neves da Silveira | <a href="mailto:gleidisonmt@gmail.com">gleidisonmt@gmail.com</a> <br>
 * Created on 17/10/2026
 */
public class CustomLabel<T> extends InputFieldItem<T> {

    public CustomLabel(String text, Icon icon, String tooltip) {
        super(text, icon, tooltip);
        setMaxWidth(Double.MAX_VALUE);
        setMaxHeight(Double.MAX_VALUE);

        setMouseTransparent(false);
        getStyleClass().addAll("pd-10 bg-transparent  ".split("\\s+"));
        setGraphicTextGap(10);
    }
}
