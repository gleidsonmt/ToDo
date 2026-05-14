package io.github.gleidsonmt.todo.global;

import io.github.gleidsonmt.todo.bd.dao.DaoRecurrence;
import io.github.gleidsonmt.todo.bd.dao.DaoTask;
import io.github.gleidsonmt.todo.model.ToDoTask;
import io.github.gleidsonmt.todo.model.recurrence.Recurrence;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;

import java.util.Optional;

/**
 * Description:
 *
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Created On: Mar 17, 2026
 * <p>
 * Version History: Initial version
 */
public class TaskPresenter extends AbstractPresenter<ToDoTask> {

    private final DaoRecurrence daoRecurrence;

    public TaskPresenter() {
        super(new DaoTask());
        this.daoRecurrence = new DaoRecurrence();
    }

    public long storeRecurrence(Recurrence recurrence) {
        return daoRecurrence.store(recurrence);
    }

    public Optional<Recurrence> getRecurrence(long id) {
        return daoRecurrence.getWhere("task_id = " + id );
    }

    public Task<ObservableList<ToDoTask>> selectAllBefore() {
        return dao.fetchWhere(FXCollections.observableArrayList(), "WHERE completed = 0 AND DATE(remind) <= CURRENT_DATE AND TIME(remind) <= CURRENT_TIME");
//        select * from task WHERE DATE(remind) <= CURRENT_DATE AND TIME(remind) <= CURRENT_TIME;
    }

    @Override
    public Class<ToDoTask> getModelClass() {
        return ToDoTask.class;
    }
}