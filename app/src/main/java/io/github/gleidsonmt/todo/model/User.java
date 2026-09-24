package io.github.gleidsonmt.todo.model;

import java.util.Arrays;

/**
 * @author Gleidson Neves da Silveira | <a href="mailto:gleidisonmt@gmail.com">gleidisonmt@gmail.com</a> <br>
 * Created on 24/09/2026
 */
public record User(long id, String name, String username, String password, byte[] salt, String imageUrl,
                   boolean logged) implements Modle {

    @Override
    public String toString() {
        return "{\"UserTest\":"
               + ", \"id\":\"" + id + "\""
               + ", \"username\":\"" + username + "\""
               + ", \"password\":\"" + password + "\""
               + ", \"salt\":" + Arrays.toString(salt)
               + ", \"imageUrl\":\"" + imageUrl + "\""
               + ", \"logged\":\"" + logged + "\""
               + "}";
    }
}
