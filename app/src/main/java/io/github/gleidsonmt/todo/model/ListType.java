

package io.github.gleidsonmt.todo.model;

/**
 * @author Gleidson Neves da Silveira | <a href="mailto:gleidisonmt@gmail.com">gleidisonmt@gmail.com</a> <br>
 * Create on 10/10/2024
 */
public enum ListType {
    DEFAULT, ALL, COMPLETED, TASKS, IMPORTANT, DAILY, PLANED;

    public static ListType convert(String convert) {
        return switch (convert) {
            case "drawer.list.myDay", "drawer.list.my_day" -> ListType.DAILY;
            case "drawer.list.important" -> ListType.IMPORTANT;
            case "drawer.list.tasks" -> ListType.TASKS;
            case "drawer.list.all" -> ListType.ALL;
            default -> DEFAULT;
        };

    }

}
