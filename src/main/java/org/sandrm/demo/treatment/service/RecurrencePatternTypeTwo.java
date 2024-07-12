package org.sandrm.demo.treatment.service;

import java.util.Date;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.sandrm.demo.treatment.service.RecurrencePatternUtil.*;

/**
 * Processing pattern like "every day at 08:00 and 16:00"
 */
public class RecurrencePatternTypeTwo {
    public static String PATTERN_TYPE_TWO = "^every day at " + regEx_HH_MM + AND + regEx_HH_MM + "$";
    public static Pattern PATTERN_TYPE_TWO_COMPILED = Pattern.compile(PATTERN_TYPE_TWO);
    public static
    String PATTERN_TYPE_TWO_BEFORE_TIME2 = "every day at " + regEx_HH_MM + AND;  //"every day at 08:00 and "


    public Optional<Date[]> calculateTaskDate(Date treatmentDate, String recurrencePattern) {
        Date[] result = new Date[2];

        Matcher matcherTwo = PATTERN_TYPE_TWO_COMPILED.matcher(recurrencePattern);
        if (matcherTwo.find()) {
            result[0] = parseDate(treatmentDate, recurrencePattern, EVERY_DAY_AT);
            result[1] = parseDate(treatmentDate, recurrencePattern, PATTERN_TYPE_TWO_BEFORE_TIME2);

            return Optional.of(result);
        }

        return Optional.empty();
    }
}
