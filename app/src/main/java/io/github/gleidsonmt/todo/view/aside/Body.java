package io.github.gleidsonmt.todo.view.aside;

import io.github.gleidsonmt.todo.view_model.ViewModel;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

/**
 * @author Gleidson Neves da Silveira | <a href="mailto:gleidisonmt@gmail.com">gleidisonmt@gmail.com</a> <br>
 * Created on 15/10/2026
 */
public class Body extends ScrollPane {

    private final VBox body = new VBox();

    public Body(ViewModel viewModel) {
        body.getStyleClass().addAll("spacing-10 pd-10".split("\\s+"));
        body.getChildren().setAll(
                new TitleSection(),
                new MyDaySection(viewModel),
                new MultipleBlockSection()
        );
        setContent(body);
        setFitToHeight(true);
        setFitToWidth(true);
        VBox.setVgrow(this, Priority.ALWAYS);

    }
}