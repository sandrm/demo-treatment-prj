package org.sandrm.demo.treatment.service;

import java.util.Date;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.sandrm.demo.treatment.service.RecurrencePatternUtil.*;

/**
 * Processing pattern like "every Monday at 08:00"
 */
public class RecurrencePatternTypeOne {
    public static String DAY_OF_WEEK = "(Monday|Tuesday|Wednesday|Thursday|Friday|Saturday|Sunday)";
    public static Pattern PATTERN_DAY_OF_WEEK_COMPILED =
            Pattern.compile("^every " + DAY_OF_WEEK + " at " + regEx_HH_MM + "$");


    public Optional<Date> calculateTaskDate(Date treatmentDate, String recurrencePattern) {
        Matcher matcher = PATTERN_DAY_OF_WEEK_COMPILED.matcher(recurrencePattern);
        if (matcher.find()) {
            String dayName = getDayName(treatmentDate);
            String firstPartOfPattern = getFirstPartOfPatternType2(dayName);

            if (recurrencePattern.contains(firstPartOfPattern)) {
                Date taskStartTime = parseDate(treatmentDate, recurrencePattern, firstPartOfPattern);

                return Optional.of(taskStartTime);
            }
        }

        return Optional.empty();
    }

}