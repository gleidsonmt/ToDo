package io.github.gleidsonmt.todo.view_model.map;

import io.github.gleidsonmt.todo.model.RecurrenceViewModel;
import io.github.gleidsonmt.todo.model.recurrence.Recurrence;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author Gleidson Neves da Silveira | <a href="mailto:gleidisonmt@gmail.com">gleidisonmt@gmail.com</a> <br>
 * Created on 29/09/2026
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface RecurrenceMapper {

    RecurrenceMapper INSTANCE =  Mappers.getMapper(RecurrenceMapper.class);

    Recurrence toDTO(RecurrenceViewModel recurrence);

    @Mapping(target = "name", ignore = true)
    @Mapping(target = "daysOfWeek", source = "daysOfWeek", qualifiedByName = "toAll")
    RecurrenceViewModel toViewModel(Recurrence dto, @MappingTarget RecurrenceViewModel model);

    @Named("toAll")
    default Set<DayOfWeek> toAll(Set<DayOfWeek> dayOfWeeks) {
        return dayOfWeeks.isEmpty() ? new TreeSet<>(List.of(DayOfWeek.values())) : dayOfWeeks;
    }
}
