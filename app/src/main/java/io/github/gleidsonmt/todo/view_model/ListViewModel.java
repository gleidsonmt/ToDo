package io.github.gleidsonmt.todo.view_model;

import java.util.Optional;

import io.github.gleidsonmt.glad.controls.icon.Icon;
import io.github.gleidsonmt.todo.bd.dao.DaoSize;
import io.github.gleidsonmt.todo.global.Global;
import io.github.gleidsonmt.todo.global.ListPresenter;
import io.github.gleidsonmt.todo.global.SizePresenter;
import io.github.gleidsonmt.todo.model.List;
import io.github.gleidsonmt.todo.model.ListType;
import io.github.gleidsonmt.todo.model.Size;
import io.github.gleidsonmt.todo.utils.I18n;
import io.github.gleidsonmt.todo.utils.StringUtils;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
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

    private ObjectProperty<Icon> icon;

    private boolean fixed;
    private ListType type;

    private ListViewModelConverter converter;
    private ListPresenter presenter;

    private IntegerProperty numberOfTasks = new SimpleIntegerProperty(0);
    private SizePresenter sizePresenter;

    private Size size = null;

    private String keyName;

    public ListViewModel(String name) {
        this(new List(name));
    }

    public ListViewModel(List list) {
        this(list, Icon.CHECK_LIST, list.isFixed());
    }

    public ListViewModel(List list, Icon icon, boolean fixed) {
        this.converter = new ListViewModelConverter();
        this.fixed = fixed;
        this.setId(list.getId());

        this.type = ListType.convert(list.getName());
        this.setNumberOfTasks(list.getSize());

        if (fixed) {
            this.keyName = StringUtils.kebabToCamel(list.getName());
            this.setName(I18n.get("drawer.list." + this.keyName));
        } else {
            this.nameProperty().set(list.getName());
        }

        this.icon = new SimpleObjectProperty<>(icon);

        presenter = (ListPresenter) Global.get(List.class);
        // sizePresenter = (SizePresenter) Global.get(Size.class);

        init();
    }

    private void init() {

        // size = sizePresenter.getListSize(getId());

        // if (size != null) {
        // setNumberOfTasks(size.getVal());
        // }

        // this.numberOfTasks.addListener((_, _, val) -> {
        // size = sizePresenter.getListSize(getId());
        // if (size != null) {
        // size.setVal(val.intValue());
        // sizePresenter.update(size);
        // }
        // });
    }

    public ListViewModel save() {
        var converted = converter.convert(this);
        this.setId(presenter.store(converted));
        return this;
    }

    public void delete() {
        var item = converter.convert(this);
        presenter.delete(item);
    }

    public void update() {
        // commit in db
        var item = converter.convert(this);
        presenter.update(item);
    }

    @Override
    public String getName() {
        return !isFixed() ? this.nameProperty().get() : StringUtils.camelToKebab(keyName);
    }

    public ListType getType() {
        return this.type;
    }

    public ObjectProperty<Icon> iconProperty() {
        return this.icon;
    }

    public void setNumberOfTasks(int val) {
        this.numberOfTasks.set(val);
    }

    public int getNumberOfTasks() {
        return this.numberOfTasks.get();
    }

    public void addNumberOfTasks(int val) {
        this.numberOfTasks.set(getNumberOfTasks() + val);
    }

    public boolean isFixed() {
        return this.fixed;
    }

    @Deprecated
    public void updateCount() {
        DaoSize sizes = new DaoSize();
        Optional<Size> optionalSize = sizes.getBy("list_id = " + this.getId());

        if (optionalSize.isPresent()) {
            // this.numberOfTasks.set(optionalSize.get().getSize());
        } else {
            this.numberOfTasks.set(0);
        }
    }

    @Deprecated
    public void addCount() {
        this.numberOfTasks.set(this.numberOfTasks.get() + 1);
    }

    @Deprecated
    public void subtractCount() {
        this.numberOfTasks.set(this.numberOfTasks.get() - 1);
    }

    public IntegerProperty numberOfTasksProperty() {
        return this.numberOfTasks;
    }

    @Override
    public String toString() {
        return "ListViewModel [id=" + this.getId() + ", name=" + nameProperty().get() + ", icon=" + icon + "]";
    }
}
