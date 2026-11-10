

package io.github.gleidsonmt.todo.view.panel.containers;

import io.github.gleidsonmt.todo.bd.dao.DaoTask;
import io.github.gleidsonmt.todo.model.ListType;
import io.github.gleidsonmt.todo.model.ToDoTask;
import io.github.gleidsonmt.todo.view.panel.containers.sections.AnimatedSection;
import io.github.gleidsonmt.todo.view.panel.containers.sections.SingleSection;
import io.github.gleidsonmt.todo.view_model.ListViewModel;
import io.github.gleidsonmt.todo.view_model.TaskViewModel;
import io.github.gleidsonmt.todo.view_model.map.TaskMapper;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.IntegerBinding;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;

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
//        System.out.println("query = " + query);
        new Thread(task).start();
//        unbind();
        task.addEventHandler(WorkerStateEvent.WORKER_STATE_SUCCEEDED, _ -> {
            System.out.println("task.getValue() = " + task.getValue());

        // update list task
            task.getValue().forEach(el -> data.add(TaskMapper.INSTANCE.toViewModel(el, new TaskViewModel())));

            if (!getChildren().contains(sectionCompleted)) {
                getChildren().add(1, sectionCompleted);
            }

            if (getList().getType() == ListType.IMPORTANT) {

                sectionIncomplete.setList(data.filtered(el -> !el.isCompleted() && el.isImportant() ));
                getChildren().remove(sectionCompleted);

            } else if (getList().getType() == ListType.DAILY) {

                sectionIncomplete.setList(data.filtered(el -> !el.isCompleted() && el.isMyDay()));
                sectionCompleted.setList(data.filtered(TaskViewModel::isCompleted));

            } else {

                // filtra os nao completos
                sectionIncomplete.setList(data.filtered(el -> !el.isCompleted() && list.getId() == el.getListID()));
                // filtra os completos
                sectionCompleted.setList(data.filtered(el -> el.isCompleted() && list.getId() == el.getListID()));
//                sectionCompleted.setList(data.filtered(TaskViewModel::isCompleted));.

            }

            bind();
        });

        return task;
    }

    private void bind() {
//        IntegerBinding sizeOfTheFirstSection = Bindings.size(sectionIncomplete.getSortedList());
//
//        if (getChildren().contains(sectionCompleted)) {
//            IntegerBinding sizeOfTheSecondSection = Bindings.size(sectionCompleted.getSortedList());
//            this.sizeProperty().bind(sizeOfTheFirstSection.add(sizeOfTheSecondSection));
//        } else {
//            this.sizeProperty().bind(sizeOfTheFirstSection);
//        }
//
//        this.sizeProperty().addListener((_, _, _) -> this.setSpacing(sizeOfTheFirstSection.get() == 0 ? 0 : 10));
    }
}