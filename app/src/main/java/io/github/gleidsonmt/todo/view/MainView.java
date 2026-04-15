package io.github.gleidsonmt.todo.view;

import io.github.gleidsonmt.glad.base.Layout;
import io.github.gleidsonmt.glad.base.responsive.Container;
import io.github.gleidsonmt.todo.model.ListType;
import io.github.gleidsonmt.todo.model.User;
import io.github.gleidsonmt.todo.view.nav.SideNavNew;
import io.github.gleidsonmt.todo.view.panel.ListRootNew;
import io.github.gleidsonmt.todo.view.panel.Panel;
import io.github.gleidsonmt.todo.view.panel.events.TaskChangeEvent;
import io.github.gleidsonmt.todo.view_model.ListViewModel;
import javafx.beans.binding.Bindings;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;

import java.util.logging.Logger;

/**
 * Description: The class responsible for call the tasks to create the main
 * view.
 *
 * @author Gleidson Neves da Silveira | <gleidisonmt@gmail.com>
 * Created On: Feb 25, 2026
 * <p>
 * Version History: Initial version
 */
public class MainView extends Container implements Layout {

    // the logged user
    private User user;
    // the content layout
    private final BorderPane body;
    // the navigation (drawwer or sidenav)
    private final SideNavNew sideNav;

    private final Panel panel;
    private final ListRootNew listRoot;

    public MainView(User user) {
        this.user = user;
        this.panel = new Panel();
        this.sideNav = new SideNavNew();
        this.body = new BorderPane();
        this.listRoot = new ListRootNew();
        getChildren().add(body);
        init();

        this.addEventHandler(TaskChangeEvent.ADD, e -> {
            Logger.getGlobal().info(() -> "[TaskChangeEvent [FILTER], Type = " + e.getEventType() + " ] -> " + e.getModel());
            sideNav.get(e.getModel().getListId()).addNumberOfTasks(1).update();
        });

        this.addEventHandler(TaskChangeEvent.DELETE_TASK, e -> {
            Logger.getGlobal().info(() -> "[TaskChangeEvent [FILTER], Type = " + e.getEventType() + " ] -> " + e.getModel());
            sideNav.get(e.getModel().getListId()).addNumberOfTasks(-1).update();
        });

        this.addEventHandler(TaskChangeEvent.MOVED, e -> {
            Logger.getGlobal().info(() -> "[TaskChangeEvent [FILTER], Type = " + e.getEventType() + " ] -> " + e.getModel());
            if (e.getActual() != e.getPrevious()) {
                sideNav.get(e.getActual()).addNumberOfTasks(1).update();
                sideNav.get(e.getPrevious()).addNumberOfTasks(-1).update();
            }
        });

        this.addEventHandler(TaskChangeEvent.MY_DAY_CHANGED, e -> {
            Logger.getGlobal().info(() -> "[TaskChangeEvent [FILTER], Type = " + e.getEventType() + " ] -> " + e.getModel());
            if (e.getModel().isCompleted()) return;
            sideNav.get(ListType.DAILY).addNumberOfTasks(e.getModel().isMyDay() ? 1 : -1).update();
        });

        this.addEventHandler(TaskChangeEvent.FAVORITE_CHANGED, e -> {
            Logger.getGlobal().info(() -> "[TaskChangeEvent [FILTER], Type = " + e.getEventType() + " ] -> " + e.getModel());
            if (e.getModel().isCompleted()) return;
            sideNav.get(ListType.IMPORTANT).addNumberOfTasks(e.getModel().isImportant() ? 1 : -1).update();
        });

        this.addEventHandler(TaskChangeEvent.COMPLETE, e -> {
            Logger.getGlobal().info(() -> "[TaskChangeEvent [FILTER], Type = " + e.getEventType() + " ] -> " + e.getModel());

            sideNav.get(e.getModel().getListId()).addNumberOfTasks(!e.getModel().isCompleted() ? 1 : -1).update();

            if (e.getModel().isMyDay()) {
                sideNav.get(ListType.DAILY).addNumberOfTasks(!e.getModel().isCompleted() ? 1 : -1).update();
            }
            if (e.getModel().isImportant()) {
                sideNav.get(ListType.IMPORTANT).addNumberOfTasks(!e.getModel().isCompleted() ? 1 : -1).update();
            }

        });
    }

    public void init() {

        body.setLeft(this.sideNav);
        body.setCenter(this.panel);
        panel.setContent(listRoot);

        bind();

        this.sideNav.load();

        // Presenter<ToDoTask> pres = Global.get(ToDoTask.class);
        // Task<ObservableList<ToDoTask>> task = pres.fetch();

        // sideNav = new SideNav(pres.getData(), user);
        // body.setLeft(sideNav);

        // listRoot = new ListRoot(pres.getData());
        // body.setCenter(panel);
        // panel.setContent(listRoot);

        // bind();

        this.addBreakpoint((event) -> {
            body.setLeft(null);
        }, "<MD");

        this.addBreakpoint((event) -> {
            body.setLeft(sideNav);
        }, ">MD");
        // new Thread(task).start();
    }

    private void bind() {
        // the tile of the panel with the side nav actual item selected.
        panel.titleProperty().bind(Bindings.selectString(sideNav.itemSelectedProperty(), "viewModel", "name"));

        panel.titleIconProperty().bind(Bindings.select(sideNav.itemSelectedProperty(), "viewModel", "icon"));

        listRoot.actualListProperty().bind(Bindings.select(sideNav.itemSelectedProperty(), "viewModel"));

        // listRoot.needsUpdateProperty().bind(Bindings.select(sideNav.itemSelectedProperty(),
        // "update"));

        // Bindings.select(sideNav.itemSelectedProperty(), "viewModel",
        // "numberOfNotifications");
    }

    private void bindOld() {
        // only for tests this method does not change any behavior
        currentModule.addListener((_, oldValue, newValue) -> {
            if (newValue != null) {
                updateView(oldValue, newValue);
            }
        });

        // bind bi directional between the main view current module with the
        // side nav current module, the side nav has to be the same module
        // with this bind the current module is always the side nav item
        // seleceted.
        // currentModule.bind(sideNav.currentModuleProperty());
        // if current module has its list update so the panel needs to adpate it
        // panel.actualListProperty().bind(Bindings.select(sideNav.itemSelectedProperty(),
        // "list"));
        // the list root is update as side nav selected item changes
        listRoot.actualListProperty().bind(Bindings.select(sideNav.itemSelectedProperty(), "list"));
        // the tile of the panel with the side nav actual item selected.
        panel.titleProperty().bind(Bindings.select(sideNav.itemSelectedProperty(), "list", "name"));
        // the panee icon title with the list icon selected in the side nav.
        panel.titleIconProperty().bind(Bindings.select(sideNav.itemSelectedProperty(), "list", "icon"));
    }

    @Override
    public Node getLeft() {
        return this.sideNav;
    }

}