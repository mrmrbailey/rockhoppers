package uk.co.rockhoppersuk.date;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.Test;

public class DateUtilsTest {

    @Test
    public final void testGetDateForTime() {
        Long timeInMills = 0L;

        Calendar calExpected = new GregorianCalendar();
        calExpected.setTimeInMillis(timeInMills);
        
        Calendar calActual = new GregorianCalendar();
        calActual.setTime(DateUtils.getDateForTime(timeInMills));
        
        assertEquals(calExpected.getTimeInMillis(), calActual.getTimeInMillis());
    }

    @Test
    public final void testGetFormattedDate() {
        Long timeInMills = 0L;
        assertEquals("1970-01-01", DateUtils.getFormattedDate(timeInMills));

        timeInMills = 1319886992138L;
        assertEquals("2011-10-29", DateUtils.getFormattedDate(timeInMills));

        timeInMills = 1327332415709L;
        System.out.println(DateUtils.getFormattedDate(timeInMills));

    }

    @Test
    public final void testGetDateFromCDC() {
        int cdc = 1;
        assertEquals("1992-01-01", DateUtils.getFormattedDate(DateUtils.getDateFromCDC(cdc)));

        cdc = 7270;
        System.out.println(DateUtils.getFormattedDate(DateUtils.getDateFromCDC(cdc)));
    }

    @Test
    public final void testGetCDCFromDate() {
        Calendar cal = new GregorianCalendar();
        cal.set(Calendar.YEAR, 2013);
        cal.set(Calendar.MONTH, Calendar.MARCH);
        cal.set(Calendar.DAY_OF_MONTH, 19);
        int expectedCDC = 7749;
        System.out.print("cal : " + DateUtils.getFormattedDate(cal.getTimeInMillis()));
        System.out.println(" - asserted date equates to : " + DateUtils.getCDCFromDate(new Date(cal.getTimeInMillis())));
        System.out.print("cdc : " + expectedCDC);
        System.out.println(" - asserted cdc equates to : " + DateUtils.getFormattedDate(DateUtils.getDateFromCDC(expectedCDC)));
        assertEquals(expectedCDC, DateUtils.getCDCFromDate(new Date(cal.getTimeInMillis())));
        
        cal = new GregorianCalendar();
        cal.set(Calendar.YEAR, 2012);
        cal.set(Calendar.MONTH, Calendar.JUNE);
        cal.set(Calendar.DAY_OF_MONTH, 26);
        System.out.println("cdc for date : " + DateUtils.getFormattedDate(cal.getTimeInMillis()) + " is " + DateUtils.getCDCFromDate(new Date(cal.getTimeInMillis())));
    }
}
