

package io.github.gleidsonmt.todo.bd.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.logging.Logger;

import org.jetbrains.annotations.NotNull;

import io.github.gleidsonmt.todo.bd.dao.internal.AbstractDao;
import io.github.gleidsonmt.todo.model.ToDoTask;

/**
 * @author Gleidson Neves da Silveira | <a href="mailto:gleidisonmt@gmail.com">gleidisonmt@gmail.com</a>
 * Create on 04/03/2024
 */
public final class DaoTask extends AbstractDao<ToDoTask> {

    @Override
    protected ToDoTask createElement(@NotNull ResultSet result) throws SQLException {




        return new ToDoTask(
                result.getInt("id"),
                result.getString("name"),
                result.getBoolean("completed"),
                result.getBoolean("important"),
                result.getBoolean("my_day"),
                result.getString("due_date") != null ? LocalDate.parse(result.getString("due_date")) : null,
                result.getString("remind") != null ? LocalDateTime.parse(result.getString("remind")) : null,
                result.getString("created_at") != null ? LocalDate.parse(result.getString("created_at")) : null,
                result.getInt("list_id")
//                result.getDate("due_date") != null ? result.getDate("due_date").toLocalDate() : null
        );
//        return new ToDoTask(result.getInt("task.id"), result.getString("task.name"),
//                result.getBoolean("task.completed"), result.getBoolean("task.important"),
//                result.getBoolean("task.my_day"),
//                result.getDate("task.due_date") != null ? result.getDate("task.due_date").toLocalDate() : null,
//                result.getTimestamp("task.remind") != null ? result.getTimestamp("task.remind").toLocalDateTime()
//                        : null,
//                result.getDate("task.created_at").toLocalDate(), result.getInt("task.list_id"));
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
                prepare.setString(5, model.getDueDate().toString());
            }

            if (model.getRemind() == null) {
                prepare.setNull(6, 0);
            } else {
                prepare.setString(6, model.getRemind().toString());
            }

            prepare.setString(7, model.getCreatedAt().toString());

            prepare.setLong(8, model.getListId());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
