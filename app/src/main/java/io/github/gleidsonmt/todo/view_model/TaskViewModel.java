package io.github.gleidsonmt.todo.view_model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import io.github.gleidsonmt.todo.global.Global;
import io.github.gleidsonmt.todo.global.TaskPresenter;
import io.github.gleidsonmt.todo.model.ToDoTask;
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
 *         Created On: Mar 09, 2026
 * 
 *         Version History: Initial version
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

    // private final TaskItem taskItem;

    public TaskViewModel(ToDoTask task) {
        this.setId(task.getId());

        this.presenter = (TaskPresenter) Global.get(ToDoTask.class);

        this.setName(task.getName());
        this.completed = new SimpleBooleanProperty(task.isCompleted());
        this.important = new SimpleBooleanProperty(task.isImportant());

        this.myDay = new SimpleBooleanProperty(task.isMyDay());
        this.dueDate = new SimpleObjectProperty<>(task.getDueDate());
        this.remind = new SimpleObjectProperty<>(task.getRemind());
        this.createdAt = new SimpleObjectProperty<>(task.getCreatedAt());

        this.listId = new SimpleLongProperty(task.getListId());

        bind();
        registerListeners();
    }

    private void bind() {

        // this.nameProperty().bindBidirectional(taskItem.nameProperty());
        // this.important.bindBidirectional(taskItem.favoriteProperty());
        // this.completed.bindBidirectional(taskItem.completedProperty());

    }

    private void registerListeners() {

    }

    public ToDoTask save() {
        var converted = converter.convert(this);
        this.setId(presenter.store(converted));
        return converted;
    }

    public void update() {
        // commit in db
        var item = converter.convert(this);
        presenter.update(item);
    }

    public void delete() {
        var item = converter.convert(this);
        presenter.delete(item);
    }

    public ObjectProperty<LocalDateTime> remindProperty() {
        return this.remind;
    }

    public void setRemind(LocalDateTime remind) {
        this.remind.set(remind);
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

    @Deprecated
    private Optional<ToDoTask> find(ToDoTask task) {
        return presenter.getData().stream().filter(el -> el.getId() == task.getId()).findAny();
    }

    @Override
    public String toString() {
        return "Task{" + "id=" + super.getId() +
               ", name=" + super.getName() +
               ", myDay=" + myDay.get() +
               ", important=" + important.get() +
               ", completed=" + isCompleted() +
               ", dueDate=" + dueDate.get() +
               ", listId=" + listId.get() +
               '}';
    }
}