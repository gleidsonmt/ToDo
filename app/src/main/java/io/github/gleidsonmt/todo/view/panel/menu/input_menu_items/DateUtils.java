

package io.github.gleidsonmt.todo.view.panel.menu.input_menu_items;

import io.github.gleidsonmt.todo.model.recurrence.Recurrence;
import io.github.gleidsonmt.todo.model.recurrence.RecurrenceType;
import io.github.gleidsonmt.todo.utils.StringUtils;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * @author Gleidson Neves da Silveira | <a href="mailto:gleidisonmt@gmail.com">gleidisonmt@gmail.com</a>
 * Create on  09/04/2026
 */
public class DateUtils {

    public static boolean isWeekend(LocalDate date) {
        return date.getDayOfWeek().equals(DayOfWeek.SUNDAY) || date.getDayOfWeek().equals(DayOfWeek.SATURDAY);
    }

    public static String formatDay(LocalDate localDate) {
        return DateTimeFormatter
                .ofPattern("E")
                .format(localDate)
                ;
    }

    public static String format(LocalDate localDate) {
        if (localDate.equals(LocalDate.now())) {
            return "Today";
        } else if (localDate.equals(LocalDate.now().plusDays(1))) {
            return "Tomorrow";
        } else if (localDate.equals(LocalDate.now().minusDays(1))) {
            return "Yesterday";
        } else {
            return DateTimeFormatter
                    .ofPattern("E d 'of' MMM")
                    .format(localDate)
                    .replaceFirst("\\.", ",")
                    .replace(".", "");
        }
    }

    public static String format(LocalTime time) {
        return DateTimeFormatter.ofPattern("H:mm").format(time);
    }

    public static String format(LocalDate localDate, LocalTime time) {
        return DateTimeFormatter.ofPattern("E").format(localDate) + " " + DateTimeFormatter.ofPattern("H:mm").format(time);
    }

    public static String format(LocalDateTime dateTime) {
        return format(dateTime.toLocalDate(), dateTime.toLocalTime());
    }

    public static String format(Recurrence recurrence) {
        if (recurrence.getType() == RecurrenceType.WEEKDAYS) {
            return StringUtils.name(RecurrenceType.WEEKDAYS.toString());
        } else if (recurrence.getType() != RecurrenceType.CUSTOM) {
            return StringUtils.name(recurrence.toString());
        } else {
            return "Custom";
        }
    }

    public static String format(String init, LocalTime time, String with, LocalDate localDate) {
        if (localDate.equals(LocalDate.now())) {
            return init + DateTimeFormatter.ofPattern("H:mm").format(time) + ", Today";
        } else if (localDate.equals(LocalDate.now().plusDays(1))) {
            return init + DateTimeFormatter.ofPattern("H:mm").format(time) + ", Tomorrow";
        } else if (localDate.equals(LocalDate.now().minusDays(1))) {
            return init + DateTimeFormatter.ofPattern("H:mm").format(time) + ", Yesterday";
        } else {
            return init + DateTimeFormatter.ofPattern("H:mm").format(time) + ", " +
                   DateTimeFormatter
                           .ofPattern("E d 'of' MMM")
                           .format(localDate)
                           .replaceFirst("\\.", ",")
                           .replace(".", "");
        }
    }
}
