package io.github.gleidsonmt.todo.view.aside.sections;

import io.github.gleidsonmt.todo.view.aside.labels.FileLabel;
import io.github.gleidsonmt.todo.view.aside.Item;
import io.github.gleidsonmt.todo.view_model.TaskViewModel;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * @author Gleidson Neves da Silveira | <a href="mailto:gleidisonmt@gmail.com">gleidisonmt@gmail.com</a> <br>
 * Created on 18/10/2026
 */
public class FileSection extends Section {

    public FileSection(TaskViewModel model) {

        getChildren().addAll(createItem());

        getChildren().addListener((ListChangeListener<Node>) c -> {
            if (c.next()) {
                if (c.wasAdded()) {
                    for (Node node : c.getAddedSubList()) {
                        if (node instanceof Item<?> item) {
                            item.bind();
                        }
                    }
                }
                if (c.wasRemoved()) {

                }
            }
        });
    }

    private Item<File> createItem() {
        return new Item<>(new FileLabel()) {
            @Override
            public void bind() {
                System.out.println("bind");
//                item.
                getNode().valueProperty().addListener((_, _, newValue) -> {
                    System.out.println("newValue = " + newValue);
                    if (newValue != null) {
                        ((FileLabel) getNode()).setFile(newValue);
                        FileSection.this.getChildren().add(createItem());
                    } else {

                        if (FileSection.this.getChildren().size() > 1) {
                            FileSection.this.getChildren().remove(this);
                        } else {
                            getNode().reset();
                        }
                    }
                });
            }
        };
    }
}
