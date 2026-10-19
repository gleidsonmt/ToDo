

package io.github.gleidsonmt.todo.global;

import io.github.gleidsonmt.todo.model.DTO;
import javafx.application.HostServices;
import org.jetbrains.annotations.ApiStatus;

import java.io.File;
import java.io.IOException;
import java.util.prefs.Preferences;

/**
 * Description:
 *
 * @author Gleidson Neves da Silveira | <a href="mailto:gleidisonmt@gmail.com">gleidisonmt@gmail.com</a> <br>
 * Created On: Mar 17, 2026
 * <p>
 * Version History: Initial version
 */
public class Global {

    private static io.github.gleidsonmt.todo.global.Repository repository;
    private static Preferences preferences;

    public static HostServices hostServices;

    public static void openLink(String link) {
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

    private static String getPath() {
        return new File(new File(System.getProperty("user.dir")), "JavaFx ToDo.exe").toString();
    }

    private static boolean installOrRemoveWindowsRegister(boolean add) {
        String regCommand = "reg " + (add ? "add" : "delete") + "\"HKCU\\Software\\Microsoft\\Windows\\CurrentVersion\\Run\" /v \"JavaFx ToDo\" /t REG_SZ /d \"\\\""
                            + getPath() + "\\\" --background\" /f";

        try {
            ProcessBuilder pb = new ProcessBuilder("cmd", "/c", regCommand);
            Process pro = pb.start();
            pro.waitFor();
            return true;
        } catch (IOException e) {
            return false;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean installWindowsRegister() {
        return installOrRemoveWindowsRegister(true);
    }

    public static boolean removeWindowsRegister() {
        return installOrRemoveWindowsRegister(false);
    }

    @ApiStatus.Experimental
    @Deprecated(forRemoval = true)
    private static Repository getRepository() {
        if (repository == null) {
            repository = new Repository();
            System.getProperties().put("repository", repository);
            return repository;
        } else
            return repository;
    }

    public static <T extends DTO> Presenter<T> get(Class<T> model) {
        return getRepository().of(model);
    }
}
