package io.github.gleidsonmt.todo.view.aside;

import io.github.gleidsonmt.todo.view.panel.items.FavoriteButton;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

/**
 * @author Gleidson Neves da Silveira | <a href="mailto:gleidisonmt@gmail.com">gleidisonmt@gmail.com</a> <br>
 * Created on 15/10/2026
 */
public class TitleSection extends Section {

    public TitleSection() {



        GridPane container = new GridPane();
        container.getStyleClass().addAll("align-center-left pd-10".split("\\s+"));

        CheckBox checkBox = new CheckBox();
        checkBox.getStyleClass().addAll("check-circle".split("\\s+"));

        TextField field = new TextField("Praticar no duolingo");
        field.getStyleClass().addAll("inside-text-field text-14".split("\\s+"));

        FavoriteButton favoriteButton = new FavoriteButton();

        container.add(checkBox, 0,0);
        container.add(field, 1,0);
        GridPane.setHgrow(field, Priority.ALWAYS);
        container.add(favoriteButton, 2,0);
        getChildren().add(container);
    }
}
