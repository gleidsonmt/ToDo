package io.github.gleidsonmt.todo.view.panel.items;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import io.github.gleidsonmt.glad.controls.icon.Icon;
import io.github.gleidsonmt.todo.global.Global;
import io.github.gleidsonmt.todo.global.ListPresenter;
import io.github.gleidsonmt.todo.global.TaskPresenter;
import io.github.gleidsonmt.todo.model.List;
import io.github.gleidsonmt.todo.model.ToDoTask;
import io.github.gleidsonmt.todo.model.recurrence.Recurrence;
import io.github.gleidsonmt.todo.utils.StringUtils;
import io.github.gleidsonmt.todo.view.panel.menu.input_menu_items.DateUtils;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.scene.layout.FlowPane;

/**
 * Description:
 *
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Created On: Mar 28, 2026
 */
public class Options extends FlowPane {

    private final BooleanProperty has = new SimpleBooleanProperty();
    private final TaskOption taskOption = new TaskOption();
    private final DueDateOption dueDateOption = new DueDateOption();

    private final RemindOption remindOption;

    public Options(TaskItem item) {

        this.setMinHeight(10);
        this.setHgap(5);

        TaskPresenter taskPresenter = (TaskPresenter) Global.get(ToDoTask.class);
        Optional<Recurrence> recurrence = taskPresenter.getRecurrence(item.getViewModel().getId());

        MyDayOption myDayOption = new MyDayOption();
        BooleanProperty hasMyDayOption = new SimpleBooleanProperty();
        hasMyDayOption.addListener(createUpdateListener(myDayOption));
        hasMyDayOption.bind(item.getViewModel().myDayProperty());

        BooleanProperty hasDueDateOption = new SimpleBooleanProperty();
        hasDueDateOption.addListener(createUpdateListener(dueDateOption));
        hasDueDateOption.bind(item.getViewModel().dueDateProperty().isNotNull());

        BooleanProperty hasRepeatOption = new SimpleBooleanProperty();
//        hasRepeatOption.bind(item.getViewModel());

        remindOption = new RemindOption();
        BooleanProperty hasRemindOption = new SimpleBooleanProperty();
        hasRemindOption.addListener(createUpdateListener(remindOption));
        hasRemindOption.bind(item.getViewModel().remindProperty().isNotNull());

//        RepeatOption repeatOption = new RepeatOption();
//        BooleanProperty hasRepeatOption = new SimpleBooleanProperty();
//        hasRepeatOption.addListener(createUpdateListener(repeatOption));

        BooleanProperty hasTaskOption = new SimpleBooleanProperty();
        hasTaskOption.addListener(createUpdateListener(taskOption));

        hasTaskOption.bind(item.getListViewModel().idProperty().isNotEqualTo(item.getViewModel().listIdProperty()));
//        hasRepeatOption.bind(item.getListViewModel().idProperty().greaterThan(0));

        has.bind(hasTaskOption.or(hasMyDayOption).or(hasDueDateOption).or(hasRemindOption));

        if (item.getViewModel().getDueDate() != null) {
            updateDueDate(item.getViewModel().getDueDate());
        }

        if (item.getViewModel().getRemind() != null) {
            remindOption.setName(DateUtils.format(item.getViewModel().getRemind().toLocalDate()));
        }

        if (recurrence.isPresent()) {
            dueDateOption.setNeedRepeatIcon(true);
        }

        item.getViewModel().listIdProperty().addListener((_, _, val) -> {
            ListPresenter presenter = (ListPresenter) Global.get(List.class);
            var newVal = presenter.get(val.longValue());
            newVal.ifPresent(list -> taskOption.setName(StringUtils.name(list.getName())));
        });

        item.getViewModel().dueDateProperty().addListener((_, _, val) -> {
            if (val != null) {
                updateDueDate(val);
            }
        });

        item.getViewModel().remindProperty().addListener((_, _, val) -> {
            remindOption.setName(DateUtils.format(val.toLocalDate()));
        });
    }

    private void updateDueDate(LocalDate date) {
        dueDateOption.setName(DateUtils.format(date));
        if (date.equals(LocalDate.now())) {
            dueDateOption.setIcon(Icon.TODAY);
        } else {
            dueDateOption.setIcon(Icon.CALENDAR_MONTH);
        }
    }

    private ChangeListener<Boolean> createUpdateListener(Option option) {
        return (_, _, val) -> {
            if (val) {
                if (!getChildren().contains(option)) {
                    addOption(option);
                }
            } else {
                removeOption(option);
            }
        };
    }

    public void addOption(Option option) {
        if (getChildren().isEmpty()) {
            getChildren().add(option);
            return;
        }

        AtomicInteger act = new AtomicInteger();
        Optional<Option> optional = getChildren()
                .stream()
                .filter(node -> node instanceof Option)
                .map(node -> (Option) node)
                .filter(opt -> option.getIndex() < opt.getIndex()).findAny();

        if (optional.isPresent()) {
            act.set(getChildren().indexOf(optional.get()));
            getChildren().add(act.get(), option);
        } else getChildren().add(option);

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
                opt.needsBulletProperty().set(ind != 0);
            } else {
                opt.needsBulletProperty().set(false);
            }
        });
    }

    public BooleanProperty hasProperty() {
        return has;
    }
}
