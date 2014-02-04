/*
 * (c) Mark Bailey - www.rockhoppersuk.co.uk - 2010
 */
package uk.co.rockhoppersuk.tvApp.action.listing;

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
import uk.co.rockhoppersuk.tvApp.channel.Channel;
import uk.co.rockhoppersuk.tvApp.listing.Listing;
import static org.junit.Assert.*;
import uk.co.rockhoppersuk.tvApp.listing.ListingCatalogue;

/**
 * Unit test for the View Future Listings action.
 *
 * @author mbailey
 * @version 1.0
 */
public class ViewFutureListingsActionTest extends StrutsTestCase {

    @Mock
    private ListingCatalogue mockListingCatalogue;
    private Listing testListing;
    private List<Listing> testListings;
    private ViewFutureListingAction action;

    @Before
    @Override
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        testListing = new Listing();
        testListing.setListingDateTime(getMidnightToday());
        testListing.setChannel(Channel.Channel4);

        testListings = new ArrayList<Listing>();
        testListings.add(testListing);

        when(mockListingCatalogue.getListingsFromDate(getMidnightToday())).thenReturn(testListings);

        action = new ViewFutureListingAction();
        action.setListingsCatalogue(mockListingCatalogue);

    }

    /**
     * Test of execute method, of class ViewFutureListingAction.
     * @throws Exception
     */
    @Test
    public void testExecute() throws Exception {
        String result = action.execute();
        assertTrue("Expected a success result!", ActionSupport.SUCCESS.equals(result));
    }

    /**
     * Test of getListings method, of class ViewFutureListingAction.
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
     * Test of getToday method, of class ViewFutureListingAction.
     * @throws Exception
     */
    @Test
    public void testGetToday() throws Exception {
        String result = action.execute();
        assertTrue("Expected a success result!", ActionSupport.SUCCESS.equals(result));

        Date expectedResult = getMidnightToday();
        Date actualResult = action.getToday();
        assertEquals("getListingDates", expectedResult, actualResult);
    }

    /**
     * Test of getListingDat method, of class ViewFutureListingAction.
     * @throws Exception
     */
    @Test
    public void testGetListingDate() throws Exception {
        String result = action.execute();
        assertTrue("Expected a success result!", ActionSupport.SUCCESS.equals(result));

        Date expectedResult = getMidnightToday();
        Date actualResult = action.getListingDate();
        assertEquals("getListingDates", expectedResult, actualResult);
    }
    
    /**
     * creates a date representing midnight today to set up test cases.
     * @return a date representing midnight today to set up test cases.
     */
    private Date getMidnightToday() {
        TimeZone.setDefault(TimeZone.getTimeZone("Europe/London"));
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTimeInMillis(new Date().getTime());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return new Date(calendar.getTimeInMillis());
    }

}
