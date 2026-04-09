package io.github.gleidsonmt.todo.bd.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import io.github.gleidsonmt.todo.bd.dao.internal.AbstractDao;
import io.github.gleidsonmt.todo.model.Size;

/**
 * Description:
 *
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 *         Created On: Mar 23, 2026
 * 
 *         Version History: Initial version
 */
public class DaoSize extends AbstractDao<Size> {

    @Override
    public Size createElement(ResultSet result) throws SQLException {
        Size item = new Size(result.getInt("size.id"));
        item.setVal(result.getInt("size.val"));
        item.setListId(result.getInt("size.list_id"));
        return item;
    }

    @Override
    protected void prepareElement(PreparedStatement prepare, Size model) {
        try {
            prepare.setInt(1, model.getVal());
            prepare.setLong(2, model.getListId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}