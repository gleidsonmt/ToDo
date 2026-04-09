package io.github.gleidsonmt.todo.global;

import io.github.gleidsonmt.todo.bd.dao.DaoList;
import io.github.gleidsonmt.todo.bd.dao.DaoSize;
import io.github.gleidsonmt.todo.model.List;
import io.github.gleidsonmt.todo.model.Size;

/**
 * Description:
 *
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 *         Created On: Mar 17, 2026
 * 
 *         Version History: Initial version
 */
public class ListPresenter extends AbstractPresenter<List> {

    private final DaoSize daoSize;

    public ListPresenter() {
        super(new DaoList());
        daoSize = new DaoSize();
    }

    @Override
    public long store(List list) {

        long listID = dao.store(list);

        Size size = new Size(0);
        size.setVal(0);
        size.setListId(listID);
        daoSize.store(size);

        return listID;
    }

    @Override
    public Class<List> getModelClass() {
        return List.class;
    }

    public int size(long id) {
        return ((DaoList) dao).getSize(id);
    }

    public int sizeFrom(int limit, long id) {
        return dao.sizeWhere("select * from task where list_id = " + id + " limit " + limit);
    }
}
