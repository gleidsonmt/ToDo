// package io.github.gleidsonmt.todo.view.panel.containers;

// import io.github.gleidsonmt.todo.view.panel.items.TaskItem;
// import io.github.gleidsonmt.todo.view.panel.sections.SingleSection;
// import io.github.gleidsonmt.todo.view_model.ListViewModel;
// import io.github.gleidsonmt.todo.view_model.TaskViewModel;
// import javafx.application.Platform;

// /**
//  * Description: The first main list container, is a simple list container
//  * its actions is only
//  *
//  * @author Gleidson Neves da Silveira | <a href="mailto:gleidisonmt@gmail.com">gleidisonmt@gmail.com</a> <br>
//  *         Created On: Feb 26, 2026
//  * 
//  *         Version History: Initial version
//  */
// public class SingleListContainer extends ListContainer {

//     // private final SingleSection sectionIncomplete = new SingleSection();

//     // public SingleListContainer(List list, ObservableList<ToDoTask> data) {
//     // // super(list, data);
//     // init();
//     // registerListeners();
//     // }

//     public SingleListContainer(ListViewModel model) {
//         super(model);
//     }

//     private void init() {
//         this.getChildren().add(0, sectionIncomplete);
//     }

//     private void registerListeners() {
//         // this list filter items from the main data list
//         // with this list updated so the ui needs to adapat it
//         // list.getItems().addListener((ListChangeListener<ToDoTask>) c -> {
//         // if (c.next()) {
//         // if (c.wasReplaced()) {
//         // // Todo: do not do something if the data is changed
//         // return;
//         // }
//         // if (c.wasAdded()) {
//         // c.getAddedSubList().forEach(task ->
//         // sectionIncomplete.getChildren().add(createTaskItem(task)));
//         // }
//         // if (c.wasRemoved()) {
//         // c.getRemoved().forEach(el -> {
//         // sectionIncomplete.getChildren().remove(sectionIncomplete.get(el));
//         // });
//         // }
//         // }
//         // });
//     }

//     /**
//      * Create an task item (gui component) based on object domain state.
//      */
//     protected TaskItem createTaskItem(TaskViewModel task) {
//         TaskItem item = new TaskItem(task);
//         group.getToggles().add(item);

//         // Set actions
//         item.setOnImportantChange((viewModel) -> {
//             // sectionIncomplete.getChildren().remove(item);
//             viewModel.update();
//         });
//         // The listeners will be trigger when this properteis are change
//         item.setOnCompletedChange((viewModel) -> {
//             // remove this task item from the gui
//             sectionIncomplete.getChildren().remove(item);
//             viewModel.update();
//         });

//         return item;
//     }

//     /**
//      * Load the tasks.
//      */
//     @Override
//     public void load() {
//         // loadTasks(_ -> list.getItems().forEach(this::loadTask));
//     }

//     private void loadTask(TaskViewModel task) {
//         try {
//             Thread.sleep((long) (sectionIncomplete.getSpeed() / 2));
//         } catch (InterruptedException e1) {

//         }

//         TaskItem item = createTaskItem(task);
//         Platform.runLater(() -> {
//             if (!task.isCompleted()) {
//                 sectionIncomplete.getChildren().add(item);
//             }
//         });
//     }
// }
