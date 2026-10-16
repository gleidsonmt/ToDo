

package io.github.gleidsonmt.todo.model.recurrence;

import java.time.DayOfWeek;
import java.util.Set;

/**
 * @author Gleidson Neves da Silveira | <a href="mailto:gleidisonmt@gmail.com">gleidisonmt@gmail.com</a> <br>
 * Create on  13/12/2024
 */
@Deprecated(forRemoval = true)
public final class Monthly extends RecurrenceOld {

    public Monthly() {
        this(1);
    }

    public Monthly(int gap) {
        super(0, gap, 0, RecurrenceType.MONTHLY);
    }

    public Monthly(long id, int gap, int taskId, Set<DayOfWeek> dayOfWeeks) {
        super(id, gap, taskId, RecurrenceType.MONTHLY,  dayOfWeeks);
    }

    @Override
    public String getShortName() {
        return "months";
    }
}
