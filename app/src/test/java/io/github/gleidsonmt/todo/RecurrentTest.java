package io.github.gleidsonmt.todo;

import io.github.gleidsonmt.todo.model.DTO;
import io.github.gleidsonmt.todo.model.RecurrenceViewModel;
import io.github.gleidsonmt.todo.model.recurrence.Recurrence;
import io.github.gleidsonmt.todo.model.recurrence.RecurrenceType;
import io.github.gleidsonmt.todo.view_model.map.RecurrenceMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
/**
 * @author Gleidson Neves da Silveira | <a href="mailto:gleidisonmt@gmail.com">gleidisonmt@gmail.com</a> <br>
 * Created on 29/09/2026
 */
public class RecurrentTest {

    @Test
    void testConversion() {
//        public record Recurrence(long id, int gap, RecurrenceType type, long taskID) implements DTO {
        var viewModel = new RecurrenceViewModel(RecurrenceType.DAILY);
        viewModel.setName("Daily Rec");
        viewModel.setId(1);
        viewModel.setGap(1);
        viewModel.setTaskID(1);

//        var recurrence = new Recurrence(1,1, RecurrenceType.DAILY, 1);
        var rec = RecurrenceMapper.INSTANCE.toDTO(viewModel);

//        assertEquals(1, rec.id());
//        assertEquals(1, rec.id());
//        assertEquals(RecurrenceType.DAILY, rec.type());
//        assertEquals(1, rec.taskID());
    }
}
