

package io.github.gleidsonmt.todo.view.panel.items;

import io.github.gleidsonmt.glad.controls.icon.Icon;
import io.github.gleidsonmt.todo.utils.I18n;

/**
 * Description:
 *
 * @author Gleidson Neves da Silveira | <a href="mailto:gleidisonmt@gmail.com">gleidisonmt@gmail.com</a>
 * Created On: Mar 28, 2026
 * <p>
 * Version History: Initial version
 */
public class MyDayOption extends Option {

    public MyDayOption() {
        super(1, I18n.get("drawer.list.my_day"), Icon.SUN);
    }

}
