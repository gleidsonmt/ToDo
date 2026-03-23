package io.github.gleidsonmt.todo.global;

import java.util.Optional;

import io.github.gleidsonmt.todo.bd.dao.DaoSizes;
import io.github.gleidsonmt.todo.bd.dao.DaoTask;
import io.github.gleidsonmt.todo.model.Sizes;
import io.github.gleidsonmt.todo.model.ToDoTask;
import io.github.gleidsonmt.todo.view_model.TaskViewModel;
import io.github.gleidsonmt.todo.view_model.TaskViewModelConverter;

/**
 * Description:
 *
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 *         Created On: Mar 17, 2026
 * 
 *         Version History: Initial version
 */
public class TaskPresenter extends AbstractPresenter<ToDoTask> {

    private final TaskViewModelConverter converter;
    private final DaoSizes sizes;

    public TaskPresenter() {
        super(new DaoTask());
        this.converter = new TaskViewModelConverter();
        this.sizes = new DaoSizes();
    }

    public void update(TaskViewModel viewModel) {
        dao.update(converter.convert(viewModel));

        Optional<Sizes> optional = sizes.getBy("list_id = " + viewModel.getListId());

        if (optional.isPresent()) {

            var size = optional.get();
            if (!viewModel.isCompleted()) {
                size.setSize(size.getSize() + 1);
            } else {
                size.setSize(size.getSize() - 1);
            }
            sizes.update(size);
        }

    }

    public void delete(TaskViewModel model) {
        dao.delete(converter.convert(model));
    }

    @Override
    public Class<ToDoTask> getModelClass() {
        return ToDoTask.class;
    }
}
