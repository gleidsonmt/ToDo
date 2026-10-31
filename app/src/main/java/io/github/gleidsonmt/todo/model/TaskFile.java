package io.github.gleidsonmt.todo.model;

/**
 * @author Gleidson Neves da Silveira | <a href="mailto:gleidisonmt@gmail.com">gleidisonmt@gmail.com</a> <br>
 * Created on 29/10/2026
 */
public record TaskFile(long id, String path, long taskID) implements DTO {
}
