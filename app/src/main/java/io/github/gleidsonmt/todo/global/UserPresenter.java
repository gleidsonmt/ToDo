

package io.github.gleidsonmt.todo.global;

import java.util.Optional;

import io.github.gleidsonmt.todo.bd.dao.DaoUser;
import io.github.gleidsonmt.todo.model.User;

/**
 * Description:
 *
 * @author Gleidson Neves da Silveira | <a href="mailto:gleidisonmt@gmail.com">gleidisonmt@gmail.com</a> <br>
 * Created On: Mar 17, 2026
 * <p>
 * Version History: Initial version
 */
public class UserPresenter extends AbstractPresenter<User> {

    public UserPresenter() {
        super(new DaoUser());
    }

    public Optional<User> getLogged() {
        return dao.findWhere("logged = true");
    }

    @Override
    public Class<User> getModelClass() {
        return User.class;
    }

}
