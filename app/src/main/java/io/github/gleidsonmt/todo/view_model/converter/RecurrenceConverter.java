package io.github.gleidsonmt.todo.view_model.converter;

import io.github.gleidsonmt.todo.model.RecurrenceViewModel;
import io.github.gleidsonmt.todo.model.recurrence.Recurrence;

/**
 * @author Gleidson Neves da Silveira | <a href="mailto:gleidisonmt@gmail.com">gleidisonmt@gmail.com</a> <br>
 * Created on 25/09/2026
 */
@Deprecated(forRemoval = true)
public class RecurrenceConverter extends ViewModelConverter<Recurrence, RecurrenceViewModel> {


    @Override
    public RecurrenceViewModel toViewModel(Recurrence model) {
        return null;
    }

    @Override
    public Recurrence toDTO(RecurrenceViewModel model) {
        return new Recurrence(
                model.getId(),
                model.getGap(),
                model.getType(),
                model.getDaysOfWeek(),
                model.getTaskID()
        );
    }
}
