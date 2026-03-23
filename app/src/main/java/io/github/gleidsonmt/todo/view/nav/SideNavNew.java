package io.github.gleidsonmt.todo.view.nav;

import java.util.Optional;

import io.github.gleidsonmt.todo.global.Global;
import io.github.gleidsonmt.todo.global.ListPresenter;
import io.github.gleidsonmt.todo.model.List;
import io.github.gleidsonmt.todo.view_model.ListViewModel;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;

/**
 * Description:
 *
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 *         Created On: Mar 20, 2026
 * 
 *         Version History: Initial version
 */
public class SideNavNew extends VBox {

    private ToggleGroup group;
    private ObjectProperty<CustomDrawerItemNew> selected;
    private VBox container;

    private ListPresenter presenter;

    public SideNavNew() {
        init();
        configLayout();
        bind();
    }

    private void init() {
        this.group = new ToggleGroup();
        this.container = new VBox();
        this.selected = new SimpleObjectProperty<>();
    }

    private void configLayout() {
        this.setId("drawer");
        this.getChildren().setAll(container);
    }

    private void bind() {
        selected.bind(group.selectedToggleProperty().map(e -> (CustomDrawerItemNew) e));
    }

    public void load() {
        presenter = (ListPresenter) Global.get(List.class);
        Task<ObservableList<List>> task = presenter.fetch();

        new Thread(task).start();

        task.setOnSucceeded(_ -> {
            task.getValue().forEach(list -> {
                createItem(list, false);
            });
            this.getChildren().add(new FooterNew());
            selectFirst();

        });
    }

    public void select(ListViewModel list) {
        Optional<CustomDrawerItemNew> optional = group.getToggles().stream().map(e -> (CustomDrawerItemNew) e)
                .filter(el -> el.getViewModel() == list).findFirst();
        if (optional.isPresent()) {
            group.selectToggle(optional.get());
        }
    }

    public void selectFirst() {
        group.selectToggle(group.getToggles().get(0));
    }

    public ListViewModel add(List model) {
        return createItem(model, true);
    }

    private ListViewModel createItem(List list, boolean editable) {
        ListViewModel viewModel = new ListViewModel(list);

        CustomDrawerItemNew drawerItem = new CustomDrawerItemNew(viewModel, presenter.size(list.getId()));
        group.getToggles().add(drawerItem);
        container.getChildren().add(drawerItem);
        drawerItem.setEditable(editable);
        return viewModel;
    }

    public ObjectProperty<CustomDrawerItemNew> itemSelectedProperty() {
        return selected;
    }
}