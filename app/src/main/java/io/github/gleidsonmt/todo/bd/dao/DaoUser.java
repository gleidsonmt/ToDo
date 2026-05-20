

package io.github.gleidsonmt.todo.bd.dao;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import io.github.gleidsonmt.todo.model.Usernew;
import org.jetbrains.annotations.NotNull;

import io.github.gleidsonmt.todo.bd.dao.internal.AbstractDao;
import io.github.gleidsonmt.todo.bd.dao.internal.DaoAction;
import io.github.gleidsonmt.todo.model.User;

/**
 * @author Gleidson Neves da Silveira | <a href="mailto:gleidisonmt@gmail.com">gleidisonmt@gmail.com</a> <br>
 * Create on 04/03/2024
 */
public final class DaoUser extends AbstractDao<Usernew> {

    @Override
    protected Usernew createElement(@NotNull ResultSet result) throws SQLException {

        Usernew item = new Usernew(result.getLong("id"), result.getString("name"));
        item.setUsername(result.getString("username"));
//        item.setSalt(result.getBytes("salt"));
//        item.setLogged(result.getBoolean("logged"));
//        item.setRemember(result.getBoolean("remember"));
//        item.setPassword(result.getString("password"));
//        item.setImageUrl(result.getString("image_url"));

        // item.setAvatar(item.getImageUrl());

        return item;
    }

    @Override
    protected void prepareElement(PreparedStatement prepare, @NotNull Usernew model) {

        String pass;
        byte[] salt;

        System.out.println("getAction() = " + getAction());

//        if (getAction() == DaoAction.CREATE && model.isNeedChangePass()) {
//        if (getAction() == DaoAction.CREATE) {
//            pass = model.getPassword();
//            salt = model.getSalt();
//        } else {
            salt = createSalt();
            pass = createSecurePassword(model.getPassword(), salt);
//        }
        // insert into user(name, salt, username, password) values(?, ?, ?, ?);

        try {
            prepare.setString(1, model.getName());
//            prepare.setString(2, model.getImageUrl());
            prepare.setString(2, model.getUsername());
            prepare.setString(3, pass);
            prepare.setBytes(4, salt);
//            prepare.setBoolean(4, model.isRemember());
//            prepare.setBoolean(5, model.isLogged());
//            prepare.setString(6, model.getUsername());
//            prepare.setString(7, pass);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean validatePassword(@NotNull Usernew user, String compare) {
//        String one = user.getPassword();
//        String two = createSecurePassword(compare, user.getSalt());
//
//        return one.equals(two);
        return false;
    }

    protected String createSecurePassword(@NotNull String password, byte[] salt) {

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
            e.printStackTrace();
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
