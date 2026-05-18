

package io.github.gleidsonmt.todo.bd.dao;

import io.github.gleidsonmt.todo.bd.dao.internal.AbstractDao;
import io.github.gleidsonmt.todo.model.recurrence.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Gleidson Neves da Silveira | <a href="mailto:gleidisonmt@gmail.com">gleidisonmt@gmail.com</a> <br>
 * Created on  12/04/2026
 */
public final class DaoRecurrence extends AbstractDao<Recurrence> {

    private WeekDayConverter converter = new WeekDayConverter();


    @Override
    protected Recurrence createElement(ResultSet result) throws SQLException {

        RecurrenceType type = RecurrenceType.valueOf(result.getString("type"));


        Recurrence item = create(
                type,
                result.getLong("id"),
                result.getInt("gap"),
                result.getInt("task_id"),
                converter.toSet(result.getInt("days_of_week"))
        );

        return item;
    }

    private Recurrence create(RecurrenceType type, long id, int gap, int taskId,  Set<DayOfWeek> set) {
        Recurrence item = null;

        switch (type) {
            case DAILY -> item = new Daily(id, gap, taskId, set);
//            case WEEKLY -> item = new Weekly(id, gap, taskId, type, set);
            case MONTHLY -> item = new Monthly();
            case YEARLY -> item = new Yearly();
        }

        return item;
    }

    @Override
    protected void prepareElement(PreparedStatement prepare, Recurrence element) {
        try {

            prepare.setInt(1, element.getGap());

            if (element.getType().equals(RecurrenceType.WEEKDAYS)) {
                prepare.setString(2, RecurrenceType.WEEKLY.toString());
            } else {
                prepare.setString(2, element.getType().toString());
            }

            Set<DayOfWeek> set = new HashSet<>();

            if (element instanceof Weekly item) {
                StringBuilder stringBuilder = new StringBuilder();

                item.getDaysOfWeek().forEach(e -> stringBuilder.append(e.name()).append(","));
                Arrays.stream(stringBuilder.toString().split(",")).forEach(day -> {
                    set.add(DayOfWeek.valueOf(day));
                });
                prepare.setInt(3, converter.toInteger(set, false));
            } else {
                prepare.setInt(3,converter.toInteger(set, true));

            }

            prepare.setLong(4, element.getTaskID());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
