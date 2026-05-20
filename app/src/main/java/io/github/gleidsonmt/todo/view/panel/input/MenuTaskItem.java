

package io.github.gleidsonmt.todo.view.panel.input;

import io.github.gleidsonmt.glad.controls.icon.Icon;
import io.github.gleidsonmt.glad.controls.icon.SVGIcon;
import io.github.gleidsonmt.todo.model.List;
import io.github.gleidsonmt.todo.utils.Assets;
import io.github.gleidsonmt.todo.utils.IconUtils;
import io.github.gleidsonmt.todo.utils.StringUtils;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;

/**
 *
 * @author Gleidson Neves da Silveira | <a href="mailto:gleidisonmt@gmail.com">gleidisonmt@gmail.com</a> <br> <br>
 * Created on  22/04/2026
 */
public class MenuTaskItem extends MenuItem {

    private final List list;

    public MenuTaskItem(List list) {
        this.list = list;
        setText(StringUtils.name(list.getName()));
        setGraphic(IconUtils.getIcon(list));
    }

    public List getList() {
        return list;
    }

    @Override
    public String toString() {
        return getText();
    }
}
