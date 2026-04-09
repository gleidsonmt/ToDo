package io.github.gleidsonmt.todo.view.panel.items;

import java.time.LocalDate;
import java.util.Optional;

import io.github.gleidsonmt.todo.global.Global;
import io.github.gleidsonmt.todo.global.ListPresenter;
import io.github.gleidsonmt.todo.model.List;
import io.github.gleidsonmt.todo.utils.StringUtils;
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

    private TaskOption taskOption;

    private final BooleanProperty hasTaskOption = new SimpleBooleanProperty();

    private final MyDayOption myDayOption = new MyDayOption();
    private final BooleanProperty hasMyDayOption = new SimpleBooleanProperty();

    private final TodayOption todayOption = new TodayOption();
    private final BooleanProperty hasTodayOption = new SimpleBooleanProperty();

    private final TomorrowOption tomorrowOption = new TomorrowOption();
    private final BooleanProperty hasTomorrowOption = new SimpleBooleanProperty();

    // private final RemindOption remindOption = new RemindOption();
    // private final BooleanProperty hasRemindOption = new
    // SimpleBooleanProperty();

    // private Option myDayOption = new MyDayOption();

    public Options(TaskViewModel viewModel, boolean needTaskOption) {

        this.setMinHeight(10);
        this.setHgap(5);
        this.setVgap(5);

        // hasTaskOption.addListener(createUpdateListener(hasTaskOption));
        hasMyDayOption.addListener(createUpdateListener(myDayOption));
        hasTodayOption.addListener(createUpdateListener(todayOption));
        hasTomorrowOption.addListener(createUpdateListener(tomorrowOption));

        // hasTaskOption.bind();

        hasMyDayOption.bind(viewModel.myDayProperty());

        hasTodayOption.bind(
                viewModel.dueDateProperty().isNotNull().and(viewModel.dueDateProperty().isEqualTo(LocalDate.now())));

        hasTomorrowOption.bind(viewModel.dueDateProperty().isNotNull()
                .and(viewModel.dueDateProperty().isEqualTo(LocalDate.now().plusDays(1))));

        hasTaskOption.bind(viewModel.listIdProperty().greaterThan(-1));

        has.bind(hasTaskOption.or(hasMyDayOption).or(hasTodayOption).or(hasTomorrowOption));
        // has.bind(hasTodayOption.or(hasMyDayOption));

        ListPresenter presenter = (ListPresenter) Global.get(List.class);
        Optional<List> optional = presenter.get(viewModel.getListId());

        if (needTaskOption) {
            taskOption = new TaskOption();
            taskOption.setName(StringUtils.name(optional.get().getName()));
            addOption(taskOption);
        }

        viewModel.listIdProperty().addListener((_, _, val) -> {
//            if (needTaskOption) {
                var newVal = presenter.get(val.longValue());
                taskOption.setName(StringUtils.name(newVal.get().getName()));
//            }

        });

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

        if (option.getIndex() > getChildren().size() - 1) {
            getChildren().add(getChildren().size(), option);
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
