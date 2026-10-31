package io.github.gleidsonmt.todo.view_model.map;

import io.github.gleidsonmt.todo.model.ToDoTask;
import io.github.gleidsonmt.todo.view_model.TaskViewModel;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * @author Gleidson Neves da Silveira | <a href="mailto:gleidisonmt@gmail.com">gleidisonmt@gmail.com</a> <br>
 * Created on 27/09/2026
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface TaskMapper extends GenericMapper<TaskViewModel, ToDoTask> {
    TaskMapper INSTANCE = Mappers.getMapper(TaskMapper.class);
}