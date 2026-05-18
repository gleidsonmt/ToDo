

package io.github.gleidsonmt.todo.view.panel.events;

import io.github.gleidsonmt.todo.view_model.TaskViewModel;
import javafx.beans.NamedArg;
import javafx.event.Event;
import javafx.event.EventTarget;
import javafx.event.EventType;

/**
 * Description:
 *
 * @author Gleidson Neves da Silveira | <a href="mailto:gleidisonmt@gmail.com">gleidisonmt@gmail.com</a>
 * Created On: Apr 07, 2026
 * <p>
 * Version History: Initial version
 */
public class TaskChangeEvent extends Event {

    public static final EventType<TaskChangeEvent> ALL = new EventType<>(Event.ANY, "TASK_CHANGE");

//    public static final EventType<TaskChangeEvent> MOVED = new EventType<>(Event.ANY, "MOVED");
//    public static final EventType<TaskChangeEvent> MY_DAY_CHANGED = new EventType<>(Event.ANY, "MY_DAY_CHANGED");
//    public static final EventType<TaskChangeEvent> ADD = new EventType<>(Event.ANY, "ADD");
//    public static final EventType<TaskChangeEvent> FAVORITE_CHANGED = new EventType<>(Event.ANY, "FAVORITE_CHANGED");
//    public static final EventType<TaskChangeEvent> COMPLETE = new EventType<>(Event.ANY, "COMPLETE");
//    public static final EventType<TaskChangeEvent> DELETE_TASK = new EventType<>(Event.ANY, "DELETE_TASK");

    public static final EventType<TaskChangeEvent> MOVED = new EventType<>(ALL, "MOVED");
    public static final EventType<TaskChangeEvent> MY_DAY_CHANGED = new EventType<>(ALL, "MY_DAY_CHANGED");
    public static final EventType<TaskChangeEvent> ADD = new EventType<>(ALL, "ADD");
    public static final EventType<TaskChangeEvent> FAVORITE_CHANGED = new EventType<>(ALL, "FAVORITE_CHANGED");
    public static final EventType<TaskChangeEvent> COMPLETE = new EventType<>(ALL, "COMPLETE");
    public static final EventType<TaskChangeEvent> DELETE_TASK = new EventType<>(ALL, "DELETE_TASK");


    private final long idActual;
    private final long idPrevious;
    private final TaskViewModel model;
    // public static final EventType<TaskChangeEvent> LOGIN_FAILED = new
    // EventType<>(ANY, "LOGIN_FAILED");

    public TaskChangeEvent(EventType<? extends Event> eventType, TaskViewModel model) {
        super(eventType);
        this.idActual = model.getId();
        this.idPrevious = model.getListId();
        this.model = model;
    }

    public TaskChangeEvent(final @NamedArg("source") Object source,
                           final @NamedArg("target") EventTarget target,
                           EventType<? extends Event> eventType, TaskViewModel model) {
        super(source, target, eventType);
        this.idActual = model.getId();
        this.idPrevious = model.getListId();
        this.model = model;
    }

    public TaskChangeEvent(EventType<? extends Event> eventType, TaskViewModel model, long previous, long actual) {
        super(eventType);
        this.idActual = actual;
        this.idPrevious = previous;
        this.model = model;
    }

    public long getActual() {
        return this.idActual;
    }

    public long getPrevious() {
        return this.idPrevious;
    }

    public TaskViewModel getModel() {
        return this.model;
    }

}
