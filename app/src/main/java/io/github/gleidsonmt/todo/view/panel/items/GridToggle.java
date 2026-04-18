package io.github.gleidsonmt.todo.view.panel.items;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.BooleanPropertyBase;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.css.PseudoClass;
import javafx.scene.AccessibleAttribute;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;

/**
 * Description: Custom GridPane that implements Toggle interface.
 * Used as base for TaskItem to allow selection of the entire item.
 * Grid toggles was created because it's almost impossible to achieve a toogle
 * button that grows its height as the content increases.
 *
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Created On: Mar 30, 2026
 * <p>
 * Version History: Initial version
 */
public class GridToggle extends GridPane implements Toggle {

    private ObjectProperty<ToggleGroup> toggleGroup;
    private BooleanProperty selected;

    PseudoClass PSEUDO_CLASS_SELECTED = PseudoClass.getPseudoClass("selected");

    @Override
    public ToggleGroup getToggleGroup() {
        return toggleGroup == null ? null : toggleGroup.get();
    }

    @Override
    public void setToggleGroup(ToggleGroup value) {
        toggleGroupProperty().set(value);
    }

    @Override
    public ObjectProperty<ToggleGroup> toggleGroupProperty() {
        if (toggleGroup == null) {
            toggleGroup = new SimpleObjectProperty<>(this, "toggleGroup");
        }
        return this.toggleGroup;
    }

    @Override
    public boolean isSelected() {
        return selected == null ? false : selected.get();
    }

    @Override
    public void setSelected(boolean selected) {
        this.selectedProperty().set(selected);
    }

    @Override
    public BooleanProperty selectedProperty() {
        if (selected == null) {
            selected = new BooleanPropertyBase() {
                @Override
                protected void invalidated() {
                    final boolean selected = get();
                    final ToggleGroup tg = getToggleGroup();
                    // Note: these changes need to be done before
                    // selectToggle/clearSelectedToggle since
                    // those operations change properties and can execute user
                    // code, possibly modifying selected property again
                    pseudoClassStateChanged(PSEUDO_CLASS_SELECTED, selected);
                    notifyAccessibleAttributeChanged(AccessibleAttribute.SELECTED);
                    if (tg != null) {
                        if (selected) {
                            tg.selectToggle(GridToggle.this);
                        } else if (tg.getSelectedToggle() == GridToggle.this) {
                            // tg.clearSelectedToggle();
                            if (!tg.getSelectedToggle().isSelected()) {
                                for (Toggle toggle : tg.getToggles()) {
                                    if (toggle.isSelected()) {
                                        return;
                                    }
                                }
                            }
                            toggleGroup.get().selectToggle(null);
                        }
                    }
                }

                @Override
                public Object getBean() {
                    return GridToggle.this;
                }

                @Override
                public String getName() {
                    return "selected";
                }
            };
        }
        return selected;
    }

}
