

package io.github.gleidsonmt.todo.view_model;

import io.github.gleidsonmt.todo.bd.dao.DaoRecurrence;
import io.github.gleidsonmt.todo.bd.dao.DaoTask;
import io.github.gleidsonmt.todo.model.DTO;
import io.github.gleidsonmt.todo.model.ToDoTask;
import io.github.gleidsonmt.todo.model.recurrence.Recurrence;
import io.github.gleidsonmt.todo.view_model.map.TaskMapper;
import javafx.beans.property.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
    private final DaoRecurrence daoRecurrence = new DaoRecurrence();
    private final DaoTask dao = new DaoTask();

    // View actions
    private final BooleanProperty completed = new SimpleBooleanProperty();;
    private final BooleanProperty important = new SimpleBooleanProperty();
    private final ObjectProperty<LocalDateTime> remind = new SimpleObjectProperty<>();
    private final BooleanProperty myDay = new SimpleBooleanProperty();
    private final ObjectProperty<LocalDate> dueDate = new SimpleObjectProperty<>();
    private final LongProperty listID = new SimpleLongProperty();
    private final ObjectProperty<LocalDate> createdAt = new SimpleObjectProperty<>();
    private String note;


    public TaskViewModel() {
    }

    public void save() {
        var converted = convert();
        this.setId(dao.store(converted));
    }

    public void storeRecurrence(Recurrence rec) {
        daoRecurrence.store(rec);
    }

    // commit in db
    public void update() {
        dao.update(convert());
    }

    // delete item in db
    public void delete() {
        var item = converter.toDTO(this);
        dao.delete(item);
    }

    public TaskMapper getConverter() {
        return converter;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
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

    public ToDoTask convert() {
        return converter.toDTO(this);
    }

    public void dispose() {
        this.nameProperty().unbind();
        this.dueDateProperty().unbind();
        this.remindProperty().unbind();
        this.myDayProperty().unbind();
        this.completedProperty().unbind();
        this.importantProperty().unbind();
        this.listIDProperty().unbind();
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
               ", note=" + note +
               ", listID=" + listID.get() +
               '}';
    }

}