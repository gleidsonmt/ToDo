package io.github.gleidsonmt.todo.view.panel.containers;

import io.github.gleidsonmt.todo.bd.dao.DaoList;
import io.github.gleidsonmt.todo.bd.dao.DaoTask;
import io.github.gleidsonmt.todo.model.List;
import io.github.gleidsonmt.todo.model.ListType;
import io.github.gleidsonmt.todo.model.ToDoTask;
import io.github.gleidsonmt.todo.utils.I18n;
import io.github.gleidsonmt.todo.view.panel.containers.sections.AnimatedSection;
import io.github.gleidsonmt.todo.view.panel.containers.sections.CompositeSection;
import io.github.gleidsonmt.todo.view.panel.containers.sections.SingleSection;
import io.github.gleidsonmt.todo.view_model.ListViewModel;
import io.github.gleidsonmt.todo.view_model.TaskViewModel;
import io.github.gleidsonmt.todo.view_model.map.ListMapper;
import io.github.gleidsonmt.todo.view_model.map.TaskMapper;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.IntegerBinding;
import javafx.beans.binding.NumberBinding;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.scene.Node;
import javafx.scene.control.Label;

/**
 * @author Gleidson Neves da Silveira | <a href="mailto:gleidisonmt@gmail.com">gleidisonmt@gmail.com</a> <br>
 * Created on 01/11/2026
 */
public class MultipleListContainer extends ListContainer {

    private String query;
    public MultipleListContainer(ListViewModel list, String query) {
        super(list);
        this.query = query;
    }

    @Override
    public Task<ObservableList<ToDoTask>> load() {

        DaoList daoList = new DaoList();
        DaoTask daoTask = new DaoTask();

        Task<ObservableList<List>> task = daoList.fetch(FXCollections.observableArrayList());

        System.out.println("query = " + query);

        task.setOnSucceeded(_ -> {

            for (List list : task.getValue()) {
                if (!list.fixed() || list.name().equals("drawer.list.tasks")) {
                    ListViewModel viewModel = ListMapper.INSTANCE.toViewModel(list, new ListViewModel());
                    var item = createItem(viewModel);
                    item.setList(data.filtered(el -> el.getListID() == viewModel.getId()));
                    if (!item.getSortedList().isEmpty()) {
                        this.getChildren().addFirst(item);
                    }
                }
            }
//
//            setTasks(tasks);
        });


//        Task<ObservableList<ToDoTask>> ts = daoTask.fetch(FXCollections.observableArrayList());
        Task<ObservableList<ToDoTask>> ts = daoTask.fetch(70, 0, query);

        ts.addEventHandler(WorkerStateEvent.WORKER_STATE_SUCCEEDED, _ -> {
            System.out.println("two");

            ts.getValue().forEach(el -> data.add(TaskMapper.INSTANCE.toViewModel(el, new TaskViewModel())));
            sizeProperty().bind(Bindings.size(this.data));


            new Thread(task).start();

        });

        new Thread(ts).start();

        return ts;
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

    private SingleSection createItem(ListViewModel viewModel) {
        return new CompositeSection(viewModel);
    }

}
