

package io.github.gleidsonmt.todo.view.panel.containers;

import io.github.gleidsonmt.todo.bd.dao.DaoTask;
import io.github.gleidsonmt.todo.global.Global;
import io.github.gleidsonmt.todo.global.TaskPresenter;
import io.github.gleidsonmt.todo.model.ListType;
import io.github.gleidsonmt.todo.model.ToDoTask;
import io.github.gleidsonmt.todo.view.panel.events.TaskChangeEvent;
import io.github.gleidsonmt.todo.view.panel.containers.sections.AnimatedSection;
import io.github.gleidsonmt.todo.view.panel.containers.sections.SingleSection;
import io.github.gleidsonmt.todo.view_model.ListViewModel;
import io.github.gleidsonmt.todo.view_model.TaskViewModel;
import io.github.gleidsonmt.todo.view_model.map.TaskMapper;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.IntegerBinding;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;

import java.time.LocalDate;
import java.util.logging.Logger;

/**
 * Description:
 *
 * @author Gleidson Neves da Silveira | <a href="mailto:gleidisonmt@gmail.com">gleidisonmt@gmail.com</a> <br> <br>
 * Created On: Feb 26, 2026
 * <p>
 * Version History: Initial version
 */
public class DoubleListContainer extends ListContainer {

    private final SingleSection sectionIncomplete;
    private final AnimatedSection sectionCompleted;
    private final String query;

    private final DaoTask dao;

    static final Logger logger = Logger.getGlobal();

    public DoubleListContainer(ListViewModel list, String query) {
        super(list);
        this.dao = new DaoTask();
        this.query = query;
        this.sectionIncomplete = new SingleSection(list);
        this.sectionCompleted = new AnimatedSection(list);

        this.getChildren().add(0, sectionIncomplete);
        this.getChildren().add(1, sectionCompleted);
    }

    @Override
    public Task<ObservableList<ToDoTask>> load() {
        Task<ObservableList<ToDoTask>> task = dao.fetch(70, 0, query);
        new Thread(task).start();

        // update list task
        task.setOnSucceeded(_ -> {
            task.getValue().forEach(el -> data.add(TaskMapper.INSTANCE.toViewModel(el, new TaskViewModel())));

            sectionIncomplete.setList(data.filtered(el -> !el.isCompleted()));
            sectionCompleted.setList(data.filtered(TaskViewModel::isCompleted));

            bind();
        });
        return task;
    }

    private void bind() {

        IntegerBinding sizeOfTheFirstSection = Bindings.size(sectionIncomplete.getSortedList());
        IntegerBinding sizeOfTheSecondSection = Bindings.size(sectionCompleted.getSortedList());

        this.sizeProperty().bind(sizeOfTheFirstSection.add(sizeOfTheSecondSection));

        this.sizeProperty().addListener((_, _, _) -> this.setSpacing(sizeOfTheFirstSection.get() == 0 ? 0 : 10));

        this.setSpacing(this.sizeProperty().get() == 0 ? 0 : 10);

        this.addEventFilter(TaskChangeEvent.MOVED, (e) -> {
            logger.info(() -> "[TaskChangeEvent, Type = " + e.getEventType() + " ] -> " + e.getModel());
            if (e.getActual() != e.getPrevious()) {
                if (!list.isFixed() || list.getId() == 1) {
                    data.remove(e.getModel());
                }
            }
        });

        this.addEventHandler(TaskChangeEvent.MY_DAY_CHANGED, e -> {
            logger.info(() -> "[TaskChangeEvent, Type = " + e.getEventType() + " ] -> " + e.getModel());
            if (getList().getType() == ListType.DAILY) {
                if (!e.getModel().isMyDay()) {
                    data.remove(e.getModel());
                }
            }
        });

        this.addEventFilter(TaskChangeEvent.FAVORITE_CHANGED, e -> {
            logger.info(() -> "[TaskChangeEvent, Type = " + e.getEventType() + " ] -> " + e.getModel());
            if (getList().getType() == ListType.IMPORTANT) {
                if (!e.getModel().isImportant()) {
                    data.remove(e.getModel());
                }
            }
        });

        this.addEventFilter(TaskChangeEvent.DELETE_TASK, e -> {
            logger.info(() -> "[TaskChangeEvent, Type = " + e.getEventType() + " ] -> " + e.getModel());
            getData().remove(e.getModel());
            e.getModel().delete();
        });

    }
}