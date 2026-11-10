package io.github.gleidsonmt.todo.view_model.converter;

import io.github.gleidsonmt.todo.utils.I18n;
import javafx.util.StringConverter;

/**
 * @author Gleidson Neves da Silveira | <a href="mailto:gleidisonmt@gmail.com">gleidisonmt@gmail.com</a> <br>
 * Created on 27/09/2026
 */
public class ListNameConverter extends StringConverter<String> {
    @Override
    public String toString(String object) {
        return object == null ? "" : (object.contains("drawer.list")) ? I18n.get(object) : object;
    }

    @Override
    public String fromString(String string) {
        return "";
    }
}
