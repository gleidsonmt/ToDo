

package io.github.gleidsonmt.todo.model.recurrence;

import java.time.DayOfWeek;
import java.util.Set;

/**
 * @author Gleidson Neves da Silveira | <a href="mailto:gleidisonmt@gmail.com">gleidisonmt@gmail.com</a> <br>
 * Create on  13/12/2024
 */
@Deprecated(forRemoval = true)
public final class DailyOld extends RecurrenceOld {

    public DailyOld() {
        super(0, 1, 0, RecurrenceType.DAILY);
    }

    public DailyOld(long id, int gap, int taskId, Set<DayOfWeek> dayOfWeeks) {
        super(id, gap, taskId, RecurrenceType.DAILY,  dayOfWeeks);
    }

    @Override
    public String getShortName() {
        return "days";
    }
}
