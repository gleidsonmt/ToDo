package io.github.gleidsonmt.todo.model;
import org.jspecify.annotations.NonNull;

import java.util.Arrays;

/**
 * @author Gleidson Neves da Silveira | <a href="mailto:gleidisonmt@gmail.com">gleidisonmt@gmail.com</a> <br>
 * Created on 24/09/2026
 */
public record User(long id, String name, String username, String password, byte[] salt,
                   boolean logged) implements DTO {

    @Override
    public @NonNull String toString() {
        return "{\"User\":"
               + ", \"id\":\"" + id + "\""
               + ", \"username\":\"" + username + "\""
               + ", \"password\":\"" + password + "\""
               + ", \"salt\":" + Arrays.toString(salt)
               + ", \"logged\":\"" + logged + "\""
               + "}";
    }
}
