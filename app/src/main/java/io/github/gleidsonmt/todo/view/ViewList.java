

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
public class ViewList {

    private ObjectProperty<ListOld> list;
    private BooleanProperty editable = new SimpleBooleanProperty();

    public ObjectProperty<ListOld> listProperty() {
        return list;
    }

    public ListOld getList() {
        return list.get();
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
