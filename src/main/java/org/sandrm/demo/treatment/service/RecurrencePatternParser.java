package org.sandrm.demo.treatment.service;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/*
    TODO it needs fix for more complex patterns like "every day at 08:00 and 16:00"
*/
public class RecurrencePatternParser {
    public static String EVERY_DAY_AT = "every day at ";
    public static String EVERY_MONDAY_AT = "every Monday at ";
    public static String EVERY_TUESDAY_AT = "every Tuesday at ";
    public static String EVERY_WEDNESDAY_AT = "every Wednesday at ";
    public static String EVERY_THURSDAY_AT = "every Thursday at ";
    public static String EVERY_FRIDAY_AT = "every Friday at ";
    public static String EVERY_SATURDAY_AT = "every Saturday at ";
    public static String EVERY_SUNDAY_AT = "every Sunday at ";

    public static Map<String, String> MAP_DAYS_OF_WEEK = new HashMap<>();

    static {
        MAP_DAYS_OF_WEEK.put("Monday", EVERY_MONDAY_AT);
        MAP_DAYS_OF_WEEK.put("Tuesday", EVERY_TUESDAY_AT);
        MAP_DAYS_OF_WEEK.put("Wednesday", EVERY_WEDNESDAY_AT);
        MAP_DAYS_OF_WEEK.put("Thursday", EVERY_THURSDAY_AT);
        MAP_DAYS_OF_WEEK.put("Friday", EVERY_FRIDAY_AT);
        MAP_DAYS_OF_WEEK.put("Saturday", EVERY_SATURDAY_AT);
        MAP_DAYS_OF_WEEK.put("Sunday", EVERY_SUNDAY_AT);
    }


    public static String getDayName(Date treatmentDay) {
        Format f = new SimpleDateFormat("EEEE");
        String dayName = f.format(treatmentDay);

        return dayName;
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