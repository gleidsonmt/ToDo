

package io.github.gleidsonmt.todo.view.panel.containers;

import io.github.gleidsonmt.glad.base.Root;
import io.github.gleidsonmt.glad.base.dialog.WrapperEffect;
import io.github.gleidsonmt.glad.base.dialog.alert.AlertType;
import io.github.gleidsonmt.glad.controls.button.Button;
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
import javafx.beans.value.WeakChangeListener;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

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
//        System.out.println("query = " + query);
        new Thread(task).start();

        // update list task
        task.setOnSucceeded(_ -> {

            task.getValue().forEach(el -> data.add(TaskMapper.INSTANCE.toViewModel(el, new TaskViewModel())));

            if (!getChildren().contains(sectionCompleted)) {
                getChildren().add(1, sectionCompleted);
            }

            if (getList().getType() == ListType.IMPORTANT) {
                sectionIncomplete.setList(data.filtered(el -> !el.isCompleted() && el.isImportant()));
                getChildren().remove(sectionCompleted);
//                sectionCompleted.setList(data.filtered(el -> el.isCompleted() && !el.isImportant()));
            } else if (getList().getType() == ListType.DAILY) {

                sectionIncomplete.setList(data.filtered(el -> !el.isCompleted() && el.isMyDay()));
                sectionCompleted.setList(data.filtered(TaskViewModel::isCompleted));

            } else {
                // filtra os nao completos
                sectionIncomplete.setList(data.filtered(el -> !el.isCompleted()));
                // filtra os completos
                sectionCompleted.setList(data.filtered(TaskViewModel::isCompleted));
            }

            bind();
        });
        return task;
    }

    private void bind() {

        IntegerBinding sizeOfTheFirstSection = Bindings.size(sectionIncomplete.getSortedList());
        if (getChildren().contains(sectionCompleted)) {
            IntegerBinding sizeOfTheSecondSection = Bindings.size(sectionCompleted.getSortedList());

            this.sizeProperty().bind(sizeOfTheFirstSection.add(sizeOfTheSecondSection));
        } else {
            this.sizeProperty().bind(sizeOfTheFirstSection);
        }

        this.sizeProperty().addListener((_, _, _) -> this.setSpacing(sizeOfTheFirstSection.get() == 0 ? 0 : 10));

        this.setSpacing(this.sizeProperty().get() == 0 ? 0 : 10);

        // list container has an observable list that observe changes in the data
        // if i change a task item property if its listed in the observable the change will be catch here
        data.addListener((ListChangeListener<TaskViewModel>) c -> {
            if (c.next()) {

                if (c.wasUpdated()) { // if one of the task items are updated
                    for (TaskViewModel model : c.getList()) {
                        // cal his update method
                        model.update();

                    }
                }

            }
        });

        this.addEventFilter(TaskChangeEvent.DELETE_TASK, e -> {
            logger.info(() -> "[TaskChangeEvent, Type = " + e.getEventType() + " ] -> " + e.getModel());
            Root root = (Root) getScene().getRoot();

            var title = new Text("Delete Task");
            title.getStyleClass().addAll("text-18 bold".split("\\s+"));

            var confirm = new Button("Confirm");
            var cancel = new Button("Cancel");
            cancel.setCancelButton(true);
            cancel.setOnAction(_ -> root.behavior().dialog().hide());
            confirm.setOnAction(_ -> {

            });

            HBox buttonsBar = new HBox(confirm, cancel);
            buttonsBar.getStyleClass().addAll("align-center-left spacing-5".split("\\s+"));

            var flow = new TextFlow(new Text("\"" + e.getModel().getName() + "\" will be deleted permanently"));
            VBox container = new VBox(title, flow, buttonsBar);

            container.getStyleClass().addAll("bg-background pd-20 align-center-left rounded spacing-10".split("\\s+"));

            root.behavior().dialog()
                    .block()
                    .with(WrapperEffect.GRAY)
                    .content(container)
                    .width(-1)
                    .height(-1)
//                    .buttons(confirm, cancel)
                    .show();

//            getData().remove(e.getModel());
//            e.getModel().delete();
        });

    }
}