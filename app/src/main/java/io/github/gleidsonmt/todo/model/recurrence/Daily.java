

package io.github.gleidsonmt.todo.model.recurrence;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Gleidson Neves da Silveira | <a href="mailto:gleidisonmt@gmail.com">gleidisonmt@gmail.com</a> <br>
 * Create on  13/12/2024
 */
public final class Daily extends Recurrence {

    public Daily() {
        super(0, 1, 0, RecurrenceType.DAILY);
    }

    public Daily(long id, int gap, int taskId, Set<DayOfWeek> dayOfWeeks) {
        super(id, gap, taskId, RecurrenceType.DAILY,  dayOfWeeks);
    }

    @Override
    public String getShortName() {
        return "days";
    }
}
