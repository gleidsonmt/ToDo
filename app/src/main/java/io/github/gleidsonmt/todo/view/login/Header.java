package io.github.gleidsonmt.todo.view.login;

import io.github.gleidsonmt.glad.controls.button.IconButton;
import io.github.gleidsonmt.glad.controls.icon.Icon;
import io.github.gleidsonmt.glad.controls.icon.SVGIcon;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.Separator;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 *         Create on 27/10/2024
 */
public class Header extends GridPane {

    private final GridPane title;

    public Header(Icon _icon, String _title, String _text) {
        this.title = createTitle(_icon, _title, _text);
        configLayout();
    }

    private void configLayout() {
        this.getChildren().add(title);
    }

    private GridPane createTitle(Icon _icon, String _title, String _text) {

        GridPane title = new GridPane();
        GridPane.setHgrow(title, Priority.ALWAYS);
        title.setHgap(15);
        title.setVgap(15);

        Text text = new Text(_title);

        IconButton keyButton = new IconButton();
        keyButton.setIcon(new SVGIcon(_icon));
        keyButton.getStyleClass().add("key-button");

        text.getStyleClass().addAll("h3", "title");

        Text info = new Text(_text);
        info.getStyleClass().addAll("text-18", "custom-text");
        TextFlow textFlow = new TextFlow(info);

        Separator separator = new Separator();
        separator.setOrientation(Orientation.HORIZONTAL);
        VBox.setMargin(separator, new Insets(10, 0, 10, 0));

        title.getChildren().addAll(keyButton, text, textFlow, separator);

        // for (int i = 0; i < 2; i++) {
        // ColumnConstraints col = new ColumnConstraints();
        // col.setPercentWidth(50);
        // title.getColumnConstraints().add(col);
        // }

        GridPane.setColumnIndex(keyButton, 0);
        GridPane.setColumnIndex(text, 1);
        GridPane.setHgrow(text, Priority.ALWAYS);

        GridPane.setRowIndex(textFlow, 1);
        GridPane.setRowIndex(separator, 2);
        GridPane.setColumnSpan(textFlow, REMAINING);
        GridPane.setColumnSpan(separator, REMAINING);

        return title;
    }

}
