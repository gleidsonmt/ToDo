

package io.github.gleidsonmt.todo.view.panel.sections;

import io.github.gleidsonmt.glad.controls.icon.Icon;
import io.github.gleidsonmt.glad.controls.icon.SVGIcon;
import javafx.animation.RotateTransition;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import javafx.util.Duration;

/**
 * Description:
 *
 * @author Gleidson Neves da Silveira | <a href="mailto:gleidisonmt@gmail.com">gleidisonmt@gmail.com</a> <br>
 * Created On: Mar 26, 2026
 */
public class SectionTitle extends GridPane {

    private final Label titleLabel = new Label();
    private final Text sizeIndicator = new Text("0");
    private final SVGIcon arrow = new SVGIcon(Icon.CHEVRON_RIGHT);

    private final BooleanProperty collapsed = new SimpleBooleanProperty(false);

    private EventHandler<ActionEvent> onShow;
    private EventHandler<ActionEvent> onHide;

    public SectionTitle(String name, Icon icon) {

        this.titleLabel.setText(name);
        titleLabel.setGraphic(new SVGIcon(icon));

        init();
        configLayout();

        this.setOnMouseClicked(_ -> collapsed.set(!collapsed.get()));
        registerListeners();
    }

    private void registerListeners() {
        collapsedProperty().addListener((_, _, newVal) -> {

            RotateTransition transition = new RotateTransition();
            transition.setNode(this.arrow);
            transition.setDuration(Duration.millis(100));

            if (newVal) { // show the items
                transition.setToAngle(0);
                transition.setFromAngle(90);
                transition.setOnFinished(onShow);
            } else { // hide the items
                transition.setFromAngle(0);
                transition.setToAngle(90);
                transition.setOnFinished(onHide);
            }
            transition.play();
        });
    }

    private void init() {

        this.setCursor(Cursor.HAND);
        this.setMinHeight(50D);
        this.setAlignment(Pos.CENTER);

        this.getStyleClass().add("animated-section-title");

        this.setHgap(10);
        this.setMaxWidth(Region.USE_PREF_SIZE);

        titleLabel.getStyleClass().add("title-label");
        titleLabel.setGraphicTextGap(10);

        sizeIndicator.getStyleClass().add("number-label");

        arrow.setRotate(90);

    }

    private void configLayout() {

        this.getChildren().setAll(arrow, titleLabel, sizeIndicator);

        GridPane.setColumnIndex(arrow, 0);
        GridPane.setColumnIndex(titleLabel, 1);
        GridPane.setColumnIndex(sizeIndicator, 2);

    }

    public void setOnShow(EventHandler<ActionEvent> onShow) {
        this.onShow = onShow;
    }

    public void setOnHide(EventHandler<ActionEvent> onHide) {
        this.onHide = onHide;
    }

    public BooleanProperty collapsedProperty() {
        return this.collapsed;
    }

    public StringProperty sizeIndicatorProperty() {
        return this.sizeIndicator.textProperty();
    }
}
