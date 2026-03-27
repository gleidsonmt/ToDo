package io.github.gleidsonmt.todo.global;

import io.github.gleidsonmt.todo.bd.dao.DaoList;
import io.github.gleidsonmt.todo.bd.dao.DaoSizes;
import io.github.gleidsonmt.todo.model.List;
import io.github.gleidsonmt.todo.model.Sizes;

/**
 * Description:
 *
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 *         Created On: Mar 17, 2026
 * 
 *         Version History: Initial version
 */
public class ListPresenter extends AbstractPresenter<List> {

    private DaoSizes sizes;

    public ListPresenter() {
        super(new DaoList());
    }

    public void update(List list) {
        dao.update(list);
    }

    public void store(List list) {
        Sizes si = new Sizes(0);
        si.setSize(0);
        si.setListId(list.getId());
        sizes.store(si);
    }

    @Override
    public Class<List> getModelClass() {
        return List.class;
    }

    public int size(long id) {
        return ((DaoList) dao).getSize(id);
    }

    public int sizeFrom(int limit, long id) {

        // return dao.sizeWhere(limit, " where list_id = " + list.getId());
        return dao.sizeWhere("select * from task where list_id = " + id + " limit " + limit);
    }
}
