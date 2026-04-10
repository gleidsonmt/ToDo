package io.github.gleidsonmt.todo.view.panel.items;

import java.time.LocalDate;
import java.util.Optional;

import io.github.gleidsonmt.glad.controls.icon.Icon;
import io.github.gleidsonmt.todo.global.Global;
import io.github.gleidsonmt.todo.global.ListPresenter;
import io.github.gleidsonmt.todo.model.List;
import io.github.gleidsonmt.todo.utils.StringUtils;
import io.github.gleidsonmt.todo.view.panel.menu.input_menu_items.DateUtils;
import io.github.gleidsonmt.todo.view_model.TaskViewModel;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.scene.layout.FlowPane;

/**
 * Description:
 *
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 *         Created On: Mar 28, 2026
 * 
 *         Version History: Initial version
 */
public class Options extends FlowPane {

    private final BooleanProperty has = new SimpleBooleanProperty();
    private final TaskOption taskOption = new TaskOption();
    private final DueDateOption dueDateOption = new DueDateOption();

    // private final RemindOption remindOption = new RemindOption();
    // private final BooleanProperty hasRemindOption = new
    // SimpleBooleanProperty();

    public Options(TaskViewModel viewModel, boolean needTaskOption) {

        this.setMinHeight(10);
        this.setHgap(5);
        this.setVgap(5);

        MyDayOption myDayOption = new MyDayOption();
        BooleanProperty hasMyDayOption = new SimpleBooleanProperty();
        hasMyDayOption.addListener(createUpdateListener(myDayOption));
        BooleanProperty hasDueDateOption = new SimpleBooleanProperty();
        hasDueDateOption.addListener(createUpdateListener(dueDateOption));

        hasMyDayOption.bind(viewModel.myDayProperty());
        hasDueDateOption.bind(viewModel.dueDateProperty().isNotNull());

        BooleanProperty hasTaskOption = new SimpleBooleanProperty();
        has.bind(hasTaskOption.or(hasMyDayOption).or(hasDueDateOption));

        ListPresenter presenter = (ListPresenter) Global.get(List.class);
        Optional<List> optional = presenter.get(viewModel.getListId());

        if (needTaskOption && optional.isPresent()) {
            taskOption.setName(StringUtils.name(optional.get().getName()));
            addOption(taskOption);
        }

        if (viewModel.getDueDate() != null) {
           updateDueDate(viewModel.getDueDate());
        }

        viewModel.listIdProperty().addListener((_, _, val) -> {
                var newVal = presenter.get(val.longValue());
                taskOption.setName(StringUtils.name(newVal.get().getName()));
        });

        viewModel.dueDateProperty().addListener((_, _, val) -> {
            if (val != null) {
                updateDueDate(val);
            }
        });

    }

    private void updateDueDate(LocalDate date) {
        dueDateOption.setName(DateUtils.format(date));
        if (date.equals(LocalDate.now())) {
            dueDateOption.setIcon(Icon.SUN);
        } else if (date.equals(LocalDate.now().plusDays(1))) {
            dueDateOption.setIcon(Icon.DATE_RANGE);
        } else {
            dueDateOption.setIcon(Icon.CALENDAR_MONTH);
        }
    }

    private ChangeListener<Boolean> createUpdateListener(Option option) {
        return (__, _, val) -> {
            if (val) {
                if (!getChildren().contains(option)) {
                    addOption(option);
                }
            } else {
                getChildren().remove(option);
            }
        };
    };

    public void addOption(Option option) {
        if (getChildren().isEmpty()) {
            getChildren().add(option);
            return;
        }

        if (option.getIndex() > getChildren().size()) {
            getChildren().add(getChildren().size(), option);
            updateBullets();
            return;
        }
        if (option.getIndex() == getChildren().size()) {
            getChildren().add(option.getIndex() -1, option);
            updateBullets();
            return;
        }
        getChildren().add(option.getIndex(), option);
        updateBullets();
    }

    public void removeOption(Option option) {
        getChildren().remove(option);
        updateBullets();
    }

    private void updateBullets() {
        getChildren().stream().filter(node -> node instanceof Option).forEach(node -> {
            Option opt = (Option) node;
            if (getChildren().size() > 1) {
                var ind = getChildren().indexOf(opt);
                if (ind != 0) {
                    opt.needsBulletProperty().set(true);
                } else {
                    opt.needsBulletProperty().set(false);
                }
            } else {
                opt.needsBulletProperty().set(false);
            }
        });
    }

    public BooleanProperty hasProperty() {
        return has;
    }
}
