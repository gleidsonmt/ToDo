package io.github.gleidsonmt.todo.model.recurrence;

import java.time.DayOfWeek;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  13/12/2024
 */
public final class Yearly extends Recurrence {

    public Yearly() {
        this(1);
    }

    public Yearly(int gap) {
        super(0, gap, 0,RecurrenceType.YEARLY, DayOfWeek.values());
    }

    @Override
    public String getShortName() {
        return "years";
    }
}
