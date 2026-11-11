package io.github.gleidsonmt.todo.view.panel.containers;

import io.github.gleidsonmt.glad.base.Root;
import io.github.gleidsonmt.todo.utils.Assets;
import io.github.gleidsonmt.todo.view.panel.Panel;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Node;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.util.List;

/**
 * @author Gleidson Neves da Silveira | <a href="mailto:gleidisonmt@gmail.com">gleidisonmt@gmail.com</a> <br>
 * Created on 10/11/2026
 */
public class ThemeBox extends GridPane {

    private ObjectProperty<Theme> selected = new SimpleObjectProperty<>(this, "theme");

    public ThemeBox(Panel panel) {
        int i = 0;
        var title = new Text("Theme");
        add(title, 0, 0);
        List<Theme> themes = List.of(
                new Theme(ThemeType.COLOR, "white", "#F5F7FA", "#434A54"),
                new Theme(ThemeType.COLOR, "#33B5E5", "#F5F7FA", "white"),
                new Theme(ThemeType.COLOR, "#02C852", "#F5F7FA", "white"),
                new Theme(ThemeType.IMAGE, "background.jpg", "#F5F7FA", "white")

//                INFO("#33B5E5", "light-gray"),
//                MINT("#02C852", "light-gray"),
//                DEFAULT("white", "light-gray");
        );
        for (Theme theme : themes) {
            add(createBox(theme), i++, 1);
        }

        selected.addListener((observable, oldValue, newValue) -> {
            panel.updateTheme(newValue);
        });
        getStyleClass().addAll("padding-5 border-light-gray-2 rounded bg-background hgap-5 vgap-5".split("\\s+"));

        setOnMouseExited(e -> {
            Root root = (Root) getScene().getRoot();
            root.flow().hide();
        });
    }

    private Node createBox(Theme theme) {
        var rect = new Rectangle();
        rect.setWidth(50);
        rect.setHeight(50);

        rect.setArcHeight(10);
        rect.setArcWidth(10);
        rect.setOnMouseClicked(event -> {
            this.selected.set(theme);
        });

        rect.getStyleClass().addAll((" min-w-50 min-h-50 rounded insets-1 border-2 border-" + (theme.border())).split("\\s+"));

        if (theme.type().equals(ThemeType.COLOR)) {
//            rect.setStyle("-fx-background-color: " + theme.color());
            rect.setFill(Color.web(theme.color()));
            rect.setStroke(Color.web(theme.border()));
        }

        if (theme.type().equals(ThemeType.IMAGE)) {
            ImagePattern imagePattern = new ImagePattern(Assets.getImage(theme.color()));
            rect.setFill(imagePattern);
//            rect.setBackground(new Background(new BackgroundImage(Assets.getImage(theme.color()),
//                    BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
//                    new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false))));
        }


        return rect;
    }

    public Theme getSelected() {
        return selected.get();
    }
}
