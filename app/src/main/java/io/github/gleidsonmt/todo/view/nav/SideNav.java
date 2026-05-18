

package io.github.gleidsonmt.todo.view.nav;

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

import java.util.Optional;

/**
 * @author Gleidson Neves da Silveira | <a href="mailto:gleidisonmt@gmail.com">gleidisonmt@gmail.com</a> <br>
 * Created On: Mar 20, 2026
 */
public class SideNav extends VBox {

    private ToggleGroup group;
    private ObjectProperty<DrawerItem> selected;
    private VBox smartListsContainer;
    private VBox container;

    public SideNav() {
        init();
        configLayout();
        bind();
        registerListener();
    }

    private void registerListener() {
        group.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {

        });

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
        selected.bind(group.selectedToggleProperty().map(e -> (DrawerItem) e));
        VBox.setVgrow(container, Priority.ALWAYS);
    }

    public void load() {
        ListPresenter presenter = (ListPresenter) Global.get(List.class);
        Task<ObservableList<List>> task = presenter.fetch();

        new Thread(task).start();

        task.setOnSucceeded(_ -> {
            task.getValue().forEach(this::createItem);

            this.getChildren().add(new Footer());
            selectFirst();
        });
    }

    public void select(ListViewModel list) {
        Optional<DrawerItem> optional = group.getToggles().stream().map(e -> (DrawerItem) e)
                .filter(el -> el.getViewModel().getId() == list.getId()).findFirst();

        optional.ifPresent(drawerItem -> group.selectToggle(drawerItem));
    }

    public void selectFirst() {
        group.selectToggle(group.getToggles().getFirst());
    }

    @Deprecated
    public DrawerItem get(TaskViewModel model) {
        Optional<DrawerItem> optional = group.getToggles().stream().map(e -> (DrawerItem) e)
                .filter(el -> el.getViewModel().getId() == model.getListId()).findFirst();

        return optional.orElse(null);
    }

    public ListViewModel get(long id) {
        Optional<ListViewModel> optional = group.getToggles().stream().map(e -> (DrawerItem) e)
                .map(DrawerItem::getViewModel).filter(viewModel -> viewModel.getId() == id).findFirst();

        return optional.orElse(null);
    }

    public ListViewModel get(ListType type) {
        Optional<ListViewModel> optional = group.getToggles().stream().map(e -> (DrawerItem) e)
                .map(DrawerItem::getViewModel).filter(viewModel -> viewModel.getType() == type).findFirst();

        return optional.orElse(null);
    }

    public java.util.List<ListViewModel> getModels() {
        return group.getToggles().stream().filter(el -> el instanceof DrawerItem)
                .map(e -> (DrawerItem) e).map(DrawerItem::getViewModel).toList();
    }

    public java.util.List<DrawerItem> getCustomLists() {
        return container.getChildren()
                .stream()
                .filter(el -> el instanceof DrawerItem)
                .map(el -> (DrawerItem) el).toList();
    }

    public DrawerItem getSelected() {
        return itemSelectedProperty().get();
    }

    @Deprecated
    public ListViewModel add(List model) {
        return createItem(model);
    }

    public ListViewModel add(ListViewModel model) {
        return createItem(model);
    }

    public void addAndSelect(ListViewModel model) {
        var drawerItem = createDrawerItem(model);
        group.getToggles().add(drawerItem);
        container.getChildren().add(drawerItem);
        drawerItem.setSelected(true);
        drawerItem.setEditable(!model.isFixed());
    }

    public void remove(ListViewModel model) {
        Optional<DrawerItem> optional = group.getToggles().stream().map(e -> (DrawerItem) e)
                .filter(el -> el.getViewModel().getId() == model.getId()).findFirst();

        optional.ifPresent(e -> container.getChildren().remove(e));
    }

    private ListViewModel createItem(List list) {
        ListViewModel viewModel = new ListViewModel(list);
        return createItem(viewModel);
    }

    private DrawerItem createDrawerItem(ListViewModel viewModel) {
        DrawerItem drawerItem = new DrawerItem(viewModel);
        drawerItem.numberOfNotificationsProperty().bind(viewModel.numberOfTasksProperty());
        return drawerItem;
    }

    private ListViewModel createItem(ListViewModel viewModel) {

        DrawerItem drawerItem = createDrawerItem(viewModel);

        group.getToggles().add(drawerItem);

        if (viewModel.isFixed()) {
            // // List completed = new List(I18n.get("drawer.list.completed"),
// ListType.COMPLETED, Icon.DONE_CIRCLE, true);
// // List all = new List(I18n.get("drawer.list.all"), ListType.ALL,
// Icon.DONE_ALL, true);
            smartListsContainer.getChildren().add(drawerItem);
//            drawerItem.graphicProperty().bind(viewModel.iconProperty());

        } else {
            container.getChildren().add(drawerItem);
//            drawerItem.graphicProperty().bind(viewModel.iconProperty());
        }

        // drawerItem.setEditable(!viewModel.isFixed());

        return viewModel;
    }

    public ObjectProperty<DrawerItem> itemSelectedProperty() {
        return selected;
    }
}