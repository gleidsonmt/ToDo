package io.github.gleidsonmt.todo.bd.dao.internal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import io.github.gleidsonmt.todo.bd.DatabaseConnection;
import io.github.gleidsonmt.todo.model.Model;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;

/**
 * This class abstracts the common actions in a database.
 * Provides a connection to database, set and get elements from a database.
 *
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 *         Create on 04/03/2024
 */
public abstract class AbstractDao<T extends Model> implements Dao<T>, ListDao<T> {

    protected final ObservableList<T> items;
    // Mono state to grant only one connection per time
    protected static DatabaseConnection data = new DatabaseConnection();
    // Some operations can be done and closed different from transactions.
    protected static boolean autoCloseable = true;
    private static String errorMessage;

    protected static final Logger logger = Logger.getGlobal();

    protected ModelSQLCreator<T> modelSQLCreator;

    public AbstractDao() {
        items = new SimpleListProperty<>(FXCollections.observableArrayList());
        modelSQLCreator = new ModelSQLCreator<>(getClass());
    }

    /**
     * This method creates an item using the model T.
     *
     * @param result The result to select.
     * @return The model created.
     * @throws SQLException if a database access error occurs or this method is
     *                          called on a closed result set.
     */
    protected abstract T createElement(ResultSet result) throws SQLException;

    protected abstract T prepareElement(PreparedStatement prepare, T element) throws SQLException;

    /**
     * Updates a row in database.
     *
     * @param model The table/model to update.
     * @return The model updated.
     */
    @Override
    public boolean update(T model) {
        connect();
        String sql = "";
        try {
            sql = modelSQLCreator.create(DaoAction.UPDATE, model);
            PreparedStatement preparedStatement = prepareStatement(sql);
            prepareElement(preparedStatement, model);
            preparedStatement.execute();
            Logger.getGlobal().log(Level.INFO, "SQL Action, [Type = UPDATE] =>\nSQL: {0}", sql);
            return true;
        } catch (SQLException e) {
            errorMessage = "Error on updating a on: SQL => '" + sql + "';\n" + e;
            logger.severe(errorMessage);
            throw new RuntimeException(e);
            // return false;
        } finally {
            if (autoCloseable)
                close();
        }
    }

    /**
     * Save a model in a database.
     *
     * @param model The model/table to save.
     * @return The model stored.
     */
    @SuppressWarnings("null")
    @Override
    public boolean store(T model) {
        connect();
        String sql = "";
        try {
            sql = modelSQLCreator.create(DaoAction.CREATE, model);
            PreparedStatement preparedStatement = prepareStatement(sql);
            model = prepareElement(preparedStatement, model);
            preparedStatement.execute();
            model.setId(getLastId());
            logger.info("SQL Action, [Type = STORE]  SQL => \n" + sql);
            return true;
        } catch (SQLException e) {
            errorMessage = "SQL Action, [Type = ERROR]  SQL => \n" + sql + "';\n" + e;
            logger.severe(errorMessage);
            return false;
        } finally {
            if (autoCloseable)
                close();
        }
    }

    /**
     * Delete a row/model in database.
     *
     * @param model The model to delete.
     * @return if the model is deleted.
     */
    @SuppressWarnings("null")
    @Override
    public boolean delete(T model) {
        return delete(model.getId());
    }

    /**
     * Delete a row using id.
     *
     * @param id The id.
     * @return if deleted action was successful.
     */
    @Override
    public boolean delete(long id) {
        connect();
        String sql = "delete from " + getTable() + " where id = " + id + ";";
        boolean execute = data.executeUpdate(sql);
        if (autoCloseable)
            close();
        return execute;
    }

