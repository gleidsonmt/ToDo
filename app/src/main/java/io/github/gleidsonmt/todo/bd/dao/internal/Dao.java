

package io.github.gleidsonmt.todo.bd.dao.internal;

import java.util.Optional;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.ApiStatus.Experimental;

import io.github.gleidsonmt.todo.model.Model;

/**
 * Description: The base class for dao action models.
 *
 * @author Gleidson Neves da Silveira | <a href="mailto:gleidisonmt@gmail.com">gleidisonmt@gmail.com</a> <br> <br>
 * Created On: Feb 22, 2026
 */
@ApiStatus.Internal
public interface Dao<T extends Model> {

    // Default CRUD Actions
    long store(T model);

    boolean update(T model);

    boolean delete(T model);

    boolean delete(long model);

    // Getters
    Optional<T> get(long id);

    @Experimental
    Optional<T> getFirst();

}
