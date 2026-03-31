package io.github.gleidsonmt.todo;

import org.scenicview.ScenicView;

import fr.brouillard.oss.cssfx.CSSFX;
import io.github.gleidsonmt.glad.theme.Css;
import io.github.gleidsonmt.glad.theme.Font;
import io.github.gleidsonmt.glad.theme.ThemeProvider;
import io.github.gleidsonmt.todo.global.Global;
import io.github.gleidsonmt.todo.global.Presenter;
import io.github.gleidsonmt.todo.model.List;
import io.github.gleidsonmt.todo.model.ToDoTask;
import io.github.gleidsonmt.todo.utils.Assets;
import io.github.gleidsonmt.todo.view.panel.ListRootNew;
import io.github.gleidsonmt.todo.view.panel.Panel;
import io.github.gleidsonmt.todo.view.panel.items.TaskItem;
import io.github.gleidsonmt.todo.view.panel.sections.Comparators;
import io.github.gleidsonmt.todo.view_model.ListViewModel;
import io.github.gleidsonmt.todo.view_model.TaskViewModel;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Description:
 *
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 *         Created On: Mar 25, 2026
 * 
 *         Version History: Initial version
 */
public class ListRootTest extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        Presenter<List> listPresenter = Global.get(List.class);
        ListViewModel viewModel = new ListViewModel(listPresenter.get(1).get());

        BorderPane root = new BorderPane();
        ListRootNew listRoot = new ListRootNew();
        BorderPane.setMargin(listRoot, new Insets(10));

        Panel panel = new Panel();
        panel.setContent(listRoot);

        root.setCenter(panel);
        root.setRight(createAside(listRoot, viewModel));
        stage.setScene(new Scene(root, 800, 600));

        ThemeProvider.install(stage.getScene(), Css.ALL, Font.INSTAGRAM);
        stage.getScene().getStylesheets().add(Assets.getCss("app.css"));

        stage.show();

        ScenicView.show(root);
        CSSFX.start(stage.getScene());
    }

    private Node createAside(ListRootNew listRoot, ListViewModel listViewModel) {
        VBox container = new VBox();
        BorderPane.setMargin(container, new Insets(10));
        container.setAlignment(Pos.TOP_CENTER);

        TextField taskName = new TextField("Task Name");

        Button btnPopulate = new Button("Populate tasks");
        container.setSpacing(5);

        container.getChildren().addAll(taskName, btnPopulate);

        ToDoTask task = new ToDoTask(1 + taskName.getText());
        task.setCompleted(true);

        TaskViewModel taskViewModel = new TaskViewModel(task);
        TaskItem taskItem = new TaskItem(taskViewModel);

        // ToDoTaskNew task = new TodoTaskNew();
        // TaskViewModel model = new TaskViewModel(task);
        // TaskItem taskItem = new TaskItem(model);

        btnPopulate.setOnAction(e -> {
            listRoot.updateContainer(listViewModel);
        });

        Button addMyDay = new Button("Add to My Day");
        addMyDay.setOnAction(e -> {
            var selected = listRoot.getContainer().getSelected();
            if (selected != null) {
                selected.getViewModel().setMyDay(!selected.getViewModel().isMyDay());
                // selected.getViewModel().setRemind(LocalDateTime.now().plusDays(1));  
            }
        });
        Button removeTask = new Button("Remove a task");
        Button sortAlpha = new Button("Sort Alphabetically");
        Button sortImportance = new Button("Sort Importance");

        removeTask.setOnAction(e -> {
            var selected = listRoot.getContainer().getSelected();
            listRoot.getContainer().remove(selected);
        });
        sortAlpha.setOnAction(e -> {
            listRoot.getContainer().setComparator(Comparators.ALPHABETICALLY);
        });
        sortImportance.setOnAction(e -> {
            listRoot.getContainer().setComparator(Comparators.IMPORTANCE);
        });

        container.getChildren().add(addMyDay);
        container.getChildren().add(removeTask);
        container.getChildren().add(sortAlpha);
        container.getChildren().add(sortImportance);
        return container;
    }

    public static void main(String[] args) {
        launch(args);
    }

}
