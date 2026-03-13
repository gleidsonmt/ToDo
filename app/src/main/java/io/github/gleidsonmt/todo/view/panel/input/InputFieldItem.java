package io.github.gleidsonmt.todo.view.panel.input;

import java.time.LocalDate;

import io.github.gleidsonmt.glad.controls.icon.Icon;
import io.github.gleidsonmt.glad.controls.icon.SVGIcon;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Label;

/**
 * Description:
 *
 * @author Gleidson Neves da Silveira | <gleidisonmt@gmail.com>
 *         Created On: Mar 02, 2026
 * 
 *         Version History: Initial version
 */
public class InputFieldItem extends Label {

    protected BooleanProperty selected = new SimpleBooleanProperty(false);
    protected ObjectProperty<LocalDate> dueDate = new SimpleObjectProperty<>();

    public InputFieldItem(Icon icon) {
        this(icon, null);
    }

    public InputFieldItem(Icon icon, String text) {
        setGraphic(new SVGIcon(icon));
        setText(text);
        getStyleClass().add("input-field-item");
        // setWrapText(true);
    }
}
