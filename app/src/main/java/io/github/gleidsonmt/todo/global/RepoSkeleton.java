package io.github.gleidsonmt.todo.global;

import java.util.Optional;

import io.github.gleidsonmt.todo.bd.dao.internal.AbstractDao;
import io.github.gleidsonmt.todo.bd.dao.internal.Dao;
import io.github.gleidsonmt.todo.bd.dao.internal.ListDao;
import io.github.gleidsonmt.todo.model.Model;
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
public class RepoSkeleton<D extends AbstractDao<T>, T extends Model> implements Dao<T>, ListDao<T> {

    public AbstractDao<T> dao;

    public RepoSkeleton(AbstractDao<T> dao) {
        this.dao = dao;
    }

    public void load(ObservableList<T> list) {
        this.dao.fetch(list);
    }

    @Override
    public Task<ObservableList<T>> fetch(ObservableList<T> items) {
        return this.dao.fetch(items);
    }

    @Override
    public Task<ObservableList<T>> fetchWhere(ObservableList<T> items, String condition) {
        return this.dao.fetchWhere(items, condition);
    }

    @Override
    public ObservableList<T> fetchByModel(Model model) {
       return this.dao.fetchByModel(model);
    }

    @Override
    public boolean store(T model) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'store'");
    }

    @Override
    public boolean update(T model) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public boolean delete(T model) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

    @Override
    public boolean delete(long model) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

    @Override
    public Optional<T> get(long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'get'");
    }

    @Override
    public Optional<T> getFirst() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getFirst'");
    }

    // @Override
    // public void store(T value) {
    // this.dao.store(value);
    // }
}
