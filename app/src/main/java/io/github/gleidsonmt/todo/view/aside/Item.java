package io.github.gleidsonmt.todo.view.aside;

import io.github.gleidsonmt.glad.tail.ClassConverter;
import io.github.gleidsonmt.todo.view_model.ViewModel;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.ListChangeListener;
import javafx.css.PseudoClass;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

/**
 * @author Gleidson Neves da Silveira | <a href="mailto:gleidisonmt@gmail.com">gleidisonmt@gmail.com</a> <br>
 * Created on 15/10/2026
 */
public class Item extends GridPane {

    private final BooleanProperty selected = new SimpleBooleanProperty(false);
    private final CloseButton closeButton = new CloseButton();

    public Item(Node node, ViewModel viewModel) {
        getStyleClass().addAll("bg-transparent h-50 align-center aside-item".split("\\s+"));
        getChildren().setAll(node);
        GridPane.setColumnIndex(closeButton, 1);
        GridPane.setHgrow(node, Priority.ALWAYS);

        setOnMouseClicked(e -> {
//            if (isSelected()) e.consume();
//            else
                setSelected(true);
        });

        var converter = new ClassConverter();
        pseudoClassStateChanged(PseudoClass.getPseudoClass("selected"), isSelected());

        selectedProperty().addListener((_, _, val) -> {
            System.out.println("val = " + val);
            if (val && !getChildren().contains(closeButton)) getChildren().add(closeButton);
            else getChildren().remove(closeButton);
            pseudoClassStateChanged(PseudoClass.getPseudoClass("selected"), val);
        });

        closeButton.setOnMouseClicked(_ -> {
            getChildren().remove(closeButton);
            setSelected(false);
            pseudoClassStateChanged(PseudoClass.getPseudoClass("selected"), false);
        });
    }

    public boolean isSelected() {
        return selected.get();
    }

    public BooleanProperty selectedProperty() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected.set(selected);
    }
}
