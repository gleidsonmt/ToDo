package io.github.gleidsonmt.todo.model.recurrence;

import io.github.gleidsonmt.todo.model.DTO;

import java.time.DayOfWeek;
import java.util.Set;

/**
 * @author Gleidson Neves da Silveira | <a href="mailto:gleidisonmt@gmail.com">gleidisonmt@gmail.com</a> <br>
 * Created on 24/09/2026
 */
public record Recurrence(long id, int gap, RecurrenceType type, Set<DayOfWeek> daysOfWeek, long taskID) implements DTO {
}
