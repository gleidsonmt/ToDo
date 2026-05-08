package io.github.gleidsonmt.todo;

import io.github.gleidsonmt.glad.base.Layout;
import io.github.gleidsonmt.glad.base.Root;
import io.github.gleidsonmt.glad.controls.loaders.CircleLoader;
import io.github.gleidsonmt.glad.controls.loaders.Suspense3DCircle;
import io.github.gleidsonmt.todo.bd.mysql.Setup;
import io.github.gleidsonmt.todo.events.LoginEvent;
import io.github.gleidsonmt.todo.view.MainView;
import javafx.application.Platform;
import javafx.beans.property.StringProperty;
import javafx.scene.layout.StackPane;

/**
 *
 * @author Gleidson Neves da Silveira | <gleidisonmt@gmail.com>
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
                Root root = (Root) getParent();
                root.setLayout(new MainView());
            });
        });

    }
}
