package io.github.gleidsonmt.todo.global;

import io.github.gleidsonmt.todo.bd.dao.DaoList;
import io.github.gleidsonmt.todo.bd.dao.DaoSize;
import io.github.gleidsonmt.todo.model.List;
import io.github.gleidsonmt.todo.model.Size;

import java.util.Objects;

/**
 * Description:
 *
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Created On: Mar 17, 2026
 * <p>
 * Version History: Initial version
 */
public class ListPresenter extends AbstractPresenter<List> {

    public ListPresenter() {
        super(new DaoList());
    }

    @Override
    public Class<List> getModelClass() {
        return List.class;
    }

    public int getSize(long id) {
        return Objects.requireNonNull(dao.findWhere("id = " + id).orElse(null)).getSize();
    }

    @Deprecated(forRemoval = true)
    public int size(long id) {
        return ((DaoList) dao).getSize(id);
    }

    @Deprecated(forRemoval = true)
    public int sizeFrom(int limit, long id) {
        return dao.sizeWhere("select * from task where list_id = " + id + " limit " + limit);
    }
}
