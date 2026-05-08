package io.github.gleidsonmt.todo.utils;

import com.dlsc.gemsfx.SVGImageView;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ResourceList;
import io.github.classgraph.ScanResult;
import io.github.gleidsonmt.todo.App;
import javafx.scene.image.Image;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on 19/02/2025
 */
public class Assets {

    private static final String path = "/io/github/gleidsonmt/todo";

    public static List<SVGImageView> getAllIcons(int size) {
        // Defina o caminho relativo à raiz do classpath
        String _path = path + "/svg";
        List<SVGImageView> svgs = new ArrayList<>();
        try (ScanResult scanResult = new ClassGraph()
                .acceptPaths(_path) // Limita o scan apenas a essa pasta
                .scan()) {

            // Obtém todos os recursos dentro do caminho especificado
            ResourceList resources = scanResult.getAllResources();

            // Filtra e processa apenas imagens (opcional, dependendo da extensão)
            resources.filter(resource ->
                    resource.getPath().endsWith(".svg")
            ).forEach(resource -> {
                SVGImageView imageView = new SVGImageView(resource.getURL().toExternalForm());
                imageView.setPreserveRatio(true);
                imageView.setFitHeight(size);
                imageView.setFitWidth(size);

                imageView.setSmooth(true);
                svgs.add(imageView);
                // Para carregar o conteúdo:
                // InputStream is = resource.open();
            });
        }
        return svgs;
    }

    public static SVGImageView getIcon(String iconName, int size) {
        URL resource = App.class.getResource("svg/" + iconName + ".svg");
        SVGImageView imageView = new SVGImageView(Objects.requireNonNull(resource).toExternalForm());
        imageView.setPreserveRatio(true);
        imageView.setFitHeight(size);
        imageView.setFitWidth(size);

        imageView.setSmooth(true);
        return imageView;
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
