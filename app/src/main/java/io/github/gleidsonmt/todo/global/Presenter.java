package io.github.gleidsonmt.todo.global;

import io.github.gleidsonmt.todo.bd.dao.internal.Dao;
import io.github.gleidsonmt.todo.model.Model;
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
public interface Presenter<T extends Model> extends Dao<T> {

    Task<ObservableList<T>> fetch();

    Task<ObservableList<T>> fetch(ObservableList<T> items);

    Class<T> getModelClass();
}
