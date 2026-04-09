package io.github.gleidsonmt.todo.view.panel.items;

import io.github.gleidsonmt.glad.controls.icon.Icon;
import io.github.gleidsonmt.glad.controls.icon.SVGIcon;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

/**
 * Description:
 *
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 *         Created On: Mar 28, 2026
 * 
 *         Version History: Initial version
 */
public class Option extends GridPane {

    private int index;
    private Text text;
    private SVGIcon icon;
    private Circle circle = new Circle(3);
    private BooleanProperty needsBullet = new SimpleBooleanProperty();

    public Option(int index, String text) {
        this(index, text, Icon.NONE);
    }

    public Option(int index, String text, Icon icon) {
        this.index = index;
        this.text = new Text(text);

        getChildren().addAll(this.text);
        if (icon != null) {
            this.icon = new SVGIcon(icon);
            getChildren().add(this.icon);
            this.icon.setScale(0.8);
        }
        setFocusTraversable(false);

        circle.setStyle("-fx-fill: -fx-accent;");

        needsBullet.addListener((_, _, val) -> {
            if (val) {
                bulletLayout();
            } else {
                minLayout();
            }
        });
        if (needsBullet.get()) {
            bulletLayout();
        } else {
            minLayout();
        }
    }

    private void minLayout() {
        this.setHgap(5);
        getChildren().remove(circle);
        if (icon != null) {
            GridPane.setColumnIndex(icon, 0);
            GridPane.setColumnIndex(text, 1);

        } else {
            GridPane.setColumnIndex(text, 0);
        }
    }

    private void bulletLayout() {
        this.setHgap(5);

        getChildren().add(circle);
        if (icon != null) {
            GridPane.setColumnIndex(circle, 0);
            GridPane.setColumnIndex(icon, 1);
            GridPane.setColumnIndex(text, 2);
        } else {
            GridPane.setColumnIndex(circle, 0);
            GridPane.setColumnIndex(text, 1);
        }

    }

    public String getName() {
        return text.getText();
    }

    public BooleanProperty needsBulletProperty() {
        return needsBullet;
    }

    public int getIndex() {
        return index;
    }

    public void setName(String name) {
        this.text.setText(name);
    }

}
