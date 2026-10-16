package io.github.gleidsonmt.todo.model;

/**
 * @author Gleidson Neves da Silveira | <a href="mailto:gleidisonmt@gmail.com">gleidisonmt@gmail.com</a> <br>
 * Created on 24/09/2026
 */
public record List(long id, String name, boolean fixed, int size, String icon) implements DTO {
}
