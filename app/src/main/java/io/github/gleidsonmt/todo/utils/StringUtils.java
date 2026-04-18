package io.github.gleidsonmt.todo.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jetbrains.annotations.NotNull;

/**
 * Description:
 *
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Created On: Mar 16, 2026
 * <p>
 * Version History: Initial version
 */
public class StringUtils {

    /**
     * Get the last number with the pattern string [09]
     * ex. String x = "same_string 5";
     * will be return 5;
     *
     * @param val The string withe the number;
     * @return The last number in a string
     */
    public static int getLastNumber(String val) {
        return Integer.parseInt(val.substring(val.indexOf(" ") + 1));
    }

    public static String name(String string) {
        return string.substring(0, 1).toUpperCase() + string.substring(1).toLowerCase();
    }

    public static @NotNull String camelToKebab(String input) {
        Pattern pattern = Pattern.compile("([a-z])([A-Z]+)");
        Matcher matcher = pattern.matcher(input);
        StringBuilder output = new StringBuilder();
        while (matcher.find()) {
            matcher.appendReplacement(output, matcher.group(1) + "_" + matcher.group(2).toLowerCase());
        }
        matcher.appendTail(output);
        return output.toString().toLowerCase();
    }

    public static String kebabToCamel(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }

        StringBuilder sb = new StringBuilder();
        boolean capitalizeNext = false;

        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c == '_') {
                capitalizeNext = true;
            } else {
                if (capitalizeNext) {
                    sb.append(Character.toUpperCase(c));
                    capitalizeNext = false;
                } else {
                    sb.append(Character.toLowerCase(c));
                }
            }
        }
        return sb.toString();
    }

}
