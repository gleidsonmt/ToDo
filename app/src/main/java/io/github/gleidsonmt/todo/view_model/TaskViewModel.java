package io.github.gleidsonmt.todo.view_model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import io.github.gleidsonmt.todo.global.Global;
import io.github.gleidsonmt.todo.global.TaskPresenter;
import io.github.gleidsonmt.todo.model.ToDoTask;
import io.github.gleidsonmt.todo.model.recurrence.Recurrence;
import io.github.gleidsonmt.todo.view_model.converter.TaskViewModelConverter;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 * Description:
 *
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Created On: Mar 09, 2026
 * <p>
 * Version History: Initial version
 */
public class TaskViewModel extends ViewModel {

    private final TaskPresenter presenter;

    private final TaskViewModelConverter converter = new TaskViewModelConverter();

    private final BooleanProperty completed;
    private final BooleanProperty important;
    private final ObjectProperty<LocalDateTime> remind;
    private final BooleanProperty myDay;
    private final ObjectProperty<LocalDate> dueDate;
    private final LongProperty listId;
    private final ObjectProperty<LocalDate> createdAt;

    public TaskViewModel() {
        this(null);
    }

    public TaskViewModel(ToDoTask task) {
        this.setId(task != null ? task.getId() : 0);

        this.presenter = (TaskPresenter) Global.get(ToDoTask.class);
//        task != null ?  : ""
        this.setName(task != null ? task.getName() : "");
        this.completed = new SimpleBooleanProperty(task != null && task.isCompleted());
        this.important = new SimpleBooleanProperty(task != null && task.isImportant());

        this.myDay = new SimpleBooleanProperty(task != null && task.isMyDay());

        this.dueDate = new SimpleObjectProperty<>(task != null ? task.getDueDate() : null);
        this.remind = new SimpleObjectProperty<>(task != null ? task.getRemind() : null);
        this.createdAt = new SimpleObjectProperty<>(task != null ? task.getCreatedAt() : null);

        this.listId = new SimpleLongProperty(task != null ? task.getListId() : 0);

    }

    public void save() {
        var converted = converter.toModel(this);
        this.setId(presenter.store(converted));
    }

    public void storeRecurrence(Recurrence rec) {
        presenter.storeRecurrence(rec);
    }

    public void update() {
        // commit in db
        var item = converter.toModel(this);
        presenter.update(item);
    }

    public void delete() {
        var item = converter.toModel(this);
        presenter.delete(item);
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

    public Long getListId() {
        return this.listId.get();
    }

    public LongProperty listIdProperty() {
        return this.listId;
    }

    public void setListId(long id) {
        this.listId.set(id);
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
               ", listId=" + listId.get() +
               '}';
    }
}