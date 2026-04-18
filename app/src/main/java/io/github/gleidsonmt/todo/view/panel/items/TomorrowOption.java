package io.github.gleidsonmt.todo.view.panel.items;

import io.github.gleidsonmt.glad.controls.icon.Icon;
import io.github.gleidsonmt.todo.utils.I18n;

/**
 * Description:
 *
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Created On: Mar 30, 2026
 * <p>
 * Version History: Initial version
 */
@Deprecated
public class TomorrowOption extends Option {

    public TomorrowOption() {
        super(3, I18n.get("option.tomorrow"), Icon.DATE_RANGE);
    }

}
