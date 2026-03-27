package io.github.gleidsonmt.todo.global;

import java.util.Optional;

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
public interface Presenter<T extends Model> {

    @Deprecated
    ObservableList<T> getData();

    @Deprecated
    Task<ObservableList<T>> fetch();

    Task<ObservableList<T>> fetch(ObservableList<T> items);

    void save(T model);

    void delete(T model);

    Optional<T> get(long id);

    Class<T> getModelClass();
}
