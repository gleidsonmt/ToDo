package io.github.gleidsonmt.todo.view.panel.input;

import io.github.gleidsonmt.glad.controls.icon.Icon;
import io.github.gleidsonmt.glad.controls.icon.SVGIcon;
import io.github.gleidsonmt.todo.model.List;
import io.github.gleidsonmt.todo.model.ToDoTask;
import io.github.gleidsonmt.todo.view.nav.SideNav;
import io.github.gleidsonmt.todo.view_model.TaskViewModel;
import javafx.geometry.Side;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tooltip;
import javafx.stage.PopupWindow;

/**
 * Description:
 *
 * @author Gleidson Neves da Silveira | <gleidisonmt@gmail.com>
 *         Created On: Mar 02, 2026
 * 
 *         Version History: Initial version
 */
public class InputFieldItemTask extends InputFieldItem {

    private final ContextMenu contextMenu;
    private TaskViewModel viewModel;
    private long listId = 0;

    public InputFieldItemTask(Icon icon, String text, ToDoTask viewModel) {
        super(icon, text);
        // this.viewModel = viewModel;

        this.contextMenu = new ContextMenu();

        Tooltip tooltip = new Tooltip("Select a list");
        this.setTooltip(tooltip);

        this.setOnMouseReleased(e -> {
            SideNav nav = (SideNav) getScene().lookup("#drawer");

            contextMenu.getItems().clear();
            contextMenu.getItems().add(createMenuItem(null));
            nav.getCustomLists().forEach(el -> {
                var menuItem = createMenuItem(el);
                contextMenu.getItems().add(menuItem);
            });

            if (contextMenu.isShowing())
                return;
            contextMenu.setAutoFix(true);
            contextMenu.setAnchorLocation(PopupWindow.AnchorLocation.CONTENT_BOTTOM_RIGHT);

            contextMenu.show(this, Side.BOTTOM, 0, 0);
        });

    }

    private MenuItem createMenuItem(List list) {
        var menuItem = new MenuItem(list == null ? "Tasks" : list.getName());
        menuItem.setGraphic(list == null ? new SVGIcon(Icon.HOME) : new SVGIcon(list.getIcon()));
        menuItem.setOnAction(e -> {
            this.setText(list == null ? "Tasks" : list.getName());
            this.setGraphic(list == null ? new SVGIcon(Icon.HOME) : new SVGIcon(list.getIcon()));

            // this.task.setListId(list == null ? 0 : list.getId());
            this.listId = list == null ? 0 : list.getId();
        });
        return menuItem;
    }

    public long getListId() {
        return this.listId;
    }
}
