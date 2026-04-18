package io.github.gleidsonmt.todo.global;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.util.Arrays;
import java.util.Objects;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on 19/11/2024
 */
public class Folder {

    private final File avatarFolder;
    private final String separator;
    private String defaultFolder;

    public Folder() {
        separator = FileSystems.getDefault().getSeparator();
        defaultFolder = System.getProperty("user.dir");
        avatarFolder = new File(defaultFolder + separator + "avatars");
        createAvatarFolder();
    }

    public boolean has(String imageUrl) {
        if (imageUrl == null)
            return false;
        return Arrays.stream(Objects.requireNonNull(avatarFolder.list())).anyMatch(imageUrl::equalsIgnoreCase);
    }

    private boolean createAvatarFolder() {
        if (!avatarFolder.exists()) {
            return avatarFolder.mkdir();
        }
        return false;
    }

    public Image getAvatar(String url) {
        Image image;
        try {
            File file = new File(avatarFolder.getCanonicalFile() + separator + url);
            image = new Image(file.toURI().toURL().toExternalForm());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return image;
        // return null;
    }

    public Image getAvatar(String url, int size) {
        Image image;
        try {
            File file = new File(avatarFolder.getCanonicalFile() + separator + url);
            image = new Image(file.toURI().toURL().toExternalForm(), size, size, true, true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return image;
    }

    public void saveAvatar(Image image, String name) {

        File file = new File(avatarFolder + separator + name + ".png");

        BufferedImage bImage = SwingFXUtils.fromFXImage(image, null);
        try {
            ImageIO.write(bImage, "png", file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
