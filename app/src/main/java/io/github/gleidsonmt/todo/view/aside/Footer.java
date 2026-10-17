package io.github.gleidsonmt.todo.view.aside;

import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

/**
 * @author Gleidson Neves da Silveira | <a href="mailto:gleidisonmt@gmail.com">gleidisonmt@gmail.com</a> <br>
 * Created on 17/10/2026
 */
public class Footer extends GridPane {
    public Footer() {
        getStyleClass().addAll("align-center pd-5 border-t-1 border-medium-gray".split("\\s+"));
        Label label = new Label("Created on qui, 18 de jun");

        add(label, 0, 0);
        add(new DeleteButton(), 1, 0);
    }
}
