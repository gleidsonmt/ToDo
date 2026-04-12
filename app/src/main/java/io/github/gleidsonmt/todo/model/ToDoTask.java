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

    private final long recurrenceID;
    private final long listID;
    private final LocalDate createdAt;
    private final LocalDateTime remind;
    private final LocalDate dueDate;
    private final boolean myDay;
    private final boolean important;
    private final boolean completed;

    public ToDoTask(String name) {
        this(0, name, false, false, false, LocalDate.now(), LocalDateTime.now(), LocalDate.now(), 0,0);
    }

    public ToDoTask(long id, String name, boolean completed, boolean important, boolean myDay, LocalDate dueDate,
            LocalDateTime remind, LocalDate createdAt, long listID, long recurrenceID) {
        super(id, name);
        this.myDay = myDay;
        this.completed = completed;
        this.dueDate = dueDate;
        this.important = important;
        this.createdAt = createdAt;
        this.remind = remind;
        this.listID = listID;
        this.recurrenceID = recurrenceID;
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

    public long getRecurrenceID() {
        return recurrenceID;
    }


    @Override
    public String toString() {
        return "Task{" + "id=" + super.getId() +
               ", name=" + super.getName() +
               ", recurrenceId=" + recurrenceID +
               ", myDay=" + myDay +
               ", dueDate=" + dueDate +
               ", remind=" + remind +
               ", completed=" + completed +
               ", important=" + important +
               ", listId=" + listID +
               '}';
    }

}
