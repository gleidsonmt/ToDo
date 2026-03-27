package io.github.gleidsonmt.todo.view.panel.sections;

import io.github.gleidsonmt.glad.controls.icon.Icon;
import io.github.gleidsonmt.todo.model.List;
import io.github.gleidsonmt.todo.utils.I18n;
import io.github.gleidsonmt.todo.view_model.TaskViewModel;
import javafx.beans.binding.Bindings;
import javafx.collections.ListChangeListener;
import javafx.collections.transformation.FilteredList;

/**
 * Description:
 *
 * @author Gleidson Neves da Silveira | <gleidisonmt@gmail.com>
 *         Created On: Feb 26, 2026
 * 
 *         Version History: Initial version
 */
public class AnimatedSection extends SingleSection {

    private SectionTitle title;

    public AnimatedSection() {
        this(I18n.get("panel.tile.title"), Icon.NONE);
    }

    public AnimatedSection(List list) {
        this(list.getName(), list.getIcon());
    }

    public AnimatedSection(String name, Icon icon) {
        this.hasHeader = true;

        title = new SectionTitle(name, icon);
        title.setOnShow(e -> {
            getChildren().remove(1, getChildren().size());
        });

        title.setOnHide(e -> {
            sortedList.stream().forEach(el -> {
                loadTask(el);
            });
        });
    }

    @Override
    public void setList(FilteredList<TaskViewModel> list) {
        if (!list.isEmpty()) {
            getChildren().add(0, this.title);
        }
        list.addListener((ListChangeListener<TaskViewModel>) c -> {
            if (c.next()) {
                if (!c.getList().isEmpty() && !getChildren().contains(title)) {
                    getChildren().add(1, title);
                }
            }
        });
        super.setList(list);
        title.sizeIndicatorProperty().bind(Bindings.convert(Bindings.size(this.sortedList)));
    }
}