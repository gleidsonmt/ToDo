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

import io.github.gleidsonmt.todo.model.Usernew;
import io.github.gleidsonmt.todo.view_model.converter.ViewModelConverter;

/**
 * Description:
 *
 * @author Gleidson Neves da Silveira | <a href="mailto:gleidisonmt@gmail.com">gleidisonmt@gmail.com</a> <br>
 * Create on 28/05/2026
 */
public class UserConverter extends ViewModelConverter<Usernew, UserViewModel> {

    @Override
    public Usernew toEntity(UserViewModel model) {
        var entity = new Usernew(model.getId(), model.getName());
        entity.setUsername(model.getUsername());
        entity.setPassword(model.getPassword());
        entity.setImageUrl(model.getImageUrl());
        return entity;
    }

    @Override
    public UserViewModel toViewModel(Usernew model) {
        var converted = new UserViewModel();
        converted.setId(model.getId());
        converted.setUsername(model.getUsername());
        converted.setImageUrl(model.getImageUrl());
        return converted;
    }




}
