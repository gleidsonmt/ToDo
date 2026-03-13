package io.github.gleidsonmt.todo.view.panel.sections;

import io.github.gleidsonmt.todo.model.ToDoTask;
import io.github.gleidsonmt.todo.view.panel.items.TaskItem;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.collections.ListChangeListener;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

/**
 * Description:
 *
 * @author Gleidson Neves da Silveira | <gleidisonmt@gmail.com>
 *         Created On: Feb 26, 2026
 * 
 *         Version History: Initial version
 */
public class SingleSection extends VBox {

    // In the class, add a field for items (if not already present)

    private double speed = 200;

    protected boolean sync = true;

    public SingleSection() {
        this.getStyleClass().add("single-selection");
        this.setSpacing(5);

        // Sync items list with children
        this.getChildren().addListener((ListChangeListener<Node>) c -> {
            while (c.next()) {
                // if (!sync)
                // return;
                if (c.wasAdded()) {
                    c.getAddedSubList().forEach(el -> {
                        // if (el instanceof TaskItem task &&
                        // !items.contains(task)) {
                        if (el instanceof TaskItem task) {
                            // items.add(task);
                            add(task).play(); // Assuming add() is a method
                            // on
                        }
                    });
                } else if (c.wasRemoved()) {
                    c.getRemoved().forEach(el -> {
                        if (el instanceof TaskItem taskItem) {
                            // items.remove(taskItem);
                        }
                    });
                }
            }
        });
    }

    /**
     * The animation when the item enters on the list container.
     * 
     * @param el The task item.
     * @return The timeline.
     */
    protected Timeline add(Node el) {

        Timeline timeline = new Timeline();
        timeline.getKeyFrames().setAll(new KeyFrame(Duration.ZERO, new KeyValue(el.opacityProperty(), 0)),
                new KeyFrame(Duration.millis(speed), new KeyValue(el.opacityProperty(), 1)),
                new KeyFrame(Duration.ZERO, new KeyValue(el.translateXProperty(), -150)),
                new KeyFrame(Duration.millis(speed), new KeyValue(el.translateXProperty(), 0)));

        return timeline;
    }

    public double getSpeed() {
        return speed;
    }

    public TaskItem get(ToDoTask task) {
        // return getItems().stream().map(el -> (TaskItem) el).anyMatch(el ->
        // el.getTask().equals(task));
        return getChildren().stream().map(el -> (TaskItem) el).filter(el -> task.getId() == el.getViewModel().getId())
                .findAny().get();
    }
}
