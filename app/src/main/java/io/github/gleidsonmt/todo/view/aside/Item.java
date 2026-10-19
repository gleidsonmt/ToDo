package io.github.gleidsonmt.todo.view.aside;

import io.github.gleidsonmt.todo.view.panel.input.InputFieldItem;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.css.PseudoClass;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

/**
 * @author Gleidson Neves da Silveira | <a href="mailto:gleidisonmt@gmail.com">gleidisonmt@gmail.com</a> <br>
 * Created on 15/10/2026
 */
public abstract class Item<T> extends GridPane {

    private final BooleanProperty selected = new SimpleBooleanProperty(false);
    private final CloseButton closeButton = new CloseButton();
    private final InputFieldItem<T> node;

    public Item(InputFieldItem<T> node) {
        this.node = node;
        getStyleClass().addAll("bg-transparent h-50 align-center aside-item".split("\\s+"));
        getChildren().setAll(node);
        GridPane.setColumnIndex(closeButton, 1);
        GridPane.setHgrow(node, Priority.ALWAYS);

        pseudoClassStateChanged(PseudoClass.getPseudoClass("selected"), isSelected());

        node.valueProperty().addListener((_, _, newValue) -> {
            var needSelection = newValue instanceof Boolean ? (Boolean) newValue : newValue != null;

            setSelected(needSelection);

            if (needSelection) {
                if (!getChildren().contains(closeButton)) getChildren().add(closeButton);
            } else {
                getChildren().remove(closeButton);
            }

//            if (newValue instanceof Boolean val) {
//                setSelected(val);
//
//                if (val  && !getChildren().contains(closeButton)) getChildren().add(closeButton);
//                else getChildren().remove(closeButton);
//            } else  {
//                setSelected(newValue != null);
//
//                if (newValue != null && !getChildren().contains(closeButton)) getChildren().add(closeButton);
//                else getChildren().remove(closeButton);
//            }


        });

        selectedProperty().addListener((_, _, val) -> pseudoClassStateChanged(PseudoClass.getPseudoClass("selected"), val));

        closeButton.setOnMouseClicked(_ -> {
            node.setValue(null);
            setSelected(false);
            pseudoClassStateChanged(PseudoClass.getPseudoClass("selected"), false);
            getChildren().remove(closeButton);
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

    public InputFieldItem<T> getNode() {
        return node;
    }

    public abstract void bind();
}