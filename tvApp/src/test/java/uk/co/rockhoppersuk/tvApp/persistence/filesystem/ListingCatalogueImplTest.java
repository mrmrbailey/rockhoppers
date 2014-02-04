/*
 * (c) Mark Bailey - www.rockhoppersuk.co.uk - 2010
 */
package uk.co.rockhoppersuk.tvApp.persistence.filesystem;

import java.util.Date;
import uk.co.rockhoppersuk.tvApp.listing.Listing;
import uk.co.rockhoppersuk.tvApp.listing.ListingCatalogue;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import static org.mockito.Mockito.*;
import java.util.List;
import java.util.TimeZone;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import uk.co.rockhoppersuk.tvApp.channel.Channel;
import uk.co.rockhoppersuk.tvApp.show.TvShow;
import uk.co.rockhoppersuk.tvApp.show.episode.Episode;
import uk.co.rockhoppersuk.tvApp.show.episode.EpisodeStatus;
import static org.junit.Assert.*;

/**
 * Unit test for the listing catalogue.
 *
 * @author mbailey
 * @version 1.0
 */
public class ListingCatalogueImplTest {

    @Mock
    ListingFileHelper mockListingFileHelper;
    private ListingCatalogueImpl listingCatalogueImpl;
    private Date minDate;
    private Date maxDate;
    private Calendar testCalendar;
    private List<Date> testListingDates;
    private List<Listing> testFilteredListings;
    private List<Listing> allTestListings;
    private final static int NUMBER_OF_TEST_DATES = 10;
    private final static TvShow testShow = TvShow.Lost;
    private final static String testTitle = "Title";
    private final static Channel testChannel = Channel.BBC1;
    private final static EpisodeStatus testStatus = EpisodeStatus.missed;
    private final static TvShow anotherTestShow = TvShow.TheEvent;
    private final static String anotherTestTitle = "Another Title";
    private final static Channel anotherTestChannel = Channel.BBC2;
    private final static EpisodeStatus anotherTestStatus = EpisodeStatus.watched;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        TimeZone.setDefault(TimeZone.getTimeZone("Europe/London"));
        testCalendar = new GregorianCalendar(2004, 9, 22);
        minDate = new Date(testCalendar.getTimeInMillis());

        testListingDates = new ArrayList<Date>();
        testFilteredListings = new ArrayList<Listing>();
        allTestListings = new ArrayList<Listing>();
        for (int i = 0; i < NUMBER_OF_TEST_DATES; i++) {
            maxDate = new Date(testCalendar.getTimeInMillis());
            testListingDates.add(maxDate);
            testFilteredListings.add(getTestListing(maxDate));
            List<Listing> mockListings = new ArrayList<Listing>();
            mockListings.add(testFilteredListings.get(i));
            mockListings.add(getAnotherTestListing(maxDate));
            allTestListings.addAll(mockListings);
            when(mockListingFileHelper.getDailyListingFromFileSystem(maxDate)).thenReturn(mockListings);
            testCalendar.add(Calendar.DAY_OF_WEEK, 1);
        }

        when(mockListingFileHelper.getListingDatesFromFileSystem()).thenReturn(testListingDates);

