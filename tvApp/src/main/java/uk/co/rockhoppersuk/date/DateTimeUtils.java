/*
 * (c) Mark Bailey - www.rockhoppersuk.co.uk - 2010
 */
package uk.co.rockhoppersuk.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import org.apache.log4j.Logger;

/**
 * Utility Class handling any date time utilities.
 *
 * @author mbailey
 * @version 1.0
 */
public final class DateTimeUtils {

    /**
     * log4j static variable.
     */
    private static Logger logger = Logger.getLogger(DateTimeUtils.class);

    /**
     * Utility class should not be instantiated.
     */
    private DateTimeUtils() {
        logger.error("Attempting to instantiate Utility Class");
        throw new UnsupportedOperationException(
                "Utility Class should not be instantiated.");
    }

    /**
     * Method to return today's date.
     * @return today's date.
     */
    public static Date getToday() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            logger.info("exiting getToday(). Today is : " + sdf.parse(sdf.format(new Date())));
            return sdf.parse(sdf.format(new Date()));
        } catch (ParseException ex) {
            logger.error("unable to parse today's date " + ex.getMessage());
            throw new IllegalArgumentException(ex);
        }
    }

    /**
     * Method to add one day to the passed in date.
     * This method preserves the time element of the date.
     *
     * @param date The Date which a day is to be added.
     * This method preserves the time element of the date.
     *
     * @return The Date which is a day later than the passed date.
     */
    public static Date getNextDay(final Date date) {
        logger.info("entering getNextDay method. date: " + date);
        TimeZone.setDefault(TimeZone.getTimeZone("Europe/London"));
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTimeInMillis(date.getTime());
        calendar.add(Calendar.DAY_OF_WEEK, 1);
        logger.info("exiting getNextDay method. nextDay: " + calendar.getTimeInMillis());
        return new Date(calendar.getTimeInMillis());
    }

    /**
     * Method to remove one day from the passed in date.
     * This method preserves the time element of the date.
     *
     * @param date The Date which a day is to be removed.
     * @return The Date which is a day earlier than the passed date.
     */
    public static Date getPreviousDay(final Date date) {
        logger.info("entering getPreviousDay method. date: " + date);
        TimeZone.setDefault(TimeZone.getTimeZone("Europe/London"));
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTimeInMillis(date.getTime());
        calendar.add(Calendar.DAY_OF_WEEK, -1);
        logger.info("exiting getPreviousDay method. previousDay: " + calendar.getTimeInMillis());
        return new Date(calendar.getTimeInMillis());
    }
}
