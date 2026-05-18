

package io.github.gleidsonmt.todo.view.panel.items;

import javafx.scene.control.Label;

/**
 * Description:
 *
 * @author Gleidson Neves da Silveira | <a href="mailto:gleidisonmt@gmail.com">gleidisonmt@gmail.com</a> <br>
 * Created On: Feb 26, 2026
 * <p>
 * Version History: Initial version
 */
public class Title extends Label {

    public Title() {
        this.getStyleClass().addAll("task-info", "h5", "bold");
        this.setWrapText(true);
        // setMinHeight(Region.USE_PREF_SIZE);
    }
}
