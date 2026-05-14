package io.github.gleidsonmt.todo.global;

import io.github.gleidsonmt.todo.model.Model;
import javafx.application.HostServices;
import org.jetbrains.annotations.ApiStatus;

import java.util.prefs.Preferences;

/**
 * Description:
 *
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Created On: Mar 17, 2026
 * <p>
 * Version History: Initial version
 */
public class Global {

    private static io.github.gleidsonmt.todo.global.Repository repository;
    private static Preferences preferences;

    public static HostServices hostServices;

    public static void openLink(String link) {
        System.out.println("hostServices = " + link);
        hostServices.showDocument(link);
    }

    public static Preferences getPreferences() {
        if (preferences == null) {
            preferences = Preferences.userNodeForPackage(Global.class);
        }
        return preferences;
    }

    public static boolean isRunningOnExecutable() {
        String processName = ProcessHandle.current().info().command().orElse("");
        return processName.endsWith(".exe") && !processName.contains("java.exe");
    }

    @ApiStatus.Experimental
    private static Repository getRepository() {
        if (repository == null) {
            repository = new Repository();
            System.getProperties().put("repository", repository);
            return repository;
        } else
            return repository;
    }

    public static <T extends Model> Presenter<T> get(Class<T> model) {
        return getRepository().of(model);
    }
}
