package io.github.gleidsonmt.todo.view_model.map;

import io.github.gleidsonmt.todo.model.User;
import io.github.gleidsonmt.todo.view_model.UserViewModel;
import io.github.gleidsonmt.todo.view_model.ViewModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * @author Gleidson Neves da Silveira | <a href="mailto:gleidisonmt@gmail.com">gleidisonmt@gmail.com</a> <br>
 * Created on 27/09/2026
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface UserMapper extends GenericMapper<UserViewModel, User> {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "salt", ignore = true)
    User toDTO(UserViewModel viewModel);
}