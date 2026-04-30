package io.github.gleidsonmt.todo.events;

import io.github.gleidsonmt.todo.view.panel.events.TaskChangeEvent;
import javafx.event.Event;
import javafx.event.EventType;

/**
 *
 * @author Gleidson Neves da Silveira | <gleidisonmt@gmail.com>
 * Created on  30/04/2026
 */
public class LoginEvent extends Event {

    public static final EventType<TaskChangeEvent> ALL = new EventType<>(Event.ANY, "LOGIN_EVENT");
    public static final EventType<TaskChangeEvent> LOGIN = new EventType<>(ALL, "LOGIN");
    public static final EventType<TaskChangeEvent> LOGOUT = new EventType<>(ALL, "LOGOUT");

    public LoginEvent(EventType<? extends Event> eventType) {
        super(eventType);
    }
}
