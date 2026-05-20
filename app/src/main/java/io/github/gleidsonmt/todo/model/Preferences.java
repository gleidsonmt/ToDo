

package io.github.gleidsonmt.todo.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * @author Gleidson Neves da Silveira | <a href="mailto:gleidisonmt@gmail.com">gleidisonmt@gmail.com</a> <br>
 * Create on 01/11/2024
 */
public class Preferences extends Model {

    private final IntegerProperty userId = new SimpleIntegerProperty(); // 4
    private final BooleanProperty smartAll = new SimpleBooleanProperty(); // 3
    private final BooleanProperty smartCompleted = new SimpleBooleanProperty(); // 2
    private final BooleanProperty smartImportant = new SimpleBooleanProperty(); // 1

    public Preferences() {
        this(0, 0);
    }

    public Preferences(int id, int _userId) {
        super(id);
        userId.set(_userId);
    }

    public boolean isSmartImportant() {
        return smartImportant.get();
    }

    public BooleanProperty smartImportantProperty() {
        return smartImportant;
    }

    public void setSmartImportant(boolean smartImportant) {
        this.smartImportant.set(smartImportant);
    }

    public boolean isSmartCompleted() {
        return smartCompleted.get();
    }

    public BooleanProperty smartCompletedProperty() {
        return smartCompleted;
    }

    public boolean isSmartAll() {
        return smartAll.get();
    }

    public BooleanProperty smartAllProperty() {
        return smartAll;
    }

    public void setSmartCompleted(boolean smartCompleted) {
        this.smartCompleted.set(smartCompleted);
    }

    public void setSmartAll(boolean smartAll) {
        this.smartAll.set(smartAll);
    }

    public int getUserId() {
        return userId.get();
    }

    public IntegerProperty userIdProperty() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId.set(userId);
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Preferences{");
        sb.append("userId=").append(userId);
        sb.append(", smartImportant=").append(smartImportant);
        sb.append(", smartCompleted=").append(smartCompleted);
        sb.append(", smartAll=").append(smartAll);
        sb.append('}');
        return sb.toString();
    }
}
