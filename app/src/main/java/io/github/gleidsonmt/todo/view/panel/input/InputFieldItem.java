

package io.github.gleidsonmt.todo.view.panel.input;

import java.time.LocalDate;

import io.github.gleidsonmt.glad.base.Root;
import io.github.gleidsonmt.glad.controls.icon.Icon;
import io.github.gleidsonmt.glad.controls.icon.SVGIcon;
import io.github.gleidsonmt.todo.view.panel.menu.input_menu_items.CustomContextMenu;
import io.github.gleidsonmt.todo.view_model.ListViewModel;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.concurrent.Task;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;

/**
 * Description:
 *
 * @author Gleidson Neves da Silveira | <a href="mailto:gleidisonmt@gmail.com">gleidisonmt@gmail.com</a> <br>
 * Created On: Mar 02, 2026
 * <p>
 * Version History: Initial version
 */
public class InputFieldItem<T> extends Label {

    protected CustomContextMenu<T> contextMenu;

    protected ObjectProperty<T> value;

    public InputFieldItem(Icon icon) {
        this(icon, null);
    }

    public InputFieldItem(Icon icon, String toolTipText) {

        this.value = new SimpleObjectProperty<>();

        setTooltip(new Tooltip(toolTipText));
        setGraphic(new SVGIcon(icon));
        setText(null);
        getStyleClass().add("input-field-item");

        this.addEventFilter(MouseEvent.MOUSE_CLICKED, _ -> {
            ((Root) getScene().getRoot())
                    .flow().hide();
        });
        // setWrapText(true);
    }

    public void setValue(T value) {
        this.value.set(value);
    }

    public T getValue() {
        return value.get();
    }

    public ObjectProperty<T> valueProperty() {
        return value;
    }
}
