package io.github.gleidsonmt.todo.view.panel.containers.sections;

import java.util.Optional;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.IntegerBinding;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.ObservableList;
import javafx.scene.layout.Pane;
import org.jetbrains.annotations.ApiStatus.Experimental;

import io.github.gleidsonmt.todo.view.panel.containers.ListContainer;
import io.github.gleidsonmt.todo.view.panel.items.TaskItem;
import io.github.gleidsonmt.todo.view_model.ListViewModel;
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
 * @author Gleidson Neves da Silveira | <a href="mailto:gleidisonmt@gmail.com">gleidisonmt@gmail.com</a> <br> <br>
 * Created On: Feb 26, 2026
 * <p>
 * Version History: Initial version
 */
public class SingleSection extends VBox {

    private final double speed = 200;

    protected FilteredList<TaskViewModel> filteredList;
    protected SortedList<TaskViewModel> sortedList;

    private final IntegerProperty size = new SimpleIntegerProperty();

    protected boolean hasHeader = false;

    private final ListViewModel listViewModel;

    public SingleSection(ListViewModel listViewModel) {
        this.listViewModel = listViewModel;
        this.getStyleClass().add("single-selection");
        this.setSpacing(5);
        this.parentProperty().addListener((_, _, parent) -> {
            if (parent instanceof ListContainer container) {
                getChildren().stream()
                        .filter(TaskItem.class::isInstance)
                        .map(TaskItem.class::cast)
                        .forEach(taskItem -> taskItem.setToggleGroup(container.getGroup()));
            }
        });
    }

    @Experimental
    protected Timeline removeAnimation(Node el) {

        Timeline timeline = new Timeline();
        timeline.getKeyFrames().setAll(new KeyFrame(Duration.ZERO, new KeyValue(el.opacityProperty(), 1)),
                new KeyFrame(Duration.millis(speed), new KeyValue(el.opacityProperty(), 0)));
        // new KeyFrame(Duration.ZERO, new KeyValue(el.translateXProperty(),
        // 0)),
        // new KeyFrame(Duration.millis(speed), new
        // KeyValue(el.translateXProperty(), 500)));

        return timeline;
    }

    /**
     * The animation when the item enters on the list container.
     *
     * @param el The task item.
     * @return The timeline.
     */
    @Experimental
    protected Timeline addAnimation(Node el) {

        Timeline timeline = new Timeline();
        timeline.getKeyFrames().setAll(new KeyFrame(Duration.ZERO, new KeyValue(el.opacityProperty(), 0)),
                new KeyFrame(Duration.millis(speed), new KeyValue(el.opacityProperty(), 1)));
        // new KeyFrame(Duration.ZERO, new KeyValue(el.translateXProperty(),
        // -500)),
        // new KeyFrame(Duration.millis(speed), new
        // KeyValue(el.translateXProperty(), 0)));

        return timeline;
    }

    public void setList(FilteredList<TaskViewModel> list) {
        if (this.filteredList != null)
            this.sortedList.removeListener(updateList);

        this.filteredList = list;
        this.sortedList = new SortedList<>(this.filteredList);
        this.sortedList.forEach(e -> {
            TaskItem item = loadTask(e);
            add(item);
        });
        size.unbind();
        size.bind(Bindings.size(this.sortedList));
        // this.filteredList.addListener(updateList);
        this.sortedList.addListener(updateList);
        // getContainer().getData().addListener(updateList);
    }

    public IntegerBinding sizeProperty() {
        return Bindings.size(this.sortedList);
    }

    public  int size() {
        return size.get();
    }

    @Experimental
    protected final ListChangeListener<TaskViewModel> updateList = c -> {

        if (c.next()) {
            if (c.wasPermutated()) { // used for comparators

                getChildren().remove(hasHeader ? 1 : 0, getChildren().size());

                for (int i = c.getFrom(); i < c.getTo(); i++) {
                    TaskItem taskItem = loadTask(c.getList().get(i));
                    add(taskItem);
                }
            }


            if (c.wasAdded()) {
                c.getAddedSubList().forEach(el -> {
                    TaskItem taskItem = loadTask(el);

                    add(taskItem);
                });

            }
            if (c.wasRemoved()) {
                c.getRemoved().forEach(this::delete);
//                Platform.runLater(() -> {
//                    ((ListContainer)getParent()).getChildren().remove(this);
//
//                });
            }
        }

    };

    @Experimental
    protected TaskItem loadTask(TaskViewModel viewModel) {
        TaskItem taskItem = new TaskItem(viewModel, listViewModel);
        if (getParent() instanceof ListContainer container) {
            taskItem.setToggleGroup(container.getGroup());
        }
        return taskItem;
    }

    private ListContainer getContainer() {
        return (ListContainer) this.getParent();
    }

    @Experimental
    private void delete(TaskViewModel task) {

        Optional<TaskItem> optional = getChildren().stream().filter(el -> el instanceof TaskItem)
                .map(map -> (TaskItem) map).filter(el -> el.getViewModel().getId() == task.getId()).findAny();

        optional.ifPresent(this::remove);
    }

    @Experimental
    protected void add(TaskItem taskItem) {
        this.getChildren().add(hasHeader ? 1 : 0, taskItem);
        taskItem.setOpacity(0);
        var animation = addAnimation(taskItem);
        // animation.setOnFinished(e -> getContainer().select(taskItem));
        animation.play();
        // return animation;
    }

    @Experimental
    private void remove(TaskItem taskItem) {
        var animation = removeAnimation(taskItem);
        taskItem.setOpacity(1);
        animation.setOnFinished(_ -> this.getChildren().remove(taskItem));
        animation.play();
    }

    public SortedList<TaskViewModel> getSortedList() {
        return this.sortedList;
    }

    public static int size(ObservableList<?> objects) {
        var size = 0;
        for (Object object : objects) {
            if (object instanceof TaskViewModel) {
                size++;
            }
        }
        return size;
    }
}
