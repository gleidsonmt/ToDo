/*
 * *
 *  * Description:
 *  *
 *  * @author Gleidson Neves da Silveira | <a href="mailto:gleidisonmt@gmail.com">gleidisonmt@gmail.com</a> <br>
 *  * Create on ${DATE}
 *
 */

package io.github.gleidsonmt.todo.view_model;

import io.github.gleidsonmt.todo.model.Usernew;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Description:
 *
 * @author Gleidson Neves da Silveira | <a href="mailto:gleidisonmt@gmail.com">gleidisonmt@gmail.com</a> <br>
 * Create on 28/05/2026
 */
public class UserViewModel extends ViewModel {

    private final StringProperty username = new SimpleStringProperty(this, "username");
    private final StringProperty password = new SimpleStringProperty(this, "password");
    private final StringProperty imageUrl = new SimpleStringProperty(this, "imageUrl");

    private final UserModel model;

    public UserViewModel() {
        this.model = new UserModel();
    }

    public boolean save() {
        if (!this.model.hasUsername(this.getUsername())) {
            model.save(this);
            return true;
        } else {
            return false;
        }
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

    public final String getImageUrl() {
        return imageUrl.get();
    }

    public final void setImageUrl(String value) {
        imageUrl.set(value);
    }

    public final StringProperty imageUrlProperty() {
        return imageUrl;
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

    @Override
    public String toString() {
        return "{\"UserViewModel\":"
               + super.toString()
               + ", \"imageUrl\":" + imageUrl.get()
               + ", \"username\":" + username.get()
               + ", \"password\":" + password.get()
               + "}";
    }
}
