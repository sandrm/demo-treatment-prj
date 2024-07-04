package org.sandrm.demo.treatment;

import org.junit.jupiter.api.Test;
import org.sandrm.demo.treatment.service.RecurrencePatternParser;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class RecurrencePatternParserTest {

    private static final String recurrencePatternEveryDay = "every day at 08:00";
    private static final String recurrencePatternEveryDayOfWeek = "every Monday at 12:00";

    @Test
    public void test_get_day_name() {
        Date today = new Date();
        String name = RecurrencePatternParser.getDayName(today);

        assertNotNull(name, "Name should be one day of week!");
        System.out.println(name);
    }


    @Test
    public void test_patter_HH_MM() {
        String regEx = "^([0-1]?[0-9]|2[0-3]):[0-5][0-9]$";
        String expected = "08:00";
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(expected);

        assertTrue(matcher.find(), "Not correct patter for HH:MM");

        expected = "13:00";
        pattern = Pattern.compile(regEx);
        matcher = pattern.matcher(expected);

        assertTrue(matcher.find(), "Not correct patter for HH:MM");
    }
}
