package io.github.gleidsonmt.todo.global;

import java.util.Optional;

import io.github.gleidsonmt.todo.bd.dao.DaoSize;
import io.github.gleidsonmt.todo.model.Size;

/**
 * Description:
 *
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Created On: Apr 02, 2026
 * <p>
 * Version History: Initial version
 */
@Deprecated(forRemoval = true)
public class SizePresenter extends AbstractPresenter<Size> {

    public SizePresenter() {
        super(new DaoSize());
    }

    public Size getListSize(long listID) {
        Optional<Size> optional = dao.getBy("list_id = " + listID);
        if (optional.isPresent()) {
            return optional.get();
        } else {
            return null;
        }
    }

    @Override
    public Class<Size> getModelClass() {
        return Size.class;
    }
}
