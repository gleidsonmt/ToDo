package io.github.gleidsonmt.todo.view.aside.sections;

import io.github.gleidsonmt.todo.bd.dao.DaoTaskFile;
import io.github.gleidsonmt.todo.model.TaskFile;
import io.github.gleidsonmt.todo.view.aside.Item;
import io.github.gleidsonmt.todo.view.aside.labels.FileLabel;
import io.github.gleidsonmt.todo.view_model.TaskViewModel;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.scene.Node;

/**
 * @author Gleidson Neves da Silveira | <a href="mailto:gleidisonmt@gmail.com">gleidisonmt@gmail.com</a> <br>
 * Created on 18/10/2026
 */
public class FileSection extends Section {

    private final DaoTaskFile dao = new DaoTaskFile();

    public FileSection(TaskViewModel model) {

        ObservableList<TaskFile> list = FXCollections.observableArrayList();
        Task<ObservableList<TaskFile>> task = dao.fetchWhere(list, "where task_id = " + model.getId());
        new Thread(task).start();

        task.setOnSucceeded(_ -> {
            list.forEach(taskFile -> {
                var created = createItem(model);
                created.getNode().setValue(taskFile);
                created.bind();
                getChildren().add(created);
            });

            var temp = createItem(model);
            temp.bind();
            getChildren().addLast(temp);

            getChildren().addListener((ListChangeListener<Node>) c -> {
                if (c.next()) {
                    if (c.wasAdded()) {
                        for (Node node : c.getAddedSubList()) {
                            if (node instanceof Item<?> item) {
                                item.bind();
                            }
                        }
                    }
                }
            });
        });


    }

    private Item<TaskFile> createItem(TaskViewModel model) {
        return new Item<>(new FileLabel(model.getId())) {

            {
                getStyleClass().add("file-label");
            }

            @Override
            public void bind() {
                this.getStyleClass().addAll("border-b-1 border-light-gray".split("\\s+"));

                getNode().valueProperty().addListener((_, oldValue, newValue) -> {
                    if (newValue != null) {
                        var item = createItem(model);
                        FileSection.this.getChildren().add(item);
                        dao.store(newValue);
                    } else {
                        if (FileSection.this.getChildren().size() > 1) {
                            FileSection.this.getChildren().remove(this);
                        } else {
                            getNode().reset();
                        }
                        dao.delete(oldValue);
                    }
                });
            }
        };
    }
}
