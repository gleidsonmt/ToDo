package io.github.gleidsonmt.todo.global;

import io.github.gleidsonmt.todo.model.Model;

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

    private static Repository repository;
    private static Preferences preferences;

    public static Preferences getPreferences() {
        if (preferences == null) {
            preferences = Preferences.userNodeForPackage(Global.class);
            System.getProperties().put("preferences", preferences);
        }
        return preferences;
    }

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
