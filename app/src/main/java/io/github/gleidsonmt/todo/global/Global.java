package io.github.gleidsonmt.todo.global;

import io.github.gleidsonmt.todo.model.Model;

/**
 * Description:
 *
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 *         Created On: Mar 17, 2026
 * 
 *         Version History: Initial version
 */
public class Global {

    private static Repository repository;

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
