

package io.github.gleidsonmt.todo.global;

import io.github.gleidsonmt.todo.bd.dao.DaoList;
import io.github.gleidsonmt.todo.model.ListOld;

/**
 * Description:
 *
 * @author Gleidson Neves da Silveira | <a href="mailto:gleidisonmt@gmail.com">gleidisonmt@gmail.com</a> <br>
 * Created On: Mar 17, 2026
 * <p>
 * Version History: Initial version
 */
public class ListPresenter extends AbstractPresenter<ListOld> {

    public ListPresenter() {
        super(new DaoList());
    }

    @Override
    public Class<ListOld> getModelClass() {
        return ListOld.class;
    }
}
