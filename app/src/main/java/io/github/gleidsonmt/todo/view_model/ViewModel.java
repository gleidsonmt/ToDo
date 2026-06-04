

package io.github.gleidsonmt.todo.view_model;

import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Description:
 *
 * @author Gleidson Neves da Silveira | <a href="mailto:gleidisonmt@gmail.com">gleidisonmt@gmail.com</a> <br>
 * Created On: Mar 09, 2026
 * <p>
 * Version History: Initial version
 */
public abstract class ViewModel {

    private final StringProperty name = new SimpleStringProperty();
    private final LongProperty id = new SimpleLongProperty();

    public String getName() {
        return name.get();
    }

    public long getId() {
        return id.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public void setId(long id) {
        this.id.set(id);
    }

    public StringProperty nameProperty() {
        return this.name;
    }

    public LongProperty idProperty() {
        return this.id;
    }


    @Override
    public String toString() {
        return "{\"ViewModel\":{"
               + "\"id\":" + id.get()
               + ", \"name\":" + name.get()
               + "}}";
    }


}
