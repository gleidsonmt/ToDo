

package io.github.gleidsonmt.todo.global;

import io.github.gleidsonmt.todo.bd.dao.DaoList;
import io.github.gleidsonmt.todo.model.List;

/**
 * Description:
 *
 * @author Gleidson Neves da Silveira | <a href="mailto:gleidisonmt@gmail.com">gleidisonmt@gmail.com</a> <br>
 * Created On: Mar 17, 2026
 * <p>
 * Version History: Initial version
 */
@Deprecated(forRemoval = true)
public class ListPresenter extends AbstractPresenter<List> {

    public ListPresenter() {
        super(new DaoList());

    }

    @Override
    public Class<List> getModelClass() {
        return List.class;
    }
}
