package org.sandrm.demo.treatment.service;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

/**
 * Recurrence pattern: for example:
 * “every day at 08:00 and 16:00”
 * “every Monday at 12:00”
 */
public class RecurrencePatternParser {
    public static final String AND = " and ";
    static String regEx_HH_MM = "([0-1]?[0-9]|2[0-3]):[0-5][0-9]";  //08:00
    public static String EVERY_DAY_AT = "every day at ";
    public static String PATTERN_TYPE_TWO = "^every day at " + regEx_HH_MM + AND + regEx_HH_MM + "$";  //every day at 08:00 and 16:00
    public static String PATTERN_TYPE_TWO_BEFORE_TIME2 = "every day at " + regEx_HH_MM + AND;  //"every day at 08:00 and "
    public static Pattern PATTERN_TYPE_TWO_COMPILED = Pattern.compile(PATTERN_TYPE_TWO);
    public static String DAY_OF_WEEK = "(Monday|Tuesday|Wednesday|Thursday|Friday|Saturday|Sunday)";
    public static Pattern PATTERN_DAY_OF_WEEK_COMPILED =
            Pattern.compile("^every " + DAY_OF_WEEK + " at " + regEx_HH_MM + "$");

    public static String getDayName(Date treatmentDay) {
        Format f = new SimpleDateFormat("EEEE");
        String dayName = f.format(treatmentDay);

        return dayName;
    }

    public static String getFirstPartOfPatternType2(String dayOfWeekName) {
        return "every " + dayOfWeekName + " at ";
    }

    public static Date setTimeTimeForTreatmentDate(Date treatmentDay, String[] time) {
        Calendar c = Calendar.getInstance();
        c.setTime(treatmentDay);

        c.set(Calendar.HOUR_OF_DAY, Integer.valueOf(time[0]));
        c.set(Calendar.MINUTE, Integer.valueOf(time[1]));
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);

        return c.getTime();
    }


    public static String[] convertHHMM(String time_HH_MM) {
        String[] time = time_HH_MM.substring(0, 5).split(":");
        return time;
    }
}