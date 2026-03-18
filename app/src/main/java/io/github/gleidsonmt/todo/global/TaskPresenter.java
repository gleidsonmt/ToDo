package io.github.gleidsonmt.todo.global;

import io.github.gleidsonmt.todo.bd.dao.DaoTask;
import io.github.gleidsonmt.todo.model.ToDoTask;

/**
 * Description:
 *
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 *         Created On: Mar 17, 2026
 * 
 *         Version History: Initial version
 */
public class TaskPresenter extends AbstractPresenter<ToDoTask> {

    public TaskPresenter() {
        super(new DaoTask());
    }

    @Override
    public Class<ToDoTask> getModelClass() {
        return ToDoTask.class;
    }
}
