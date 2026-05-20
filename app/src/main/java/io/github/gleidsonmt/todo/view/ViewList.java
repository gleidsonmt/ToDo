

package io.github.gleidsonmt.todo.view;

import io.github.gleidsonmt.glad.base.View;
import io.github.gleidsonmt.todo.model.List;
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

    private ObjectProperty<List> list;
    private BooleanProperty editable = new SimpleBooleanProperty();

    public ViewList(List list) {
        this(list.getName(), list);
    }

    public ViewList(String name, List list) {
        this(name, list, false);
    }

    public ViewList(List list, boolean editable) {
        this(list.getName(), list, editable);
    }

    public ViewList(String name, List list, boolean editable) {
        super(name);
        this.list = new SimpleObjectProperty<>(list);
        this.editable.set(editable);
    }

    public ObjectProperty<List> listProperty() {
        return list;
    }

    public List getList() {
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
