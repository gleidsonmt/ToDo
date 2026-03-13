package io.github.gleidsonmt.todo.view.panel.input;

import io.github.gleidsonmt.glad.controls.icon.Icon;
import io.github.gleidsonmt.todo.model.ToDoTask;

/**
 * Description:
 *
 * @author Gleidson Neves da Silveira | <gleidisonmt@gmail.com>
 *         Created On: Mar 02, 2026
 * 
 *         Version History: Initial version
 */
public class InputFieldItemDueDate extends InputFieldItem {

    public InputFieldItemDueDate(Icon icon, ToDoTask task) {
        super(icon);

        setId("input-field-due-date");
    }
}
