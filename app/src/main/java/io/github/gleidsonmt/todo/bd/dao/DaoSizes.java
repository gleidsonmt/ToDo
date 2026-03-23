package io.github.gleidsonmt.todo.bd.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import io.github.gleidsonmt.todo.bd.dao.internal.AbstractDao;
import io.github.gleidsonmt.todo.model.Sizes;

/**
 * Description:
 *
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 *         Created On: Mar 23, 2026
 * 
 *         Version History: Initial version
 */
public class DaoSizes extends AbstractDao<Sizes> {

    @Override
    public Sizes createElement(ResultSet result) throws SQLException {
        Sizes item = new Sizes(result.getInt("sizes.id"));
        item.setSize(result.getInt("sizes.size"));
        return item;
    }

    @Override
    protected Sizes prepareElement(PreparedStatement prepare, Sizes model) {
        try {
            prepare.setInt(1, model.getSize());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return model;
    }

}