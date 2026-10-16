package io.github.gleidsonmt.todo.model;

import io.github.gleidsonmt.todo.model.recurrence.Recurrence;
import io.github.gleidsonmt.todo.model.recurrence.RecurrenceType;
import io.github.gleidsonmt.todo.view_model.ViewModel;
import javafx.beans.property.*;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author Gleidson Neves da Silveira | <a href="mailto:gleidisonmt@gmail.com">gleidisonmt@gmail.com</a> <br>
 * Created on 25/09/2026
 */
public class RecurrenceViewModel extends ViewModel {

    private final ObjectProperty<RecurrenceType> recurrenceType = new SimpleObjectProperty<>();
    private final IntegerProperty gap = new SimpleIntegerProperty(1);
    private final LongProperty id = new SimpleLongProperty();
    private final LongProperty taskID = new SimpleLongProperty();

    private Set<DayOfWeek> daysOfWeek = new TreeSet<>(List.of(DayOfWeek.values()));

    public RecurrenceViewModel() {

    }

    public RecurrenceViewModel(RecurrenceType type) {
        this.recurrenceType.set(type);
        if (type == RecurrenceType.WEEKDAYS) {
            daysOfWeek.removeAll(List.of(DayOfWeek.SUNDAY, DayOfWeek.SATURDAY));
        }
    }

    public long getTaskID() {
        return taskID.get();
    }

    public int getGap() {
        return gap.get();
    }

    public void setGap(int gap) {
        this.gap.setValue(gap);
    }

    public IntegerProperty gapProperty() {
        return gap;
    }

    public Set<DayOfWeek> getDaysOfWeek() {
        return daysOfWeek;
    }

    public void setDaysOfWeek(Set<DayOfWeek> daysOfWeek) {
        this.daysOfWeek = daysOfWeek;
    }

    public long getId() {
        return id.get();
    }

    public void setTaskID(long taskId) {
        this.taskID.set(taskId);
    }

    public RecurrenceType getType() {
        return recurrenceType.get();
    }


    @Override
    public String toString() {
        return "{\"RecurrenceViewModel\":"
               + ", \"id\":" + id.get()
               + ", \"daysOfWeek\":" + daysOfWeek
               + ", \"type\":" + recurrenceType.get()
               + ", \"gap\":" + gap.get()
               + ", \"taskID\":" + taskID.get()
               + "}";
    }
}
