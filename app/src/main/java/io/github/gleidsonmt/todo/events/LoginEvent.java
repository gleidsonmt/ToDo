

package io.github.gleidsonmt.todo.events;

import io.github.gleidsonmt.todo.bd.dao.DaoUser;
import io.github.gleidsonmt.todo.model.User;
import io.github.gleidsonmt.todo.view_model.UserViewModel;
import io.github.gleidsonmt.todo.view_model.map.UserMapper;
import javafx.event.Event;
import javafx.event.EventType;

/**
 *
 * @author Gleidson Neves da Silveira | <a href="mailto:gleidisonmt@gmail.com">gleidisonmt@gmail.com</a> <br> <br>
 * Created on  30/04/2026
 */
public class LoginEvent extends Event {

    public static final EventType<LoginEvent> LOGIN_EVENT = new EventType<>(Event.ANY, "LOGIN_EVENT");
    public static final EventType<LoginEvent> LOGIN = new EventType<>(LOGIN_EVENT, "LOGIN");
    public static final EventType<LoginEvent> LOGIN_FAILED = new EventType<>(LOGIN_EVENT, "LOGIN_FAILED");
    public static final EventType<LoginEvent> LOGIN_SUCCESS = new EventType<>(LOGIN_EVENT, "LOGIN_SUCCESS");
    public static final EventType<LoginEvent> LOGOUT = new EventType<>(LOGIN_EVENT, "LOGOUT");

    private UserViewModel user = new UserViewModel();

    public LoginEvent(EventType<? extends Event> eventType, long id) {
        super(eventType);
        if (id == 0) return;
        var dto = new DaoUser().get(id);
        if (dto.isEmpty()) return;
        UserMapper.INSTANCE.toViewModel(dto.get(), user);
    }

    public LoginEvent(EventType<? extends Event> eventType, User user) {
        super(eventType);
        UserMapper.INSTANCE.toViewModel(user, this.user);
    }

    public LoginEvent(EventType<? extends Event> eventType, UserViewModel user) {
        super(eventType);
        this.user = user;
    }

    public UserViewModel getUser() {
        return user;
    }
}
