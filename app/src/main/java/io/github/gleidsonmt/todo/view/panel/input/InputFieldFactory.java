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
public class InputFieldFactory {
    public static InputFieldItem createItem(InputFieldType type, ToDoTask task) {
        switch (type) {
        case TASK -> {
            return new InputFieldItemTask(Icon.HOME, "Tasks", task);
        }
        case DUE_DATE -> {
            return new InputFieldItemDueDate(Icon.CALENDAR_MONTH, task);
        }
        case REMIND -> {
            return new InputFieldItemRemind(Icon.CLOCK, task);
        }
        case REPEAT -> {
            return new InputFieldItemRepeat(Icon.EVENT_REPEAT, task);
        }
        }
        return null;
    }
}
