package io.github.gleidsonmt.todo.model.recurrence;

import java.time.DayOfWeek;
import java.util.EnumSet;
import java.util.Set;

/**
 * Converter para o banco de dados utilizando bit mask.
 * Um enum de dias [Segunda, Terça, Quarta, Quinta, Sexta, Sábado, Domingo].
 * Cada bit representa um espaco em um espaco total de bits
 * ele pode aprecer como 0 ou 1.
 * O total de bits é 7, pois são 7 dias da semana.
 * Ou seja, 0000001 onde seria o primeiro dia da semana, segunda-feira.
 * Mais um dia 00000011 seria segunda e terça-feira.
 * Estes bits podem ser convertidos e salvos como um unico numero no banco de dados.
 * Por exemplo: todos os dias seriam 7 de 1... 1111111 salvo como 127 no bd.
 * Apenas os dias de semana... 011111 salvo como 31 no bd.
 * Segunda-feira	1	0	0000001 →	1
 * Terça-feira	2	1	0000010	→ 2
 * Quarta-feira	3	2	0000100 →	4
 * Quinta-feira	4	3	0000100 →	8
 * Sexta-feira	5	4	0001000 →	16
 * Sábado	6	5	0010000	→ 32
 * Domingo	7	6	0100000	→ 64
 * TODOS OS DIAS 	1111111 →	127
 *
 * @author Gleidson Neves da Silveira | <a href="mailto:gleidisonmt@gmail.com">gleidisonmt@gmail.com</a> <br> <br>
 * Created on  15/05/2026
 */
public class WeekDayConverter {
    private static final int ANY_VALUE = 127;

    // Converter Java para Banco
    public int toInteger(Set<DayOfWeek> dias) {
        if (dias == null || dias.isEmpty()) return 0;

        int mask = 0;
        for (DayOfWeek dia : dias) {
            // O valor do DayOfWeek (1=Segunda, 7=Domingo) vira o deslocamento do bit
            mask |= (1 << (dia.getValue() - 1));
        }
        return mask;
    }

    @Deprecated
    public int toInteger(Set<DayOfWeek> dias, boolean isAny) {

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
    public Set<DayOfWeek> toSet(int mask) {
        Set<DayOfWeek> dias = EnumSet.noneOf(DayOfWeek.class);
        if (mask == 127) {
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
