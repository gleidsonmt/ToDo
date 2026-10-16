/*
 * *
 *  * Description:
 *  *
 *  * @author Gleidson Neves da Silveira | <a href="mailto:gleidisonmt@gmail.com">gleidisonmt@gmail.com</a> <br>
 *  * Create on ${DATE}
 *
 */

package io.github.gleidsonmt.todo.view_model;

import io.github.gleidsonmt.todo.bd.dao.DaoUser;
import io.github.gleidsonmt.todo.utils.Assets;
import io.github.gleidsonmt.todo.view_model.map.TaskMapper;
import io.github.gleidsonmt.todo.view_model.map.UserMapper;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;

import java.io.File;

/**
 * Description:
 *
 * @author Gleidson Neves da Silveira | <a href="mailto:gleidisonmt@gmail.com">gleidisonmt@gmail.com</a> <br>
 * Create on 28/05/2026
 */
public class UserViewModel extends ViewModel {

    private final UserMapper converter = UserMapper.INSTANCE;
    private final DaoUser dao = new DaoUser();

    private final StringProperty username = new SimpleStringProperty(this, "username");
    private final StringProperty password = new SimpleStringProperty(this, "password");
    private final BooleanProperty logged = new SimpleBooleanProperty(this, "logged");



    public void update() {
        dao.update(converter.toDTO(this));
    }

    public boolean save() {
        var converted = converter.toDTO(this);
        this.setId(dao.store(converted));
        return this.id.get() != 0;
    }

    public final String getUsername() {
        return username.get();
    }

    public final void setUsername(String value) {
        username.set(value);
    }

    public final StringProperty usernameProperty() {
        return username;
    }

    public final String getPassword() {
        return password.get();
    }

    public final void setPassword(String value) {
        this.password.set(value);
    }

    public final StringProperty passwordProperty() {
        return password;
    }

    public boolean isLogged() {
        return logged.get();
    }

    public void setLogged(boolean logged) {
        this.logged.set(logged);
    }

    public Image getAvatar() {
        return getAvatar(60);
    }

    public Image getAvatar(int size) {
        File file = new File("./avatars/" + this.getUsername().replace(" ", "") + ".png");
        if (!file.exists()) {
            return Assets.getImage("default_avatar.jpg", size);
        } else {
            return new Image(String.valueOf(file.toURI()));
        }
    }

    @Override
    public String toString() {
        return "{\"UserViewModel\":"
               + ", \"id\":" + super.getId()
               + ", \"name\":" + super.getName()
               + ", \"username\":" + username.get()
               + ", \"password\":" + password.get()
               + "}";
    }
}