package io.github.gleidsonmt.todo.view.nav;

import java.util.Optional;

import io.github.gleidsonmt.todo.global.Global;
import io.github.gleidsonmt.todo.global.ListPresenter;
import io.github.gleidsonmt.todo.model.List;
import io.github.gleidsonmt.todo.model.ListType;
import io.github.gleidsonmt.todo.view_model.ListViewModel;
import io.github.gleidsonmt.todo.view_model.TaskViewModel;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.scene.control.Separator;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Priority;
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
    private VBox smartListsContainer;
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
        this.smartListsContainer = new VBox();
        this.selected = new SimpleObjectProperty<>();
    }

    private void configLayout() {
        this.setId("drawer");
        this.getChildren().setAll(smartListsContainer, new Separator(), container);
    }

    private void bind() {
        selected.bind(group.selectedToggleProperty().map(e -> (CustomDrawerItemNew) e));
        VBox.setVgrow(container, Priority.ALWAYS);
    }

    public void load() {
        presenter = (ListPresenter) Global.get(List.class);
        Task<ObservableList<List>> task = presenter.fetch();

        new Thread(task).start();

        task.setOnSucceeded(_ -> {
            task.getValue().forEach(list -> {
                createItem(list);
            });

            this.getChildren().add(new Footer());
            selectFirst();
        });
    }

    public void select(ListViewModel list) {
        Optional<CustomDrawerItemNew> optional = group.getToggles().stream().map(e -> (CustomDrawerItemNew) e)
                .filter(el -> el.getViewModel().getId() == list.getId()).findFirst();

        if (optional.isPresent()) {
            group.selectToggle(optional.get());
        }
    }

    public void selectFirst() {
        group.selectToggle(group.getToggles().get(0));
    }

    @Deprecated
    public CustomDrawerItemNew get(TaskViewModel model) {
        Optional<CustomDrawerItemNew> optional = group.getToggles().stream().map(e -> (CustomDrawerItemNew) e)
                .filter(el -> el.getViewModel().getId() == model.getListId()).findFirst();

        return optional.get();
    }

    public ListViewModel get(long id) {
        Optional<ListViewModel> optional = group.getToggles().stream().map(e -> (CustomDrawerItemNew) e)
                .map(CustomDrawerItemNew::getViewModel).filter(viewModel -> viewModel.getId() == id).findFirst();

        return optional.orElse(null);
    }

    public ListViewModel get(ListType type) {
        Optional<ListViewModel> optional = group.getToggles().stream().map(e -> (CustomDrawerItemNew) e)
                .filter(el -> el.getViewModel().getType() == type).map(el -> el.getViewModel()).findFirst();

        return optional.orElse(null);
    }

    public java.util.List<ListViewModel> getModels() {
        return group.getToggles().stream().filter(el -> el instanceof CustomDrawerItemNew)
                .map(e -> (CustomDrawerItemNew) e).map(el -> el.getViewModel()).toList();
    }

    public CustomDrawerItemNew getSelected() {
        return itemSelectedProperty().get();
    }

    @Deprecated
    public ListViewModel add(List model) {
        return createItem(model);
    }

    public ListViewModel add(ListViewModel model) {
        return createItem(model);
    }

    public void remove(ListViewModel model) {
        Optional<CustomDrawerItemNew> optional = group.getToggles().stream().map(e -> (CustomDrawerItemNew) e)
                .filter(el -> el.getViewModel().getId() == model.getId()).findFirst();

        container.getChildren().remove(optional.get());
    }

    private ListViewModel createItem(List list) {

        ListViewModel viewModel = new ListViewModel(list);
        return createItem(viewModel);

    }

    private ListViewModel createItem(ListViewModel viewModel) {
        CustomDrawerItemNew drawerItem = new CustomDrawerItemNew(viewModel);
        drawerItem.numberOfNotificationsProperty().bind(viewModel.numberOfTasksProperty());

        group.getToggles().add(drawerItem);

        if (viewModel.isFixed()) {
            smartListsContainer.getChildren().add(drawerItem);
        } else {
            container.getChildren().add(drawerItem);
        }

        // drawerItem.setEditable(!viewModel.isFixed());

        return viewModel;
    }

    public ObjectProperty<CustomDrawerItemNew> itemSelectedProperty() {
        return selected;
    }
}