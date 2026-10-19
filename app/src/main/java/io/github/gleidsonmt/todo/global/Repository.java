

package io.github.gleidsonmt.todo.global;

import io.github.gleidsonmt.todo.model.DTO;

import java.util.List;


/**
 * Description:
 *
 * @author Gleidson Neves da Silveira | <a href="mailto:gleidisonmt@gmail.com">gleidisonmt@gmail.com</a> <br>
 * Created On: Feb 25, 2026
 * <p>
 * Version History: Initial version
 */
@Deprecated(forRemoval = true)
public class Repository {

    private final List<Presenter<?>> repos;

    @SuppressWarnings("null")
    public Repository() {
        this.repos = List.of(new UserPresenter(), new ListPresenter(), new TaskPresenter());
    }

    public <T extends DTO> Presenter<T> of(Class<?> presenter) {

        for (Presenter<?> p : repos) {
            // Get the generic superclass (e.g., AbstractPresenter<User>)
            if (p.getModelClass().equals(presenter)) {
                return (Presenter<T>) p;
            }

        }
        return null; // Or throw an exception if no match found
    }
}
