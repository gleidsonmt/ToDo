package io.github.gleidsonmt.todo.global;

import java.util.Optional;

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
 * Created On: Mar 17, 2026
 * <p>
 * Version History: Initial version
 */
public class AbstractPresenter<T extends Model> implements Presenter<T> {

    @Deprecated
    private final ObservableList<T> data;
    protected AbstractDao<T> dao;

    private boolean isLoaded = false;

    public AbstractPresenter(AbstractDao<T> dao) {
        data = FXCollections.observableArrayList();
        this.dao = dao;
    }

    @Deprecated
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
    @Deprecated
    private final ListChangeListener<T> commitChangesInDatabase = (ListChangeListener<T>) c -> {
        if (!isLoaded)
            return;
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
        return fetch(FXCollections.observableArrayList());
    }

    @Override
    public Task<ObservableList<T>> fetch(ObservableList<T> items) {
        return this.dao.fetch(items);
    }

    public Task<ObservableList<T>> fetch(long range) {
        return fetch(range, null);
    }

    public Task<ObservableList<T>> fetch(long range, String where) {
        return fetch(0, range, where);
    }

    public Task<ObservableList<T>> fetch(long ini, long fin) {
        return fetch(ini, fin, null);
    }

    public Task<ObservableList<T>> fetch(long limit, long offset, String where) {
        return dao.fetch(data, limit, offset, where);
    }

    @Override
    public long store(T model) {
        return dao.store(model);
    }

    @Override
    public boolean update(T model) {
        return dao.update(model);
    }

    @Override
    public boolean delete(T model) {
        return dao.delete(model);
    }

    @Override
    public boolean delete(long id) {
        return dao.delete(id);
    }

    @Override
    public Optional<T> get(long id) {
        return dao.get(id);
    }

    @Override
    public Optional<T> getFirst() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
