package io.github.gleidsonmt.todo.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import io.github.gleidsonmt.todo.bd.dao.internal.Ignore;

/**
 * Description:
 *
 * @author Gleidson Neves da Silveira | <gleidisonmt@gmail.com>
 *         Created On: Feb 25, 2026
 * 
 *         Version History: Initial version
 */
public class ToDoTask extends Entity {

    // sql = insert into task(name, list_id, important, completed, due_date,
    // my_day, recurrence_id, created_at) values(?, ?, ?, ?, ?, ?, ?, ?);
    // eh o primeiro na lista

    @Ignore
    private final int recurrenceId = 0;
    // Doing and refactoring to improve memory usage

    private final long listID;
    private final LocalDate createdAt;
    private final LocalDateTime remind;
    private final LocalDate dueDate;
    private final boolean myDay;
    private final boolean important;
    private final boolean completed;

    public ToDoTask(String name) {
        this(0, name, false, false, false, LocalDate.now(), LocalDateTime.now(), LocalDate.now(), 0);
    }

    public ToDoTask(long id, String name, boolean completed, boolean important, boolean myDay, LocalDate dueDate,
            LocalDateTime remind, LocalDate createdAt, long listID) {
        super(id, name);
        this.myDay = myDay;
        this.completed = completed;
        this.dueDate = dueDate;
        this.important = important;
        this.createdAt = createdAt;
        this.remind = remind;
        this.listID = listID;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public boolean isCompleted() {
        return completed;
    }

    public boolean isImportant() {
        return important;
    }

    public long getListId() {
        return listID;
    }

    public boolean isMyDay() {
        return myDay;
    }

    public LocalDateTime getRemind() {
        return remind;
    }

    public int getRecurrenceId() {
        return recurrenceId;
    }

    public String toJason() {
        return """
                Task {
                \tname=""" + super.getName() + "\n\tid=" + super.getId() + "\n\trecurrenceId=" + recurrenceId
                + "\n\tmyDay=" + myDay + "\n\tdueDate=" + dueDate + "\n\tremind=" + remind + "\n\tcompleted="
                + recurrenceId + "\n\tlistId=" + listID + "\n}";
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
        sb.append(", important=").append(important);
        sb.append(", listId=").append(listID);
        sb.append('}');
        return sb.toString();
    }

}
