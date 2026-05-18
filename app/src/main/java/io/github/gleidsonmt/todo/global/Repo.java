

package io.github.gleidsonmt.todo.global;

import io.github.gleidsonmt.todo.model.Model;

/**
 * Description:
 *
 * @author Gleidson Neves da Silveira | <a href="mailto:gleidisonmt@gmail.com">gleidisonmt@gmail.com</a>
 * Created On: Mar 17, 2026
 * <p>
 * Version History: Initial version
 */
public interface Repo<T extends Model> {
    void store(T value);
}
