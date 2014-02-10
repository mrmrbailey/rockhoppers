/*
 * (c) Mark Bailey - www.rockhoppersuk.co.uk - 2011
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
import uk.co.rockhoppersuk.tvApp.listing.Listing;
import static org.junit.Assert.*;
import uk.co.rockhoppersuk.tvApp.listing.ListingCatalogue;

/**
 * Unit test for the View Listings For Unwatched shows action.
 *
 * @author mbailey
 * @version 1.0
 */
public class ViewListingForUnwatchedShowsActionTest extends StrutsTestCase {

    @Mock
    private ListingCatalogue mockListingCatalogue;
    private Listing testListing;
    private List<Listing> testListings;
    private ViewListingForUnwatchedShowsAction action;

    @Before
    @Override
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        testListing = new Listing();
        testListing.setListingDateTime(getTestDate());

        testListings = new ArrayList<Listing>();
        testListings.add(testListing);

        when(mockListingCatalogue.getListingsForUnwatchedShows()).thenReturn(testListings);

        action = new ViewListingForUnwatchedShowsAction();
        action.setListingsCatalogue(mockListingCatalogue);
    }

    /**
     * Test of execute method, of class ViewListingForTvShowAction.
     * @throws Exception
     */
    @Test
    public void testExecute() throws Exception {
        String result = action.execute();
        assertTrue("Expected a success result!", ActionSupport.SUCCESS.equals(result));
    }

    /**
     * Test of getListings method, of class ViewListingForTvShowAction.
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
     * Creates a test listing date to allow comparisons.
     * @return a test listing date to allow comparisons.
     */
    private Date getTestDate() {
        TimeZone.setDefault(TimeZone.getTimeZone("Europe/London"));
        GregorianCalendar calendar = new GregorianCalendar(2010, 9, 22);
        return new Date(calendar.getTimeInMillis());
    }
}
