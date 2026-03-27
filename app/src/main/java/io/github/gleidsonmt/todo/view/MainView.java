package io.github.gleidsonmt.todo.view;

import io.github.gleidsonmt.glad.base.Layout;
import io.github.gleidsonmt.glad.base.responsive.Container;
import io.github.gleidsonmt.todo.model.User;
import io.github.gleidsonmt.todo.view.nav.SideNavNew;
import io.github.gleidsonmt.todo.view.panel.ListRootNew;
import io.github.gleidsonmt.todo.view.panel.Panel;
import javafx.beans.binding.Bindings;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;

/**
 * Description: The class responsible for call the tasks to create the main
 * view.
 *
 * @author Gleidson Neves da Silveira | <gleidisonmt@gmail.com>
 *         Created On: Feb 25, 2026
 * 
 *         Version History: Initial version
 */
public class MainView extends Container implements Layout {

    // the logged user
    private User user;
    // the content layout
    private BorderPane body;
    // the navigation (drawwer or sidenav)
    private SideNavNew sideNav;

    private Panel panel;
    private ListRootNew listRoot;

    public MainView(User user) {
        this.user = user;
        this.panel = new Panel();
        this.sideNav = new SideNavNew();
        this.body = new BorderPane();
        this.listRoot = new ListRootNew();
        getChildren().add(body);
        init();
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

        // this.addBreakpoint((event) -> {
        // body.setLeft(null);
        // }, "<MD");

        // this.addBreakpoint((event) -> {
        // body.setLeft(sideNav);
        // }, ">MD");
        // new Thread(task).start();
    }

    private void bind() {
        // the tile of the panel with the side nav actual item selected.
        panel.titleProperty().bind(Bindings.selectString(sideNav.itemSelectedProperty(), "viewModel", "name"));

        panel.titleIconProperty().bind(Bindings.select(sideNav.itemSelectedProperty(), "viewModel", "icon"));

        listRoot.actualListProperty().bind(Bindings.select(sideNav.itemSelectedProperty(), "viewModel"));

        // listRoot.needsUpdateProperty().bind(Bindings.select(sideNav.itemSelectedProperty(), "update"));

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