package io.github.gleidsonmt.todo.model;

import io.github.gleidsonmt.todo.bd.dao.internal.Ignore;

/**
 * 
 * Description: The base model class for lists.
 * 
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 *         Create on: 2026-02-03
 */
public class List extends Entity {

    // @Ignore
    // private final SimpleListProperty<ToDoTask> items;
    // @Ignore
    // private ObjectProperty<Icon> icon;
    // @Ignore
    // private final SimpleListProperty<List> lists;

    private final int size; // 2
    private final boolean fixed; // 3

    @Ignore
    private String i18nKey;

    // all above needs refactoring

    public List() {
        this(0, null, false, 0);
    }

    public List(String name) {
        this(0, name, false, 0);
    }

    public List(long id, String name, boolean fixed, int size) {
        super(id, name);
        this.fixed = fixed;
        this.size = size;

    }

    public boolean isFixed() {
        return this.fixed;
    }

    public int getSize() {
        return this.size;
    }

    public String getI18nKey() {
        return this.i18nKey;
    }

    @Override
    public String toString() {
        return "List [id=" + super.getId() + ", fixed=" + fixed + "]";
    }
}