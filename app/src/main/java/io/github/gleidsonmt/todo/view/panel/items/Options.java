package io.github.gleidsonmt.todo.view.panel.items;

import io.github.gleidsonmt.todo.view_model.TaskViewModel;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.layout.FlowPane;

/**
 * Description:
 *
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 *         Created On: Mar 28, 2026
 * 
 *         Version History: Initial version
 */
public class Options extends FlowPane {

    private final BooleanProperty has = new SimpleBooleanProperty();

    private final MyDayOption myDayOption = new MyDayOption();
    private final BooleanProperty hasMyDayOption = new SimpleBooleanProperty();

    private final RemindOption remindOption = new RemindOption();
    private final BooleanProperty hasRemindOption = new SimpleBooleanProperty();

    // private Option myDayOption = new MyDayOption();

    public Options(TaskViewModel viewModel) {

        hasMyDayOption.bind(viewModel.myDayProperty());
        hasRemindOption.bind(viewModel.remindProperty().isNotNull());

        has.bind(hasMyDayOption.or(hasRemindOption));

        this.setMinHeight(10);
        this.setHgap(5);
        this.setVgap(5);

        hasMyDayOption.addListener((_, _, val) -> {
            if (val) {
                if (!getChildren().contains(myDayOption)) {
                    getChildren().add(myDayOption);
                }
            } else {
                getChildren().remove(myDayOption);
            }
        });

        hasRemindOption.addListener((_, _, val) -> {
            if (val) {
                if (!getChildren().contains(remindOption)) {
                    getChildren().add(remindOption);
                }
            } else {
                getChildren().remove(remindOption);
            }
        });
    }

    public BooleanProperty hasProperty() {
        return has;
    }
}
