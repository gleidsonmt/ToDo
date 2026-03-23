package io.github.gleidsonmt.todo.model;

/**
 * Description:
 *
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 *         Created On: Mar 23, 2026
 * 
 *         Version History: Initial version
 */
public class Sizes extends Model {

    private int size = 0;

    public Sizes(int id) {
        super(id);
    }

    public int getSize() {
        return this.size;
    }

    public void setSize(int size) {
        this.size = size;
    }

}
