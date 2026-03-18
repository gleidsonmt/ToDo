package io.github.gleidsonmt.todo.global;

import io.github.gleidsonmt.todo.bd.dao.DaoList;
import io.github.gleidsonmt.todo.bd.dao.internal.AbstractDao;
import io.github.gleidsonmt.todo.model.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Description:
 *
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 *         Created On: Mar 17, 2026
 * 
 *         Version History: Initial version
 */
public class RepoList extends RepoSkeleton<DaoList, List> {

    private final ObservableList<List> lists;

    public RepoList(AbstractDao<List> dao) {
        super(dao);
        this.lists = FXCollections.observableArrayList();

    }

    protected void load() {
        super.load(lists);
    }
}
