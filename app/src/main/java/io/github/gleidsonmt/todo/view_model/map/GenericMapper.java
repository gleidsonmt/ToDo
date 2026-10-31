package io.github.gleidsonmt.todo.view_model.map;

import org.mapstruct.MappingTarget;

/**
 * Description: The generic mapper interface for mapping between view models and data transfer objects.
 * It's a simplifier the process of mapping between view models and data transfer objects.
 * @author Gleidson Neves da Silveira | <a href="mailto:gleidisonmt@gmail.com">gleidisonmt@gmail.com</a> <br>
 * Created on 29/10/2026
 */
public interface GenericMapper<V, D> { // primeiro o view model depois o dto

    D toDTO(V viewModel);

    V toViewModel(D dto, @MappingTarget V viewModel);

}
