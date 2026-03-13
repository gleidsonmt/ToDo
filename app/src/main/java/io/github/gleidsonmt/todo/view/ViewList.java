package io.github.gleidsonmt.todo.view;

import io.github.gleidsonmt.glad.base.View;
import io.github.gleidsonmt.todo.model.List;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 * Description:
 *
 * @author Gleidson Neves da Silveira | <gleidisonmt@gmail.com>
 *         Created On: Feb 26, 2026
 * 
 *         Version History: Initial version
 */
public class ViewList extends View {

    private ObjectProperty<List> list;

    public ViewList(List list) {
        this(list.getName(), list);
    }

    public ViewList(String name, List list) {
        super(name);
        this.list = new SimpleObjectProperty<>(list);
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

}
