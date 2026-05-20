

package io.github.gleidsonmt.todo.view.panel.items;

import io.github.gleidsonmt.glad.controls.icon.Icon;
import io.github.gleidsonmt.todo.utils.I18n;

/**
 * Description:
 *
 * @author Gleidson Neves da Silveira | <a href="mailto:gleidisonmt@gmail.com">gleidisonmt@gmail.com</a> <br>
 * Created On: Mar 31, 2026
 * <p>
 * Version History: Initial version
 */
public class TodayOption extends Option {

    public TodayOption() {
        super(2, I18n.get("option.today"), Icon.TODAY);
    }

}
