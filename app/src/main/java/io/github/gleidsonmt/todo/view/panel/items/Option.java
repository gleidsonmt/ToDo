package io.github.gleidsonmt.todo.view.panel.items;

import io.github.gleidsonmt.glad.controls.icon.Icon;
import io.github.gleidsonmt.glad.controls.icon.SVGIcon;
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

    private Text text;
    private SVGIcon icon;

    public Option(String text) {
        this(text, Icon.NONE);
    }

    public Option(String text, Icon icon) {
        this.text = new Text(text);
        this.icon = new SVGIcon(icon);
        this.icon.setScale(0.8);
        getChildren().addAll(this.text, this.icon);
        setFocusTraversable(false);
        configLayout();
    }

    private void configLayout() {
        this.setHgap(5);
        GridPane.setColumnIndex(icon, 0);
        GridPane.setColumnIndex(text, 1);
    }
}
