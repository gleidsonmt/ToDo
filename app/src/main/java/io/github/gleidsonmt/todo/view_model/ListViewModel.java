package io.github.gleidsonmt.todo.view_model;

import io.github.gleidsonmt.glad.controls.icon.Icon;
import io.github.gleidsonmt.todo.model.List;
import io.github.gleidsonmt.todo.model.ListType;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 * Description:
 *
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 *         Created On: Mar 20, 2026
 * 
 *         Version History: Initial version
 */
public class ListViewModel extends ViewModel {

    private ObjectProperty<Icon> icon = new SimpleObjectProperty();
    private final boolean fixed;
    private List list;

    public ListViewModel(List list) {
        this.list = list;
        this.setId(list.getId());
        this.fixed = list.isFixed();
        this.nameProperty().set(list.getName());
        this.icon.set(list.getIcon());
    }

    public ObjectProperty<Icon> iconProperty() {
        return this.icon;
    }

    public boolean isFixed() {
        return this.fixed;
    }

    public ListType getType() {
        return this.list.getType();
    }

}