    /**
     * Get and item using its id.
     * 
     * @param The id to get the item.
     * @return The item.
     */
    @Override
    public Optional<T> get(long id) {
        connect();
        ResultSet result = executeQuery("select * from " + getTable() + " where id = " + id + ";");
        try {
            if (result.first())
                return Optional.of(createElement(result));
            else
                return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Get the first item in a tabale.
     * 
     * @param The id to get the item.
     * @return The item.
     */
    @Override
    public Optional<T> getFirst() {
        connect();
        ResultSet result = executeQuery("select * from " + getTable() + ";");
        try {
            if (result.first())
                return Optional.of(createElement(result));
            else
                return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * This method uses a foreign key, to get a model.
     * This method uses a pattern defined to using foreign keys.
     * The foreign keys need to have this format [table_name]_id;
     * In DaoPreferences example you need to get a preference by user -> SQL
     * `select * from preferences where user_id = 20`;
     *
     * @param model The
     * @return The {@link #getTable()} Model
     */
    @SuppressWarnings("null")
    @ApiStatus.Internal
    public T getByModel(Model model) {
        connect();
        ResultSet result = executeQuery("select * from " + getTable() + " where "
                + model.getClass().getSimpleName().toLowerCase() + "_id = " + model.getId() + ";");
        try {
            if (result.first()) {
                return createElement(result);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public Optional<T> getByName(String name) {
        return getWhere("name like '" + name + "';");
    }

    @ApiStatus.Experimental
    public Optional<T> getWhere(String condition) {
        connect();
        ResultSet result = executeQuery("select * from " + getTable() + " where " + condition + ";");
        try {
            if (result.first()) {
                return Optional.of(createElement(result));
            } else
                return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Create a and prepare a statement for a database action.
     *
     * @param sql The SQL to prepare.
     * @return The prepared statement to execute.
     */
    @ApiStatus.Internal
    protected PreparedStatement prepareStatement(String sql) {
        try {
            return data.getConnection().prepareStatement(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    protected ResultSet executeQuery(String sql) {
        try {
            return data.executeQuery(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @ApiStatus.Internal
    protected int getLastId() {
        ResultSet rs;
        try {
            rs = prepareStatement("SELECT LAST_INSERT_ID()").executeQuery();
            rs.first();
            return rs.getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    protected DaoAction getAction() {
        return modelSQLCreator.getAction();
    }

    @ApiStatus.Internal
    private String getTable() {
        return getClass().getSimpleName().replace("Dao", "").toLowerCase();
    }

    public void begin() {
        try {
            autoCloseable = false;
            connect();
            data.getConnection().setAutoCommit(false);
            data.getConnection().beginRequest();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void rollback() {
        try {
            if (data.hasConnection() && !autoCloseable) {
                data.getConnection().rollback();
            }
            autoCloseable = true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void commit() {
        try {
            if (data.hasConnection() && !autoCloseable) {
                data.getConnection().endRequest();
                data.getConnection().commit();
                data.getConnection().setAutoCommit(true);
            }
            // data.getConnection().setAutoCommit(true);
            close();
            autoCloseable = true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @ApiStatus.Internal
    private void connect() {
        try {
            if (!data.hasConnection() || data.getConnection().isClosed()) {
                data.connect();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @ApiStatus.Internal
    private void close() {
        data.close();
    }

    public Optional<T> findWhere(String condition) {
        connect();
        ResultSet result = executeQuery("select * from " + getTable() + " where " + condition + ";");
        try {
            if (result.first())
                return Optional.of(createElement(result));
            else
                return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Get a model from database using condition.
     * Ex. this.getBy("logged = 1");
     */
    public Optional<T> getBy(String condition) {
        connect();
        ResultSet result = executeQuery("select * from " + getTable() + " where " + condition + ";");
        try {
            if (result.first()) {
                return Optional.of(createElement(result));
            } else
                return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // List Dao methods

    @Override
    public Task<ObservableList<T>> fetch(ObservableList<T> items) {
        return fetchWhere(items, "");
    }

    @ApiStatus.Experimental
    @Override
    public Task<ObservableList<T>> fetchWhere(ObservableList<T> items, String condition) {
        Task<ObservableList<T>> task = new Task<>() {
            @Override
            protected ObservableList<T> call() {
                connect();
                ResultSet result = executeQuery("select * from " + getTable() + " " + condition + ";");
                Logger.getGlobal().info(() -> """
                        SQL Action, [Type = FETCH]  SQL =>
                        select * from """ + getTable() + " " + condition + ";");
                try {
                    if (data.hasConnection()) {
                        while (result.next()) {
                            T element = createElement(result);
                            items.add(element);
                        }
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                return items;
            }
        };

        task.setOnFailed(e -> {
            throw new RuntimeException(task.getException());
        });

        return task;
    }

    @ApiStatus.Experimental
    @Override
    public ObservableList<T> fetchByModel(@NotNull Model model) {
        connect();
        ResultSet result = executeQuery("select * from " + getTable() + " where "
                + model.getClass().getSimpleName().toLowerCase() + "_id = " + model.getId() + ";");
        items.clear();
        try {
            while (result.next()) {
                items.add(createElement(result));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return items;
    }

}