        ListingCatalogue listingCatalogue = ListingCatalogueImpl.getInstance();
        listingCatalogueImpl = (ListingCatalogueImpl) listingCatalogue;
        listingCatalogueImpl.setListingFileHelper(mockListingFileHelper);
        listingCatalogueImpl.refreshCatalogue();
    }

    /**
     * Test of getInstance method, of class ListingsCatalogueImpl.
     */
    @Test
    public void testGetInstance() {
        ListingCatalogue result = ListingCatalogueImpl.getInstance();
        assertTrue("Is the ListingsCatalogueImpl",
                result instanceof ListingCatalogueImpl);
        assertEquals("returns the same",
                result, ListingCatalogueImpl.getInstance());
    }

    /**
     * Test of refreshCatalogue method, of class ListingsCatalogueImpl.
     */
    @Test
    public void testRefreshCatalogue() {
        try {
            listingCatalogueImpl.refreshCatalogue();
        } catch (Exception ex) {
            fail("refresh threw exception" + ex.getMessage());
        }
    }

    /**
     * Test of getListingDates method, of class ListingsCatalogueImpl.
     */
    @Test
    public void testGetListingDates() {
        List<Date> result = listingCatalogueImpl.getListingDates();
        assertEquals(testListingDates, result);
    }

    /**
     * Test of getListingsForDate method, of class ListingsCatalogueImpl.
     */
    @Test
    public void testGetListingsForDate() {
        List<Listing> expectedListings = new ArrayList<Listing>();
        expectedListings.add(allTestListings.get(NUMBER_OF_TEST_DATES * 2 - 2));
        expectedListings.add(allTestListings.get(NUMBER_OF_TEST_DATES * 2 - 1));
        List<Listing> result = listingCatalogueImpl.getListingsForDate(maxDate);
        assertEquals(expectedListings, result);
    }

    /**
     * Test of getListingsFromDate method, of class ListingsCatalogueImpl.
     */
    @Test
    public void testGetListingsFromDate() {
        final int offset = 3;
        assert (new Integer(NUMBER_OF_TEST_DATES - offset).compareTo(0) > 0);

        testCalendar.add(Calendar.DAY_OF_WEEK, offset * -1);
        Date fromDate = new Date(testCalendar.getTimeInMillis());
        List<Listing> expectedListings = new ArrayList<Listing>();
        for (int i = NUMBER_OF_TEST_DATES - offset; i < NUMBER_OF_TEST_DATES; i++) {
            expectedListings.add(allTestListings.get(i * 2));
            expectedListings.add(allTestListings.get(i * 2 + 1));
        }
        List<Listing> result = listingCatalogueImpl.getListingsFromDate(fromDate);
        assertEquals(expectedListings, result);
    }

    /**
     * Test of getMinListingDate method, of class ListingsCatalogueImpl.
     */
    @Test
    public void testGetMinListingDate() {
        Date result = listingCatalogueImpl.getMinListingDate();
        assertEquals(minDate, result);
    }

    /**
     * Test of getMaxListingDate method, of class ListingsCatalogueImpl.
     */
    @Test
    public void testGetMaxListingDate() {
        Date result = listingCatalogueImpl.getMaxListingDate();
        assertEquals(maxDate, result);
    }

    /**
     * Test of testGetListingsForTvShow method, of class ListingsCatalogueImpl.
     */
    @Test
    public void testGetListingsForTvShow() {
        List<Listing> result = listingCatalogueImpl.getListingsForTvShow(testShow);
        assertEquals(testFilteredListings, result);
    }
    
    /**
     * Test of getListingsForEpisode method, of class ListingsCatalogueImpl.
     */
    @Test
    public void testGetListingsForEpisode() {
        List<Listing> result = listingCatalogueImpl.getListingsForEpisode(testShow, testTitle);
        assertEquals(testFilteredListings, result);
    }    

    /**
     * Test of getListingsForEpisode method, of class ListingsCatalogueImpl.
     */
    @Test
    public void testGetListingsForEpisodeIncorrectTitle() {
        List<Listing> result = listingCatalogueImpl.getListingsForEpisode(testShow, "not " + testTitle);
        assertEquals(0, result.size());
    }    

    /**
     * Test of getListingsForStatus method, of class ListingsCatalogueImpl.
     */
    @Test
    public void testGetListingsForStatus() {
        List<Listing> result = listingCatalogueImpl.getListingsForStatus(testStatus);
        assertEquals(testFilteredListings, result);
    }

    /**
     * Test of getListingsForStatus method, of class ListingsCatalogueImpl.
     */
    @Test
    public void testGetListingsForChannel() {
        List<Listing> result = listingCatalogueImpl.getListingsForChannel(testChannel);
        assertEquals(testFilteredListings, result);
    }

    /**
     * Test of getListingsForUnwatchedShows method, of class ListingsCatalogueImpl.
     */
    @Test
    public void testGetListingsForUnwatchedShows() {
        List<Listing> result = listingCatalogueImpl.getListingsForUnwatchedShows();
        assertEquals(testFilteredListings, result);
    }

    /**
     * Creates a test listing that should match .
     * @param listingDate The date for the test listing.
     * @return 
     */
    private Listing getTestListing(Date listingDate) {
        Listing testListing = new Listing();
        testListing.setListingDateTime(listingDate);

        Episode testEpisode = new Episode();
        testEpisode.setTvShow(testShow);
        testEpisode.setTitle(testTitle);
        testEpisode.setStatus(testStatus);
        testListing.setEpisode(testEpisode);

        testListing.setChannel(testChannel);
        return testListing;
    }

    private Listing getAnotherTestListing(Date listingDate) {
        Listing anotherTestListing = new Listing();
        anotherTestListing.setListingDateTime(listingDate);

        Episode anotherTestEpisode = new Episode();
        anotherTestEpisode.setTvShow(anotherTestShow);
        anotherTestEpisode.setTitle(anotherTestTitle);
        anotherTestEpisode.setStatus(anotherTestStatus);

        anotherTestListing.setEpisode(anotherTestEpisode);
        anotherTestListing.setChannel(anotherTestChannel);

        return anotherTestListing;
    }
}
