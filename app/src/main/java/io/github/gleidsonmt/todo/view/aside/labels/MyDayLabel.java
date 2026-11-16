package io.github.gleidsonmt.todo.view.aside.labels;

import io.github.gleidsonmt.glad.controls.icon.Icon;
import io.github.gleidsonmt.todo.utils.I18n;
import io.github.gleidsonmt.todo.view.aside.Item;

/**
 * @author Gleidson Neves da Silveira | <a href="mailto:gleidisonmt@gmail.com">gleidisonmt@gmail.com</a> <br>
 * Created on 17/10/2026
 */
public class MyDayLabel extends CustomLabel<Boolean> {

    public MyDayLabel() {
        super(I18n.get("drawer.list.my_day"), Icon.SUN, "Add task to My day");

        setOnMouseClicked(_ -> {
            Item<?> item = (Item<?>) getParent();
            if (item.isSelected()) return;
//            setValue(getValue() != null);
            if (getValue() != null) setValue(!getValue());
            else setValue(true);
            System.out.println("value = " + value);
        });


    }
}
