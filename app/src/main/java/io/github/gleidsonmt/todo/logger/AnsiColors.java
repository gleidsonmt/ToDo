

package io.github.gleidsonmt.todo.logger;

import org.jetbrains.annotations.Contract;

/**
 *
 * @author Gleidson Neves da Silveira | <a href="mailto:gleidisonmt@gmail.com">gleidisonmt@gmail.com</a> <br> <br>
 * Created on  19/04/2026
 */
public enum AnsiColors {

    ANSI_RESET("\u001B[0m"), //
    ANSI_BLACK("\u001B[30m"),
    ANSI_RED("\u001B[31m"), // severe/errors
    ANSI_GREEN("\u001B[32m"),
    ANSI_YELLOW("\u001B[33m"), // warnings
    ANSI_BLUE("\u001B[34m"), // infos
    ANSI_PURPLE("\u001B[35m"), // purple
    ANSI_CYAN("\u001B[36m"),
    ANSI_WHITE("\u001B[37m"); // default

    private final String content;

    @Contract(pure = true)
    AnsiColors(String content) {
        this.content = content;
    }

    @Contract(pure = true)
    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return this.content;
    }

}
