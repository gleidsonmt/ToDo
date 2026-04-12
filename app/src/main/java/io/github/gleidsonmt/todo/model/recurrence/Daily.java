package io.github.gleidsonmt.todo.model.recurrence;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  13/12/2024
 */
public final class Daily extends Recurrence {

    public Daily() {
        this(1);
    }

    public Daily(int gap) {
        this(0, gap, RecurrenceType.DAILY);
    }

    public Daily(long id, int times, RecurrenceType type) {
        super(0, times, type);
    }

    @Override
    public String getShortName() {
        return "days";
    }
}
