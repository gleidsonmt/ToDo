package io.github.gleidsonmt.todo.view.panel.input.menu.input_menu_items;

import io.github.gleidsonmt.todo.model.RecurrenceViewModel;
import io.github.gleidsonmt.todo.model.recurrence.RecurrenceType;
import io.github.gleidsonmt.todo.utils.I18n;
import io.github.gleidsonmt.todo.utils.StringUtils;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * @author Gleidson Neves da Silveira | <a href="mailto:gleidisonmt@gmail.com">gleidisonmt@gmail.com</a> <br>
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
            return I18n.get("recurrence.today");
        } else if (localDate.equals(LocalDate.now().plusDays(1))) {
            return I18n.get("recurrence.tomorrow");
        } else if (localDate.equals(LocalDate.now().minusDays(1))) {
            return I18n.get("recurrence.yesterday");
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

    public static String shortName(RecurrenceType type) {
        return switch (type) {
            case DAILY -> I18n.get("date.daily.short");
            case WEEKDAYS -> I18n.get("date.weekdays.short");
            case WEEKLY -> I18n.get("date.weekly.short");
            case MONTHLY -> I18n.get("date.monthly.short");
            case YEARLY -> I18n.get("date.yearly.short");
            default ->  I18n.get("date.custom.short");
        };
    }

    public static String format(RecurrenceViewModel recurrence) {
        return format(recurrence.getType());
    }
    public static String format(RecurrenceType type) {
        return StringUtils.name(I18n.get("recurrence." + type.toString().toLowerCase()));
    }

    public static String format(String init, LocalTime time, String with, LocalDate localDate) {
        if (localDate.equals(LocalDate.now())) {
            return init + DateTimeFormatter.ofPattern("H:mm").format(time) + ", " + I18n.get("recurrence.today");
        } else if (localDate.equals(LocalDate.now().plusDays(1))) {
            return init + DateTimeFormatter.ofPattern("H:mm").format(time) + ", " + I18n.get("recurrence.tomorrow");
        } else if (localDate.equals(LocalDate.now().minusDays(1))) {
            return init + DateTimeFormatter.ofPattern("H:mm").format(time) + ", " + I18n.get("recurrence.yesterday");
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
