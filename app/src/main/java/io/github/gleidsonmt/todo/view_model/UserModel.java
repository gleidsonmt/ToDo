package io.github.gleidsonmt.todo.view_model;

import io.github.gleidsonmt.todo.bd.dao.DaoUser;

/**
 * Description:
 *
 * @author Gleidson Neves da Silveira | <a href="mailto:gleidisonmt@gmail.com">gleidisonmt@gmail.com</a> <br>
 * Create on 28/05/2026
 */
public class UserModel {

    private final DaoUser dao;
    private final UserConverter converter;

    public UserModel() {
        this.dao = new DaoUser();
        this.converter = new UserConverter();
    }

    public boolean save(UserViewModel user) {
        var converted = converter.toEntity(user);
        user.setId(dao.store(converted));
        return false;
    }

    public boolean hasUsername(String val) {
        return this.dao.findWhere("username = \"" + val + "\"" ).isPresent();
    }

    public UserConverter getConverter() {
        return converter;
    }
}
