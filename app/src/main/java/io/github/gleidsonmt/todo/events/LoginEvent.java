

package io.github.gleidsonmt.todo.events;

import io.github.gleidsonmt.todo.view.panel.events.TaskChangeEvent;
import javafx.event.Event;
import javafx.event.EventType;

/**
 *
 * @author Gleidson Neves da Silveira | <a href="mailto:gleidisonmt@gmail.com">gleidisonmt@gmail.com</a> <br>
 * Created on  30/04/2026
 */
public class LoginEvent extends Event {

    public static final EventType<LoginEvent> LOGIN_EVENT = new EventType<>(Event.ANY, "LOGIN_EVENT");
    public static final EventType<LoginEvent> LOGIN = new EventType<>(LOGIN_EVENT, "LOGIN");
    public static final EventType<LoginEvent> LOGOUT = new EventType<>(LOGIN_EVENT, "LOGOUT");

    public LoginEvent(EventType<? extends Event> eventType) {
        super(eventType);
    }
}
