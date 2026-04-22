package io.github.gleidsonmt.todo.utils;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ResourceList;
import io.github.classgraph.ScanResult;
import io.github.gleidsonmt.todo.App;
import javafx.scene.image.Image;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on 19/02/2025
 */
public class Assets {

    public static Image getIcon(String iconName) {
        return getIcon(iconName, 20);
    }

    public static Image getIcon(String iconName, int size) {
        try {
            Optional<Image> icon = getAllIcons(size).stream().filter(el -> el.getUrl().endsWith(iconName)).findFirst();
            return icon.orElse(null);
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Image> getAllIcons(int size) throws IOException, URISyntaxException {
        // Defina o caminho relativo à raiz do classpath
        String path = "/io/github/gleidsonmt/todo/icons";
        List<Image> images = new ArrayList<>();
        try (ScanResult scanResult = new ClassGraph()
                .acceptPaths(path) // Limita o scan apenas a essa pasta
                .scan()) {

            // Obtém todos os recursos dentro do caminho especificado
            ResourceList resources = scanResult.getAllResources();

            // Filtra e processa apenas imagens (opcional, dependendo da extensão)
            resources.filter(resource ->
                    (resource.getPath().endsWith(".png") ||
                     resource.getPath().endsWith(".jpg"))
            ).forEach(resource -> {
                Image image = new Image(resource.getURL().toExternalForm(), size, size, true, true);
                images.add(image);
                // Para carregar o conteúdo:
                // InputStream is = resource.open();
            });
        }
        return images;
    }

    public static String getCss(String name) {
        return Objects.requireNonNull(App.class.getResource("css/" + name)).toExternalForm();
    }

    public static Image getImage(String name) {
        return new Image(Objects.requireNonNull(App.class.getResource("img/" + name)).toExternalForm(), -1, -1, true,
                true);
    }

    @SuppressWarnings("unused")
    public static Image getImage(String name, int size) {
        return new Image(Objects.requireNonNull(App.class.getResource("img/" + name)).toExternalForm(), size, size,
                true, true);
    }
}
