package io.github.gleidsonmt.todo.view_model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Description:
 *
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 *         Created On: Mar 09, 2026
 * 
 *         Version History: Initial version
 */
public class Model {

    private final StringProperty name = new SimpleStringProperty();
    private final IntegerProperty id = new SimpleIntegerProperty();

    public String getName() {
        return name.get();
    }

    public int getId() {
        return id.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public StringProperty nameProperty() {
        return this.name;
    }

}
