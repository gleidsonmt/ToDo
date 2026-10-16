/*
 * *
 *  * Description:
 *  *
 *  * @author Gleidson Neves da Silveira | <a href="mailto:gleidisonmt@gmail.com">gleidisonmt@gmail.com</a> <br>
 *  * Create on ${DATE}
 *
 */

/*
 * *
 *  * Description:
 *  *
 *  * @author Gleidson Neves da Silveira | <a href="mailto:gleidisonmt@gmail.com">gleidisonmt@gmail.com</a> <br>
 *  * Create on ${DATE}
 *
 */

package io.github.gleidsonmt.todo.view_model.converter;

import io.github.gleidsonmt.todo.model.User;
import io.github.gleidsonmt.todo.view_model.UserViewModel;

/**
 * Description:
 *
 * @author Gleidson Neves da Silveira | <a href="mailto:gleidisonmt@gmail.com">gleidisonmt@gmail.com</a> <br>
 * Create on 28/05/2026
 */
@Deprecated(forRemoval = true)
public class UserConverter extends ViewModelConverter<User, UserViewModel> {

    @Override
    public User toDTO(UserViewModel model) {
        return new User(
                model.getId(),
                model.getName(),
                model.getUsername(),
                model.getPassword(),
                null,
                true
        );
    }

    @Override
    public User toEntity(UserViewModel model) {
        return new User(
                model.getId(),
                model.getName(),
                model.getUsername(),
                model.getPassword(),
                null,
                true
        );
    }

    @Override
    public UserViewModel toViewModel(User model) {
        var converted = new UserViewModel();

        converted.setId(model.id());
        converted.setUsername(model.username());
        converted.setName(model.name());

        return converted;
    }




}
