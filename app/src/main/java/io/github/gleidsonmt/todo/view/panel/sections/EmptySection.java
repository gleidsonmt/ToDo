

package io.github.gleidsonmt.todo.view.panel.sections;

import io.github.gleidsonmt.todo.view.panel.items.FakeTaskItem;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

/**
 * Description:
 *
 * @author Gleidson Neves da Silveira | <a href="mailto:gleidisonmt@gmail.com">gleidisonmt@gmail.com</a> <br> <br>
 * Created On: Feb 26, 2026
 * <p>
 * Version History: Initial version
 */
@Deprecated
public class EmptySection extends VBox {

    public EmptySection() {
        VBox.setVgrow(this, Priority.ALWAYS);

        this.heightProperty().addListener((observable, oldValue, newValue) -> {

            int size = (int) Math.floor(((newValue.doubleValue())) / 50);
            if (size == 0) {
                this.getChildren().clear();
                return;
            }
            int itemsSize = this.getChildren().size();

            if (newValue.doubleValue() > oldValue.doubleValue()) {
                if (size > itemsSize) {
                    for (int i = 0; i < (size - itemsSize); i++) {
                        this.getChildren().add(new FakeTaskItem());
                    }
                }
            } else {
                if (itemsSize > size) {
                    for (int i = itemsSize; i > size; i--) {
                        if (i - 1 > 0) {
                            this.getChildren().remove(i - 1);
                        }
                    }
                }
            }
        });
    }
}
