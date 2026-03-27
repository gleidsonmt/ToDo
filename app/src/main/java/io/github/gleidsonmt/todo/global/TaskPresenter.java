package io.github.gleidsonmt.todo.global;

import java.util.Optional;

import io.github.gleidsonmt.todo.bd.dao.DaoSizes;
import io.github.gleidsonmt.todo.bd.dao.DaoTask;
import io.github.gleidsonmt.todo.model.Sizes;
import io.github.gleidsonmt.todo.model.ToDoTask;

/**
 * Description:
 *
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 *         Created On: Mar 17, 2026
 * 
 *         Version History: Initial version
 */
public class TaskPresenter extends AbstractPresenter<ToDoTask> {

    private final DaoSizes sizes;

    public TaskPresenter() {
        super(new DaoTask());
        this.sizes = new DaoSizes();
    }

    public void update(ToDoTask task) {
        dao.update(task);
        updateSize(findSize(task), !task.isCompleted());
        // somar ou subtrair / sum or subtract
    }

    public void store(ToDoTask task) {
        dao.store(task);
        updateSize(findSize(task), true);
        // somar / sum
    }

    @Override
    public void delete(ToDoTask task) {
        dao.delete(task);
        updateSize(findSize(task), false);
        // subtrair / subtract
    }

    private void updateSize(Optional<Sizes> optional, boolean sum) {
        if (optional.isPresent()) {
            var size = optional.get();
            sumOrSubtractSize(size, sum);
            sizes.update(size);
        }
    }

    private Sizes sumOrSubtractSize(Sizes size, boolean sum) {
        size.setSize(size.getSize() + (sum ? +1 : -1));
        return size;
    }

    private Optional<Sizes> findSize(ToDoTask task) {
        return sizes.getBy("list_id = " + task.getListId());
    }

    @Override
    public Class<ToDoTask> getModelClass() {
        return ToDoTask.class;
    }
}