

package io.github.gleidsonmt.todo.view;

import io.github.gleidsonmt.glad.base.Root;
import io.github.gleidsonmt.glad.theme.Css;
import io.github.gleidsonmt.glad.theme.ThemeProvider;
import io.github.gleidsonmt.todo.model.ListType;
import io.github.gleidsonmt.todo.model.User;
import io.github.gleidsonmt.todo.view.aside.Aside;
import io.github.gleidsonmt.todo.view.nav.DrawerItem;
import io.github.gleidsonmt.todo.view.nav.SideNav;
import io.github.gleidsonmt.todo.view.panel.ListRoot;
import io.github.gleidsonmt.todo.view.panel.Panel;
import io.github.gleidsonmt.todo.view.panel.events.TaskChangeEvent;
import io.github.gleidsonmt.todo.view_model.TaskViewModel;
import io.github.gleidsonmt.todo.view_model.UserViewModel;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.util.logging.Logger;

/**
 * Description: The class responsible for call the tasks to create the main
 * view.
 *
 * @author Gleidson Neves da Silveira | <a href="mailto:gleidisonmt@gmail.com">gleidisonmt@gmail.com</a> <br> <br>
 * Created On: Feb 25, 2026
 * <p>
 * Version History: Initial version
 */
public class MainView extends BorderPane {

    // the logged user
//    private User user;
    // the content layout
//    private final BorderPane body;
    // the navigation (drawwer or sidenav)

    private final SideNav sideNav;

    private final Panel panel;
    private final ListRoot listRoot;

    public MainView(UserViewModel user) {
        if (!user.isLogged()) {
            user.setLogged(true);
            user.update();
        }

        this.panel = new Panel();
        this.sideNav = new SideNav(user);
        this.listRoot = new ListRoot();
        init();


        this.addEventHandler(TaskChangeEvent.ADD, e -> {
            Logger.getGlobal().info(() -> "[TaskChangeEvent [FILTER], Type = " + e.getEventType() + " ] -> " + e.getModel());
            sideNav.get(e.getModel().getListID()).updateSize(1);
        });

        this.addEventHandler(TaskChangeEvent.DELETE_TASK, e -> {
            Logger.getGlobal().info(() -> "[TaskChangeEvent [FILTER], Type = " + e.getEventType() + " ] -> " + e.getModel());
            sideNav.get(e.getModel().getListID()).updateSize(-1);
        });

        this.addEventHandler(TaskChangeEvent.MOVED, e -> {
            Logger.getGlobal().info(() -> "[TaskChangeEvent [FILTER], Type = " + e.getEventType() + " ] -> " + e.getModel());
            if (e.getActual() != e.getPrevious()) {
                sideNav.get(e.getActual()).updateSize(1);
                sideNav.get(e.getPrevious()).updateSize(-1);
            }
        });

        this.addEventHandler(TaskChangeEvent.MY_DAY_CHANGED, e -> {
            Logger.getGlobal().info(() -> "[TaskChangeEvent [FILTER], Type = " + e.getEventType() + " ] -> " + e.getModel());
            if (e.getModel().isCompleted()) return;
            sideNav.get(ListType.DAILY).updateSize(e.getModel().isMyDay() ? 1 : -1);
        });

        this.addEventHandler(TaskChangeEvent.FAVORITE_CHANGED, e -> {
            Logger.getGlobal().info(() -> "[TaskChangeEvent [FILTER], Type = " + e.getEventType() + " ] -> " + e.getModel());
            if (e.getModel().isCompleted()) return;
            sideNav.get(ListType.IMPORTANT).updateSize(e.getModel().isImportant() ? 1 : -1);
        });

        this.addEventHandler(TaskChangeEvent.COMPLETE, e -> {
            Logger.getGlobal().info(() -> "[TaskChangeEvent [FILTER], Type = " + e.getEventType() + " ] -> " + e.getModel());
            sideNav.get(e.getModel().getListID()).updateSize(!e.getModel().isCompleted() ? 1 : -1);

            if (e.getModel().isMyDay()) {
                sideNav.get(ListType.DAILY).updateSize(!e.getModel().isCompleted() ? 1 : -1);
            }
            if (e.getModel().isImportant()) {
                sideNav.get(ListType.IMPORTANT).updateSize(!e.getModel().isCompleted() ? 1 : -1);
            }
        });
    }

    public void init() {

        setLeft(this.sideNav);
        setCenter(this.panel);
        setRight(new Aside(new TaskViewModel()));

        panel.setContent(listRoot);

        bind();

        this.sideNav.load();

        Platform.runLater(() -> {

            var root = (Root) getScene().getRoot();

            root.addBreakpoint((_) -> {
                setLeft(null);
            }, "<MD");

            root.addBreakpoint((_) -> {
                root.unblock();
                setLeft(sideNav);
            }, ">MD");
        });
    }

    public VBox getNav() {
        return this.sideNav;
    }

    private void bind() {
        // the tile of the panel with the side nav actual item selected.

        panel.actualListProperty().bind(sideNav.itemSelectedProperty().map(DrawerItem::getViewModel));
        panel.titleIconNameProperty().bind(Bindings.selectString(sideNav.itemSelectedProperty(), "viewModel", "icon"));
        listRoot.actualListProperty().bind(Bindings.select(sideNav.itemSelectedProperty(), "viewModel"));
    }
}