package io.github.gleidsonmt.todo.view.panel.containers;

import io.github.gleidsonmt.todo.bd.dao.DaoList;
import io.github.gleidsonmt.todo.bd.dao.DaoTask;
import io.github.gleidsonmt.todo.model.List;
import io.github.gleidsonmt.todo.model.ListType;
import io.github.gleidsonmt.todo.model.ToDoTask;
import io.github.gleidsonmt.todo.view.panel.containers.sections.ListSection;
import io.github.gleidsonmt.todo.view.panel.containers.sections.SingleSection;
import io.github.gleidsonmt.todo.view_model.ListViewModel;
import io.github.gleidsonmt.todo.view_model.TaskViewModel;
import io.github.gleidsonmt.todo.view_model.map.ListMapper;
import io.github.gleidsonmt.todo.view_model.map.TaskMapper;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.IntegerBinding;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.scene.Node;

/**
 * @author Gleidson Neves da Silveira | <a href="mailto:gleidisonmt@gmail.com">gleidisonmt@gmail.com</a> <br>
 * Created on 01/11/2026
 */
public class MultipleListContainer extends ListContainer {

    private final String query;

    public MultipleListContainer(ListViewModel list, String query) {
        super(list);
        this.query = query;

//        Runnable rebind = () -> {
//            var deps = getChildren().stream()
//                    .filter(ListSection.class::isInstance)
//                    .map(n -> (ListSection) n)
//                    .map(ListSection::getSortedList)
//                    .toArray(Observable[]::new);
//
//            sizeProperty().bind(Bindings.createIntegerBinding(
//                    () -> getChildren().stream()
//                            .filter(ListSection.class::isInstance)
//                            .map(n -> (ListSection) n)
//                            .mapToInt(s -> s.getSortedList().size())
//                            .sum(),
//                    deps
//            ));
//        };
//
//        getChildren().addListener((ListChangeListener<Node>) _ -> rebind.run());
//        rebind.run();

//        sizeProperty().bind(Bindings.createIntegerBinding(
//                () -> getChildren().stream()
//                        .filter(ListSection.class::isInstance)
//                        .map(el -> (ListSection) el)
//                        .mapToInt(el -> el.getSortedList().size())
//                        .sum(),
//                getChildren()
//        ));

//        sizeProperty().bind(Bindings.size(this.data));

    }

    @Override
    public Task<ObservableList<ToDoTask>> load() {
        DaoList daoList = new DaoList();
        DaoTask daoTask = new DaoTask();

        Task<ObservableList<List>> task = daoList.fetch(FXCollections.observableArrayList());


        task.addEventHandler(WorkerStateEvent.WORKER_STATE_SUCCEEDED, _ -> {

            for (List list : task.getValue()) {
                if (!list.fixed() || list.name().equals("drawer.list.tasks")) {
                    ListViewModel viewModel = ListMapper.INSTANCE.toViewModel(list, new ListViewModel());
                    var item = createItem(viewModel);

                    item.setList(
                            getList().getType().equals(ListType.ALL) ?
                                    data.filtered(el -> !el.isCompleted() && el.getListID() == viewModel.getId()) :
                                    data.filtered(el -> el.isCompleted() && el.getListID() == viewModel.getId())
                    );
                    System.out.println("item.getSortedList() = " + item.getSortedList());

                    if (!item.getSortedList().isEmpty()) {
                        this.getChildren().addFirst(item);
                    }
                }
            }

//            sizeProperty().bind(Bindings.size(this.data));

        });

        Task<ObservableList<ToDoTask>> ts = daoTask.fetch(70, 0, query);

        ts.addEventHandler(WorkerStateEvent.WORKER_STATE_SUCCEEDED, _ -> {

            ts.getValue().forEach(el -> data.add(TaskMapper.INSTANCE.toViewModel(el, new TaskViewModel())));

            new Thread(task).start();
        });


        new Thread(ts).start();

        return ts;
    }

    private SingleSection createItem(ListViewModel viewModel) {
        return new ListSection(viewModel);
    }
}