package io.github.gleidsonmt.todo.view.panel.input;

import io.github.gleidsonmt.glad.base.Root;
import io.github.gleidsonmt.glad.controls.icon.Icon;
import io.github.gleidsonmt.glad.controls.icon.SVGIcon;
import io.github.gleidsonmt.todo.model.List;
import io.github.gleidsonmt.todo.model.RecurrenceViewModel;
import io.github.gleidsonmt.todo.utils.I18n;
import io.github.gleidsonmt.todo.utils.IconUtils;
import io.github.gleidsonmt.todo.utils.StringUtils;
import io.github.gleidsonmt.todo.view.panel.input.menu.input_menu_items.CustomContextMenu;
import io.github.gleidsonmt.todo.view.panel.input.menu.input_menu_items.DateUtils;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.css.PseudoClass;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Description:
 *
 * @author Gleidson Neves da Silveira | <a href="mailto:gleidisonmt@gmail.com">gleidisonmt@gmail.com</a> <br> <br>
 * Created On: Mar 02, 2026
 * <p>
 * Version History: Initial version
 */
public class InputFieldItem<T> extends Label {

    protected CustomContextMenu<T> contextMenu;
    protected ObjectProperty<T> value;
    protected boolean center;
    private final String placeholder;

    public InputFieldItem(Icon icon, String toolTipText) {
        this(null, icon, toolTipText);
    }

    public InputFieldItem(String placeholder, Icon icon, String toolTipText) {

        this.value = new SimpleObjectProperty<>();
        setTooltip(new Tooltip(toolTipText));
        setGraphic(new SVGIcon(icon));
        this.placeholder = placeholder;
        setText(placeholder);
        getStyleClass().add("input-field-item");

        this.addEventFilter(MouseEvent.MOUSE_CLICKED, _ -> {
            ((Root) getScene().getRoot())
                    .flow().hide();
        });

        value.addListener((_, _, val) -> {
                if (icon.equals(Icon.HOME)) return;
                pseudoClassStateChanged(PseudoClass.getPseudoClass("selected"), val != null);

                if (val != null) {
                    if (val instanceof LocalDate date) {
                        setText(DateUtils.format(date));
                        getTooltip().setText("");
                    }
                    if (val instanceof LocalDateTime dateTime) {
                        setText(DateUtils.format("Remind at ", dateTime.toLocalTime(), " at ", dateTime.toLocalDate()));
                    }
                    if (val instanceof RecurrenceViewModel recurrence) {
                        setText(DateUtils.format(recurrence));
                    }
                    if (val instanceof List list) {
                        setText(
                                StringUtils.isFixedList(list.name()) ?
                                        I18n.get(list.name()) :
                                        StringUtils.name(list.name())
                        );
                        setGraphic(IconUtils.getIcon(list));
                    }
                } else {
                    reset();
                }
        });

//         setWrapText(true);

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

    public void setCenter(boolean val) {
        this.center = val;
    }

    public boolean isCenter() {
        return this.center;
    }


    public String getPlaceholder() {
        return placeholder;
    }

    public void reset() {
        setText(placeholder);
    }
}