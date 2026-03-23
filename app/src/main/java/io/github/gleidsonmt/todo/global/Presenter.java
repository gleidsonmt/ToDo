package io.github.gleidsonmt.todo.global;

import io.github.gleidsonmt.todo.model.Model;
import io.github.gleidsonmt.todo.view_model.ViewModel;
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

    Task<ObservableList<T>> fetch();

    public void update(ViewModel viewModel);

    Class<T> getModelClass();
}
