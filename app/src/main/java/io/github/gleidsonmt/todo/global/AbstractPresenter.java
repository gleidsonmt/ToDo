

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
 * @author Gleidson Neves da Silveira | <a href="mailto:gleidisonmt@gmail.com">gleidisonmt@gmail.com</a> <br>
 * Created On: Mar 17, 2026
 * <p>
 * Version History: Initial version
 */
@Deprecated
public class AbstractPresenter<T extends Model> implements Presenter<T> {

    protected AbstractDao<T> dao;

    public AbstractPresenter(AbstractDao<T> dao) {
        this.dao = dao;
    }

    @Override
    public Class<T> getModelClass() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getModelClass'");
    }

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
        return dao.fetch(FXCollections.observableArrayList(), limit, offset, where);
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
