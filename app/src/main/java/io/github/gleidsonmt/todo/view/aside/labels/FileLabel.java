package io.github.gleidsonmt.todo.view.aside.labels;

import io.github.gleidsonmt.glad.controls.icon.Icon;
import io.github.gleidsonmt.todo.model.TaskFile;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.VPos;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.OverrunStyle;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * @author Gleidson Neves da Silveira | <a href="mailto:gleidisonmt@gmail.com">gleidisonmt@gmail.com</a> <br>
 * Created on 18/10/2026
 */
public class FileLabel extends CustomLabel<TaskFile> {
    private File file;

    public FileLabel(long id) {
        super("Add file ", Icon.ATTACH_FILE, "");

        value.addListener((_, _, newValue) -> {

            if (newValue != null) {
                File file = new File(newValue.path());
                setFile(file);
            }
        });

        setOnMouseClicked(_ -> {
            if (this.getValue() != null) {
                if (Desktop.isDesktopSupported()) {
                    Desktop desktop = Desktop.getDesktop();
                    try {
                        // Abre o arquivo com o programa padrão associado no Windows
                        desktop.open(new File(this.getValue().path()));
                    } catch (IOException _) {
                        System.out.println("Erro ao abrir o arquivo.");
                    }
                } else {
                    System.out.println("A função Desktop não é suportada neste ambiente.");
                }
                return;
            }
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Resource File");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("All Files", "*.*"));

            File selectedFile = fileChooser.showOpenDialog(new Stage());

            if (selectedFile == null) return;

            TaskFile taskFile = new TaskFile(0, selectedFile.getPath(), id);
            setValue(taskFile);
        });
    }

    public File getFile() {
        return this.file;
    }

    public void setFile(File file) {
        this.file = file;
        double megabytes = (double) file.length() / (1024 * 1024);

        FileSystemView fsv = FileSystemView.getFileSystemView();

        javax.swing.Icon icon = fsv.getSystemIcon(file);

        int iconWidth = Math.max(icon.getIconWidth(), 25);
        int iconHeight = Math.max(icon.getIconHeight(), 25);
        BufferedImage bufferedImage = new BufferedImage(iconWidth, iconHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = bufferedImage.createGraphics();
        try {
            icon.paintIcon(null, graphics, 3, 5);
        } finally {
            graphics.dispose();
        }

        // 4. Converter o BufferedImage para Image do JavaFX
        Image img = SwingFXUtils.toFXImage(bufferedImage, null);

        ImageView imageView = new ImageView(img);
        imageView.setFitHeight(30);
        imageView.setFitWidth(30);

        GridPane grid = new GridPane();
//        grid.setPadding(new Insets(2));
        setGraphic(grid);

        var title = new Label(file.getName());
        title.setTextOverrun(OverrunStyle.CENTER_ELLIPSIS);
        var size = String.format("%.2f MB%n", megabytes);
        var additional = new Label(size.trim());

        GridPane.setHgrow(additional, Priority.ALWAYS);
        GridPane.setValignment(imageView, VPos.CENTER);

        grid.add(imageView, 0, 0, 1, GridPane.REMAINING);
        grid.add(title, 1, 0);
        grid.add(additional, 1, 1);

        setGraphic(grid);
        setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
    }
}
