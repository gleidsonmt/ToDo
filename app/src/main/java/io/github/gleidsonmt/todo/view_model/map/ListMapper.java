package io.github.gleidsonmt.todo.view_model.map;

import io.github.gleidsonmt.todo.model.List;
import io.github.gleidsonmt.todo.model.ListType;
import io.github.gleidsonmt.todo.view_model.ListViewModel;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

/**
 * @author Gleidson Neves da Silveira | <a href="mailto:gleidisonmt@gmail.com">gleidisonmt@gmail.com</a> <br>
 * Created on 27/09/2026
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface ListMapper {

    ListMapper INSTANCE = Mappers.getMapper(ListMapper.class);

    List toDTO(ListViewModel model);

    @Mapping(target = "icon", source = "icon", qualifiedByName = "toIcon")
    @Mapping(target = "type", source = "name", qualifiedByName = "toEnum")
    ListViewModel toViewModel(List dto, @MappingTarget ListViewModel model);

    @Named("toEnum")
    default ListType toEnum(String name) {
        return name == null || name.isBlank() ? null : ListType.convert(name);
    }

    @Named("toIcon")
    default String toIcon(String icon) {
        return icon == null || icon.isBlank() ? "check-list" : icon;
    }
}