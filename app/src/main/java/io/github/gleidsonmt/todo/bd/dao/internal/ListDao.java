

package io.github.gleidsonmt.todo.bd.dao.internal;

import io.github.gleidsonmt.todo.model.Model;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;

/**
 * @author Gleidson Neves da Silveira | <a href="mailto:gleidisonmt@gmail.com">gleidisonmt@gmail.com</a> <br>
 * Create on 17/01/2025
 */
public interface ListDao<T extends Model> {

    /**
     * A javafx task that fills out a list.
     *
     * @param items The list to fill.
     * @return The list of items filled.
     */
    Task<ObservableList<T>> fetch(ObservableList<T> items);

    /**
     * If a fetching list action needs some options and conditions.
     *
     * @param items     The list to fill.
     * @param condition The condition to get the list.
     * @return The list filled.
     */
    Task<ObservableList<T>> fetchWhere(ObservableList<T> items, String condition);

    /**
     * Fetch an item using another.
     *
     * @param model The model with the foreign key.
     * @return The model.
     */
    ObservableList<T> fetchByModel(Model model);
}
