package io.github.gleidsonmt.todo.model.recurrence;

import io.github.gleidsonmt.todo.bd.dao.internal.Ignore;
import javafx.collections.FXCollections;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.time.DayOfWeek;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  13/12/2024
 */
public final class Weekly extends Recurrence {

    @Ignore
    private boolean onlyDaysOfWeek = false;

    public Weekly() {
        this(1, DayOfWeek.values());
    }

    public Weekly(int gap) {
        this(gap,  DayOfWeek.values());
    }

    public Weekly(int gap, DayOfWeek... daysOfWeek) {
        this(gap, RecurrenceType.WEEKLY, daysOfWeek);
    }

    public Weekly(boolean onlyDaysOfWeek) {
        this(1, onlyDaysOfWeek, RecurrenceType.WEEKLY);
    }

    public Weekly(int times, RecurrenceType type, DayOfWeek... daysOfWeek) {
        this(times, false, type, daysOfWeek);
    }

    public Weekly(int times, boolean onlyDaysOfWeek, RecurrenceType type, DayOfWeek... daysOfWeek) {
        super(0, times, type);
        this.onlyDaysOfWeek = onlyDaysOfWeek;
        if (onlyDaysOfWeek) {
            this.daysOfWeek = FXCollections.observableArrayList(getOnlyDaysOfWeek());
        } else {
            this.daysOfWeek = FXCollections.observableArrayList(daysOfWeek);
        }
    }

    @Override
    public RecurrenceType getType() {
        if (onlyDaysOfWeek) {
            return RecurrenceType.WEEKDAYS;
        } else {
            return super.getType();
        }
    }

    private DayOfWeek[] getOnlyDaysOfWeek() {
        return new DayOfWeek[]{ DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY };
    }

    public void setOnlyDaysOfWeek(boolean bol) {
        this.onlyDaysOfWeek = bol;
    }

    public boolean isOnlyDaysOfWeek() {
        return this.onlyDaysOfWeek;
    }

    @Contract(pure = true)
    @Override
    public @NotNull String getShortName() {
        return "weeks";
    }
}
