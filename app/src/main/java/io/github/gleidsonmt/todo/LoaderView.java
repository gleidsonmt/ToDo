package io.github.gleidsonmt.todo;

import io.github.gleidsonmt.glad.base.Layout;
import io.github.gleidsonmt.glad.base.Root;
import io.github.gleidsonmt.glad.controls.loaders.CircleLoader;
import io.github.gleidsonmt.glad.controls.loaders.Suspense3DCircle;
import io.github.gleidsonmt.todo.bd.mysql.Setup;
import io.github.gleidsonmt.todo.view.MainView;
import javafx.beans.property.StringProperty;
import javafx.scene.layout.StackPane;

/**
 *
 * @author Gleidson Neves da Silveira | <gleidisonmt@gmail.com>
 * Created on  28/04/2026
 */
public class LoaderView extends StackPane implements Layout {

    private CircleLoader circleLoader = new Suspense3DCircle();
    private final Setup setup = new Setup();

    public LoaderView() {
        getChildren().add(circleLoader);

        circleLoader.legendProperty().bind(setup.messageProperty());

        setup.setOnSucceeded(_ -> {
            Root root = (Root) getParent();
            root.setLayout(new MainView(setup.getValue()));
        });
        new Thread(setup).start();
    }

    public void updateLegend(String legend) {
        circleLoader.setLegend(legend);
    }

    public StringProperty legendProperty() {
        return circleLoader.legendProperty();
    }
}
