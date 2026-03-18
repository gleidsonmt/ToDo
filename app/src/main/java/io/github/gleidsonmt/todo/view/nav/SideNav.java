package io.github.gleidsonmt.todo.view.nav;

import io.github.gleidsonmt.glad.base.Module;
import io.github.gleidsonmt.glad.base.ModuleView;
import io.github.gleidsonmt.glad.base.drawer.Drawer;
import io.github.gleidsonmt.glad.base.drawer.ModuleSeparator;
import io.github.gleidsonmt.glad.controls.icon.Icon;
import io.github.gleidsonmt.glad.drawer.DrawerItem;
import io.github.gleidsonmt.glad.drawer.DrawerMenu;
import io.github.gleidsonmt.todo.bd.dao.DaoList;
import io.github.gleidsonmt.todo.bd.dao.DaoPreferences;
import io.github.gleidsonmt.todo.global.Global;
import io.github.gleidsonmt.todo.global.Presenter;
import io.github.gleidsonmt.todo.model.List;
import io.github.gleidsonmt.todo.model.ListType;
import io.github.gleidsonmt.todo.model.Preferences;
import io.github.gleidsonmt.todo.model.ToDoTask;
import io.github.gleidsonmt.todo.model.User;
import io.github.gleidsonmt.todo.utils.I18n;
import io.github.gleidsonmt.todo.view.ViewList;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.scene.control.Separator;
import javafx.scene.layout.VBox;

/**
 * Description:
 *
 * @author Gleidson Neves da Silveira | <gleidisonmt@gmail.com>
 *         Created On: Feb 25, 2026
 * 
 *         Version History: Initial version
 */
public class SideNav extends Drawer {

    private final Header header;

    private final DaoList daoList;
    private final ObservableList<ToDoTask> data;

    public SideNav(ObservableList<ToDoTask> tasks, User user) {
        this.data = tasks;
        this.header = new Header(user);
        this.daoList = new DaoList();

        this.setCellFactory(param -> {
            switch (param) {
            case ViewList view -> {
                var drawerItem = new CustomDrawerItem(view);
                switch (view.getList().getType()) {
                case ALL -> {
                    drawerItem.numberOfNotificationsProperty()
                            .bind(Bindings.size(data.filtered(task -> !task.isCompleted())));

                }
                case COMPLETED -> {
                    drawerItem.numberOfNotificationsProperty()
                            .bind(Bindings.size(data.filtered(ToDoTask::isCompleted)));
                }
                default -> {
                    drawerItem.numberOfNotificationsProperty()
                            .bind(Bindings.size(view.getList().getItems().filtered(e -> !e.isCompleted())));
                }

                }
                return drawerItem;
            }
            case ModuleSeparator separator -> {
                VBox.setMargin(separator.getGraphic(), new Insets(10));
                return separator.getGraphic();
            }
            case ModuleView menu -> {
                return new DrawerMenu(menu);
            }
            case null, default -> {
                assert param != null;
                return new DrawerItem(param);
            }
            }
        });
        createFixedLists(tasks);
        createCustomLists();
    }

    private ObservableList<List> customLists;

    private void createFixedLists(ObservableList<ToDoTask> data) {
        // Default lists
        List daily = new List(I18n.get("drawer.list.myDay"), ListType.DAILY, Icon.SUN, true);
        List tasks = new List(I18n.get("drawer.list.task"), ListType.TASKS, Icon.HOME, true);
        List important = new List(I18n.get("drawer.list.important"), ListType.IMPORTANT, Icon.STAR, true);
        List completed = new List(I18n.get("drawer.list.completed"), ListType.COMPLETED, Icon.DONE_CIRCLE, true);
        List all = new List(I18n.get("drawer.list.all"), ListType.ALL, Icon.DONE_ALL, true);

        daily.setItems(data.filtered(ToDoTask::isMyDay));

        tasks.setItems(data.filtered(el -> el.getListId() == 0));

        important.setItems(data.filtered(task -> task.isImportant() && !task.isCompleted()));

        List tasksCompleted = new List(I18n.get("drawer.list.task"), ListType.TASKS, Icon.HOME);
        List tasksIncompleted = new List(I18n.get("drawer.list.task"), ListType.TASKS, Icon.HOME);
        tasksIncompleted.setItems(data.filtered(task -> task.getListId() == 0 && !task.isCompleted()));
        tasksCompleted.setItems(data.filtered(task -> task.isCompleted() && task.getListId() == 0));

        all.getLists().add(tasksIncompleted);
        completed.getLists().add(tasksCompleted);

        // adding fixed views
        getItems().addAll(new ViewList(daily), new ViewList(tasks), new ViewList(important), new ViewList(completed),
                new ViewList(all));

        // adding a separator
        getItems().add(new ModuleSeparator(new Separator(), "Separator"));

        // this.setHeader(header);

    }

    public void selectList(List list) {
        Presenter<List> presenter = Global.get(List.class);
        presenter.getData().add(list);
        Platform.runLater(() -> {
            selectLast();
            var item = (CustomDrawerItem) getSelected();
            item.setEditable(true);
        });
    }

