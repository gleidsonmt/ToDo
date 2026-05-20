

package io.github.gleidsonmt.todo;

import com.dustinredmond.fxtrayicon.FXTrayIcon;
import io.github.gleidsonmt.glad.base.Layout;
import io.github.gleidsonmt.glad.base.Root;
import io.github.gleidsonmt.glad.base.dialog.alert.AlertType;
import io.github.gleidsonmt.glad.controls.button.Button;
import io.github.gleidsonmt.glad.controls.loaders.CircleLoader;
import io.github.gleidsonmt.glad.controls.loaders.Suspense3DCircle;
import io.github.gleidsonmt.todo.bd.Setup;
import io.github.gleidsonmt.todo.bd.sqlite.SQLiteConnection;
import io.github.gleidsonmt.todo.events.DialogEvent;
import io.github.gleidsonmt.todo.events.LoginEvent;
import io.github.gleidsonmt.todo.global.Global;
import io.github.gleidsonmt.todo.model.ToDoTask;
import io.github.gleidsonmt.todo.services.TimerAlertService;
import io.github.gleidsonmt.todo.utils.Assets;
import io.github.gleidsonmt.todo.utils.I18n;
import io.github.gleidsonmt.todo.view.MainView;
import io.github.gleidsonmt.todo.view.panel.menu.input_menu_items.DateUtils;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

/**
 *
 * @author Gleidson Neves da Silveira | <a href="mailto:gleidisonmt@gmail.com">gleidisonmt@gmail.com</a> <br>
 * Created on  28/04/2026
 */
public class LoaderView extends StackPane implements Layout {

    public LoaderView() {
        CircleLoader circleLoader = new Suspense3DCircle();
        getChildren().add(circleLoader);
        Setup setup = new Setup(this);
        setup.start();

        circleLoader.legendProperty().bind(setup.messageProperty());

        addEventHandler(LoginEvent.LOGIN, _ -> {
            Platform.runLater(() -> {
                Root root = (Root) getScene().getRoot();
                root.setLayout(new MainView());
            });

            var stage = (Stage) getParent().getScene().getWindow();
            FXTrayIcon trayIcon = new FXTrayIcon(stage, Assets.getImage("logo_128.png"));
            trayIcon.addExitItem(I18n.get("tray.exit"), _ -> {
                Platform.exit();
                System.exit(0);
            });
            trayIcon.addMenuItem(I18n.get("tray.show"), _ -> stage.show());
            trayIcon.show();

            TimerAlertService timerAlertService = new TimerAlertService();
            Platform.runLater(timerAlertService::start);

            timerAlertService.setOnSucceeded(e -> {
                ObservableList<ToDoTask> tasks = timerAlertService.getValue();
                tasks.forEach(task -> {
                    trayIcon.showInfoMessage(task.getName(), DateUtils.format(task.getRemind()));
                });

                trayIcon.setTrayIconTooltip("JavaFx ToDo");
            });
        });

        addEventHandler(DialogEvent.DIALOG_EVENT, e -> {
            if (e.getEventType().equals(DialogEvent.DIALOG_ERROR)) {
                Root root = (Root) getParent();

                Hyperlink link = new Hyperlink("Get Information");

                link.setOnAction(event -> {
                    Global.openLink("https://github.com/gleidsonmt/ToDo");
                });

                root.behavior()
                        .alert()
                        .title(e.getTitle()) // The main text of the alert
                        .content( // Set a node as your content
                                new TextFlow(new Text(e.getMessage()+"\n"), link)
                        )
                        .buttons(new Button("ok")) // These are actions
                        .type(AlertType.ERROR) // The type of alert (WARNING, INFO, SUCCESS, ERROR),
                        .block()
                        // you can also use .type("error")// this works as well
                        .show();
            }
        });

    }
}
