package io.github.gleidsonmt.todo.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Description:
 *
 * @author Gleidson Neves da Silveira | <a href="mailto:gleidisonmt@gmail.com">gleidisonmt@gmail.com</a> <br>
 * Create on 20/05/2026
 */
public class Usernew extends Entity{

    private byte[] salt;
    private String password;
    private String username;

    public Usernew(long id, String name) {
        super(id, name);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public byte[] getSalt() {
        return salt;
    }

    public void setSalt(byte[] salt) {
        this.salt = salt;
    }

    @Override
    public String toString() {
        return "{\"Usernew\":"
               + super.toString()
               + ", \"username\":\"" + username + "\""
               + ", \"password\":\"" + password + "\""
               + "}";
    }
}
