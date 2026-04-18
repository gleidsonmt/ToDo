package io.github.gleidsonmt.todo.model;

import io.github.gleidsonmt.todo.bd.dao.internal.Ignore;
import io.github.gleidsonmt.todo.global.Folder;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on 16/10/2024
 */
@SuppressWarnings("unused")
public class User extends Entity {

    private final StringProperty password = new SimpleStringProperty();
    private final StringProperty username = new SimpleStringProperty();
    private final BooleanProperty logged = new SimpleBooleanProperty();
    private final BooleanProperty remember = new SimpleBooleanProperty();
    private byte[] salt;
    private final StringProperty imageUrl = new SimpleStringProperty();
    @Ignore
    private final ObjectProperty<Image> avatar = new SimpleObjectProperty<>();
    @Ignore
    private boolean needChangePass = false;
    @Ignore
    private final Folder folder;

    public User(long id, String name) {
        this(id, name, null, null);
    }

    public User(long id, String name, String lastName, String email) {
        super(id, name);
        folder = new Folder();
        logged.set(false);

        // this.setAvatar(this.getImageUrl());

        // this.imageUrl.addListener((observableValue, oldValue, newValue) -> {
        ////            setAvatar(newValue);
        // System.out.println("observableValue = " + observableValue);
        // });
    }

    private void setAvatar(String url) {
        // if (folder.has(url)) {
        // this.avatar.set(folder.getAvatar(url, 100));
        // } else {
        // this.avatar.set(new
        // Image(Objects.requireNonNull(App.class.getResource("img/default_avatar.jpg")).toExternalForm()));
        // }
    }

    public String getPassword() {
        return password.get();
    }

    public StringProperty passwordProperty() {
        return password;
    }

    public void setPassword(String password) {
        this.password.set(password);
    }

    public String getUsername() {
        return username.get();
    }

    public StringProperty usernameProperty() {
        return username;
    }

    public void setUsername(String username) {
        this.username.set(username);
    }

    public boolean isLogged() {
        return logged.get();
    }

    public BooleanProperty loggedProperty() {
        return logged;
    }

    public void setLogged(boolean logged) {
        this.logged.set(logged);
    }

    public boolean isRemember() {
        return remember.get();
    }

    public BooleanProperty rememberProperty() {
        return remember;
    }

    public void setRemember(boolean remember) {
        this.remember.set(remember);
    }

    public byte[] getSalt() {
        return salt;
    }

    public void setSalt(byte[] salt) {
        this.salt = salt;
    }

    public String getImageUrl() {
        return imageUrl.get();
    }

    public StringProperty imageUrlProperty() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl.set(imageUrl);
    }

    public Image getAvatar() {
        return avatar.get();
    }

    public ObjectProperty<Image> avatarProperty() {
        return avatar;
    }

    public void setAvatar(Image image) {
        this.avatar.set(image);
    }

    public boolean isNeedChangePass() {
        return needChangePass;
    }

    public void setNeedChangePass(boolean needsChangePass) {
        this.needChangePass = needsChangePass;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("User{");
        sb.append("username=").append(username);
        sb.append(", logged=").append(logged);
        sb.append(", remember=").append(remember);
        sb.append(", imageUrl=").append(imageUrl);
        sb.append('}');
        return sb.toString();
    }
}
