

package io.github.gleidsonmt.todo.model.recurrence;

import java.time.DayOfWeek;
import java.util.EnumSet;
import java.util.Set;

/**
 *
 * @author Gleidson Neves da Silveira | <a href="mailto:gleidisonmt@gmail.com">gleidisonmt@gmail.com</a> <br> <br>
 * Created on  15/05/2026
 */
public class WeekDayConverter {
    private static final int ANY_VALUE = 127;

    // Converter Java para Banco
    public  int toInteger(Set<DayOfWeek> dias, boolean isAny) {
        if (isAny) return ANY_VALUE;
        if (dias == null || dias.isEmpty()) return 0;

        int mask = 0;
        for (DayOfWeek dia : dias) {
            // O valor do DayOfWeek (1=Segunda, 7=Domingo) vira o deslocamento do bit
            mask |= (1 << (dia.getValue() - 1));
        }
        return mask;
    }

    // Converter Banco para Java
    public  Set<DayOfWeek> toSet(int mask) {
        Set<DayOfWeek> dias = EnumSet.noneOf(DayOfWeek.class);
        if (mask == ANY_VALUE) {
            return EnumSet.allOf(DayOfWeek.class); // Retorna todos se for ANY
        }
        for (DayOfWeek dia : DayOfWeek.values()) {
            if ((mask & (1 << (dia.getValue() - 1))) != 0) {
                dias.add(dia);
            }
        }
        return dias;
    }
}