    private void createCustomLists() {

        List all = getItems().stream().filter(list -> (list instanceof ViewList)).map(list -> (ViewList) list)
                .filter(viewList -> viewList.getList().getType().equals(ListType.ALL)).findFirst().get().getList();

        List completed = getItems().stream().filter(list -> (list instanceof ViewList)).map(list -> (ViewList) list)
                .filter(viewList -> viewList.getList().getType().equals(ListType.COMPLETED)).findFirst().get()
                .getList();

        Presenter<List> presenter = Global.get(List.class);
        new Thread(presenter.fetch()).start();

        presenter.getData().addListener((ListChangeListener<List>) c -> {
            if (c.next()) {
                if (c.wasAdded()) {

                    c.getAddedSubList().forEach(list -> {

                        Platform.runLater(() -> {

                            list.setItems(data.filtered(task -> task.getListId() == list.getId()));

                            List listCompleted = new List(list.getName());

                            listCompleted.setItems(
                                    data.filtered(task -> task.getListId() == list.getId() && task.isCompleted()));

                            List listIncompleted = new List(list.getName());
                            listIncompleted.setItems(
                                    data.filtered(task -> task.getListId() == list.getId() && !task.isCompleted()));

                            // pass to all and completed lists
                            all.getLists().add(listIncompleted);
                            completed.getLists().add(listCompleted);

                            var viewList = new ViewList(list, true);

                            getItems().add(viewList);
                        });

                        // getScene().addPostLayoutPulseListener(() ->
                        // container.setVvalue(1));

                    });
                }
            }
        });
        this.setFooter(new Footer());

        Platform.runLater(() -> {
            currentModuleProperty().set(getItems().get(1));
            // select(getItems().getFirst());
            select(getItems().get(1));
            // System.out.println("first +" + getItems());
        });

    }

    @Deprecated
    private void createItems(ObservableList<ToDoTask> data, User user) {

        customLists = FXCollections.observableArrayList();
        Task<ObservableList<List>> taskOf = daoList.fetch(customLists);

        Thread one = new Thread(taskOf);
        one.start();

        taskOf.setOnSucceeded(e -> {
            DaoPreferences daoPreferences = new DaoPreferences();
            Preferences preferences = daoPreferences.getByModel(user);

            // fixed lists
            List daily = new List(I18n.get("drawer.list.myDay"), ListType.DAILY, Icon.SUN, true);
            List tasks = new List(I18n.get("drawer.list.task"), ListType.TASKS, Icon.HOME, true);
            List important = new List(I18n.get("drawer.list.important"), ListType.IMPORTANT, Icon.STAR, true);
            List completed = new List(I18n.get("drawer.list.completed"), ListType.COMPLETED, Icon.DONE_CIRCLE, true);
            List all = new List(I18n.get("drawer.list.all"), ListType.ALL, Icon.DONE_ALL, true);

            // set items for fixed lists
            // daily.setItems(data.filtered(el -> el.isMyDay() &&
            // el.getDueDate() == null
            // || (el.getDueDate() != null &&
            // el.getDueDate().equals(LocalDate.now()))));

            daily.setItems(data.filtered(ToDoTask::isMyDay));

            tasks.setItems(data.filtered(el -> el.getListId() == 0));

            important.setItems(data.filtered(task -> task.isImportant() && !task.isCompleted()));

            // adding fixed views
            getItems().addAll(new ViewList(daily), new ViewList(tasks), new ViewList(important),
                    new ViewList(completed), new ViewList(all));

            // adding a separator
            getItems().add(new ModuleSeparator(new Separator(), "Separator"));

            customLists.forEach(list -> {
                List listCompleted = new List(list.getName());
                listCompleted.setItems(data.filtered(task -> task.getListId() == list.getId() && task.isCompleted()));

                List listIncompleted = new List(list.getName());
                listIncompleted
                        .setItems(data.filtered(task -> task.getListId() == list.getId() && !task.isCompleted()));

                list.setItems(data.filtered(task -> task.getListId() == list.getId()));
                // pass to all and completed lists
                all.getLists().add(listIncompleted);
                completed.getLists().add(listCompleted);
                // crate and add custom list to the items
                getItems().addAll(new ViewList(list));
            });

            // adding custom lists
            // customLists.forEach(list -> {
            // // find in data and set items that match the list in the loop
            // list.setItems(data.filtered(el -> el.getListId() ==
            // list.getId()));

            // // pass to all and completed lists
            // all.getLists().add(list);
            // completed.getLists().add(list);
            // // crate and add custom list to the items
            // getItems().addAll(new ViewList(list));
            // });

            List tasksCompleted = new List(I18n.get("drawer.list.task"), ListType.TASKS, Icon.HOME);
            List tasksIncompleted = new List(I18n.get("drawer.list.task"), ListType.TASKS, Icon.HOME);
            tasksIncompleted.setItems(data.filtered(task -> task.getListId() == 0 && !task.isCompleted()));
            tasksCompleted.setItems(data.filtered(task -> task.isCompleted() && task.getListId() == 0));

            all.getLists().add(tasksIncompleted);
            completed.getLists().add(tasksCompleted);

            // testing
            customLists.addListener((ListChangeListener<List>) c -> {
                if (c.next()) {
                    if (c.wasAdded()) {
                        c.getAddedSubList().forEach(list -> {
                            all.getLists().add(list);
                            completed.getLists().add(list);

                            var viewList = new ViewList(list, true);

                            getItems().add(viewList);

                            // drawerItem.setEditMode(true);
                            // new DaoList().store(list);
                            // getScene().addPostLayoutPulseListener(() ->
                            // scroll.setVvalue(1));

                        });
                    }
                }
            });
            //

            // Platform.runLater(() -> {
            currentModuleProperty().set(getItems().get(1));
            // select(getItems().getFirst());
            select(getItems().get(1));
            // System.out.println("first +" + getItems());
            // });
            this.setFooter(new Footer());
        });
    }

    public ObservableList<ToDoTask> getData() {
        return data;
    }

    public ReadOnlyObjectProperty<Module> itemSelectedProperty() {
        return currentModuleProperty();
    }

    public ObservableList<List> getCustomLists() {
        return customLists;
    }
}