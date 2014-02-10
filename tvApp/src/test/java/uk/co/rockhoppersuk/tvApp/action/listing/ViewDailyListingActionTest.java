/*
 * (c) Mark Bailey - www.rockhoppersuk.co.uk - 2010
 */
package uk.co.rockhoppersuk.tvApp.action.listing;

import com.opensymphony.xwork2.ActionSupport;
import java.util.ArrayList;
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
import uk.co.rockhoppersuk.date.DateTimeUtils;
import uk.co.rockhoppersuk.tvApp.channel.Channel;
import uk.co.rockhoppersuk.tvApp.listing.Listing;
import static org.junit.Assert.*;
import uk.co.rockhoppersuk.tvApp.listing.ListingCatalogue;

/**
 * Unit test for the View Daily Listings action.
 *
 * @author mbailey
 * @version 1.0
 */
public class ViewDailyListingActionTest extends StrutsTestCase {

    @Mock
    private ListingCatalogue mockListingCatalogue;
    private Listing testListing;
    private List<Listing> testListings;
    private ViewDailyListingAction action;

    @Before
    @Override
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        testListing = new Listing();
        testListing.setListingDateTime(getTestDate());
        testListing.setChannel(Channel.Channel4);

        testListings = new ArrayList<Listing>();
        testListings.add(testListing);

        when(mockListingCatalogue.getListingsForDate(getTestDate())).thenReturn(testListings);
        when(mockListingCatalogue.getMaxListingDate()).thenReturn(getMaxListingDate());
        when(mockListingCatalogue.getMinListingDate()).thenReturn(getMinListingDate());

        action = new ViewDailyListingAction();
        action.setListingsCatalogue(mockListingCatalogue);
        action.setListingDate(getTestDate());

    }

    /**
     * Test of execute method, of class ViewDailyListingAction.
     * @throws Exception
     */
    @Test
    public void testExecute() throws Exception {
        String result = action.execute();
        assertTrue("Expected a success result!", ActionSupport.SUCCESS.equals(result));
    }

    /**
     * Test of getListings method, of class ViewDailyListingAction.
     * @throws Exception
     */
    @Test
    public void testGetListings() throws Exception {
        String result = action.execute();
        assertTrue("Expected a success result!", ActionSupport.SUCCESS.equals(result));

        List<Listing> actionListings = action.getListings();
        assertEquals("getListingDates", testListings, actionListings);
    }

    /**
     * Test of getNextListingDate method, of class ViewDailyListingAction.
     * @throws Exception
     */
    @Test
    public void testGetNextListingDate() throws Exception {
        String result = action.execute();
        assertTrue("Expected a success result!", ActionSupport.SUCCESS.equals(result));

        Date expectedResult = DateTimeUtils.getNextDay(getTestDate());
        Date actualResult = action.getNextListingDate();
        assertEquals("getListingDates", expectedResult, actualResult);
    }
    
    /**
     * Test of getNextListingDate method, of class ViewDailyListingAction.
     * @throws Exception
     */
    @Test
    public void testGetNextListingDateMaxExceeded() throws Exception {
        String result = action.execute();
        assertTrue("Expected a success result!", ActionSupport.SUCCESS.equals(result));

        action.setListingDate(getMaxListingDate());
        Date expectedResult = getMaxListingDate();
        Date actualResult = action.getNextListingDate();
        assertEquals("getListingDates", expectedResult, actualResult);
    }

    /**
     * Test of getNextListingDate method, of class ViewDailyListingAction.
     * @throws Exception
     */
    @Test
    public void testGetPreviousListingDate() throws Exception {
        String result = action.execute();
        assertTrue("Expected a success result!", ActionSupport.SUCCESS.equals(result));

        Date expectedResult = DateTimeUtils.getPreviousDay(getTestDate());
        Date actualResult = action.getPreviousListingDate();
        assertEquals("getListingDates", expectedResult, actualResult);
    }


    /**
     * Test of getNextListingDate method, of class ViewDailyListingAction.
     * @throws Exception
     */
    @Test
    public void testGetPreviousListingDateMinExcceded() throws Exception {
        String result = action.execute();
        assertTrue("Expected a success result!", ActionSupport.SUCCESS.equals(result));

        action.setListingDate(getMinListingDate());
        Date expectedResult = getMinListingDate();
        Date actualResult = action.getPreviousListingDate();
        assertEquals("getListingDates", expectedResult, actualResult);
    }

    /**
     * Test of getListingDat method, of class ViewDailyListingAction.
     * @throws Exception
     */
    @Test
    public void testGetListingDate() throws Exception {
        String result = action.execute();
        assertTrue("Expected a success result!", ActionSupport.SUCCESS.equals(result));

        Date expectedResult = getTestDate();
        Date actualResult = action.getListingDate();
        assertEquals("getListingDates", expectedResult, actualResult);
    }


    /**
     * Creates a test listing date to allow comparisons.
     * @return a test listing date to allow comparisons.
     */
    private Date getTestDate() {
        TimeZone.setDefault(TimeZone.getTimeZone("Europe/London"));
        GregorianCalendar calendar = new GregorianCalendar(2010, 9, 22);
        return new Date(calendar.getTimeInMillis());
    }

    /**
     * Creates a mock max listing date.
     * @return a mock max listing date.
     */
    private Date getMaxListingDate() {
        TimeZone.setDefault(TimeZone.getTimeZone("Europe/London"));
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTimeInMillis(getTestDate().getTime());
        calendar.add(calendar.DAY_OF_WEEK, 5);
        return new Date(calendar.getTimeInMillis());
    }


    /**
     * Creates a mock min listing date.
     * @return a mock min listing date.
     */
    private Date getMinListingDate() {
        TimeZone.setDefault(TimeZone.getTimeZone("Europe/London"));
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTimeInMillis(getTestDate().getTime());
        calendar.add(calendar.DAY_OF_WEEK, -5);
        return new Date(calendar.getTimeInMillis());
    }
}
