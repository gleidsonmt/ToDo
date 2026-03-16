package io.github.gleidsonmt.todo.global;

import io.github.gleidsonmt.todo.bd.dao.DaoList;
import io.github.gleidsonmt.todo.bd.dao.DaoTask;
import io.github.gleidsonmt.todo.model.List;
import io.github.gleidsonmt.todo.model.ToDoTask;
import io.github.gleidsonmt.todo.view.nav.SideNav;
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
public class Repository {

    private final ObservableList<ToDoTask> data;
    private final ObservableList<List> lists;

    private final DaoTask dao;
    private final DaoList daoList;

    public Repository() {
        this.dao = new DaoTask();
        this.daoList = new DaoList();
        this.data = FXCollections.observableArrayList();
        this.lists = FXCollections.observableArrayList();
    }

    public Service<ObservableList<ToDoTask>> loadData() {
        return new Service<ObservableList<ToDoTask>>() {
            @Override
            protected Task<ObservableList<ToDoTask>> createTask() {
                var task = dao.fetch(data);
                task.setOnSucceeded(e -> data.addListener(createListener()));
                return task;
            }
        };
    }

    public Service<ObservableList<List>> loadLists() {
        return new Service<ObservableList<List>>() {
            @Override
            protected Task<ObservableList<List>> createTask() {
                var task = daoList.fetch(lists);
                // task.setOnSucceeded(e -> data.addListener(createListener()));
                return task;
            }
        };
    }

    /**
     * For every action after loading the tasks, they will be reflection in
     * database (dao) actions.
     */
    private ListChangeListener<ToDoTask> createListener() {
        return ((ListChangeListener<ToDoTask>) c -> {
            if (c.next()) {
                if (c.wasReplaced()) {
                    c.getAddedSubList().forEach(this::update);
                } else {
                    if (c.wasAdded()) {
                        c.getAddedSubList().forEach(this::store);
                    } else if (c.wasRemoved()) {
                        c.getRemoved().forEach(this::delete);
                    }
                }
            }
        });
    }

    public ObservableList<ToDoTask> getData() {
        return this.data;
    }

    public ObservableList<List> getLists() {
        return this.lists;
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

    public void store(List list) {
        daoList.store(list);
    }

    public void delete(List list) {
        daoList.delete(list);
    }

    private void apply() {
        dao.commit();
    }

}
