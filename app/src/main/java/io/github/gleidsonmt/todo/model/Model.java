package io.github.gleidsonmt.todo.model;

/**
 * Description:
 *
 * @author Gleidson Neves da Silveira | <gleidisonmt@gmail.com>
 *         Created On: Feb 22, 2026
 * 
 *         Version History: Initial version
 */
public class Model {
    private long id;

    public Model(int id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
