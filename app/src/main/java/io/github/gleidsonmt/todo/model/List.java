package io.github.gleidsonmt.todo.model;

import io.github.gleidsonmt.glad.controls.icon.Icon;
import io.github.gleidsonmt.todo.bd.dao.internal.Ignore;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * 
 * Description: The base model class for lists.
 * 
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 *         Create on: 2026-02-03
 */
public class List extends Entity {

    @Ignore
    private final SimpleListProperty<ToDoTask> items;
    @Ignore
    private ObjectProperty<Icon> icon;
    @Ignore
    private final ListType type;
    @Ignore
    private final SimpleListProperty<List> lists;

    private final BooleanProperty fixed;

    public List() {
        this(null);
    }

    public List(String name) {
        this(name, ListType.DEFAULT, false);
    }

    public List(String name, ListType type) {
        this(name, type, false);
    }

    public List(String name, ListType type, Icon icon) {
        this(name, type, icon, false);
    }

    public List(String name, ListType type, boolean fixed) {
        this(name, type, Icon.CHECK_LIST, fixed);
    }

    public List(String name, ListType type, Icon icon, boolean fixed) {
        this(0, name, type, icon, fixed);
    }

    public List(int id, String name, ListType type, Icon icon, boolean fixed) {
        super(id, name);
        this.type = type;
        this.icon = new SimpleObjectProperty<>(icon);
        this.fixed = new SimpleBooleanProperty(fixed);
        items = new SimpleListProperty<>(FXCollections.observableArrayList());
        lists = new SimpleListProperty<>(FXCollections.observableArrayList());
    }

    public Icon getIcon() {
        return this.icon.get();
    }

    public ObjectProperty<Icon> iconProperty() {
        return this.icon;
    }

    public void setIcon(Icon icon) {
        this.icon.set(icon);
    }

    public boolean isFixed() {
        return fixed.get();
    }

    public BooleanProperty fixedProperty() {
        return fixed;
    }

    public void setFixed(boolean fixed) {
        this.fixed.set(fixed);
    }

    public ObservableList<ToDoTask> getItems() {
        return items.get();
    }

    public SimpleListProperty<ToDoTask> itemsProperty() {
        return items;
    }

    public void setItems(ObservableList<ToDoTask> items) {
        this.items.setValue(items);
    }

    public ObservableList<List> getLists() {
        return lists.get();
    }

    public SimpleListProperty<List> listsProperty() {
        return lists;
    }

    @Override
    public String toString() {
        return getName() + "{" + items + "}";
    }

    public ListType getType() {
        return type;
    }
}