package io.github.gleidsonmt.todo.view.panel.containers;

import io.github.gleidsonmt.todo.global.Global;
import io.github.gleidsonmt.todo.global.TaskPresenter;
import io.github.gleidsonmt.todo.model.ListType;
import io.github.gleidsonmt.todo.model.ToDoTask;
import io.github.gleidsonmt.todo.view.nav.SideNavNew;
import io.github.gleidsonmt.todo.view.panel.events.TaskChangeEvent;
import io.github.gleidsonmt.todo.view.panel.items.TaskItem;
import io.github.gleidsonmt.todo.view.panel.sections.AnimatedSection;
import io.github.gleidsonmt.todo.view.panel.sections.SingleSection;
import io.github.gleidsonmt.todo.view_model.ListViewModel;
import io.github.gleidsonmt.todo.view_model.TaskViewModel;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.IntegerBinding;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;

import javax.swing.text.html.ListView;
import java.util.logging.Logger;

/**
 * Description:
 *
 * @author Gleidson Neves da Silveira | <gleidisonmt@gmail.com>
 * Created On: Feb 26, 2026
 * <p>
 * Version History: Initial version
 */
public class DoubleListContainer extends ListContainer {

    private final SingleSection sectionIncomplete;
    private final AnimatedSection sectionCompleted;
    private final String query;

    static final Logger logger = Logger.getGlobal();

    public DoubleListContainer(ListViewModel list, String query) {
        super(list);
        this.query = query;
        this.sectionIncomplete = new SingleSection(list);
        this.sectionCompleted = new AnimatedSection(list);

        this.getChildren().add(0, sectionIncomplete);
        this.getChildren().add(1, sectionCompleted);
    }

    @Override
    public void load() {
        TaskPresenter taskPresenter = (TaskPresenter) Global.get(ToDoTask.class);
        Task<ObservableList<ToDoTask>> task = taskPresenter.fetch(40, 0, query);

        new Thread(task).start();

        // update list task
        task.setOnSucceeded(_ -> {
            task.getValue().forEach(el -> {
                data.add(new TaskViewModel(el));
            });

            // data = task.getValue();
            sectionIncomplete.setList(data.filtered(el -> !el.isCompleted()));
            sectionCompleted.setList(data.filtered(TaskViewModel::isCompleted));

            bind();
        });
    }

    private void bind() {

        IntegerBinding sizeOfTheFirstSection = Bindings.size(sectionIncomplete.getSortedList());
        IntegerBinding sizeOfTheSecondSection = Bindings.size(sectionCompleted.getSortedList());

        this.sizeProperty().bind(sizeOfTheFirstSection.add(sizeOfTheSecondSection));

        this.sizeProperty().addListener((_, _, _) -> this.setSpacing(sizeOfTheFirstSection.get() == 0 ? 0 : 10));

        this.setSpacing(this.sizeProperty().get() == 0 ? 0 : 10);

        this.addEventFilter(TaskChangeEvent.MOVED, (e) -> {
            logger.info(() -> "[TaskChangeEvent, Type = " + e.getEventType() + " ] -> " + e.getModel() + "");
            if (e.getActual() != e.getPrevious()) {
                if (!list.isFixed() || list.getId() == 0) {
                    data.remove(e.getModel());
                }
            }
        });

        this.addEventHandler(TaskChangeEvent.MY_DAY_CHANGED, e -> {
            logger.info(() -> "[TaskChangeEvent, Type = " + e.getEventType() + " ] -> " + e.getModel() + "");
            if (getList().getType() == ListType.DAILY) {
                if (!e.getModel().isMyDay()) {
                    data.remove(e.getModel());
                }
            }
        });

        this.addEventFilter(TaskChangeEvent.FAVORITE_CHANGED, e -> {
            logger.info(() -> "[TaskChangeEvent, Type = " + e.getEventType() + " ] -> " + e.getModel() + "");
            if (getList().getType() == ListType.IMPORTANT) {
                if (!e.getModel().isImportant()) {
                    data.remove(e.getModel());
                }
            }
        });

        this.addEventFilter(TaskChangeEvent.DELETE_TASK, e -> {
            logger.info(() -> "[TaskChangeEvent, Type = " + e.getEventType() + " ] -> " + e.getModel());
            getData().remove(e.getModel());
            // chamar confirmacao
            e.getModel().delete();
        });

        this.addEventFilter(TaskChangeEvent.ADD, e -> {
            logger.info(() -> "[TaskChangeEvent, Type = " + e.getEventType() + " ] -> " + e.getModel());
            getData().add(e.getModel());
        });

        data.addListener((ListChangeListener<TaskViewModel>) c -> {
            if (c.next()) {
                if (c.wasUpdated()) {
                    c.getList().subList(c.getFrom(), c.getTo()).forEach(viewModel -> {
                        if (viewModel == null)
                            return;
//                        viewModel.update();
//                        list.update();
                    });
                }
//                if (c.wasAdded() ) {
//                    list.addNumberOfTasks(c.getAddedSize());
//                }
//                if (c.wasRemoved() ) {
//                    System.out.println("removed");
//                    list.addNumberOfTasks(-c.getRemovedSize());
//                }
            }
        });
    }
}