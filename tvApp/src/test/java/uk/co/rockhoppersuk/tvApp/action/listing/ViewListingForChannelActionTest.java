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
import uk.co.rockhoppersuk.tvApp.channel.Channel;
import uk.co.rockhoppersuk.tvApp.listing.Listing;
import static org.junit.Assert.*;
import uk.co.rockhoppersuk.tvApp.listing.ListingCatalogue;

/**
 * Unit test for the View Listings For Channel action.
 *
 * @author mbailey
 * @version 1.0
 */
public class ViewListingForChannelActionTest extends StrutsTestCase {

    @Mock
    private ListingCatalogue mockListingCatalogue;
    private Listing testListing;
    private List<Listing> testListings;
    private ViewListingForChannelAction action;
    private final static Channel testChannel = Channel.Syfy; 

    @Before
    @Override
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        testListing = new Listing();
        testListing.setListingDateTime(getTestDate());
        testListing.setChannel(testChannel);

        testListings = new ArrayList<Listing>();
        testListings.add(testListing);

        when(mockListingCatalogue.getListingsForChannel(testChannel)).thenReturn(testListings);

        action = new ViewListingForChannelAction();
        action.setListingsCatalogue(mockListingCatalogue);
        action.setChannel(testChannel.getChannelName());

    }

    /**
     * Test of execute method, of class ViewListingForChannelAction.
     * @throws Exception
     */
    @Test
    public void testExecute() throws Exception {
        String result = action.execute();
        assertTrue("Expected a success result!", ActionSupport.SUCCESS.equals(result));
    }

    /**
     * Test of getListings method, of class ViewListingForChannelAction.
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
     * Test of getChannel method, of class ViewListingForChannelAction.
     * @throws Exception
     */
    @Test
    public void testGetChannel() throws Exception {
        String result = action.execute();
        assertTrue("Expected a success result!", ActionSupport.SUCCESS.equals(result));

        String expectedResult = testChannel.getChannelName();
        String actualResult = action.getChannel();
        assertEquals("getChannel", expectedResult, actualResult);
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
