package io.github.gleidsonmt.todo.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import io.github.gleidsonmt.todo.bd.dao.internal.Ignore;
import io.github.gleidsonmt.todo.view.aside.Recurrence;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 * Description:
 *
 * @author Gleidson Neves da Silveira | <gleidisonmt@gmail.com>
 *         Created On: Feb 25, 2026
 * 
 *         Version History: Initial version
 */
@SuppressWarnings("unused")
public class ToDoTask extends Entity implements Cloneable {

    // eh o primeiro na lista
    private int recurrenceId = 0;
    private boolean myDay;
    private LocalDate dueDate;
    //
    private final ObjectProperty<LocalDateTime> remind = new SimpleObjectProperty<>();
    private final BooleanProperty completed;
    // Doing and refactoring to improve memory usage
    @Ignore
    private Recurrence recurrence;
    private boolean important;
    private long listId = 0;

    public ToDoTask() {
        this(null);
    }

    public ToDoTask(String name) {
        this(name, false, false);
    }

    public ToDoTask(String name, LocalDate dueDate) {
        this(name, false, false, dueDate);
    }

    public ToDoTask(String name, boolean completed, boolean important) {
        this(name, completed, important, null);
    }

    public ToDoTask(String name, boolean completed, boolean important, LocalDate duaDate) {
        this(0, name, completed, important, duaDate);
    }

    public ToDoTask(int id, String name, boolean completed, boolean important, LocalDate dueDate) {
        super(id, name);
        this.completed = new SimpleBooleanProperty(completed);
        this.dueDate = dueDate;
        this.important = important;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public boolean isCompleted() {
        return completed.get();
    }

    public BooleanProperty completedProperty() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed.set(completed);
    }

    public boolean isImportant() {
        return important;
    }

    public void setImportant(boolean important) {
        this.important = important;
    }

    public Recurrence getRecurrence() {
        return recurrence;
    }

    public void setRecurrence(Recurrence recurrence) {
        this.recurrence = recurrence;
    }

    public long getListId() {
        return listId;
    }

    public void setListId(long listId) {
        this.listId = listId;
    }

    public boolean isMyDay() {
        return myDay;
    }

    public void setMyDay(boolean myDay) {
        this.myDay = myDay;
    }

    public void setRemind(LocalDateTime remind) {
        this.remind.set(remind);
    }

    public LocalDateTime getRemind() {
        return remind.get();
    }

    public ObjectProperty<LocalDateTime> remindProperty() {
        return remind;
    }

    public int getRecurrenceId() {
        return recurrenceId;
    }

    public void setRecurrenceId(int recurrenceId) {
        this.recurrenceId = recurrenceId;
    }

    @Override
    public ToDoTask clone() throws CloneNotSupportedException {
        try {
            ToDoTask clone = (ToDoTask) super.clone();
            // TODO: copy mutable state here, so the clone can't change the
            // internals of the original
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    public String toJason() {
        return "Task {" + "\n\tname=" + super.getName() + "\n\tid=" + super.getId() + "\n\trecurrenceId=" + recurrenceId
                + "\n\tmyDay=" + myDay + "\n\tdueDate=" + dueDate + "\n\tremind=" + remind + "\n\tcompleted="
                + completed + "\n\trecurrence=" + recurrence + "\n\timportant=" + important + "\n\trecurrenceId="
                + recurrenceId + "\n\tlistId=" + listId + "\n}";
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Task{");
        sb.append("id=").append(super.getId());
        sb.append(", name=").append(super.getName());
        sb.append(", recurrenceId=").append(recurrenceId);
        sb.append(", myDay=").append(myDay);
        sb.append(", dueDate=").append(dueDate);
        sb.append(", remind=").append(remind);
        sb.append(", completed=").append(completed);
        sb.append(", recurrence=").append(recurrence);
        sb.append(", important=").append(important);
        sb.append(", listId=").append(listId);
        sb.append('}');
        return sb.toString();
    }

}
