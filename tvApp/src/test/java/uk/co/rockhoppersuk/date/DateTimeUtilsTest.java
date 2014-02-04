/*
 * (c) Mark Bailey - www.rockhoppersuk.co.uk - 2010
 */
package uk.co.rockhoppersuk.date;

import java.util.Date;
import java.util.TimeZone;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * A test for DateTimeUtils.
 *
 * @author mbailey
 * @version 1.0
 */
public class DateTimeUtilsTest {

    private final static int numberMiilisecondsInDay = 1000 * 60 * 60 * 24;

    /**
     * Test of getMidnightToday method, of class DateTimeUtils.
     */
    @Test
    public void testGetToday() {
        TimeZone.setDefault(TimeZone.getTimeZone("GMT0"));
        Date today = new Date();
        Date expectedDate = new Date(
                (today.getTime() / numberMiilisecondsInDay) * numberMiilisecondsInDay);
        Date actualDate = DateTimeUtils.getToday();
        assertEquals("getToday", expectedDate, actualDate);
    }

    /**
     * Test of getNextDay method, of class DateTimeUtils.
     */
    @Test
    public void testGetNextDay() {
        Date date = new Date(0);
        Date expResult = new Date(numberMiilisecondsInDay);
        Date result = DateTimeUtils.getNextDay(date);
        assertEquals("Next day equals today", expResult, result);
    }

    /**
     * Test of getPreviousDay method, of class DateTimeUtils.
     */
    @Test
    public void testGetPreviousDay() {
        Date date = new Date(numberMiilisecondsInDay);
        Date expResult = new Date(0);
        Date result = DateTimeUtils.getPreviousDay(date);
        assertEquals("previous day equals today", expResult, result);
    }

}