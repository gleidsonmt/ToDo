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
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.scene.control.Label;

/**
 * @author Gleidson Neves da Silveira | <a href="mailto:gleidisonmt@gmail.com">gleidisonmt@gmail.com</a> <br>
 * Created on 01/11/2026
 */
public class MultipleListContainer extends ListContainer {

    public MultipleListContainer(ListViewModel list) {
        super(list);
    }

    @Override
    public void load() {

        DaoList daoList = new DaoList();
        DaoTask daoTask = new DaoTask();

        Task<ObservableList<List>> task = daoList.fetch(FXCollections.observableArrayList());

        task.setOnSucceeded(_ -> {

            for (List list : task.getValue()) {
                System.out.println("list = " + list);
                if (!list.fixed() || list.name().equals("drawer.list.tasks")) {
                    Task<ObservableList<ToDoTask>> tsk = daoTask.fetch(FXCollections.observableArrayList());
                    new Thread(tsk).start();
                    ListViewModel viewModel = ListMapper.INSTANCE.toViewModel(list, new ListViewModel());
                    this.getChildren().add(createItem(viewModel));
                }
            }

//            new Thread(task).start();


//            setTasks(tasks);
        });


        Task<ObservableList<ToDoTask>> ts = daoTask.fetch(FXCollections.observableArrayList());

        ts.setOnSucceeded(e -> {
            ts.getValue().forEach(el -> data.add(TaskMapper.INSTANCE.toViewModel(el, new TaskViewModel())));
            sizeProperty().bind(Bindings.size(this.getChildren()));
        });


        new Thread(ts).start();
        new Thread(task).start();

    }

    private SingleSection createItem(ListViewModel viewModel) {
        var section = new CompositeSection(viewModel);
        section.setList(data.filtered(el -> !el.isCompleted() && el.getListID() == viewModel.getId()));
        return section;
    }

}
