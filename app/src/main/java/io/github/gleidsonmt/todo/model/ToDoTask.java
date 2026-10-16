package io.github.gleidsonmt.todo.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author Gleidson Neves da Silveira | <a href="mailto:gleidisonmt@gmail.com">gleidisonmt@gmail.com</a> <br>
 * Created on 24/09/2026
 */
public record ToDoTask(long id, String name, boolean completed, boolean important, boolean myDay, LocalDate dueDate,
                       LocalDateTime remind, LocalDate createdAt, long listID) implements DTO {
}
