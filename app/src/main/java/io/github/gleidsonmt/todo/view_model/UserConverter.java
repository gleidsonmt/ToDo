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

package io.github.gleidsonmt.todo.view_model;

import io.github.gleidsonmt.todo.model.User;
import io.github.gleidsonmt.todo.model.Usernew;
import io.github.gleidsonmt.todo.view_model.converter.ViewModelConverter;

/**
 * Description:
 *
 * @author Gleidson Neves da Silveira | <a href="mailto:gleidisonmt@gmail.com">gleidisonmt@gmail.com</a> <br>
 * Create on 28/05/2026
 */
public class UserConverter extends ViewModelConverter<User, UserViewModel> {

    @Override
    public User toEntity(UserViewModel model) {
        return new User(
                model.getId(),
                model.getName(),
                model.getUsername(),
                model.getPassword(),
                null,
                model.getImageUrl(),
                true
        );
    }

    @Override
    public UserViewModel toViewModel(User model) {
        var converted = new UserViewModel();
        converted.setId(model.id());
        converted.setUsername(model.username());
        converted.setName(model.name());
        converted.setImageUrl(model.imageUrl());

//        converted.setUsername(model.getUsername());
//        converted.setImageUrl(model.getImageUrl());
        return converted;
    }




}
