

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
public class FakeTaskItem extends Label {

    public FakeTaskItem() {
        setMouseTransparent(true);
        this.getStyleClass().add("fake-task-item");
        int size = 50;
        setPrefHeight(size);
        setMaxHeight(size);
        // setMinHeight(size);
        setMaxWidth(Double.MAX_VALUE);
    }

}
