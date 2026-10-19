package io.github.gleidsonmt.todo.view.aside;

import javafx.scene.control.TextArea;
import javafx.scene.text.Text;

/**
 * @author Gleidson Neves da Silveira | <a href="mailto:gleidisonmt@gmail.com">gleidisonmt@gmail.com</a> <br>
 * Created on 17/10/2026
 */
public class AreaInsert extends TextArea {
    public AreaInsert() {
        setWrapText(true);
        getStyleClass().addAll("area-insert h-80 border-transparent".split("\\s+"));
        setPromptText("Add a note");
        textProperty().addListener((_, _, newValue) -> {
            // Criamos um nó de texto temporário para calcular o tamanho real do conteúdo

            Text textHelper = new Text();
            textHelper.setWrappingWidth(getWidth() - 20); // Desconto das bordas/paddings
            textHelper.setFont(getFont());
            textHelper.setText(newValue);

            // Obtém a altura necessária para o texto e adiciona uma margem de respiro
            double newHeight = textHelper.getLayoutBounds().getHeight() + 80;

            // Define a nova altura respeitando uma altura mínima
            setPrefHeight(Math.max(newHeight, 60));
            setMaxHeight(Math.max(newHeight, 60));
            setMinHeight(Math.max(newHeight, 60));

        });
    }
}
