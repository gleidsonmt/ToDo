package io.github.gleidsonmt.todo.view.panel.items;

import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

/**
 * Description:
 *
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 *         Created On: Mar 28, 2026
 * 
 *         Version History: Initial version
 */
public class Option extends GridPane {

    // private
    // private ObjectProperty<Icon> icon = new SimpleObjectProperty<>();

    private Text text;

    public Option(String text) {
        this.text = new Text(text);
        getChildren().addAll(this.text);
        setFocusTraversable(false);
    }

}
