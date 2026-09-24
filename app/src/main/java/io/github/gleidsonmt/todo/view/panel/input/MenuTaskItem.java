

package io.github.gleidsonmt.todo.view.panel.input;

import io.github.gleidsonmt.todo.model.ListOld;
import io.github.gleidsonmt.todo.utils.IconUtils;
import io.github.gleidsonmt.todo.utils.StringUtils;
import javafx.scene.control.MenuItem;

/**
 *
 * @author Gleidson Neves da Silveira | <a href="mailto:gleidisonmt@gmail.com">gleidisonmt@gmail.com</a> <br> <br>
 * Created on  22/04/2026
 */
public class MenuTaskItem extends MenuItem {

    private final ListOld list;

    public MenuTaskItem(ListOld list) {
        this.list = list;
        setText(StringUtils.name(list.getName()));
        setGraphic(IconUtils.getIcon(list));
    }

    public ListOld getList() {
        return list;
    }

    @Override
    public String toString() {
        return getText();
    }
}
