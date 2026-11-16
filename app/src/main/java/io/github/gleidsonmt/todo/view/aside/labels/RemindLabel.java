package io.github.gleidsonmt.todo.view.aside.labels;

import io.github.gleidsonmt.glad.controls.icon.Icon;
import io.github.gleidsonmt.todo.utils.I18n;
import io.github.gleidsonmt.todo.view.panel.input.menu.input_menu_items.RemindContextMenu;
import javafx.geometry.Side;
import javafx.scene.control.ContextMenu;

import java.time.LocalDateTime;

/**
 * @author Gleidson Neves da Silveira | <a href="mailto:gleidisonmt@gmail.com">gleidisonmt@gmail.com</a> <br>
 * Created on 17/10/2026
 */
public class RemindLabel extends CustomLabel<LocalDateTime> {

    public RemindLabel() {
        super(I18n.get("aside.remind"), Icon.CLOCK, "");
        setWrapText(true);
        setOnMouseClicked(e -> {
            ContextMenu contextMenu = new RemindContextMenu(this);
            contextMenu.show(this, Side.BOTTOM, 0,0);
        });
    }
}
