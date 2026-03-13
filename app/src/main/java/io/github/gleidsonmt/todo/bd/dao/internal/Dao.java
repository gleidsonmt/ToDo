package io.github.gleidsonmt.todo.bd.dao.internal;

import java.util.Optional;

import org.jetbrains.annotations.ApiStatus;

import io.github.gleidsonmt.todo.model.Model;

/**
 * Description: The base class for dao action models.
 * 
 * @author Gleidson Neves da Silveira | <gleidisonmt@gmail.com>
 *         Created On: Feb 22, 2026
 * 
 *         Version History: Initial version
 */
@ApiStatus.Internal
public interface Dao<T extends Model> {

    // Default CRUD Actions
    boolean store(T model);

    boolean update(T model);

    boolean delete(T model);

    boolean delete(long model);

    // Getters
    Optional<T> get(long id);

    Optional<T> getFirst();

}
