package io.github.gleidsonmt.todo.view.aside;

/**
 * @author Gleidson Neves da Silveira | <a href="mailto:gleidisonmt@gmail.com">gleidisonmt@gmail.com</a> <br>
 * Created on 17/10/2026
 */
public class MultipleBlockSection extends Section {
    public MultipleBlockSection() {
        Item itemRemind = new Item(new RemindLabel(), null);
        Item itemDueDate = new Item(new DueDateLabel(), null);
        Item itemRecurrence = new Item(new RecurrenceLabel(), null);

        itemRemind.getStyleClass().addAll("border-b-1 border-light-gray pd-2 ".split("\\s+"));
        itemDueDate.getStyleClass().addAll("border-b-1 border-light-gray pd-2 ".split("\\s+"));
        getChildren().setAll(itemRemind, itemDueDate, itemRecurrence);
    }
}
