package io.github.gleidsonmt.todo.view.aside;

import io.github.gleidsonmt.todo.view_model.TaskViewModel;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.List;

/**
 * @author Gleidson Neves da Silveira | <a href="mailto:gleidisonmt@gmail.com">gleidisonmt@gmail.com</a> <br>
 * Created on 15/10/2026
 */
public class Body extends ScrollPane {

    public Body(TaskViewModel viewModel) {
        VBox body = new VBox();
        body.getStyleClass().addAll("spacing-10 pd-10".split("\\s+"));
        List<Section> sections = List.of(
                new TitleSection(viewModel),
                new MyDaySection(viewModel),
                new MultipleBlockSection(viewModel),
                new FileSection(viewModel),
                new TextAreaSection()
        );
        for (Section section : sections) {
            section.getChildren().stream().filter(el -> el instanceof Item).map(Item.class::cast).forEach(Item::bind);
        }
        body.getChildren().setAll(sections);
        setContent(body);
        setFitToHeight(true);
        setFitToWidth(true);
        VBox.setVgrow(this, Priority.ALWAYS);

    }
}