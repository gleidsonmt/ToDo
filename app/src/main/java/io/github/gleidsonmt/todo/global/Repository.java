

package io.github.gleidsonmt.todo.global;

import java.util.List;

import io.github.gleidsonmt.todo.model.Model;
import javafx.collections.ObservableList;

/**
 * Description:
 *
 * @author Gleidson Neves da Silveira | <a href="mailto:gleidisonmt@gmail.com">gleidisonmt@gmail.com</a>
 * Created On: Feb 25, 2026
 * <p>
 * Version History: Initial version
 */
public class Repository {

    private final List<Presenter<?>> repos;

    @SuppressWarnings("null")
    public Repository() {
        this.repos = List.of(new UserPresenter(), new ListPresenter(), new TaskPresenter());
    }

    public <T extends Model> Presenter<T> of(Class<?> presenter) {

        for (Presenter<?> p : repos) {
            // Get the generic superclass (e.g., AbstractPresenter<User>)
            if (p.getModelClass().equals(presenter)) {
                return (Presenter<T>) p;
            }
            // Type type = p.getClass().getGenericSuperclass();
            // if (type instanceof ParameterizedType) {
            // ParameterizedType pt = (ParameterizedType) type;
            // Type[] typeArgs = pt.getActualTypeArguments();
            // if (typeArgs.length > 0) {
            // Type arg = typeArgs[0];
            // if (arg instanceof Class) {
            // Class<?> modelClass = (Class<?>) arg;
            // if (modelClass.equals(presenter)) {
            // // Safe cast since we verified the type matches
            // return (Presenter<T>) p;
            // }
            // }
            // }
            // }
        }
        return null; // Or throw an exception if no match found
    }
}
