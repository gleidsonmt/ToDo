package io.github.gleidsonmt.todo.view_model;

import io.github.gleidsonmt.glad.controls.icon.Icon;
import io.github.gleidsonmt.glad.controls.icon.SVGIcon;
import io.github.gleidsonmt.todo.global.Global;
import io.github.gleidsonmt.todo.global.ListPresenter;
import io.github.gleidsonmt.todo.model.List;
import io.github.gleidsonmt.todo.model.ListType;
import io.github.gleidsonmt.todo.utils.Assets;
import io.github.gleidsonmt.todo.utils.I18n;
import io.github.gleidsonmt.todo.utils.StringUtils;
import io.github.gleidsonmt.todo.view_model.converter.ListViewModelConverter;
import javafx.beans.property.*;
import javafx.scene.Node;
import javafx.scene.image.ImageView;

/**
 * Description:
 *
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Created On: Mar 20, 2026
 * <p>
 * Version History: Initial version
 */
public class ListViewModel extends ViewModel {

    private boolean fixed;
    private ListType type;

    private ListViewModelConverter converter;
    private ListPresenter presenter;

    private IntegerProperty numberOfTasks = new SimpleIntegerProperty(0);
    private StringProperty iconName;


    private String keyName;

//    public ListViewModel(String name) {
//        this(new List(name));
//    }

//    public ListViewModel(List list) {
//        this(list, Icon.CHECK_LIST, list.isFixed());
//    }

    public ListViewModel(List list) {
        this.converter = new ListViewModelConverter();
        this.fixed = list.isFixed();
        this.setId(list.getId());

        this.type = ListType.convert(list.getName());
        this.numberOfTasks.set(list.getSize());

        if (fixed) {
//            this.keyName = StringUtils.kebabToCamel(list.getName());
            this.keyName = list.getName();
            this.setName(I18n.get("drawer.list." + this.keyName));
        } else {
            this.nameProperty().set(list.getName());
        }

        this.iconName = new SimpleStringProperty(list.getIconName());

        presenter = (ListPresenter) Global.get(List.class);
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
        var item = converter.toModel(this);
        presenter.update(item);
    }

    @Override
    public String getName() {
        return !isFixed() ? this.nameProperty().get() : I18n.get("drawer.list." + this.keyName);
    }

    public String getKeyName() {
        return keyName;
    }

    public ListType getType() {
        return this.type;
    }

    public StringProperty iconNameProperty() {
        return this.iconName;
    }

    public String getIconName() {
        return this.iconName.get();
    }

    public void setIconName(String iconName) {
        this.iconName.set(iconName);
    }

    public int getNumberOfTasks() {
        return this.numberOfTasks.get();
    }

    public ListViewModel addNumberOfTasks(int val) {
        this.numberOfTasks.set(getNumberOfTasks() + val);
        return this;
    }

    public boolean isFixed() {
        return this.fixed;
    }

    public IntegerProperty numberOfTasksProperty() {
        return this.numberOfTasks;
    }

    @Override
    public String toString() {
        return "ListViewModel [id=" + this.getId() +
               ", name=" + nameProperty().get() +
               ", fixed=" + isFixed() +
               ", iconName=" + iconName + "]";
    }
}
