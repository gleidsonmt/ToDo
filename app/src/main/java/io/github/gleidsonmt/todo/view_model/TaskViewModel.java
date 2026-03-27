package io.github.gleidsonmt.todo.view_model;

import java.time.LocalDate;
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
    private final BooleanProperty myDay;
    private final ObjectProperty<LocalDate> dueDate;
    private final LongProperty listId;

    // private final TaskItem taskItem;
    private final ToDoTask task;

    public TaskViewModel(ToDoTask task) {
        this.task = task;
        this.setId(task.getId());

        this.presenter = (TaskPresenter) Global.get(ToDoTask.class);

        this.setName(task.getName());
        this.completed = new SimpleBooleanProperty(task.isCompleted());
        this.important = new SimpleBooleanProperty(task.isImportant());

        this.myDay = new SimpleBooleanProperty(task.isMyDay());
        this.dueDate = new SimpleObjectProperty<>(task.getDueDate());

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
        this.important.addListener((_, _, _) -> {
            // taskItem.onImportantChange().handle(this);
        });

        // this.completed.addListener((_, _, _) -> {
        // taskItem.onCompletedChange().handle(this);
        // });

        // this.listId.addListener((_, _, newVal) -> {
        // System.out.println(taskItem.getP);
        // if (newVal) {
        // SideNavNew drawer = (SideNavNew)
        // this.taskItem.getScene().lookup("#drawer");
        // this.update();
        // drawer.getSelected().updateNotifications();
        // CustomDrawerItemNew drawerItem = drawer.get(this);
        // drawerItem.updateNotifications();

        // }
        // });
        // updateCount();
    }

    public ToDoTask save() {
        var converted = converter.convert(this);
        presenter.store(converted);
        this.setId(converted.getId());
        return converted;
    }

    public void update() {
        // commit in db
        var item = converter.convert(this);
        presenter.update(item);
        // ListRootNew listRoot = (ListRootNew)
        // taskItem.getScene().lookup("#list-root");
        // listRoot.getData().removeIf(el -> el.getId() == item.getId());
        // updateCount();
    }

    public void delete() {
        var item = converter.convert(this);
        // ListRootNew listRoot = (ListRootNew)
        // taskItem.getScene().lookup("#list-root");
        // listRoot.getData().removeIf(el -> el.getId() == item.getId());
        // listRoot.getContainer().remove(taskItem);
        presenter.delete(item);
        // updateCount();
    }

    private void updateCount() {
        // SideNavNew nav = (SideNavNew) taskItem.getScene().lookup("#drawer");
        // nav.getSelected().updateNotifications();
    }

    public void setMyDay(boolean myDay) {
        this.myDay.set(myDay);
    }

    public boolean isMyDay() {
        return this.myDay.get();
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

    public Long getListId() {
        return this.listId.get();
    }

    public void setListId(long id) {
        this.listId.set(id);
    }

    private Optional<ToDoTask> find(ToDoTask task) {
        return presenter.getData().stream().filter(el -> el.getId() == task.getId()).findAny();
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Task{");
        sb.append("id=").append(super.getId());
        sb.append(", name=").append(super.getName());
        sb.append(", completed=").append(isCompleted());
        sb.append(", listId=").append(listId);
        sb.append('}');
        return sb.toString();
    }
}