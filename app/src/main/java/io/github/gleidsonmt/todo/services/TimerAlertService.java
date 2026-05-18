

package io.github.gleidsonmt.todo.services;

import io.github.gleidsonmt.todo.global.Global;
import io.github.gleidsonmt.todo.global.TaskPresenter;
import io.github.gleidsonmt.todo.model.ToDoTask;
import javafx.collections.ObservableList;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import javafx.util.Duration;

/**
 *
 * @author Gleidson Neves da Silveira | <a href="mailto:gleidisonmt@gmail.com">gleidisonmt@gmail.com</a> <br>
 * Created on  12/05/2026
 */
public class TimerAlertService extends ScheduledService<ObservableList<ToDoTask>> {

    private final TaskPresenter presenter;

    public TimerAlertService() {
        setPeriod(Duration.seconds(60));
        presenter = (TaskPresenter) Global.get(ToDoTask.class);
    }

    @Override
    protected Task<ObservableList<ToDoTask>> createTask() {
        return presenter.selectAllBefore()  ;
    }

}
