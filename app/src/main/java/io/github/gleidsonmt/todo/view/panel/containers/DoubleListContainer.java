package io.github.gleidsonmt.todo.view.panel.containers;

import io.github.gleidsonmt.todo.global.Global;
import io.github.gleidsonmt.todo.global.TaskPresenter;
import io.github.gleidsonmt.todo.model.ListType;
import io.github.gleidsonmt.todo.model.ToDoTask;
import io.github.gleidsonmt.todo.view.nav.SideNavNew;
import io.github.gleidsonmt.todo.view.panel.events.TaskChangeEvent;
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
 *         Created On: Feb 26, 2026
 * 
 *         Version History: Initial version
 */
public class DoubleListContainer extends ListContainer {

    private final SingleSection sectionIncomplete;
    private final AnimatedSection sectionCompleted;
    private String query;

    static final Logger logger = Logger.getGlobal();

    private boolean eventAction = true;

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
        // Task<ObservableList<ToDoTask>> task = taskPresenter.fetch(40, 0,
        // "list_id = " + list.getId());
//        System.out.println("query = " + query);
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

        this.addEventHandler(TaskChangeEvent.COMPLETE, e -> {
            logger.info(() -> "[TaskChangeEvent, Type = " + e.getEventType() + " ] -> " + e.getModel() + "");

            SideNavNew drawer = (SideNavNew) getScene().lookup("#drawer");
            ListViewModel parent = drawer.get(e.getModel().getListId());

            if (list.isFixed()) {
                list.addNumberOfTasks((!e.getModel().isCompleted() ? 1 : -1));
                list.update();
            } else {
                if (e.getModel().isMyDay()) {
                    ListViewModel daily = drawer.get(ListType.DAILY);
                    daily.addNumberOfTasks((!e.getModel().isCompleted() ? 1 : -1));
                    daily.update();
                }

                if (e.getModel().isImportant()) {
                    ListViewModel imp = drawer.get(ListType.IMPORTANT);
                    imp.addNumberOfTasks((!e.getModel().isCompleted() ? 1 : -1));
                    imp.update();
                }
            }
            parent.addNumberOfTasks((!e.getModel().isCompleted() ? 1 : -1));
            parent.update();
        });

        this.addEventHandler(TaskChangeEvent.MOVED, (e) -> {
            if (e.getActual() != e.getPrevious()) {
                SideNavNew drawer = (SideNavNew) getScene().lookup("#drawer");
                ListViewModel previous = drawer.get(e.getPrevious());
                ListViewModel source = drawer.get(e.getActual());

                System.out.println("previous = " + previous);
                System.out.println("previous = " + previous.getNumberOfTasks());
                System.out.println("source = " + source);
                System.out.println("source = " + source.getNumberOfTasks());
//
                previous.addNumberOfTasks(-1);
                source.addNumberOfTasks(1);

                source.update();
                previous.update();
//
                if (!list.isFixed()) {
                    data.remove(e.getModel());
                }
            }
        });

        this.addEventHandler(TaskChangeEvent.MY_DAY_CHANGED, e -> {

            logger.info(() -> "[TaskChangeEvent, Type = " + e.getEventType() + " ] -> " + e.getModel() + "");
            SideNavNew drawer = (SideNavNew) getScene().lookup("#drawer");
            ListViewModel dailyList = drawer.get(ListType.DAILY);

            if (list.getType() != ListType.DAILY) {
                if (!e.getModel().isCompleted()) {
                    dailyList.addNumberOfTasks(e.getModel().isMyDay() ? 1 : -1);
                    dailyList.update();
                }

            } else {
                if (!e.getModel().isMyDay()) {
                    data.remove(e.getModel());
                }
                    dailyList.addNumberOfTasks((!e.getModel().isCompleted() && e.getModel().isMyDay() ? 1 : -1));
                    dailyList.update();

            }
        });

        this.addEventHandler(TaskChangeEvent.FAVORITE_CHANGED, e -> {
            logger.info(() -> "[TaskChangeEvent, Type = " + e.getEventType() + " ] -> " + e.getModel() + "");

            SideNavNew drawer = (SideNavNew) getScene().lookup("#drawer");
            ListViewModel impList = drawer.get(ListType.IMPORTANT);

            if (list.getType() != ListType.IMPORTANT) {
                if (!e.getModel().isCompleted()) {
                    impList.addNumberOfTasks(e.getModel().isImportant() ? 1 : -1);
                    impList.update();
                }
            } else {
                if (!e.getModel().isImportant()) {
                    data.remove(e.getModel());
                }

                impList.addNumberOfTasks((!e.getModel().isCompleted() && e.getModel().isMyDay() ? 1 : -1));
                impList.update();
            }
//            if (list.getType() != ListType.IMPORTANT) {
//                SideNavNew drawer = (SideNavNew) getScene().lookup("#drawer");
//                ListViewModel destination = drawer.get(ListType.IMPORTANT);
//                destination.addNumberOfTasks((!e.getModel().isCompleted() && !e.getModel().isCompleted() ? 1 : -1));
//            } else {
//                data.remove(e.getModel());
//            }
        });

        this.addEventHandler(TaskChangeEvent.DELETE_TASK, e -> {
            SideNavNew drawer = (SideNavNew) getScene().lookup("#drawer");
            ListViewModel destination = drawer.get(e.getModel().getListId());
            destination.addNumberOfTasks(-1);
            data.remove(e.getModel());
        });

        data.addListener((ListChangeListener<TaskViewModel>) c -> {
            if (c.next()) {

                if (c.wasUpdated()) {
                    c.getList().subList(c.getFrom(), c.getTo()).forEach(viewModel -> {
                        if (viewModel == null)
                            return;
                        viewModel.update();
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