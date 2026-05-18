

package io.github.gleidsonmt.todo.view.panel.containers;

import io.github.gleidsonmt.todo.view.panel.items.FakeTaskItem;
import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.jetbrains.annotations.Contract;
import org.jspecify.annotations.NonNull;

/**
 * Description: Uses the size provided by a height property to create fake lines.
 *
 * @author Gleidson Neves da Silveira | <a href="mailto:gleidisonmt@gmail.com">gleidisonmt@gmail.com</a>
 * Created on  17/05/2026
 */
public class BackgroundLines extends VBox {

    // The height of a ceil is calculated based on a default size
    private final static double cellMediumHeight = 50;

    private void update(double old, double actual) {
        // Divide the actual size to the cell size
        int size = (int) Math.floor((actual) / cellMediumHeight);
        // if the size is zero remove all child and return
        if (size == 0) {
            getChildren().clear();
            return;
        }
        // get the size of the list
        int itemsSize = getChildren().size();

        if (actual > old) {
            if (size > itemsSize) {
                for (int i = 0; i < (size - itemsSize); i++) {
                    getChildren().add(new FakeTaskItem());
                }
            }
        } else {
            if (itemsSize > size) {
                for (int i = itemsSize; i > size; i--) {
                    if (i - 1 > 0) {
                        getChildren().remove(i - 1);
                    }
                }
            }
        }
    }

    @Contract(pure = true)
    private @NonNull ChangeListener<Number> changeListener() {
        return (_, oldValue, newValue) -> update(oldValue.doubleValue(), newValue.doubleValue());
    }

    public BackgroundLines(DoubleProperty listHeight, Insets insets) {
        // posicione in the ui
        StackPane.setMargin(this, insets);
        // bind the height to make this box has the same height as the property passed.
        this.prefHeightProperty().bind(listHeight);
        // add the listener to calc and add fake items
        listHeight.addListener(changeListener());
        // make the first change
        update(0, listHeight.doubleValue());
    }
}
