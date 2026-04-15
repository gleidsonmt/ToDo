package io.github.gleidsonmt.todo.utils;

import java.util.Objects;

import io.github.gleidsonmt.todo.App;
import javafx.scene.image.Image;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on 19/02/2025
 */
public class Assets {

    public static String getCss(String name) {
        return Objects.requireNonNull(App.class.getResource("css/" + name)).toExternalForm();
    }

    public static Image getImage(String name) {
        return new Image(Objects.requireNonNull(App.class.getResource("img/" + name)).toExternalForm(), -1, -1, true,
                true);
    }

    public static Image getImage(String name, int size) {
        return new Image(Objects.requireNonNull(App.class.getResource("img/" + name)).toExternalForm(), size, size,
                true, true);
    }

}
