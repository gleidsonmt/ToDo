package io.github.gleidsonmt.todo.global;

import java.util.List;

import io.github.gleidsonmt.todo.model.Model;
import javafx.collections.ObservableList;

/**
 * Description:
 *
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 *         Created On: Feb 25, 2026
 * 
 *         Version History: Initial version
 */
public class Repository {

    private final UserPresenter userPresenter;
    private final ListPresenter listPresenter;
    private final TaskPresenter taskPresenter;

    private final List<Presenter<?>> repos;

    public Repository() {
        this.userPresenter = new UserPresenter();
        this.listPresenter = new ListPresenter();
        this.taskPresenter = new TaskPresenter();
        this.repos = List.of(userPresenter, listPresenter, taskPresenter);
    }

    public <T extends Model> Presenter<T> of(Class<?> presenter) {

        for (Presenter<?> p : repos) {
            // Get the generic superclass (e.g., AbstractPresenter<User>)
            if (p.getModelClass().equals(presenter)) {
                return (Presenter<T>) p;
            }
            // Type type = p.getClass().getGenericSuperclass();
            // if (type instanceof ParameterizedType) {
            // ParameterizedType pt = (ParameterizedType) type;
            // Type[] typeArgs = pt.getActualTypeArguments();
            // if (typeArgs.length > 0) {
            // Type arg = typeArgs[0];
            // if (arg instanceof Class) {
            // Class<?> modelClass = (Class<?>) arg;
            // if (modelClass.equals(presenter)) {
            // // Safe cast since we verified the type matches
            // return (Presenter<T>) p;
            // }
            // }
            // }
            // }
        }
        return null; // Or throw an exception if no match found
    }

    // public Task<ObservableList<List>> loadLists() {
    // return daoList.fetch(lists);
    // }

    /**
     * For every action after loading the tasks, they will be reflection in
     * database (dao) actions.
     */
    // private ListChangeListener<ToDoTask> createListener() {
    // return ((ListChangeListener<ToDoTask>) c -> {
    // if (c.next()) {
    // if (c.wasReplaced()) {
    // c.getAddedSubList().forEach(this::update);
    // } else {
    // if (c.wasAdded()) {
    // c.getAddedSubList().forEach(this::store);
    // } else if (c.wasRemoved()) {
    // c.getRemoved().forEach(this::delete);
    // }
    // }
    // }
    // });
    // }

    // public ObservableList<ToDoTask> getData() {
    // return this.data;
    // }

    public ObservableList<List> getLists() {
        // return this.lists;
        return null;
    }

    // private void delete(ToDoTask task) {
    // dao.delete(task);
    // }

    // private void update(ToDoTask task) {
    // dao.update(task);
    // }

    // private void store(ToDoTask task) {
    // dao.store(task);
    // }

    // public void store(List list) {
    // // daoList.store(list);
    // }

    // public void delete(List list) {
    // // daoList.delete(list);
    // }

    // private void apply() {
    // dao.commit();
    // }

}
