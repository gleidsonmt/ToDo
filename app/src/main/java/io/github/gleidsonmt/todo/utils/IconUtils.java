package io.github.gleidsonmt.todo.utils;

import com.dlsc.gemsfx.SVGImageView;
import io.github.gleidsonmt.glad.controls.icon.Icon;
import io.github.gleidsonmt.glad.controls.icon.SVGIcon;
import io.github.gleidsonmt.todo.model.List;
import io.github.gleidsonmt.todo.view_model.ListViewModel;
import javafx.scene.Node;
import javafx.scene.image.Image;
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
            node = Assets.getIcon(list.getIconName());
        } else node = new SVGIcon(Icon.CHECK_LIST);
        return node;
    }

    public static Node getIcon(ListViewModel list) {
        Node node;
        if (list.isFixed()) {
            node = new SVGIcon(Icon.valueOf(list.getIconName().toUpperCase()));
        } else if (list.getIconName() != null && !list.getIconName().isEmpty()) {
            node = Assets.getIcon(list.getIconName());
        } else node = new SVGIcon(Icon.CHECK_LIST);
        return node;
    }

    public static String getIconName(Image image) {
        return image.getUrl().substring(image.getUrl().lastIndexOf("/") + 1).replaceAll("\\.(?:png|jpg)$", "");
    }

    public static String getIconName(SVGImageView image) {
        return image.getSvgUrl().substring(image.getSvgUrl().lastIndexOf("/") + 1).replaceAll("\\.(?:svg|jpg)$", "");
    }
}
