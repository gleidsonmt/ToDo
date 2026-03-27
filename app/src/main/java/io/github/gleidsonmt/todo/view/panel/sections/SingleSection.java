package io.github.gleidsonmt.todo.view.panel.sections;

import java.util.Optional;

import io.github.gleidsonmt.todo.model.ToDoTask;
import io.github.gleidsonmt.todo.view.panel.containers.ListContainer;
import io.github.gleidsonmt.todo.view.panel.items.TaskItem;
import io.github.gleidsonmt.todo.view_model.TaskViewModel;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.collections.ListChangeListener;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
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

    private double speed = 500;

    protected FilteredList<TaskViewModel> filteredList;
    protected SortedList<TaskViewModel> sortedList;

    protected boolean hasHeader = false;

    public SingleSection() {
        this.getStyleClass().add("single-selection");
        this.setSpacing(5);
    }

    protected Timeline removeAnimation(Node el) {

        Timeline timeline = new Timeline();
        timeline.getKeyFrames().setAll(new KeyFrame(Duration.ZERO, new KeyValue(el.opacityProperty(), 1)),
                new KeyFrame(Duration.millis(speed), new KeyValue(el.opacityProperty(), 0)),
                new KeyFrame(Duration.ZERO, new KeyValue(el.translateXProperty(), 0)),
                new KeyFrame(Duration.millis(speed), new KeyValue(el.translateXProperty(), 150)));

        return timeline;
    }

    /**
     * The animation when the item enters on the list container.
     * 
     * @param el The task item.
     * @return The timeline.
     */
    protected Timeline addAnimation(Node el) {

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
        return getChildren().stream().map(el -> (TaskItem) el).filter(el -> task.getId() == el.getViewModel().getId())
                .findAny().get();
    }

    public void setList(FilteredList<TaskViewModel> list) {
        if (this.filteredList != null)
            this.sortedList.removeListener(updateList);

        this.filteredList = list;
        this.sortedList = new SortedList<>(this.filteredList);
        this.sortedList.forEach(e -> {
            TaskItem item = loadTask(e);
            add(item).play();
        });
        this.sortedList.addListener(updateList);
    }

    protected final ListChangeListener<TaskViewModel> updateList = (ListChangeListener<TaskViewModel>) c -> {

        if (c.next()) {
            System.out.println("next" + c.wasPermutated());
            System.out.println();
            if (c.wasPermutated()) {

                getChildren().remove(hasHeader ? 1 : 0, getChildren().size());

                for (int i = c.getFrom(); i < c.getTo(); i++) {
                    TaskItem taskItem = loadTask(c.getList().get(i));
                    add(taskItem).play();
                }
            }

            if (c.wasAdded()) {
                c.getAddedSubList().forEach(el -> {
                    TaskItem taskItem = loadTask(el);
                    add(taskItem).play();
                });
            } else if (c.wasRemoved()) {
                c.getRemoved().forEach(el -> {
                    delete(el).play();
                });
            }
        }

    };

    private Timeline delete(TaskViewModel task) {

        Optional<TaskItem> optional = getChildren().stream().filter(el -> el instanceof TaskItem)
                .map(map -> (TaskItem) map).filter(el -> {
                    return el.getViewModel().getId() == task.getId();
                }).findAny();

        Timeline animation = null;
        if (optional.isPresent()) {
            animation = removeAnimation(optional.get());
            animation.setOnFinished(e -> this.getChildren().remove(optional.get()));
            animation.play();
        }
        return animation;
    }

    protected TaskItem loadTask(TaskViewModel task) {

        TaskItem taskItem = createTaskItem(task);
        getContainer().getGroup().getToggles().add(taskItem);

        // try {
        // Thread.sleep((long) (sectionIncomplete.getSpeed() / 2));
        // } catch (InterruptedException e1) {

        // }
        // if (task.isCompleted()) {
        // sectionCompleted.getItems().add(taskItem);
        // } else {
        // sectionIncomplete.getChildren().add(0, taskItem);
        // }

        taskItem.setOnCompletedChange((viewModel) -> {

            // loadTask(task);
            // taskItem.getViewModel().setCompleted(!taskItem.getViewModel().isCompleted());

            Optional<TaskViewModel> option = getContainer().getData().stream()
                    .filter(el -> viewModel.getId() == el.getId()).findAny();

            if (option.isPresent()) {
                getContainer().getData().remove(option.get());
                getContainer().getData().add(option.get());
            }

            // Timeline
            // Timeline add = add(taskItem);
            // add.play();

            // var del = delete(viewModel);
            // // del.setOnFinished(e ->
            // // this.getChildren().remove(optional.get()));
            // del.setOnFinished(e -> {
            // add(taskItem);

            // });

            // getChildren().remove(taskItem);

            // ToDoTask element =
            // this.filteredList.getSource().stream().filter(el -> el.getId() ==
            // viewModel.getId())
            // .findAny().get();

            // int index = this.filteredList.getSource().indexOf(task);
            // getContainer().getData().set(index, viewModel);
            // System.out.println("index = " + index);

            // getContainer().getData().add(element);

            // getContainer().getData().removeIf(el -> viewModel.getId() ==
            // el.getId());
            // viewModel.update();
            // sortedList.re
        });
        return taskItem;

    }

    private Timeline add(TaskItem taskItem) {
        var animation = addAnimation(taskItem);
        taskItem.setOpacity(1);
        this.getChildren().add(hasHeader ? 1 : 0, taskItem);
        animation.setOnFinished(e -> getContainer().select(taskItem));
        // animation.play();
        return animation;
    }

    private Timeline remove(TaskItem taskItem) {
        var animation = removeAnimation(taskItem);
        taskItem.setOpacity(0);
        animation.setOnFinished(e -> this.getChildren().remove(taskItem));
        // animation.play();
        return animation;
    }

    public void swap(TaskItem taskItem) {

        var addAnimation = addAnimation(taskItem);
        var removeAnimation = removeAnimation(taskItem);

        // taskItem.setOpacity(0);
        // this.getChildren().add(hasHeader ? 1 : 0, taskItem);

        // addAnimation.setOnFinished(e -> {
        // removeAnimation.play();
        // });

        // var index =
        // getContainer().getData().indexOf(taskItem.getViewModel());
        // getContainer().getData().add(index, taskItem.getViewModel());

        // getContainer().getData().removeIf(el ->
        // taskItem.getViewModel().getId() == el.getId());
        // if (!getContainer().getData().contains(taskItem.getViewModel())) {
        // getContainer().getData().add(taskItem.getViewModel());

        // }

        // removeAnimation.setOnFinished(e -> {
        // taskItem.setOpacity(0);

        // // addAnimation.setOnFinished(_ -> {

        // // });
        // // addAnimation.play();

        // });
        // removeAnimation.play();

    }

    private ListContainer getContainer() {
        return (ListContainer) this.getParent();
    }

    protected TaskItem createTaskItem(TaskViewModel task) {
        TaskItem item = new TaskItem(task);
        item.setId(String.valueOf(task.getId()));
        item.setUserData(task);
        return item;
    }

    public SortedList<TaskViewModel> getSortedList() {
        return this.sortedList;
    }
}
