package io.github.gleidsonmt.todo.view.panel.items;

import java.time.LocalDate;
import java.util.Optional;

import io.github.gleidsonmt.todo.global.Presenter;
import io.github.gleidsonmt.todo.global.Repository;
import io.github.gleidsonmt.todo.model.ToDoTask;
import io.github.gleidsonmt.todo.view_model.Model;
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
public class TaskItemViewModel extends Model {

    private final Repository repository;
    private final Presenter<ToDoTask> presenter;

    private final TaskViewModelConverter converter = new TaskViewModelConverter();

    private final BooleanProperty completed;
    private final BooleanProperty important;
    private final BooleanProperty myDay;
    private final ObjectProperty<LocalDate> dueDate;
    private final LongProperty listId;

    private final TaskItem taskItem;

    public TaskItemViewModel(ToDoTask task, TaskItem taskItem) {
        this.taskItem = taskItem;
        this.setId(task.getId());
        this.repository = (Repository) System.getProperties().get("repository");
        this.presenter = this.repository.<ToDoTask>of(ToDoTask.class);
        this.completed = new SimpleBooleanProperty(task.isCompleted());
        this.important = new SimpleBooleanProperty(task.isImportant());
        this.myDay = new SimpleBooleanProperty(task.isMyDay());
        this.dueDate = new SimpleObjectProperty<>(task.getDueDate());
        this.listId = new SimpleLongProperty(task.getListId());
        bind();
        registerListeners();
    }

    private void bind() {
        this.nameProperty().bindBidirectional(taskItem.nameProperty());
        this.important.bindBidirectional(taskItem.favoriteProperty());
        this.completed.bindBidirectional(taskItem.completedProperty());
    }

    private void registerListeners() {
        this.important.addListener((_, _, _) -> {
            taskItem.onImportantChange().handle(this);
        });

        this.completed.addListener((_, _, _) -> {
            taskItem.onCompletedChange().handle(this);
        });
    }

    public void save() {
        presenter.getData().add(converter.convert(this));
    }

    public void update() {
        var temp = converter.convert(this);
        var finded = find(temp);
        if (finded.isPresent()) {
            var index = presenter.getData().indexOf(finded.get());
            presenter.getData().set(index, temp);
        }
    }

    public void delete() {
        var temp = converter.convert(this);
        var finded = find(temp);
        if (finded.isPresent()) {
            presenter.getData().remove(finded.get());
        }
    }

    public void setMyDay(boolean myDay) {
        this.myDay.set(myDay);
    }

    public boolean isMyDay() {
        return this.myDay.get();
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

    public long getListId() {
        return this.listId.get();
    }

    private Optional<ToDoTask> find(ToDoTask task) {
        return presenter.getData().stream().filter(el -> el.getId() == task.getId()).findAny();
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Task{");
        sb.append("id=").append(super.getId());
        sb.append(", name=").append(super.getName());
        sb.append(", myDay=").append(isMyDay());
        sb.append(", important=").append(isImportant());
        sb.append('}');
        return sb.toString();
    }
}
