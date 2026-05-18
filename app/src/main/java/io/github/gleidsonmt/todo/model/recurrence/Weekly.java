

package io.github.gleidsonmt.todo.model.recurrence;

import io.github.gleidsonmt.todo.bd.dao.internal.Ignore;
import javafx.collections.FXCollections;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.time.DayOfWeek;
import java.util.*;

/**
 * @author Gleidson Neves da Silveira | <a href="mailto:gleidisonmt@gmail.com">gleidisonmt@gmail.com</a> <br>
 * Create on  13/12/2024
 */
public final class Weekly extends Recurrence {

    @Ignore
    private boolean onlyDaysOfWeek;

    public Weekly() {
        this(false);
    }

    public Weekly(boolean onlyDaysOfWeek) {
        super(0, 1, 0, onlyDaysOfWeek ? RecurrenceType.WEEKDAYS : RecurrenceType.WEEKLY,
                onlyDaysOfWeek ?
                        new TreeSet<>(List.of(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY)) :
                        new TreeSet<>(List.of(DayOfWeek.values()))
        );
        this.onlyDaysOfWeek = onlyDaysOfWeek;
    }

    public Weekly(long id, int times, long taskID, boolean onlyDaysOfWeek, Set<DayOfWeek> daysOfWeek) {
        super(id, times, taskID, onlyDaysOfWeek ? RecurrenceType.WEEKDAYS : RecurrenceType.WEEKLY, daysOfWeek);
        System.out.println("daysOfWeek = " + daysOfWeek);
    }

//    @Override
//    public RecurrenceType getType() {
////        if (onlyDaysOfWeek) {
////            return RecurrenceType.WEEKDAYS;
////        } else {
////            return super.getType();
////        }
//    }

    private Set<DayOfWeek> getOnlyDaysOfWeek() {
        return new HashSet<>(List.of(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY));
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
