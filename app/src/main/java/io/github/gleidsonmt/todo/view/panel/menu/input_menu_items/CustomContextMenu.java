package io.github.gleidsonmt.todo.view.panel.menu.input_menu_items;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  09/04/2026
 */
public abstract class CustomContextMenu<T> extends ContextMenu {

    protected BooleanProperty selected;
    protected ObjectProperty<T> value;

    public CustomContextMenu() {
        this.selected = new SimpleBooleanProperty(false);
        this.value = new SimpleObjectProperty<>();
    }

    public void show(Node target, Side side) {
        if (isShowing()) return;

        // Calling when first to avoid the erro on placing the value..
        show(target, side, 0, 0);
        hide();

        // second show places in the right position
        show(target, side, 0, 0);

    }

    protected abstract void update(T value);

    public boolean isSelected() {
        return selected.get();
    }

    public BooleanProperty selectedProperty() {
        return selected;
    }

    protected void setSelected(boolean selected) {
        this.selected.set(selected);
    }

    public T getValue() {
        return value.getValue();
    }

    public ObjectProperty<T> valueProperty() {
        return this.value;
    }

    public void setValue(T value) {
        this.value.setValue(value);
    }

}
