package io.github.gleidsonmt.todo.view.aside;

import io.github.gleidsonmt.glad.controls.icon.Icon;
import io.github.gleidsonmt.glad.controls.icon.SVGIcon;
import io.github.gleidsonmt.todo.view.panel.input.menu.input_menu_items.RemindContextMenu;
import io.github.gleidsonmt.todo.view.panel.input.menu.input_menu_items.RepeatContextMenu;
import javafx.geometry.Side;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;

import java.time.LocalDateTime;

/**
 * @author Gleidson Neves da Silveira | <a href="mailto:gleidisonmt@gmail.com">gleidisonmt@gmail.com</a> <br>
 * Created on 17/10/2026
 */
public class RemindLabel extends CustomLabel<LocalDateTime> {

    private ContextMenu contextMenu;

    public RemindLabel() {
        super("Remind me", Icon.CLOCK, "");

        setOnMouseClicked(e -> {
            this.contextMenu = new RemindContextMenu(this);
            this.contextMenu.show(this, Side.BOTTOM, 0,0);
        });
    }
}
