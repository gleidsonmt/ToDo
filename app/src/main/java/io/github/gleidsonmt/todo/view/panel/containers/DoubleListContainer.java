package io.github.gleidsonmt.todo.view.panel.containers;

import io.github.gleidsonmt.todo.global.Global;
import io.github.gleidsonmt.todo.global.TaskPresenter;
import io.github.gleidsonmt.todo.model.ToDoTask;
import io.github.gleidsonmt.todo.view.panel.items.TaskItem;
import io.github.gleidsonmt.todo.view.panel.sections.AnimatedSection;
import io.github.gleidsonmt.todo.view.panel.sections.SingleSection;
import io.github.gleidsonmt.todo.view_model.ListViewModel;
import io.github.gleidsonmt.todo.view_model.TaskViewModel;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.IntegerBinding;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;

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

    public DoubleListContainer(ListViewModel list) {
        super(list);

        this.sectionIncomplete = new SingleSection();
        this.sectionCompleted = new AnimatedSection();

        this.getChildren().add(0, sectionIncomplete);
        this.getChildren().add(1, sectionCompleted);

    }

    @Override
    public void load() {
        TaskPresenter presenter = (TaskPresenter) Global.get(ToDoTask.class);
        Task<ObservableList<ToDoTask>> task = presenter.fetch(40, 0, "list_id = " + list.getId());
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

        this.sizeProperty().addListener((_, _, val) -> {
            this.setSpacing(sizeOfTheFirstSection.get() == 0 ? 0 : 10);
        });
        this.setSpacing(this.sizeProperty().get() == 0 ? 0 : 10);
    }

    @Override
    protected TaskItem createTaskItem(TaskViewModel task) {

        // TaskItem item = new TaskItem(task);
        // group.getToggles().add(item);

        // item.setOnImportantChange(viewModel -> {
        // viewModel.update();
        // });

        // item.setOnCompletedChange(viewModel -> {

        // if (viewModel.isCompleted()) {
        // sectionIncomplete.getChildren().removeAll(item);
        // // sectionCompleted.getItems().addAll(item);
        // } else {
        // // sectionCompleted.getItems().removeAll(item);
        // sectionIncomplete.getChildren().add(0, item);
        // }

        // viewModel.update();

        // });
        return null;
    }
}