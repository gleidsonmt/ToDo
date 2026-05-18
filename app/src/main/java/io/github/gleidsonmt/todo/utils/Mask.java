

package io.github.gleidsonmt.todo.utils;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;

/**
 * @author Gleidson Neves da Silveira | <a href="mailto:gleidisonmt@gmail.com">gleidisonmt@gmail.com</a>
 * Create on 24/11/2018
 * Version 1.0
 */
public class Mask {
    public static void noSpaces(TextField field) {
        field.lengthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if (field.getText() != null) {
                    if (!field.getText().isEmpty()) {
                        String value = field.getText();
                        value = value.replaceFirst("[ ]", "");
                        field.setText(value);
                    }
                }
            }
        });
    }

    public static void maxField(TextField textField, int limit) {

        textField.lengthProperty().addListener(new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if (newValue.intValue() > oldValue.intValue()) {
                    // Check if the new character is greater than LIMIT
                    if (textField.getText().length() >= limit) {

                        // if it's 11th character then just setText to previous
                        // one
                        textField.setText(textField.getText().substring(0, limit));
                    }
                }
            }
        });

        // field.textProperty().addListener((observableValue, oldValue,
        // newValue) -> {
        // if (newValue == null || newValue.length() > length) {
        // field.setText(oldValue);
        // }
        // });
    }

    public static void noInitSpace(TextField field) {
        field.lengthProperty().addListener((observable, oldValue, newValue) -> {

            if (field.getText() != null) {
                if (field.getText().length() > 0) {
                    String value = field.getText();
                    value = value.replaceFirst("[^a-zA-Z ][~´]", "");
                    value = value.replaceAll("^ ", "");
                    field.setText(value);

                }
            }
        });
    }

    public static void noSymbols(final TextField field) {
        ChangeListener listener = (ChangeListener<Number>) (observable, oldValue, newValue) -> {
            if (field.getText() != null) {
                if (field.getText().length() > 0) {
                    String value = field.getText();
                    value = value.replaceAll("[^a-zA-Z0-9 ]", "");
                    field.setText(value);
                }
            }
        };
        field.lengthProperty().addListener(listener);
    }

    public static void numericField(final TextField textField) {
        textField.lengthProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.intValue() > oldValue.intValue()) {
                char ch = textField.getText().charAt(oldValue.intValue());
                if (!(ch >= '0' && ch <= '9')) {
                    textField.setText(textField.getText().substring(0, textField.getText().length() - 1));
                }
            }
        });
    }

    public static void noSymbols(final TextField field, String exceptions) {

        ChangeListener listener = (ChangeListener<Number>) (observable, oldValue, newValue) -> {
            if (field.getText() != null) {
                if (field.getText().length() > 0) {
                    String value = field.getText();
                    value = value.replaceAll("[^a-zA-Z0-9 " + exceptions + "]", "");
                    field.setText(value);
                }
            }
        };
        field.lengthProperty().addListener(listener);
    }

    public static void decimal(final TextField field) {

        ChangeListener listener = (ChangeListener<Number>) (observable, oldValue, newValue) -> {
            if (field.getText() != null) {
                if (field.getText().length() > 0) {
                    String value = field.getText();
                    value = value.replaceAll("[^0-9 ,]", "");
                    field.setText(value);
                }
            }
        };
        field.lengthProperty().addListener(listener);
    }

    public static void nameField(final TextField field) {
        field.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                String text = field.getText();
                String[] parts = text.split(" ");
                StringBuilder sb = new StringBuilder();
                for (String word : parts) {
                    if (word.length() > 2) {
                        word = word.substring(0, 1).toUpperCase() + word.substring(1);
                    }
                    sb.append(" ").append(word);
                }

                field.setText(sb.toString().replaceFirst(" ", ""));
                field.positionCaret(field.getText().length());
            }
        });
    }

    public static boolean isEmail(TextField field) { // KeyPressed
        boolean is = false;
        if (!field.getText().isEmpty()) {
            if (field.getText().contains("@") && field.getText().contains(".") && !field.getText().contains(" ")) {

                String user = field.getText().substring(0, field.getText().lastIndexOf('@'));
                String domain = field.getText().substring(field.getText().lastIndexOf('@') + 1,
                        field.getText().length());
                String subdomain = field.getText().substring(field.getText().indexOf(".") + 1,
                        field.getText().length());

                if ((user.length() >= 1) && (!user.contains("@")) && (domain.contains(".")) && (!domain.contains("@"))
                    && (domain.indexOf(".") >= 1) && (domain.lastIndexOf(".") < domain.length() - 1)
                    && subdomain.length() >= 2) {
                    is = true;
                }
            }
        }
        return is;
    }

    public static void emailField(TextField field) {
        field.lengthProperty().addListener((observable, oldValue, newValue) -> {
            if (field.getText() != null) {
                if (field.getText().length() > 0) {
                    String value = field.getText();
                    value = value.replaceAll("[^a-zA-Z0-9@.]", "");
                    field.setText(value);

                }
            }
        });
    }

    public static void noLetters(final TextField textField) {

        textField.lengthProperty().addListener((observable, oldValue, newValue) -> {
            if (textField.getText() != null) {
                if (textField.getText().length() > 0) {
                    String value = textField.getText();
                    value = value.replaceAll("[a-zA-Zç]", "");
                    textField.setText(value);
                }
            }
        });
    }

    public static void cpfCnpjField(final TextField textField) {

        textField.focusedProperty().addListener(
                (ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean fieldChange) -> {
                    String value = textField.getText();
                    if (!fieldChange) {
                        if (textField.getText().length() == 11) {
                            value = value.replaceAll("[^0-9]", "");
                            value = value.replaceFirst("([0-9]{3})([0-9]{3})([0-9]{3})([0-9]{2})$", "$1.$2.$3-$4");
                        }
                        if (textField.getText().length() == 14) {
                            value = value.replaceAll("[^0-9]", "");
                            value = value.replaceFirst("([0-9]{2})([0-9]{3})([0-9]{3})([0-9]{4})([0-9]{2})$",
                                    "$1.$2.$3/$4-$5");
                        }
                    }
                    textField.setText(value);
                    if (!textField.getText().equals(value)) {
                        textField.setText("");
                        textField.insertText(0, value);
                    }
                });

    }

}
