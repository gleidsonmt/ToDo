package io.github.gleidsonmt.todo.view.aside;

import io.github.gleidsonmt.glad.base.Root;
import io.github.gleidsonmt.todo.view.MainView;
import io.github.gleidsonmt.todo.view_model.TaskViewModel;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.layout.VBox;

/**
 * @author Gleidson Neves da Silveira | <a href="mailto:gleidisonmt@gmail.com">gleidisonmt@gmail.com</a> <br>
 * Created on 12/10/2026
 */
public class Aside extends VBox {

    private final BooleanProperty opened  = new SimpleBooleanProperty();

    public Aside(TaskViewModel viewModel) {
        var close = new CloseButton();
        getStyleClass().addAll("aside".split("\\s+"));
        close.getStyleClass().addAll("hover:bg-medium-gray".split("\\s+"));
        getChildren().setAll(new CloseButton(), new Body(viewModel),  new Footer(viewModel));
        getStyleClass().addAll("w-300 border-medium-gray border-l-1 pd-10 align-center-right".split("\\s+"));

        opened.addListener((_, _, newValue) -> {
            if (!newValue) {
                if (getParent() instanceof MainView mainView) {
                    mainView.setRight(null);
                } else {
                    Root root = (Root) getParent();
                    root.behavior().drawer().hide();
                }
            }
        });
    }

    public boolean isOpened() {
        return opened.get();
    }

    public void setOpened(boolean opened) {
        this.opened.set(opened);
    }
}



