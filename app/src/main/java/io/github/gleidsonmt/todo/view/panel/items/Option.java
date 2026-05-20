

package io.github.gleidsonmt.todo.view.panel.items;

import io.github.gleidsonmt.glad.controls.icon.Icon;
import io.github.gleidsonmt.glad.controls.icon.SVGIcon;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import org.kordamp.ikonli.Ikon;
import org.kordamp.ikonli.javafx.FontIcon;

/**
 * Description:
 *
 * @author Gleidson Neves da Silveira | <a href="mailto:gleidisonmt@gmail.com">gleidisonmt@gmail.com</a> <br>
 * Created On: Mar 28, 2026
 * <p>
 * Version History: Initial version
 */
public class Option extends GridPane {

    private final int index;
    private final Text text;
    private Node icon;
    private SVGIcon repeatIcon;
    private final Circle circle = new Circle(3);
    private final BooleanProperty needsBullet = new SimpleBooleanProperty();

    public Option(int index, String text) {
        this(index, text, Icon.NONE);
    }

    public Option(int index, String text, Ikon icon) {
        this(index, text, new FontIcon(icon));
    }

    public Option(int index, String text, Icon icon) {
        this(index, text, new SVGIcon(icon));
    }

    public Option(int index, String text, Node icon) {
        this.index = index;
        this.text = new Text(text);


        if (icon instanceof SVGIcon ik) {
            ik.setScale(0.8);
        } else if (icon instanceof FontIcon fk) {
            fk.setIconSize(18);
        }

        getChildren().addAll(this.text);
        if (icon != null) {
            this.icon = icon;
            getChildren().add(this.icon);
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
            if (repeatIcon != null) GridPane.setColumnIndex(repeatIcon, 2);
        } else {
            GridPane.setColumnIndex(text, 0);
            if (repeatIcon != null) GridPane.setColumnIndex(repeatIcon, 1);
        }
    }

    private void bulletLayout() {
        this.setHgap(5);

        if (!getChildren().contains(circle)) getChildren().add(circle);

        if (icon != null) {
            GridPane.setColumnIndex(circle, 0);
            GridPane.setColumnIndex(icon, 1);
            GridPane.setColumnIndex(text, 2);
            if (repeatIcon != null) GridPane.setColumnIndex(repeatIcon, 3);
//            GridPane.setColumnIndex(repeatIcon, 3);
        } else {
            GridPane.setColumnIndex(circle, 0);
            GridPane.setColumnIndex(text, 1);
            if (repeatIcon != null) GridPane.setColumnIndex(repeatIcon, 2);
//            GridPane.setColumnIndex(repeatIcon, 2);
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

    public void setIcon(Icon icon) {
        if (this.icon instanceof SVGIcon ik) {
            ik.setIcon(icon);
        }
    }

    public void setIcon(Ikon icon) {
        if (this.icon instanceof FontIcon ik) {
            ik.setIconCode(icon);
        }
    }

    public void setNeedRepeatIcon(boolean repeatIcon) {
        if (repeatIcon) {
            this.repeatIcon = new SVGIcon(Icon.SYNC);
            this.repeatIcon.setScale(0.7);
            getChildren().add(this.repeatIcon);
        } else {
            getChildren().add(this.repeatIcon);
        }

        if (needsBullet.get()) {
            bulletLayout();
        } else {
            minLayout();
        }
    }

    @Override
    public String toString() {
        return "Option{" + "\n\tindex=" + index +
               "\n\ttext=" + text +
               "\n\ticon=" + icon +
               "\n\tcircle=" + circle +
               "\n\tneedsBullet=" + needsBullet +
               "\n}";
    }
}
