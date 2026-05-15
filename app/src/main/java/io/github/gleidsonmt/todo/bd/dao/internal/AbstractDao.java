package io.github.gleidsonmt.todo.bd.dao.internal;

import io.github.gleidsonmt.todo.bd.DatabaseConnection;
import io.github.gleidsonmt.todo.bd.sqlite.SQLiteConnection;
import io.github.gleidsonmt.todo.model.Model;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * This class abstracts the common actions in a database.
 * Provides a connection to database, set and get elements from a database.
 *
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on 04/03/2024
 */
@SuppressWarnings("unused")
public abstract class AbstractDao<T extends Model> implements Dao<T>, ListDao<T> {

    // Mono state to grant only one connection per time
    protected static SQLiteConnection data = SQLiteConnection.INSTANCE;
    // Some operations can be done and closed differently from transactions.
    protected static boolean autoCloseable = true;

    protected static final Logger logger = Logger.getGlobal();

    protected ModelSQLCreator<T> modelSQLCreator;

    public AbstractDao() {
        modelSQLCreator = new ModelSQLCreator<>(getClass());
    }

    private void logger(String message, String sql) {
        logger.fine("[SQL Action, type=" + getAction() + " ] + " + message + "  SQL => { " + sql + " }");
    }

    private void logger(String sql) {
        logger.fine("[SQL Action, type=" + getAction() + " ] SQL => { " + sql + " }");
    }

    /**
     * This method creates an item using the model T.
     *
     * @param result The result to select.
     * @return The model created.
     * @throws SQLException if a database access error occurs or this method is
     *                      called on a closed result set.
     */
    protected abstract T createElement(ResultSet result) throws SQLException;

    protected abstract void prepareElement(PreparedStatement prepare, T element) throws SQLException;

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
            logger(sql);
            return true;
        } catch (SQLException e) {
            logger.severe("Error on updating SQL => { " + sql + " }");
            throw new RuntimeException(e);
        } finally {
            if (autoCloseable) close();
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
    public long store(T model) {
        connect();
        String sql = "";
        try {
            sql = modelSQLCreator.create(DaoAction.CREATE, model);
            PreparedStatement preparedStatement = prepareStatement(sql);
            prepareElement(preparedStatement, model);
            preparedStatement.execute();
            logger(sql);
            return getLastId();
        } catch (SQLException e) {
            logger.severe("Error on storing SQL => { " + sql + " }");
            throw new RuntimeException(e);
        } finally {
            if (autoCloseable)
                close();
        }
    }


    /**
     * Delete a row using id.
     *
     * @param id The id.
     * @return if deleted action was successful.
     */
    public boolean delete(long id) {
        connect();
        String sql = modelSQLCreator.create(DaoAction.DELETE, id);
        boolean execute = data.executeUpdate(sql);
        logger(sql);
        if (autoCloseable)
            close();
        return execute;
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
        connect();
        String sql = modelSQLCreator.create(DaoAction.DELETE, model);
        boolean execute = data.executeUpdate(sql);
        logger(sql);
        if (autoCloseable)
            close();
        return execute;
    }


    /**
     * Get the item using its id.
     *
     * @param id The id to get the item.
     * @return The item.
     */
    @Override
    public Optional<T> get(long id) {
        connect();
        ResultSet result = executeQuery(modelSQLCreator.create(DaoAction.GET, id));
        System.out.println("result = " + result);
        try {
            if (result.next())
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
            if (result.next()) {
                return Optional.ofNullable(createElement(result));
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
    protected long getLastId() {
        ResultSet rs;
        try {
            rs = prepareStatement("SELECT LAST_INSERT_ID()").executeQuery();
            rs.next();
            return rs.getLong(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (autoCloseable)
                close();
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
    protected void connect() {
        try {
            if (!data.hasConnection() || data.getConnection().isClosed()) {
                data.getConnection();
            }
        } catch (SQLException e) {
            logger.severe("Error on connecting to database");
            throw new RuntimeException(e);
        }
    }

    @ApiStatus.Internal
    protected void close() {
        data.close();
    }

    public Optional<T> findWhere(String condition) {
        connect();
        ResultSet result = executeQuery("select * from " + getTable() + " where " + condition + ";");
        try {
            if (result.next())
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
        items.clear();
        return new Task<>() {
            @Override
            protected ObservableList<T> call() {
                connect();
                var sql = modelSQLCreator.createFetch(condition);
                System.out.println("sql = " + sql);
                ResultSet result = executeQuery(sql);
                logger(sql);
                try {
                    if (data.hasConnection()) {
                        while (result.next()) {
                            T element = createElement(result);
                            items.add(element);
                        }
                    }
                } catch (SQLException e) {
                    logger.severe(() -> "[SQL Action, Type = ERROR]  SQL => select * from " + getTable() + " " + condition + ";");
                    throw new RuntimeException(e);
                }
                return items;
            }
        };
    }

    public Task<ObservableList<T>> fetch(ObservableList<T> items, long ini, long fin) {
        // SELECT * FROM sua_tabela
        // LIMIT 81 OFFSET 19;
//        StringBuilder builder = new StringBuilder();
//        builder.append("limit").append(" ").append(String.valueOf(fin)).append(" ");
//        builder.append("offset").append(" ").append(String.valueOf(ini));

        return fetch(items, ini, fin, null);
    }

    public Task<ObservableList<T>> fetch(ObservableList<T> items, long limit, long offset, String condition) {
        // SELECT * FROM sua_tabela
        // LIMIT 81 OFFSET 19;
        StringBuilder builder = new StringBuilder();

        if (condition != null) {
            builder.append(" where ").append(condition).append(" ");
        }
        builder.append("limit").append(" ").append(limit).append(" ");
        builder.append("offset").append(" ").append(offset);
        return fetchWhere(items, builder.toString());
    }

    @ApiStatus.Experimental
    @Override
    public ObservableList<T> fetchByModel(@NotNull Model model) {
        connect();
        ResultSet result = executeQuery("select * from " + getTable() + " where "
                                        + model.getClass().getSimpleName().toLowerCase() + "_id = " + model.getId() + ";");
        ObservableList<T> items = FXCollections.observableArrayList();
        try {
            while (result.next()) {
                items.add(createElement(result));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return items;
    }

    @Deprecated
    public int sizeWhere(String condition) {
        connect();
        int size = 0;
        // SELECT COUNT(*) AS size FROM (
        // SELECT * FROM list LIMIT 3
        // ) AS subconsulta;
        ResultSet result = executeQuery("select count(*) as size from (" + condition + ") as subquery;");

        // System.out.println(
        // "select count(*) as size from (select * from list limit " + limit + "
        // " + condition + ") as size;");
        try {
            result.first();

            size = result.getInt("size");
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return size;
    }
}
// @Override
// public Optional<T> get(long id) {
// connect();
// ResultSet result = executeQuery("select * from " + getTable() + " where id =
// " + id + ";");
// try {
// if (result.first())
// return Optional.of(createElement(result));
// else
// return Optional.empty();
// } catch (SQLException e) {
// throw new RuntimeException(e);
// }
// }