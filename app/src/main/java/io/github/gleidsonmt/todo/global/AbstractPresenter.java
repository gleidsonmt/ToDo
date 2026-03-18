package io.github.gleidsonmt.todo.global;

import io.github.gleidsonmt.todo.bd.dao.internal.AbstractDao;
import io.github.gleidsonmt.todo.model.Model;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;

/**
 * Description:
 *
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 *         Created On: Mar 17, 2026
 * 
 *         Version History: Initial version
 */
public class AbstractPresenter<T extends Model> implements Presenter<T> {

    private final ObservableList<T> data;
    protected AbstractDao<T> dao;

    private boolean isLoaded = false;

    public AbstractPresenter(AbstractDao<T> dao) {
        data = FXCollections.observableArrayList();
        this.dao = dao;
    }

    @Override
    public ObservableList<T> getData() {
        return this.data;
    }

    @Override
    public Class<T> getModelClass() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getModelClass'");
    }

    /**
     * For every action after loading the tasks, they will be reflection in
     * database (dao) actions.
     */
    private final ListChangeListener<T> commitChangesInDatabase = (ListChangeListener<T>) c -> {
        if (!isLoaded) return;
        if (c.next()) {
            if (c.wasReplaced()) {
                c.getAddedSubList().forEach(model -> dao.update(model));
                return;
            }
            if (c.wasAdded()) {
                c.getAddedSubList().forEach(model -> dao.store(model));
            }
            if (c.wasRemoved()) {
                c.getRemoved().forEach(model -> dao.delete(model));
            }
        }
    };

    @Override
    public Task<ObservableList<T>> fetch() {
        var task = this.dao.fetch(data);
        data.addListener(commitChangesInDatabase);
        task.setOnSucceeded(e -> isLoaded = true);
        return task;
    }

}
