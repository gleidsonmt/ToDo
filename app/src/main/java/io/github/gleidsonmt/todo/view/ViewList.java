

package io.github.gleidsonmt.todo.view;

import io.github.gleidsonmt.glad.base.drawer.View;
import io.github.gleidsonmt.todo.model.ListOld;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 * Description:
 *
 * @author Gleidson Neves da Silveira | <a href="mailto:gleidisonmt@gmail.com">gleidisonmt@gmail.com</a> <br> <br>
 * Created On: Feb 26, 2026
 * <p>
 * Version History: Initial version
 */
public class ViewList extends View {

    private ObjectProperty<ListOld> list;
    private BooleanProperty editable = new SimpleBooleanProperty();

    public ViewList(ListOld list) {
        this(list.getName(), list);
    }

    public ViewList(String name, ListOld list) {
        this(name, list, false);
    }

    public ViewList(ListOld list, boolean editable) {
        this(list.getName(), list, editable);
    }

    public ViewList(String name, ListOld list, boolean editable) {
        super(name);
        this.list = new SimpleObjectProperty<>(list);
        this.editable.set(editable);
    }

    public ObjectProperty<ListOld> listProperty() {
        return list;
    }

    public ListOld getList() {
        return list.get();
    }

    @Override
    public String toString() {
        return "View{" + "\n\tname=" + getName() + ", list=" + getList() + "\n}";
    }

    public void setEditable(boolean val) {
        this.editable.set(val);
    }

    public boolean isEditable() {
        return this.editable.get();
    }

    public BooleanProperty editableProperty() {
        return this.editable;
    }

}
