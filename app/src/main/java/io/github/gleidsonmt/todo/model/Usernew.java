package io.github.gleidsonmt.todo.model;

import com.dlsc.gemsfx.SVGImageView;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ResourceList;
import io.github.classgraph.ScanResult;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 *
 * @author Gleidson Neves da Silveira | <a href="mailto:gleidisonmt@gmail.com">gleidisonmt@gmail.com</a> <br>
 * Create on 20/05/2026
 */
public class Usernew extends Entity {

    private String imageUrl;
    private byte[] salt;
    private String password;
    private String username;

    public Usernew(long id, String name) {
        super(id, name);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public byte[] getSalt() {
        return salt;
    }

    public void setSalt(byte[] salt) {
        this.salt = salt;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public static java.util.List<SVGImageView> getAllIcons() {
        // Defina o caminho relativo à raiz do classpath
        String _path = new File("avatars").getAbsolutePath();
        System.out.println("Path = " + _path);
        List<SVGImageView> svgs = new ArrayList<>();
        try (ScanResult scanResult = new ClassGraph()
                .acceptPaths(_path) // Limita o scan apenas a essa pasta
                .scan()) {

            // Obtém todos os recursos dentro do caminho especificado
            ResourceList resources = scanResult.getAllResources();

            // Filtra e processa apenas imagens (opcional, dependendo da extensão)
            resources.filter(resource ->
                    resource.getPath().endsWith(".jpg")
            ).forEach(resource -> {
                System.out.println("resource = " + resource);
                // Para carregar o conteúdo:
                // InputStream is = resource.open();
            });
        }
        return svgs;
    }



    @Override
    public String toString() {
        return "{\"Usernew\":"
               + super.toString()
               + ", \"username\":\"" + username + "\""
               + ", \"password\":\"" + password + "\""
               + ", \"salt\":\"" + salt + "\""
               + "}";
    }
}
