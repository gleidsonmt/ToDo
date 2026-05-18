

package io.github.gleidsonmt.todo.view.aside;

import java.time.DayOfWeek;

import io.github.gleidsonmt.todo.model.Model;
import javafx.collections.ObservableList;

/**
 * Description:
 *
 * @author Gleidson Neves da Silveira | <a href="mailto:gleidisonmt@gmail.com">gleidisonmt@gmail.com</a> <br>
 * Created On: Feb 25, 2026
 * <p>
 * Version History: Initial version
 */
public abstract class Recurrence extends Model implements Cloneable {

    protected ObservableList<DayOfWeek> daysOfWeek; // only to pair with
    // database model
    protected RecurrenceType type;
    private int gap;

    public Recurrence(int gap) {
        this(0, gap, RecurrenceType.DAILY);
    }

    public Recurrence(int id, int gap, RecurrenceType type) {
        super(id);
        this.gap = gap;
        this.type = type;
    }

    public int getGap() {
        return gap;
    }

    public void setGap(int gap) {
        this.gap = gap;
    }

    public RecurrenceType getType() {
        return type;
    }

    public void setType(RecurrenceType type) {
        this.type = type;
    }

    public abstract String getShortName();

    public ObservableList<DayOfWeek> getDaysOfWeek() {
        return daysOfWeek;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Recurrence{");
        sb.append("daysOfWeek=").append(daysOfWeek);
        sb.append(", type=").append(type);
        sb.append(", gap=").append(gap);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public Recurrence clone() {
        try {
            Recurrence clone = (Recurrence) super.clone();
            // TODO: copy mutable state here, so the clone can't change the
            // internals of the original
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    // @Override
    // public String toString() {
    // final StringBuffer sb = new StringBuffer(getClass().getSimpleName()+"{");
    // sb.append("gap=").append(gap);
    // sb.append('}');
    // return sb.toString();
    // }
}
