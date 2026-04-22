package io.github.gleidsonmt.todo.utils;

import io.github.gleidsonmt.glad.controls.icon.Icon;
import io.github.gleidsonmt.glad.controls.icon.SVGIcon;
import io.github.gleidsonmt.todo.model.List;
import javafx.scene.Node;
import javafx.scene.image.ImageView;

/**
 *
 * @author Gleidson Neves da Silveira | <gleidisonmt@gmail.com>
 * Created on  22/04/2026
 */
public class IconUtils {

    public static Node getIcon(List list) {
        Node node;
        if (list.isFixed()) {
            node = new SVGIcon(Icon.valueOf(list.getIconName().toUpperCase()));
        } else if (list.getIconName() != null && !list.getIconName().isEmpty()) {
            node = new ImageView(Assets.getIconNew(list.getIconName() + ".png"));
        } else node = new SVGIcon(Icon.CHECK_LIST);
        return node;
    }
}
