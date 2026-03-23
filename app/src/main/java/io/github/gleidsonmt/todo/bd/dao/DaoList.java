package io.github.gleidsonmt.todo.bd.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import io.github.gleidsonmt.todo.bd.dao.internal.AbstractDao;
import io.github.gleidsonmt.todo.model.List;
import io.github.gleidsonmt.todo.view_model.ListViewModelConverter;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 *         Create on 04/03/2024
 */
public final class DaoList extends AbstractDao<List> {

    private final ListViewModelConverter converter = new ListViewModelConverter();

    @Override
    public List createElement(ResultSet result) throws SQLException {
        return converter.create(result.getInt("list.id"), result.getString("list.name"));
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

    public int getSize(long lis_id) {
        String sql = "select size from sizes where list_id = " + lis_id;
        ResultSet result = executeQuery(sql);
        var size = 0;
        try {
            if (result.first()) {
                size = result.getInt("size");
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return size;
    }

}
