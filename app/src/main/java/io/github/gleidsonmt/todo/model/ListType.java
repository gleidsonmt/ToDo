

package io.github.gleidsonmt.todo.model;

/**
 * @author Gleidson Neves da Silveira | <a href="mailto:gleidisonmt@gmail.com">gleidisonmt@gmail.com</a> <br>
 * Create on 10/10/2024
 */
public enum ListType {
    DEFAULT, ALL, COMPLETED, TASKS, IMPORTANT, DAILY, PLANED;

    public static ListType convert(String convert) {

        if (convert.equals("myDay") || convert.equals("my_day")) {
            return ListType.DAILY;
        }

        if (convert.equals("important")) {
            return ListType.IMPORTANT;
        }

        return DEFAULT;
    }

}
