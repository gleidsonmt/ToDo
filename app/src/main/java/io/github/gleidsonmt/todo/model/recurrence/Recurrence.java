package io.github.gleidsonmt.todo.model.recurrence;

import io.github.gleidsonmt.todo.model.Model;
import javafx.collections.ObservableList;

import java.time.DayOfWeek;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  11/04/2026
 */
public abstract class Recurrence extends Model {

    protected ObservableList<DayOfWeek> daysOfWeek; // only to pair with database model
    protected RecurrenceType type;
    private int gap;

    public Recurrence() {
        this(0, 0, RecurrenceType.DAILY);
    }

    public Recurrence(long id, int gap, RecurrenceType type) {
        super(id);
        this.gap = gap;
        this.type = type;
    }

    public int getGap() {
        return gap;
    }

    public RecurrenceType getType() {
        return type;
    }

    public ObservableList<DayOfWeek> getDaysOfWeek() {
        return daysOfWeek;
    }

    public abstract String getShortName();
}
