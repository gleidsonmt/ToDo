package io.github.gleidsonmt.todo.global;

import io.github.gleidsonmt.todo.bd.dao.DaoTask;
import io.github.gleidsonmt.todo.model.ToDoTask;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

/**
 * Description:
 *
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 *         Created On: Feb 25, 2026
 * 
 *         Version History: Initial version
 */
public class TaskRepository extends Service<ObservableList<ToDoTask>> {

    private final ObservableList<ToDoTask> data;
    private final DaoTask dao;

    public TaskRepository() {
        this(FXCollections.observableArrayList());
    }

    public TaskRepository(ObservableList<ToDoTask> data) {
        this.dao = new DaoTask();
        this.data = data;
    }

    @Override
    protected Task<ObservableList<ToDoTask>> createTask() {
        return dao.fetch(data);
    }

    /**
     * For every action after loading the tasks, they will be reflection in
     * database (dao) actions.
     */
    @Override
    protected void succeeded() {
        data.addListener((ListChangeListener<ToDoTask>) c -> {
            if (c.next()) {
                if (c.wasReplaced()) {
                    c.getAddedSubList().forEach(this::update); // upadte in db
                } else {
                    if (c.wasAdded()) {
                        c.getAddedSubList().forEach(this::store); // store in db
                    } else if (c.wasRemoved()) {
                        c.getRemoved().forEach(this::delete); // delete in db
                    }
                }
            }
        });
    }

    public ObservableList<ToDoTask> getData() {
        return this.data;
    }

    private void delete(ToDoTask task) {
        dao.delete(task);
    }

    private void update(ToDoTask task) {
        dao.update(task);
    }

    private void store(ToDoTask task) {
        dao.store(task);
    }

    private void apply() {
        dao.commit();
    }

}
