package io.github.gleidsonmt.todo.view.panel.input;

import io.github.gleidsonmt.todo.model.List;
import io.github.gleidsonmt.todo.utils.I18n;
import io.github.gleidsonmt.todo.utils.IconUtils;
import io.github.gleidsonmt.todo.utils.StringUtils;
import javafx.scene.control.MenuItem;

/**
 *
 * @author Gleidson Neves da Silveira | <a href="mailto:gleidisonmt@gmail.com">gleidisonmt@gmail.com</a> <br> <br>
 * Created on  22/04/2026
 */
public class MenuTaskItem extends MenuItem {

    private final List list;

    public MenuTaskItem(List list) {
        this.list = list;
        setText(
                StringUtils.isFixedList(list.name()) ?
                I18n.get(list.name()) :
                StringUtils.name(list.name())
        );
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