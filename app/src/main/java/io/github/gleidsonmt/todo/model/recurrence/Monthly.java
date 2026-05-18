

package io.github.gleidsonmt.todo.model.recurrence;

import java.time.DayOfWeek;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Gleidson Neves da Silveira | <a href="mailto:gleidisonmt@gmail.com">gleidisonmt@gmail.com</a>
 * Create on  13/12/2024
 */
public final class Monthly extends Recurrence {

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
