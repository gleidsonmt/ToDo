package io.github.gleidsonmt.todo.view.panel.input;

import java.time.LocalTime;

import io.github.gleidsonmt.glad.controls.icon.Icon;
import io.github.gleidsonmt.todo.model.ToDoTask;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 * Description:
 *
 * @author Gleidson Neves da Silveira | <gleidisonmt@gmail.com>
 *         Created On: Mar 02, 2026
 * 
 *         Version History: Initial version
 */
public class InputFieldItemRemind extends InputFieldItem {

    private ObjectProperty<LocalTime> remindTime = new SimpleObjectProperty<>();

    public InputFieldItemRemind(Icon icon, ToDoTask task) {
        super(icon);
    }
}
