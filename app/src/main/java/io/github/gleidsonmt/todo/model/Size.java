package io.github.gleidsonmt.todo.model;

/**
 * Description:
 *
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Created On: Mar 23, 2026
 * <p>
 * Version History: Initial version
 */
@Deprecated(forRemoval = true)
public class Size extends Model {

    private long listId = 0;
    private int val = 0;

    public Size(int id) {
        super(id);
    }

    public int getVal() {
        return this.val;
    }

    public void setVal(int size) {
        this.val = size;
    }

    public long getListId() {
        return listId;
    }

    public void setListId(long listId) {
        this.listId = listId;
    }

}
