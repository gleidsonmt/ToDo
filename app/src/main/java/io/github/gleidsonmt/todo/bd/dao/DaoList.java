package io.github.gleidsonmt.todo.bd.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import io.github.gleidsonmt.todo.bd.dao.internal.AbstractDao;
import io.github.gleidsonmt.todo.model.List;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 *         Create on 04/03/2024
 */
public final class DaoList extends AbstractDao<List> {

    @Override
    public synchronized List createElement(ResultSet result) throws SQLException {

        List item = new List(result.getString("list.name"));
        item.setId(result.getInt("list.id"));
        item.setName(result.getString("list.name"));
        item.setFixed(result.getBoolean("list.fixed"));

        return item;
    }

    @Override
    protected List prepareElement(PreparedStatement prepare, List model) {
        try {
            prepare.setString(1, model.getName());
            prepare.setBoolean(2, model.isFixed());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return model;
    }

}
