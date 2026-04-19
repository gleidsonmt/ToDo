package io.github.gleidsonmt.todo.bd.dao.internal;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import io.github.gleidsonmt.todo.model.Model;

/**
 * Description: This class use a model to create a query sql.
 * Ex. Give the class model →
 *
 * <pre>
 *     <code>
 *         class Person {
 *             int id;
 *             String name;
 *         }
 *     </code>
 * </pre>
 * <p>
 * This class will pass troughout the object person and create an string based
 * on its fields.
 * Also, it uses an enum action that define a return as SQL
 * Ex. If the action is update then the sql will be..
 * update person set id = ?, name = ?;
 * Instead
 * insert into person(id, name) values(?, ?);
 * Those sql are used for the base class {@link AbstractDao}
 *
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on 17/01/2025
 */
public class ModelSQLCreator<T extends Model> {

    private final String table;
    private DaoAction action;

    @SuppressWarnings("all")
    public ModelSQLCreator(Class<? extends AbstractDao> clazz) {
        this.table = clazz.getSimpleName().replace("Dao", "").toLowerCase();
    }

    public String create(DaoAction action, T model) {
        this.action = action;
        if (action.equals(DaoAction.CREATE)) {
            return prepareInsert(model);
        } else {
            return prepareUpdate(model);
        }
    }

    DaoAction getAction() {
        return action;
    }

    /**
     * Prepare an insert SQL.
     *
     * @param model The model to insert.
     * @return The SQL query.
     */
    @ApiStatus.Internal
    private String prepareInsert(@NotNull T model) {
        List<String> list = new ArrayList<>();
        recurse(list, model.getClass());

        // recurse(list, ToDoTaskNew.);

        StringBuilder sql = new StringBuilder("insert into " + table + "(");

        for (int i = 1; i < list.size(); i++) {
            if (i == (list.size() - 1)) {
                sql.append(list.get(i)).append(") values(");
            } else {
                sql.append(list.get(i)).append(", ");
            }
        }

        for (int i = 1; i < list.size(); i++) {
            if (i == (list.size() - 1)) {
                sql.append("?);");
            } else {
                sql.append("?, ");
            }
        }
        return sql.toString();
    }

    /**
     * Prepare an update SQL.
     *
     * @param model The model to update.
     * @return The SQL query.
     */
    @ApiStatus.Internal
    private String prepareUpdate(T model) {

        List<String> list = new ArrayList<>();
        recurse(list, model.getClass());

        StringBuilder sql = new StringBuilder("update " + table + " set ");

        for (int i = 1; i < list.size(); i++) {
            if (i + 1 == list.size()) {
                sql.append(list.get(i)).append(" = ?");
            } else {
                sql.append(list.get(i)).append(" = ?, ");
            }
        }

        sql.append(" where id = ").append(model.getId()).append(";");

        return sql.toString();
    }

    /**
     * This method gets the fields inside a model to create a Query SQL. <br>
     * <code><pre>
     *     public class User extend Entity {
     *         private String name;
     *         private String email;
     *     }
     * </code>
     * </pre>
     *
     * <br>
     * Result -> <br>
     * List<String> list = new ArrayList<>("name", "email");
     *
     * @param list  The list with the fields.
     * @param clazz The model to get the fields.
     */
    @ApiStatus.Internal
    private void recurse(List<String> list, Class<?> clazz) {
        if (clazz != null) {
            Field[] params = clazz.getDeclaredFields();
            for (Field field : params) {
                if (Arrays.stream(field.getDeclaredAnnotations()).noneMatch(e -> e.annotationType() == Ignore.class)) {
                    list.addFirst(camelCaseToKebabCase(field.getName()));
                }
            }
            recurse(list, clazz.getSuperclass());
        }
    }

    @ApiStatus.Internal
    private @NotNull String camelCaseToKebabCase(String input) {
        Pattern pattern = Pattern.compile("([a-z])([A-Z]+)");
        Matcher matcher = pattern.matcher(input);
        StringBuilder output = new StringBuilder();
        while (matcher.find()) {
            matcher.appendReplacement(output, matcher.group(1) + "_" + matcher.group(2).toLowerCase());
        }
        matcher.appendTail(output);
        return output.toString();
    }

}
