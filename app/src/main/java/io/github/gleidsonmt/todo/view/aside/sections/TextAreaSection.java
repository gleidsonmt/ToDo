package io.github.gleidsonmt.todo.view.aside.sections;

import io.github.gleidsonmt.todo.view.aside.AreaInsert;

/**
 * @author Gleidson Neves da Silveira | <a href="mailto:gleidisonmt@gmail.com">gleidisonmt@gmail.com</a> <br>
 * Created on 18/10/2026
 */
public class TextAreaSection extends Section {
    public TextAreaSection() {
        getChildren().add(new AreaInsert());
    }
}
