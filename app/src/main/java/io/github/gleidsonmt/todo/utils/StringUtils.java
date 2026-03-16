package io.github.gleidsonmt.todo.utils;

/**
 * Description:
 *
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 *         Created On: Mar 16, 2026
 * 
 *         Version History: Initial version
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
}
