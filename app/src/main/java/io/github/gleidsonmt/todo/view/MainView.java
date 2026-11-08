

package io.github.gleidsonmt.todo.view;

import io.github.gleidsonmt.glad.base.Root;
import io.github.gleidsonmt.glad.base.dialog.WrapperEffect;
import io.github.gleidsonmt.glad.controls.button.Button;
import io.github.gleidsonmt.todo.model.ListType;
import io.github.gleidsonmt.todo.view.nav.DrawerItem;
import io.github.gleidsonmt.todo.view.nav.SideNav;
import io.github.gleidsonmt.todo.view.panel.ListRoot;
import io.github.gleidsonmt.todo.view.panel.Panel;
import io.github.gleidsonmt.todo.view.panel.events.TaskChangeEvent;
import io.github.gleidsonmt.todo.view_model.TaskViewModel;
import io.github.gleidsonmt.todo.view_model.UserViewModel;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import org.jspecify.annotations.NonNull;

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
        setId("main-view");
        if (!user.isLogged()) {
            user.setLogged(true);
            user.update();
        }

        this.panel = new Panel();
        this.sideNav = new SideNav(user);
        this.listRoot = new ListRoot(this);
        init();
        addEventFilters();
    }

    public void init() {

        setLeft(this.sideNav);
        setCenter(this.panel);

        panel.setContent(listRoot);

        bind();

        this.sideNav.load();

        Platform.runLater(() -> {

            var root = (Root) getScene().getRoot();

            root.addBreakpoint((_) -> {
                setLeft(null);
            }, "<MD");

            root.addBreakpoint((_) -> {
                if (!root.isBlocked()) root.unblock();
                setLeft(sideNav);
            }, ">MD");
        });
    }

    private void addEventFilters() {

        this.addEventHandler(TaskChangeEvent.ADD, e -> {
            Logger.getGlobal().info(() -> "[TaskChangeEvent [FILTER], Type = " + e.getEventType() + " ] -> " + e.getModel());
            sideNav.get(e.getModel().getListID()).updateSize(1);
            sideNav.get(ListType.ALL).updateSize(1);
        });

//        this.addEventHandler(TaskChangeEvent.DELETE_TASK, e -> {
//            Logger.getGlobal().info(() -> "[TaskChangeEvent [FILTER], Type = " + e.getEventType() + " ] -> " + e.getModel());
//            sideNav.get(e.getModel().getListID()).updateSize(-1);
//            sideNav.get(ListType.DAILY).updateSize(-1);
//        });

        this.addEventHandler(TaskChangeEvent.MOVED, e -> {
            Logger.getGlobal().info(() -> "[TaskChangeEvent [FILTER], Type = " + e.getEventType() + " ] -> " + e.getModel());
////            System.out.println("e.getActual() = " + e.getActual());
//            System.out.println("atual = " + sideNav.get(e.getActual()).getName());
//            System.out.println("size atual = " + sideNav.get(e.getActual()).getSize());
////            System.out.println("e.getPrevious() = " + e.getPrevious());
//            System.out.println("anterior = " + sideNav.get(e.getPrevious()).getName());
//            System.out.println("size anterior = " + sideNav.get(e.getPrevious()).getSize());
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
//            updateAllListSize(e.getModel());
            sideNav.get(ListType.ALL).updateSize(!e.getModel().isCompleted() ? 1 : -1);

            sideNav.get(ListType.COMPLETED).updateSize(!e.getModel().isCompleted() ? -1 : 1);
            sideNav.get(ListType.DAILY).updateSize(!e.getModel().isCompleted() ? 1 : -1);
            sideNav.get(ListType.IMPORTANT).updateSize(!e.getModel().isCompleted() ? 1 : -1);
        });


        this.addEventFilter(TaskChangeEvent.DELETE_TASK, e -> {
            Logger.getGlobal().info(() -> "[TaskChangeEvent, Type = " + e.getEventType() + " ] -> " + e.getModel());
            Root root = (Root) getScene().getRoot();

            var title = new Text("Delete Task");
            title.getStyleClass().addAll("text-18 bold".split("\\s+"));

            HBox buttonsBar = getButtonsBar(e, root);
            buttonsBar.getStyleClass().addAll("align-center-left spacing-5".split("\\s+"));

            var flow = new TextFlow(new Text("\"" + e.getModel().getName() + "\" will be deleted permanently"));
            VBox container = new VBox(title, flow, buttonsBar);

            container.getStyleClass().addAll("bg-background pd-20 align-center-left rounded spacing-10".split("\\s+"));

            root.block();

            root.behavior().dialog()
                    .with(WrapperEffect.GRAY)
                    .content(container)
                    .width(-1)
                    .height(-1)
                    .show();


        });
    }

    private @NonNull HBox getButtonsBar(TaskChangeEvent e, Root root) {
        var confirm = new Button("Confirm");
        var cancel = new Button("Cancel");

        cancel.setCancelButton(true);
        cancel.setOnAction(_ -> {
            root.behavior().dialog().hide();
            root.behavior().drawer().hide();
            root.unblock();
        });
        confirm.setOnAction(_ -> {
            listRoot.getContainer().remove(e.getModel());
            e.getModel().delete();
            cancel.fire();
            sideNav.get(e.getModel().getListID()).updateSize(-1);
            if (e.getModel().isMyDay() && !e.getModel().isCompleted()) {
                sideNav.get(ListType.DAILY).updateSize(-1);
            }
            if (e.getModel().isImportant() && !e.getModel().isCompleted()) {
                sideNav.get(ListType.IMPORTANT).updateSize(-1);
            }
            if (!e.getModel().isCompleted()) {
                sideNav.get(ListType.ALL).updateSize(-1);
            }
        });

        return new HBox(confirm, cancel);
    }

    private void updateAllListSize(TaskViewModel model) {

        if (model.isMyDay()) {
            sideNav.get(ListType.DAILY).updateSize(!model.isCompleted() ? 1 : -1);
        }
        if (model.isImportant()) {
            sideNav.get(ListType.IMPORTANT).updateSize(!model.isCompleted() ? 1 : -1);
        }
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