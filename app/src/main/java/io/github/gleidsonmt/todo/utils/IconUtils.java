package io.github.gleidsonmt.todo.utils;

import com.dlsc.gemsfx.SVGImageView;
import io.github.gleidsonmt.glad.controls.icon.Icon;
import io.github.gleidsonmt.glad.controls.icon.SVGIcon;
import io.github.gleidsonmt.todo.model.List;
import javafx.scene.Node;
import org.jspecify.annotations.NonNull;

/**
 *
 * @author Gleidson Neves da Silveira | <gleidisonmt@gmail.com>
 * Created on  22/04/2026
 */
public class IconUtils {

    public static @NonNull Node getIcon(List list) {
        return getIcon(list, 18);
    }

    public static @NonNull Node getIcon(@NonNull List list, int size) {
        Node node;
        if (list.isFixed()) {
            node = new SVGIcon(Icon.valueOf(list.getIconName().toUpperCase()));
        } else if (list.getIconName() != null && !list.getIconName().isEmpty()) {
            node = Assets.getIcon(list.getIconName(), size);
        } else node = new SVGIcon(Icon.CHECK_LIST);
        return node;
    }

    public static @NonNull String getIconName(@NonNull SVGImageView image) {
        return image.getSvgUrl().substring(image.getSvgUrl().lastIndexOf("/") + 1).replaceAll("\\.(?:svg|jpg)$", "");
    }
}
