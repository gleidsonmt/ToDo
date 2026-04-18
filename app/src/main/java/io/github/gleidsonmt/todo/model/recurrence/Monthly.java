package io.github.gleidsonmt.todo.model.recurrence;

import java.time.DayOfWeek;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  13/12/2024
 */
public final class Monthly extends Recurrence {

    public Monthly() {
        this(1);
    }

    public Monthly(int interval) {
        super(0, interval, 0, RecurrenceType.MONTHLY, DayOfWeek.values());
    }

    @Override
    public String getShortName() {
        return "months";
    }
}
