package io.github.gleidsonmt.todo.bd.dao;

import io.github.gleidsonmt.todo.bd.dao.internal.AbstractDao;
import io.github.gleidsonmt.todo.model.recurrence.Recurrence;
import io.github.gleidsonmt.todo.model.recurrence.RecurrenceType;
import io.github.gleidsonmt.todo.model.recurrence.Weekly;
import io.github.gleidsonmt.todo.utils.StringUtils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Gleidson Neves da Silveira | <gleidisonmt@gmail.com>
 * Created on  12/04/2026
 */
public final class DaoRecurrence extends AbstractDao<Recurrence>  {
    @Override
    protected Recurrence createElement(ResultSet result) throws SQLException {

//        Recurrence item = create(StringUtils.converter(result.getString("type")));
//        assert item != null;
//        item.setId(result.getInt("id"));
//        item.setGap(result.getInt("gap"));
//        return item;
        return null;
    }

    @Override
    protected void prepareElement(PreparedStatement prepare, Recurrence element) throws SQLException {
        try {

            prepare.setInt(1, element.getGap());

            if (element.getType().equals(RecurrenceType.WEEKDAYS)) {
                prepare.setString(2, RecurrenceType.WEEKLY.toString());
            } else {
                prepare.setString(2, element.getType().toString());
            }
    
            if (element instanceof Weekly item) {
                StringBuilder stringBuilder = new StringBuilder();
                item.getDaysOfWeek().forEach(e -> stringBuilder.append(e.name()).append(","));
                stringBuilder.delete(stringBuilder.length() - 1, stringBuilder.length());
                prepare.setString(3, stringBuilder.toString());
            } else {
                prepare.setString(3, "ANY");
            }

            prepare.setLong(4, element.getTaskID());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
