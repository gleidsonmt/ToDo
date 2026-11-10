package io.github.gleidsonmt.todo.bd.dao;

import io.github.gleidsonmt.todo.bd.dao.internal.AbstractDao;
import io.github.gleidsonmt.todo.model.TaskFile;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Gleidson Neves da Silveira | <a href="mailto:gleidisonmt@gmail.com">gleidisonmt@gmail.com</a> <br>
 * Created on 29/10/2026
 */
public class DaoTaskFile extends AbstractDao<TaskFile> {

    @Override
    protected TaskFile createElement(ResultSet result) throws SQLException {
        return new TaskFile(
                result.getLong("id"),
                result.getString("path"),
                result.getLong("task_id")
        );
    }

    @Override
    protected void prepareElement(PreparedStatement prepare, TaskFile element) {
        try {
            prepare.setString(1, element.path());
            prepare.setLong(2, element.taskID());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
