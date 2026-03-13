package io.github.gleidsonmt.todo.view.panel.sections;

import io.github.gleidsonmt.glad.controls.icon.Icon;
import io.github.gleidsonmt.glad.controls.icon.SVGIcon;
import io.github.gleidsonmt.todo.model.List;
import io.github.gleidsonmt.todo.model.ToDoTask;
import io.github.gleidsonmt.todo.utils.I18n;
import io.github.gleidsonmt.todo.view.panel.items.TaskItem;
import javafx.animation.RotateTransition;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Duration;

/**
 * Description:
 *
 * @author Gleidson Neves da Silveira | <gleidisonmt@gmail.com>
 *         Created On: Feb 26, 2026
 * 
 *         Version History: Initial version
 */
public class AnimatedSection extends SingleSection {

    private final GridPane titleContainer = new GridPane();
    private final BooleanProperty collapsed = new SimpleBooleanProperty(false);

    protected ObservableList<TaskItem> items = FXCollections.observableArrayList();

    public AnimatedSection() {
        this(I18n.get("panel.tile.title"), Icon.NONE);
    }

    public AnimatedSection(List list) {
        this(list.getName(), list.getIcon());
    }

    public AnimatedSection(String name, Icon icon) {

        Label titleLabel = new Label(name);

        titleLabel.getStyleClass().add("title-label");

        titleContainer.setCursor(Cursor.HAND);
        titleContainer.setMinHeight(50D);
        titleContainer.setAlignment(Pos.CENTER);

        titleLabel.setGraphicTextGap(10);

        // titleLabel.setGraphic(multiple ? new SVGIcon(list.getIcon()) : null);
        SVGIcon arrow = new SVGIcon(Icon.CHEVRON_RIGHT);
        titleLabel.setGraphic(new SVGIcon(icon));

        Text number = new Text("0");

        number.getStyleClass().add("number-label");
        titleContainer.getChildren().addAll(arrow, titleLabel, number);

        number.textProperty().bind(Bindings.convert(Bindings.size(this.getItems())));

        titleContainer.getStyleClass().add("animated-section-title");

        titleContainer.setHgap(10);
        titleContainer.setMaxWidth(Region.USE_PREF_SIZE);

        GridPane.setColumnIndex(arrow, 0);
        GridPane.setColumnIndex(titleLabel, 1);
        GridPane.setColumnIndex(number, 2);

        arrow.setRotate(90);

        collapsed.addListener((_, _, newVal) -> {

            RotateTransition transition = new RotateTransition();
            transition.setNode(arrow);
            transition.setDuration(Duration.millis(100));

            if (newVal) { // remove os items
                transition.setToAngle(0);
                transition.setFromAngle(90);
                transition.setOnFinished(event -> {
                    getChildren().remove(1, getChildren().size());

                });
            } else { // adiciona os items
                transition.setFromAngle(0);
                transition.setToAngle(90);
                transition.setOnFinished(event -> {
                    // getChildren().addAll(getItems());
                    getItems().stream().forEach(el -> {
                        getChildren().add(el);
                        add(el);
                    });
                });
            }
            transition.play();

        });

        items.addListener((ListChangeListener<TaskItem>) c -> {
            if (c.next()) {
                if (!c.getList().isEmpty() && !getChildren().contains(titleContainer)) {
                    getChildren().add(0, titleContainer);
                    VBox.setMargin(titleContainer, new Insets(5, 0, 5, 0));
                }
                if (c.getList().size() < 1) {
                    getChildren().remove(titleContainer);
                }

                if (c.wasAdded()) {
                    if (!collapsed.get()) {
                        c.getAddedSubList().forEach(el -> {
                            getChildren().add(1, el);
                        });
                    }
                } else {
                    if (!collapsed.get()) {
                        c.getRemoved().forEach(el -> {
                            getChildren().remove(el);
                        });
                    }
                }
            }
        });

        titleContainer.setOnMouseClicked(e -> {
            collapsed.set(!collapsed.get());
        });

    }

    @Deprecated
    public boolean contains(ToDoTask task) {
        return getItems().stream().map(el -> (TaskItem) el).anyMatch(el -> el.getTask().equals(task));
    }

    @Override
    public TaskItem get(ToDoTask task) {
        return getItems().stream().filter(el -> task.getId() == el.getViewModel().getId()).findAny().get();
    }

    public ObservableList<TaskItem> getItems() {
        return this.items;
    }
}
