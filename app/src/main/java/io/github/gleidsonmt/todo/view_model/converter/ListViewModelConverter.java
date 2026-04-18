package io.github.gleidsonmt.todo.view_model.converter;

import io.github.gleidsonmt.todo.model.List;
import io.github.gleidsonmt.todo.view_model.ListViewModel;

/**
 * Description:
 *
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Created On: Mar 20, 2026
 * <p>
 * Version History: Initial version
 */
public class ListViewModelConverter extends ViewModelConverter<List, ListViewModel> {

    public List convert(ListViewModel model) {

        return create(model.getId(),
                model.getName(),
                model.isFixed(),
                model.getNumberOfTasks(),
                model.getIconName());
    }

    public List create(long id, String name, boolean fixed, int size, String iconName) {
        return new List(id, name, fixed, size, iconName);
    }

    @Override
    public ListViewModel toViewModel(List model) {
        return new ListViewModel(model);
    }

    @Override
    public List toModel(ListViewModel model) {
        return new List(
                model.getId(),
                model.getName(),
                model.isFixed(),
                model.getNumberOfTasks(),
                model.getIconName()
        );
    }
}
