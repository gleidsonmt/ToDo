package io.github.gleidsonmt.todo.bd.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import io.github.gleidsonmt.todo.bd.dao.internal.AbstractDao;
import io.github.gleidsonmt.todo.model.Preferences;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 *         Create on 01/11/2024
 */
public final class DaoPreferences extends AbstractDao<Preferences> {

    @Override
    protected Preferences createElement(ResultSet result) throws SQLException {
        Preferences item = new Preferences();
        // item.setId(result.getInt("id"));
        item.setSmartImportant(result.getBoolean("smart_important"));
        item.setSmartCompleted(result.getBoolean("smart_completed"));
        item.setSmartAll(result.getBoolean("smart_all"));
        item.setUserId(result.getInt("user_id"));

        return item;
    }

    @Override
    protected void prepareElement(PreparedStatement prepare, Preferences model) {
        try {
            prepare.setBoolean(1, model.isSmartImportant());
            prepare.setBoolean(2, model.isSmartCompleted());
            prepare.setBoolean(3, model.isSmartAll());
            prepare.setInt(4, model.getUserId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
