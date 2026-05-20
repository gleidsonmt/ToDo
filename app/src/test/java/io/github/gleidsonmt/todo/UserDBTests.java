package io.github.gleidsonmt.todo;

import io.github.gleidsonmt.todo.bd.dao.DaoUser;
import io.github.gleidsonmt.todo.bd.sqlite.SQLiteConnection;
import io.github.gleidsonmt.todo.model.User;
import io.github.gleidsonmt.todo.model.Usernew;
import org.junit.jupiter.api.Test;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Description:
 *
 * @author Gleidson Neves da Silveira | <a href="mailto:gleidisonmt@gmail.com">gleidisonmt@gmail.com</a> <br>
 * Create on 20/05/2026
 */
public class UserDBTests {



    @Test
    void createUser() {
        var db = SQLiteConnection.INSTANCE;
        try {
            db.connect();
            DaoUser daoUser = new DaoUser();
            Usernew user = new Usernew(1, "Gleidson");
            user.setUsername("gleidisonmt@gmail.com");
            user.setPassword("radar");

            daoUser.store(user);
//        user.setUsername("admin");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    @Test
    void getUser() {
        var db = SQLiteConnection.INSTANCE;

        try {
            db.connect();
            ResultSet result = db.executeQuery("select * from user;");
            System.out.println("result = " + result);
            while (result.next()) {
                System.out.println("result = " + result.getString("username"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
