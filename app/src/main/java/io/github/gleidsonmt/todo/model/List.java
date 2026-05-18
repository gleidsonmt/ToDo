

package io.github.gleidsonmt.todo.model;


/**
 *
 * Description: The base model class for lists.
 *
 * @author Gleidson Neves da Silveira | <a href="mailto:gleidisonmt@gmail.com">gleidisonmt@gmail.com</a> <br>
 * Create on: 2026-02-03
 */
public class List extends Entity {

    private final String iconName;
    private final int size;
    private final boolean fixed;

    public List(long id, String name, boolean fixed, int size, String iconName) {
        super(id, name);
        this.fixed = fixed;
        this.size = size;
        this.iconName = iconName == null ? "check-list" : iconName;
    }

    public boolean isFixed() {
        return this.fixed;
    }

    public int getSize() {
        return this.size;
    }

    public String getIconName() {
        return this.iconName;
    }

    @Override
    public String toString() {
        return "List [id=" + super.getId() + ", name=" + getName() + ", fixed=" + fixed + "]";
    }
}