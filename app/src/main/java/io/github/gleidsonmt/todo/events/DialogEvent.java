

package io.github.gleidsonmt.todo.events;

import io.github.gleidsonmt.glad.base.Root;
import io.github.gleidsonmt.glad.theme.Css;
import io.github.gleidsonmt.glad.theme.Font;
import io.github.gleidsonmt.glad.theme.ThemeProvider;
import io.github.gleidsonmt.todo.utils.Assets;
import javafx.event.Event;
import javafx.event.EventType;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author Gleidson Neves da Silveira | <a href="mailto:gleidisonmt@gmail.com">gleidisonmt@gmail.com</a> <br> <br>
 * Created on  11/05/2026
 */
public class DialogEvent extends Event {

    public static final EventType<DialogEvent> DIALOG_EVENT = new EventType<>(Event.ANY, "DIALOG_EVENT");
    public static final EventType<DialogEvent> DIALOG_ERROR = new EventType<>(DIALOG_EVENT, "DIALOG_ERROR");

    private final String message;
    private final String title;

    public DialogEvent(EventType<? extends Event> eventType, String title, String message) {
        super(eventType);
        this.message = message;
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public String getTitle() {
        return title;
    }
}
