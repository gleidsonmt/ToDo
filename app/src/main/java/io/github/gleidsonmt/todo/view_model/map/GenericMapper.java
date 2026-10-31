package io.github.gleidsonmt.todo.view_model.map;

/**
 * @author Gleidson Neves da Silveira | <a href="mailto:gleidisonmt@gmail.com">gleidisonmt@gmail.com</a> <br>
 * Created on 29/10/2026
 */
public interface GenericMapper<V, D> { // primeiro o view model depois o dto

    D toDTO(V viewModel);

}
