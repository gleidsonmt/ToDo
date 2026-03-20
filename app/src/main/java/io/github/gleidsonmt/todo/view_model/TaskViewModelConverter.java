package io.github.gleidsonmt.todo.view.panel.items;

import java.time.LocalDate;

import io.github.gleidsonmt.todo.model.ToDoTask;

/**
 * Description:
 *
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 *         Created On: Mar 09, 2026
 * 
 *         Version History: Initial version
 */
public class TaskViewModelConverter {

    public ToDoTask convert(TaskItemViewModel model) {
        ToDoTask temp = create(model.getId(), model.getName(), model.isCompleted(), model.isImportant(),
                model.isMyDay(), model.getDueDate(), model.getListId());

        return temp;
    }

    public ToDoTask create(int id, String name) {
        return create(id, name, false, false, false, null, 0);
    }

    public ToDoTask create(int id, String name, boolean completed, boolean important, boolean myDay, LocalDate dueDate,
            long listId) {
        var temp = new ToDoTask(name);
        temp.setId(id);
        temp.setCompleted(completed);
        temp.setImportant(important);
        temp.setListId(listId);
        temp.setDueDate(dueDate);
        temp.setMyDay(myDay);

        return temp;
    }
}
