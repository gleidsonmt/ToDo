

package io.github.gleidsonmt.todo.view_model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import io.github.gleidsonmt.todo.bd.dao.DaoRecurrence;
import io.github.gleidsonmt.todo.bd.dao.DaoTask;
import io.github.gleidsonmt.todo.global.Global;
import io.github.gleidsonmt.todo.global.TaskPresenter;
import io.github.gleidsonmt.todo.model.ToDoTask;
import io.github.gleidsonmt.todo.model.recurrence.Recurrence;
import io.github.gleidsonmt.todo.view_model.map.TaskMapper;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 * Description:
 *
 * @author Gleidson Neves da Silveira | <a href="mailto:gleidisonmt@gmail.com">gleidisonmt@gmail.com</a> <br>
 * Created On: Mar 09, 2026
 * <p>
 * Version History: Initial version
 */
public class TaskViewModel extends ViewModel {
    // Model actions
    private final TaskMapper converter = TaskMapper.INSTANCE;
    private final DaoTask dao = new DaoTask();
    private final DaoRecurrence daoRecurrence = new DaoRecurrence();

    // View actions
    private final BooleanProperty completed = new SimpleBooleanProperty();;
    private final BooleanProperty important = new SimpleBooleanProperty();
    private final ObjectProperty<LocalDateTime> remind = new SimpleObjectProperty<>();
    private final BooleanProperty myDay= new SimpleBooleanProperty();
    private final ObjectProperty<LocalDate> dueDate = new SimpleObjectProperty<>();
    private final LongProperty listID = new SimpleLongProperty();
    private final ObjectProperty<LocalDate> createdAt = new SimpleObjectProperty<>();


    public TaskViewModel save() {
        var converted = converter.toDTO(this);
        this.setId(dao.store(converted));
        return this;
    }

    public void storeRecurrence(Recurrence rec) {
        daoRecurrence.store(rec);
    }

    public void update() {
        // commit in db
        var item = converter.toDTO(this);
        dao.update(item);
    }

    public void delete() {
        var item = converter.toDTO(this);
        dao.delete(item);
    }

    public ObjectProperty<LocalDateTime> remindProperty() {
        return this.remind;
    }

    public void setRemind(LocalDateTime remind) {
        this.remind.set(remind);
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt.set(createdAt);
    }

    public LocalDateTime getRemind() {
        return this.remind.get();
    }

    public void setMyDay(boolean myDay) {
        this.myDay.set(myDay);
    }

    public boolean isMyDay() {
        return this.myDay.get();
    }

    public BooleanProperty myDayProperty() {
        return this.myDay;
    }

    public BooleanProperty completedProperty() {
        return this.completed;
    }

    public BooleanProperty importantProperty() {
        return this.important;
    }

    public boolean isCompleted() {
        return this.completed.get();
    }

    public void setCompleted(boolean val) {
        this.completed.set(val);
    }

    public boolean isImportant() {
        return this.important.get();
    }

    public void setImportant(boolean val) {
        this.important.set(val);
    }

    public void setDueDate(LocalDate due) {
        this.dueDate.set(due);
    }

    public LocalDate getDueDate() {
        return this.dueDate.get();
    }

    public ObjectProperty<LocalDate> dueDateProperty() {
        return this.dueDate;
    }

    public LocalDate getCreatedAt() {
        return this.createdAt.get();
    }

    public Long getListID() {
        return this.listID.get();
    }

    public LongProperty listIDProperty() {
        return this.listID;
    }

    public void setListID(long id) {
        this.listID.set(id);
    }

    @Override
    public String toString() {
        return "Task{" + "id=" + super.getId() +
               ", name=" + super.getName() +
               ", myDay=" + myDay.get() +
               ", important=" + important.get() +
               ", completed=" + isCompleted() +
               ", dueDate=" + dueDate.get() +
               ", createdAt=" + createdAt.get() +
               ", listID=" + listID.get() +
               '}';
    }
}