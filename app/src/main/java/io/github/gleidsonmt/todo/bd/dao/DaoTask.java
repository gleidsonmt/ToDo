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

        return new ToDoTask(result.getInt("task.id"), result.getString("task.name"),
                result.getBoolean("task.completed"), result.getBoolean("task.important"),
                result.getBoolean("task.my_day"),
                result.getDate("task.due_date") != null ? result.getDate("task.due_date").toLocalDate() : null,
                result.getTimestamp("task.remind") != null ? result.getTimestamp("task.remind").toLocalDateTime()
                        : null,
                result.getDate("task.created_at").toLocalDate(), result.getInt("task.list_id"));

    }

    @Override
    protected void prepareElement(@NotNull PreparedStatement prepare, @NotNull ToDoTask model) {
        try {
            prepare.setString(1, model.getName());
            prepare.setBoolean(2, model.isCompleted());
            prepare.setBoolean(3, model.isImportant());
            prepare.setBoolean(4, model.isMyDay());

            if (model.getDueDate() == null) {
                prepare.setNull(5, 0);
            } else {
                prepare.setDate(5, Date.valueOf(model.getDueDate()));
            }

            if (model.getRemind() == null) {
                prepare.setNull(6, 0);
            } else {
                prepare.setTimestamp(6, Timestamp.valueOf(model.getRemind()));
            }

            prepare.setDate(7, Date.valueOf(model.getCreatedAt()));

            // prepare.setNull(8, (int) model.getListId());
            prepare.setLong(8, model.getListId());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
