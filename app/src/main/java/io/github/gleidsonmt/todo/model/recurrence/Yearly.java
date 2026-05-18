

package io.github.gleidsonmt.todo.model.recurrence;

import java.time.DayOfWeek;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Gleidson Neves da Silveira | <a href="mailto:gleidisonmt@gmail.com">gleidisonmt@gmail.com</a> <br>
 * Create on  13/12/2024
 */
public final class Yearly extends Recurrence {

    public Yearly() {
        this(1);
    }

    public Yearly(int gap) {
        super(0, gap, 0,RecurrenceType.YEARLY);
    }

    public Yearly(long id, int gap, int taskId, Set<DayOfWeek> dayOfWeeks) {
        super(id, gap, taskId, RecurrenceType.YEARLY,  dayOfWeeks);
    }

    @Override
    public String getShortName() {
        return "years";
    }
}
