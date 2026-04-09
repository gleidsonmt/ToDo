package io.github.gleidsonmt.todo.view_model;

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

    public ToDoTask convert(TaskViewModel model) {
        ToDoTask temp = create(model.getId(), model.getName(), model.isCompleted(), model.isImportant(),
                model.isMyDay(), model.getDueDate(), model.getCreatedAt(), model.getListId());

        return temp;
    }

    public ToDoTask create(int id, String name) {
        return create(id, name, false, false, false, null, LocalDate.now(), 0);
    }

    public ToDoTask create(long id, String name, boolean completed, boolean important, boolean myDay, LocalDate dueDate,
            LocalDate created, long listId) {
        return new ToDoTask(id, name, completed, important, myDay, dueDate, null, created, listId);
    }

}
