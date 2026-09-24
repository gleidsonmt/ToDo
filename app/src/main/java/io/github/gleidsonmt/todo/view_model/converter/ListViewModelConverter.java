

package io.github.gleidsonmt.todo.view_model.converter;

import io.github.gleidsonmt.todo.model.ListOld;
import io.github.gleidsonmt.todo.view_model.ListViewModel;

/**
 * Description:
 *
 * @author Gleidson Neves da Silveira | <a href="mailto:gleidisonmt@gmail.com">gleidisonmt@gmail.com</a> <br>
 * Created On: Mar 20, 2026
 * <p>
 * Version History: Initial version
 */
public class ListViewModelConverter extends ViewModelConverter<ListOld, ListViewModel> {

    @Deprecated
    public ListOld convert(ListViewModel model) {

        return create(model.getId(),
                model.getName(),
                model.isFixed(),
                model.getNumberOfTasks(),
                model.getIconName());
    }

    @Deprecated
    public ListOld create(long id, String name, boolean fixed, int size, String iconName) {
        return new ListOld(id, name, fixed, size, iconName);
    }

    @Override
    public ListViewModel toViewModel(ListOld model) {
        return new ListViewModel(model);
    }

    @Override
    public ListOld toEntity(ListViewModel model) {
        var _name = "";
        if (model.isFixed()) {
            _name = model.getKeyName();
        } else _name = model.getName();

        return new ListOld(
                model.getId(),
                _name,
                model.isFixed(),
                model.getNumberOfTasks(),
                model.getIconName()
        );
    }
}
