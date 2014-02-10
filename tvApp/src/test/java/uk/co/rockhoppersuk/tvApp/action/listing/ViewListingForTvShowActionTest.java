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
import uk.co.rockhoppersuk.tvApp.channel.Channel;
import uk.co.rockhoppersuk.tvApp.listing.Listing;
import static org.junit.Assert.*;
import uk.co.rockhoppersuk.tvApp.listing.ListingCatalogue;
import uk.co.rockhoppersuk.tvApp.show.TvShow;

/**
 * Unit test for the View Listings For TV Show action.
 *
 * @author mbailey
 * @version 1.0
 */
public class ViewListingForTvShowActionTest extends StrutsTestCase {

    @Mock
    private ListingCatalogue mockListingCatalogue;
    private Listing testListing;
    private List<Listing> testListings;
    private ViewListingForTvShowAction action;
    private final static TvShow testShow = TvShow.Lost;

    @Before
    @Override
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        testListing = new Listing();
        testListing.setListingDateTime(getTestDate());
        testListing.setChannel(Channel.Channel4);

        testListings = new ArrayList<Listing>();
        testListings.add(testListing);

        when(mockListingCatalogue.getListingsForTvShow(testShow)).thenReturn(testListings);

        action = new ViewListingForTvShowAction();
        action.setListingsCatalogue(mockListingCatalogue);
        action.setTvShowName(testShow.getTvShowName());

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
     * Test of getTvShowName method, of class ViewListingForTvShowAction.
     * @throws Exception
     */
    @Test
    public void testGetTvShowName() throws Exception {
        String result = action.execute();
        assertTrue("Expected a success result!", ActionSupport.SUCCESS.equals(result));

        String expectedResult = testShow.getTvShowName();
        String actualResult = action.getTvShowName();
        assertEquals("getTvShowName", expectedResult, actualResult);
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
