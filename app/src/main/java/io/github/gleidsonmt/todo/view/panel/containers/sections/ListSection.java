package io.github.gleidsonmt.todo.view.panel.containers.sections;

import com.github.weisj.jsvg.nodes.SVG;
import io.github.gleidsonmt.glad.controls.icon.Icon;
import io.github.gleidsonmt.glad.controls.icon.SVGIcon;
import io.github.gleidsonmt.todo.utils.Assets;
import io.github.gleidsonmt.todo.utils.I18n;
import io.github.gleidsonmt.todo.view_model.ListViewModel;
import javafx.collections.ObservableList;

/**
 * The same as animated section but with more restrict information about its list.
 * @author Gleidson Neves da Silveira | <a href="mailto:gleidisonmt@gmail.com">gleidisonmt@gmail.com</a> <br>
 * Created on 03/11/2026
 */
public class ListSection extends AnimatedSection {

    public ListSection(ListViewModel viewModel) {
        super(viewModel,
                viewModel.isFixed() ? I18n.get( viewModel.getName()) : viewModel.getName(),
                viewModel.isFixed() ?
                        new SVGIcon(Icon.valueOf(viewModel.getIcon().toUpperCase())) :
                                Assets.getIcon(viewModel.getIcon().toLowerCase(), 22)
        );
    }

}
