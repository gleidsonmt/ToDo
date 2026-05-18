

package io.github.gleidsonmt.todo.view_model.converter;

import io.github.gleidsonmt.todo.model.List;
import io.github.gleidsonmt.todo.view_model.ListViewModel;

/**
 * Description:
 *
 * @author Gleidson Neves da Silveira | <a href="mailto:gleidisonmt@gmail.com">gleidisonmt@gmail.com</a>
 * Created On: Mar 20, 2026
 * <p>
 * Version History: Initial version
 */
public class ListViewModelConverter extends ViewModelConverter<List, ListViewModel> {

    @Deprecated
    public List convert(ListViewModel model) {

        return create(model.getId(),
                model.getName(),
                model.isFixed(),
                model.getNumberOfTasks(),
                model.getIconName());
    }

    @Deprecated
    public List create(long id, String name, boolean fixed, int size, String iconName) {
        return new List(id, name, fixed, size, iconName);
    }

    @Override
    public ListViewModel toViewModel(List model) {
        return new ListViewModel(model);
    }

    @Override
    public List toModel(ListViewModel model) {
        var _name = "";
        if (model.isFixed()) {
            _name = model.getKeyName();
        } else _name = model.getName();

        return new List(
                model.getId(),
                _name,
                model.isFixed(),
                model.getNumberOfTasks(),
                model.getIconName()
        );
    }
}
