package io.github.gleidsonmt.todo.bd.dao;

import io.github.gleidsonmt.todo.bd.dao.internal.AbstractDao;
import io.github.gleidsonmt.todo.model.recurrence.Recurrence;
import io.github.gleidsonmt.todo.model.recurrence.RecurrenceType;
import io.github.gleidsonmt.todo.model.recurrence.WeekDayConverter;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Gleidson Neves da Silveira | <a href="mailto:gleidisonmt@gmail.com">gleidisonmt@gmail.com</a> <br> <br>
 * Created on  12/04/2026
 */
public final class DaoRecurrence extends AbstractDao<Recurrence> {

    private final WeekDayConverter converter = new WeekDayConverter();

    @Override
    protected Recurrence createElement(ResultSet result) throws SQLException {
        return new Recurrence(
                result.getLong("id"),
                result.getInt("gap"),
                RecurrenceType.valueOf(result.getString("type")),
                converter.toSet(result.getInt("days_of_week")),
                result.getInt("task_id"));
    }

    @Override
    protected void prepareElement(PreparedStatement prepare, Recurrence element) {
        try {

            prepare.setLong(1, element.gap());
            prepare.setString(2, element.type().toString());
            prepare.setInt(3, converter.toInteger(element.daysOfWeek()));
            prepare.setLong(4, element.taskID());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
