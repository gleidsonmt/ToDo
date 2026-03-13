package io.github.gleidsonmt.todo.model;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 *         Create on 02/03/2024
 */
public class Entity extends Model {

    private String name;

    public Entity() {
        this(0, null);
    }

    public Entity(int id, String name) {
        super(id);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // public String toString() {
    // final StringBuffer sb = new StringBuffer("Entity{");
    // sb.append("name=").append(name);
    // sb.append('}');
    // return sb.toString();
    // }

}
