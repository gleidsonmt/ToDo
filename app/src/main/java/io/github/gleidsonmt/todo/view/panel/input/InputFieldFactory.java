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
@Deprecated
public class InputFieldFactory {
    @Deprecated
    public static InputFieldItem createItem(InputFieldType type, ToDoTask task) {
        switch (type) {
        case TASK -> {
            return new InputFieldItemTask(Icon.HOME, "Tasks", task);
        }
        }
        return null;
    }
}
