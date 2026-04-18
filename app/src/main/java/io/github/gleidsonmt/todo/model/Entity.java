package io.github.gleidsonmt.todo.model;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on 02/03/2024
 */
public class Entity extends Model {

    private final String name;

    public Entity(long id, String name) {
        super(id);
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
