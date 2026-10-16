

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

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import org.jetbrains.annotations.NotNull;

import io.github.gleidsonmt.todo.bd.dao.internal.AbstractDao;
import io.github.gleidsonmt.todo.model.ToDoTask;

/**
 * @author Gleidson Neves da Silveira | <a href="mailto:gleidisonmt@gmail.com">gleidisonmt@gmail.com</a> <br>
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

    }

    @Override
    protected void prepareElement(@NotNull PreparedStatement prepare, @NotNull ToDoTask model) {
        try {
//
            prepare.setString(1, model.name());
            prepare.setBoolean(2, model.completed());
            prepare.setBoolean(3, model.important());
            prepare.setBoolean(4, model.myDay());
//
            if (model.dueDate() == null) {
                prepare.setNull(5, 0);
            } else {
                prepare.setString(5, model.dueDate().toString());
            }
//
            if (model.remind() == null) {
                prepare.setNull(6, 0);
            } else {
                prepare.setString(6, model.remind().toString());
            }
//
            prepare.setString(7, model.createdAt().toString());
//
            prepare.setLong(8, model.listID());
//
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Task<ObservableList<ToDoTask>> fetch(long range) {
        return fetch(range, null);
    }

    public Task<ObservableList<ToDoTask>> fetch(long range, String where) {
        return fetch(0, range, where);
    }

    public Task<ObservableList<ToDoTask>> fetch(long ini, long fin) {
        return fetch(ini, fin, null);
    }

    public Task<ObservableList<ToDoTask>> fetch(long limit, long offset, String where) {
        return fetch(FXCollections.observableArrayList(), limit, offset, where);
    }

}
