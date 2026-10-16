

package io.github.gleidsonmt.todo.view_model.converter;

import io.github.gleidsonmt.todo.view_model.ViewModel;

/**
 *
 * @author Gleidson Neves da Silveira | <a href="mailto:gleidisonmt@gmail.com">gleidisonmt@gmail.com</a> <br>
 * Created on  16/04/2026
 */
@Deprecated(forRemoval = true)
public abstract class ViewModelConverter<T, E extends ViewModel> {

    // to remove
    @Deprecated(forRemoval = true)
    public T toEntity(E model) {
        return null;
    };

    // to turn it in to abstract
    public T toDTO(E model) {
        return null;
    }

    public abstract E toViewModel(T model);

}
