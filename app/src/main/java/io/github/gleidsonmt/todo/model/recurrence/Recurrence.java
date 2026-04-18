package io.github.gleidsonmt.todo.model.recurrence;

import io.github.gleidsonmt.todo.model.Model;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.DayOfWeek;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  11/04/2026
 */
public abstract class Recurrence extends Model {

    private long taskID;
    protected ObservableList<DayOfWeek> daysOfWeek;
    protected RecurrenceType type;
    private final IntegerProperty gap;

    public Recurrence(long id, int gap, long taskId, RecurrenceType type, DayOfWeek... dayOfWeek) {
        super(id);
        taskID = taskId;
        this.gap = new SimpleIntegerProperty(gap);
        this.type = type;
        this.daysOfWeek = FXCollections.observableArrayList(dayOfWeek);
    }

    public long getTaskID() {
        return this.taskID;
    }

    public void setTaskID(long id) {
        this.taskID = id;
    }

    public int getGap() {
        return gap.get();
    }

    public IntegerProperty gapProperty() {
        return gap;
    }

    public RecurrenceType getType() {
        return type;
    }

    public ObservableList<DayOfWeek> getDaysOfWeek() {
        return daysOfWeek;
    }

    public abstract String getShortName();

    @Override
    public String toString() {
        return type.name();
    }
}
