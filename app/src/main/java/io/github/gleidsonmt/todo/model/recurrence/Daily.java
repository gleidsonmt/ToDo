package io.github.gleidsonmt.todo.model.recurrence;

import java.time.DayOfWeek;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  13/12/2024
 */
public final class Daily extends Recurrence {

    public Daily() {
        super(0, 1, 0, RecurrenceType.DAILY,  DayOfWeek.values());
    }

    public Daily(long id, int gap, int taskId) {
        super(id, gap, taskId, RecurrenceType.DAILY,  DayOfWeek.values());
    }

    @Override
    public String getShortName() {
        return "days";
    }
}
