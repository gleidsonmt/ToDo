

package io.github.gleidsonmt.todo.view_model;

import io.github.gleidsonmt.todo.bd.dao.DaoList;
import io.github.gleidsonmt.todo.model.ListType;
import io.github.gleidsonmt.todo.view_model.map.ListMapper;
import javafx.beans.property.*;


/**
 * Description:
 *
 * @author Gleidson Neves da Silveira | <a href="mailto:gleidisonmt@gmail.com">gleidisonmt@gmail.com</a> <br>
 * Created On: Mar 20, 2026
 * <p>
 * Version History: Initial version
 */
public class ListViewModel extends ViewModel {

    // Model Actions
    private final ListMapper converter = ListMapper.INSTANCE;
    private final DaoList dao = new DaoList();

    // View State
    private final BooleanProperty fixed = new SimpleBooleanProperty(false);
    private final ObjectProperty<ListType> type = new SimpleObjectProperty<>(ListType.DEFAULT);
    private final IntegerProperty size = new SimpleIntegerProperty();
    private final StringProperty icon = new SimpleStringProperty("check-list");

    public ListViewModel() {
    }

    public ListViewModel(String name) {
        super(name);
    }

    public ListViewModel save() {
        var converted = converter.toDTO(this);
        this.setId(dao.store(converted));
        return this;
    }

    public void delete() {
        var item = converter.toDTO(this);
        dao.delete(item);
    }

    public void update() {
        var item = converter.toDTO(this);
        dao.update(item);
    }

    public ListType getType() {
        return this.type.get();
    }

    public void setType(ListType type) {
        this.type.set(type);
    }

    public StringProperty iconProperty() {
        return this.icon;
    }

    public String getIcon() {
        return this.icon.get();
    }

    public void setIcon(String icon) {
        this.icon.set(icon);
    }

    public int getSize() {
        return this.size.get();
    }

    public void updateSize(int val) {
        this.size.set(getSize() + val);
        update();
    }

    public boolean isFixed() {
        return this.fixed.get();
    }

    public void setFixed(boolean fixed) {
        this.fixed.set(fixed);
    }

    public IntegerProperty sizeProperty() {
        return this.size;
    }

    public void setSize(int size) {
        this.size.set(size);
    }

    @Override
    public String toString() {
        return "{\"ListViewModel\":"
               + super.toString()
               + ", \"fixed\":" + fixed
               + ", \"type\":" + type
               + ", \"size\":" + size
               + ", \"icon\":" + icon
               + ", \"id\":" + id
               + ", \"name\":" + name
               + "}";
    }
}
