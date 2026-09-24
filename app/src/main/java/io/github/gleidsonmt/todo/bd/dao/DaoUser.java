

package io.github.gleidsonmt.todo.bd.dao;

import io.github.gleidsonmt.todo.bd.dao.internal.AbstractDao;
import io.github.gleidsonmt.todo.model.User;
import io.github.gleidsonmt.todo.model.Usernew;
import org.jetbrains.annotations.NotNull;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

/**
 * @author Gleidson Neves da Silveira | <a href="mailto:gleidisonmt@gmail.com">gleidisonmt@gmail.com</a> <br>
 * Create on 04/03/2024
 */
public final class DaoUser extends AbstractDao<User> {

    @Override
    protected User createElement(@NotNull ResultSet result) throws SQLException {

//        Usernew item = new Usernew(result.getLong("id"), result.getString("name"));
//        item.setUsername(result.getString("username"));
//        item.setSalt(result.getBytes("salt"));
//        item.setPassword(result.getString("password"));
//        item.setImageUrl(result.getString("image_url"));
        return new User(
                result.getLong("id"),
                result.getString("name"),
                result.getString("username"),
                result.getString("password"),
                result.getBytes("salt"),
                result.getString("image_url"),
                false
        );
    }

    @Override
    protected void prepareElement(PreparedStatement prepare, @NotNull User model) {

        String pass;
        byte[] salt;

        salt = createSalt();
        pass = createSecurePassword(model.password(), salt);

        try {
            prepare.setString(1, model.name());
            prepare.setString(2, model.username());
            prepare.setString(3, pass);
            prepare.setBytes(4, salt);
            prepare.setString(5, model.imageUrl());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean validatePassword(@NotNull Usernew user, String compare) {
        String one = user.getPassword();
        String two = createSecurePassword(compare, user.getSalt());
        return one.equals(two);
    }

    private String createSecurePassword(@NotNull String password, byte[] salt) {
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt);
            byte[] bytes = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte aByte : bytes) {
                sb.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            Logger.getGlobal().severe("Error on creating password: " + e.getMessage());
        }
        return generatedPassword;

    }

    private byte @NotNull [] createSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;
    }

}
