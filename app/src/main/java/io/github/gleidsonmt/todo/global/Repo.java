package io.github.gleidsonmt.todo.global;

import io.github.gleidsonmt.todo.model.Model;

/**
 * Description:
 *
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Created On: Mar 17, 2026
 * <p>
 * Version History: Initial version
 */
public interface Repo<T extends Model> {
    void store(T value);
}
