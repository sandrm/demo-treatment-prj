package com.sandrm.demo.treatment;

import com.sandrm.demo.treatment.service.RecurrencePatternParser;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;


public class RecurrencePatternParserTest {

    private static final String recurrencePatternEveryDay = "every day at 08:00";
    private static final String recurrencePatternEveryDayOfWeek = "every Monday at 12:00";

    @Test
    public void test_every_day_pattern() {
        String[] expectedValue = {"08", "00"};

        String[] actualValue = RecurrencePatternParser.getTimeFromRecurrencePattern(
                recurrencePatternEveryDay, RecurrencePatternParser.EVERY_DAY_AT);

        assertArrayEquals(expectedValue, actualValue, "Should be equals!");
    }

    @Test
    public void test_get_day_name() {
        Date today = new Date();
        String name = RecurrencePatternParser.getDayName(today);

        assertNotNull(name, "Name should be one day of week!");
        System.out.println(name);
    }


    @Test
    public void test_every_day_of_week_pattern() {
        String[] expectedValue = {"12", "00"};

        String[] actualValue = RecurrencePatternParser.getTimeFromRecurrencePattern(
                recurrencePatternEveryDayOfWeek, RecurrencePatternParser.EVERY_MONDAY_AT);

        assertArrayEquals(expectedValue, actualValue, "Should be equals!");
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
