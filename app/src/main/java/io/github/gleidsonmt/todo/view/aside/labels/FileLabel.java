package io.github.gleidsonmt.todo.view.aside.labels;

import io.github.gleidsonmt.glad.controls.icon.Icon;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.swing.filechooser.FileSystemView;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * @author Gleidson Neves da Silveira | <a href="mailto:gleidisonmt@gmail.com">gleidisonmt@gmail.com</a> <br>
 * Created on 18/10/2026
 */
public class FileLabel extends CustomLabel<File> {
    public FileLabel() {
        super("Add file ", Icon.ATTACH_FILE, "");

        setOnMouseClicked(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Resource File");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("All Files", "*.*"));

            File selectedFile = fileChooser.showOpenDialog(new Stage());

            if (selectedFile == null) return;

            setValue(selectedFile);

//            System.out.println("selectedFile = " + selectedFile);
//            System.out.println("selectedFile.exists() = " + selectedFile.exists());
//            System.out.println("selectedFile.getAbsolutePath() = " + selectedFile.getAbsolutePath());
//            try {
//                System.out.println("selectedFile.getCanonicalPath() = " + selectedFile.getCanonicalPath());
//                System.out.println("selectedFile.getName() = " + selectedFile.getName());
//                System.out.println("selectedFile.getParent() = " + selectedFile.getParent());
//                System.out.println("selectedFile.getPath() = " + selectedFile.getPath());
//                System.out.println("selectedFile.getAbsoluteFile() = " + selectedFile.getAbsoluteFile());
//                System.out.println("selectedFile.getTotalSpace() = " + selectedFile.getTotalSpace());
//                System.out.println("selectedFile.getUsableSpace() = " + selectedFile.getUsableSpace());
//                System.out.println("selectedFile.getFreeSpace() = " + selectedFile.getFreeSpace());
//            } catch (IOException ex) {
//                throw new RuntimeException(ex);
//            }

//            FileSystemView fsv = FileSystemView.getFileSystemView();
//
//            javax.swing.Icon icon = fsv.getSystemIcon(selectedFile);
//
//            int iconWidth = Math.max(icon.getIconWidth(), 50);
//            int iconHeight = Math.max(icon.getIconHeight(), 50);
//            BufferedImage bufferedImage = new BufferedImage(iconWidth, iconHeight, BufferedImage.TYPE_INT_ARGB);
//            Graphics2D graphics = bufferedImage.createGraphics();
//            try {
//                icon.paintIcon(null, graphics, 2, 15);
//            } finally {
//                graphics.dispose();
//            }
//
//            // 4. Converter o BufferedImage para Image do JavaFX
//            Image img = SwingFXUtils.toFXImage(bufferedImage, null);
//
//            setGraphic(new ImageView(img));
            // selectedFile.getName();
        });
    }

    public void setFile(File file) {
        FileSystemView fsv = FileSystemView.getFileSystemView();

        javax.swing.Icon icon = fsv.getSystemIcon(file);

        int iconWidth = Math.max(icon.getIconWidth(), 20);
        int iconHeight = Math.max(icon.getIconHeight(), 50);
        BufferedImage bufferedImage = new BufferedImage(iconWidth, iconHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = bufferedImage.createGraphics();
        try {
            icon.paintIcon(null, graphics, 2, 15);
        } finally {
            graphics.dispose();
        }

        // 4. Converter o BufferedImage para Image do JavaFX
        Image img = SwingFXUtils.toFXImage(bufferedImage, null);

        setGraphic(new ImageView(img));
        setText(file.getName());
    }
}
