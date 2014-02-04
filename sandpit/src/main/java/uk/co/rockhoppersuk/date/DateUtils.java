package uk.co.rockhoppersuk.date;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class DateUtils {

    public static final int START_YEAR_CDC = 1992;
    /**
     * start month for the CDC
     */
    public static final int START_MONTH_CDC = GregorianCalendar.JANUARY;
    /**
     * start day for the CDC
     */
    public static final int START_DAY_CDC = 1;

    /**
     * Returns a date object based on a time - a slightly pointless method.
     * @param timeInMills The time for the date in question
     * @return
     */
	public static Date getDateForTime(long timeInMills) {
		return new Date(timeInMills);
	}
	
	public static String getFormattedDate(long timeInMills) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		return df.format(getDateForTime(timeInMills));
	}
	
	public static Long getDateFromCDC(int cdc) {
		Calendar calendar = GregorianCalendar.getInstance(TimeZone.getTimeZone("GMT"));
        calendar.set(START_YEAR_CDC, START_MONTH_CDC, START_DAY_CDC);
        calendar.add(Calendar.DATE, cdc - 1);
        return calendar.getTimeInMillis();		
	}
	
	public static long getCDCFromDate(Date date) {
		Calendar cdc1 = GregorianCalendar.getInstance(TimeZone.getTimeZone("GMT"));
        cdc1.set(START_YEAR_CDC, START_MONTH_CDC, START_DAY_CDC-1);
        cdc1.set(Calendar.HOUR_OF_DAY, 0);
        cdc1.set(Calendar.MINUTE, 0);
        cdc1.set(Calendar.SECOND, 0);
        
        Calendar dateCal = GregorianCalendar.getInstance(TimeZone.getTimeZone("GMT"));
        dateCal.setTime(date);
//        dateCal.set(Calendar.HOUR_OF_DAY, 0);
//        dateCal.set(Calendar.MINUTE, 0);
//        dateCal.set(Calendar.SECOND, 0);
        
        long diffInMills = dateCal.getTimeInMillis() - cdc1.getTimeInMillis();
        
        
        return diffInMills / (1000 * 60 * 60 * 24);

	}
}
