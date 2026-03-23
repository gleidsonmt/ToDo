package io.github.gleidsonmt.todo.global;

import io.github.gleidsonmt.todo.bd.dao.DaoList;
import io.github.gleidsonmt.todo.bd.dao.internal.Dao;
import io.github.gleidsonmt.todo.model.List;

/**
 * Description:
 *
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 *         Created On: Mar 17, 2026
 * 
 *         Version History: Initial version
 */
public class ListPresenter extends AbstractPresenter<List> {

    public ListPresenter() {
        super(new DaoList());
    }

    public void update(List list) {
        dao.update(list);
    }

    public void save(List list) {
        dao.store(list);
    }

    public void delete(List list) {
        dao.delete(list);
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
