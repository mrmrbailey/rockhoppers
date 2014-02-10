/*
 * (c) Mark Bailey - www.rockhoppersuk.co.uk - 2010
 */
package uk.co.rockhoppersuk.tvApp.action.hub;

import com.opensymphony.xwork2.ActionSupport;
import java.util.ArrayList;
import java.util.Calendar;
import static org.mockito.Mockito.*;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;
import org.apache.struts2.StrutsTestCase;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import uk.co.rockhoppersuk.tvApp.listing.ListingCatalogue;
import static org.junit.Assert.*;

/**
 * Unit test for the listings hub action.
 *
 * @author mbailey
 * @version 1.0
 */
public class ListingsHubActionTest extends StrutsTestCase {

    @Mock private ListingCatalogue mockListingsCatalogue;
    private List<Date> testListingsDate;
    private ListingsHubAction action;
    private final static int numberMiilisecondsInDay = 1000 * 60 * 60 * 24;

    /**
     * Creates mock listings catalogue for the unit tests.
     */
    @Before
    @Override
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        testListingsDate = new ArrayList<Date>();
        testListingsDate.add(new Date(numberMiilisecondsInDay * 0));
        testListingsDate.add(new Date(numberMiilisecondsInDay * 1));
        testListingsDate.add(new Date(numberMiilisecondsInDay * 2));
        testListingsDate.add(new Date(numberMiilisecondsInDay * 3));
        testListingsDate.add(new Date(numberMiilisecondsInDay * 4));

        when(mockListingsCatalogue.getListingDates()).thenReturn(testListingsDate);

        action = new ListingsHubAction();
        action.setListingsCatalogue(mockListingsCatalogue);
    }

    /**
     * Test of execute method, of class ListingsHubAction.
     * @throws Exception
     */
    @Test
    public void testExecute() throws Exception {
        String result = action.execute();
        assertTrue("Expected a success result!", ActionSupport.SUCCESS.equals(result));
    }

    /**
     * Test of getListingDates method, of class ListingsHubAction.
     * @throws Exception
     */
    @Test
    public void testGetListingDates() throws Exception {
        String result = action.execute();
        assertTrue("Expected a success result!", ActionSupport.SUCCESS.equals(result));

        List<Date> actionListingDates = action.getListingDates();
        assertEquals("getListingDates", testListingsDate, actionListingDates);
    }

    /**
     * Test of getToday method, of class ListingsHubAction.
     * @throws Exception
     */
    @Test
    public void testGetToday() throws Exception {

        String result = action.execute();
        assertTrue("Expected a success result!", ActionSupport.SUCCESS.equals(result));

        TimeZone.setDefault(TimeZone.getTimeZone("Europe/London"));

        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTimeInMillis(new Date().getTime());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date startOfToday = new Date(calendar.getTimeInMillis());

        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        Date endOfToday = new Date(calendar.getTimeInMillis());

        Date actionDateToday = action.getToday();
        assertTrue(actionDateToday.after(startOfToday) && actionDateToday.before(endOfToday));
    }
}
