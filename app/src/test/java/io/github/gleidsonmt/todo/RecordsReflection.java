package io.github.gleidsonmt.todo;

import io.github.gleidsonmt.todo.model.User;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.RecordComponent;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Gleidson Neves da Silveira | <a href="mailto:gleidisonmt@gmail.com">gleidisonmt@gmail.com</a> <br>
 * Created on 24/09/2026
 */
public class RecordsReflection {

    @Test
    public void test() {
        User user = new User(
                1,
                "Gleidson",
                "gleidisonmt@gmail.com",
                "radar",
                null,
                "logon.png",
                true
        );

        var fields = new ArrayList<Field>();
        RecordComponent[] components = User.class.getRecordComponents();
        for (var comp : components) {
            try {
                Field field = user.getClass()
                        .getDeclaredField(comp.getName());
                field.setAccessible(true);
                fields.add(field);
            } catch (NoSuchFieldException e) {
                // for simplicity, error handling is skipped
            }
        }

        assertEquals(3, fields.size());

        var nameField = fields.get(0);
        var ageField = fields.get(1);
        var scoreField = fields.get(2);
        try {
            assertEquals("name", nameField.getName());
            assertEquals(String.class, nameField.getType());
            assertEquals("Eric", nameField.get(user));
//
//            assertEquals("age", ageField.getName());
//            assertEquals(int.class, ageField.getType());
//            assertEquals(28, ageField.get(user));
//
//            assertEquals("score", scoreField.getName());
//            assertEquals(Long.class, scoreField.getType());
//            assertEquals(4242L, scoreField.get(user));
        } catch (IllegalAccessException exception) {
            // for simplicity, error handling is skipped
        }
    }
}
