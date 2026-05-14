package io.github.gleidsonmt.todo.view_model.converter;

import io.github.gleidsonmt.todo.model.Model;
import io.github.gleidsonmt.todo.view_model.ViewModel;

/**
 *
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Created on  16/04/2026
 */
public abstract class ViewModelConverter<T extends Model, E extends ViewModel> {

    public abstract E toViewModel(T model);

    public abstract T toModel(E model);

}
