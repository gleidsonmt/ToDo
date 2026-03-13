package io.github.gleidsonmt.todo.bd.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.jetbrains.annotations.NotNull;

import io.github.gleidsonmt.todo.bd.dao.internal.AbstractDao;
import io.github.gleidsonmt.todo.model.ToDoTask;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 *         Create on 04/03/2024
 */
public final class DaoTask extends AbstractDao<ToDoTask> {

    @Override
    protected ToDoTask createElement(@NotNull ResultSet result) throws SQLException {
        ToDoTask item = new ToDoTask();
        item.setId(result.getInt("task.id"));
        item.setName(result.getString("task.name"));
        item.setCompleted(result.getBoolean("task.completed"

        ));
        item.setImportant(result.getBoolean("task.important"));

        var te = result.getTimestamp("task.remind");

        if (te != null) {
            item.setRemind(te.toLocalDateTime());
        }

        var temp = result.getDate("task.due_date");
        item.setDueDate(temp != null ? temp.toLocalDate() : null);

        item.setListId(result.getInt("task.list_id"));
        item.setMyDay(result.getBoolean("task.my_day"));
        item.setRecurrenceId(result.getInt("task.recurrence_id"));
        return item;
    }

    @Override
    protected ToDoTask prepareElement(@NotNull PreparedStatement prepare, @NotNull ToDoTask model) {
        try {
            prepare.setString(1, model.getName());

            if (model.getListId() == 0) {
                prepare.setNull(2, (int) model.getListId());
            } else {
                prepare.setLong(2, model.getListId());
            }

            prepare.setBoolean(3, model.isImportant());

            prepare.setBoolean(4, model.isCompleted());

            if (model.getRemind() == null) {
                prepare.setNull(5, 0);
            } else {
                prepare.setTimestamp(5, Timestamp.valueOf(model.getRemind()));
            }

            if (model.getDueDate() == null) {
                prepare.setNull(6, 0);
            } else {
                prepare.setDate(6, Date.valueOf(model.getDueDate()));
            }
            prepare.setBoolean(7, model.isMyDay());

            if (model.getRecurrenceId() == 0) {
                prepare.setNull(8, 0);
            } else {
                prepare.setInt(8, model.getRecurrenceId());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return model;
    }

}
