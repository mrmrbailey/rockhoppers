/*
 * (c) Mark Bailey - www.rockhoppersuk.co.uk - 2010
 */
package uk.co.rockhoppersuk.tvApp.persistence.filesystem;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import uk.co.rockhoppersuk.tvApp.channel.Channel;
import uk.co.rockhoppersuk.tvApp.listing.Listing;
import static org.junit.Assert.*;
import uk.co.rockhoppersuk.tvApp.show.TvShow;
import uk.co.rockhoppersuk.tvApp.show.episode.Episode;
import static org.mockito.Mockito.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import uk.co.rockhoppersuk.tvApp.show.episode.EpisodeCatalogue;

/**
 * Unit test for the listings file helper.
 *
 * @author mbailey
 * @version 1.0
 */
public class ListingFileHelperTest {

    @Mock
    private EpisodeCatalogue mockEpisodeCatalogue;
    /**
     * The <code>TvShow</code> used to create this test listing.
     */
    private final static TvShow testTvShow = TvShow.Firefly;
    /**
     * The title used to create this test listing.
     */
    private final static String testTitle = "Test Episode";
    /**
     * The Channel used to create this test listing.
     */
    private final static Channel testChannel = Channel.BBC3;
    /**
     * log4j static variable.
     */
    private static Logger logger = Logger.getLogger(ListingFileHelper.class);

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        when(mockEpisodeCatalogue.getEpisode(testTvShow, testTitle)).thenReturn(getTestEpisode());
    }

    /**
     * Test of getListingDatesFromFileSystem method, of class ListingFileHelper.
     */
    @Test
    public void testGetListingDatesFromFileSystem() {
        ListingFileHelper instance = new ListingFileHelper();
        List<Date> expResult = getTestDates();
        List<Date> result = instance.getListingDatesFromFileSystem();
        assertEquals(expResult, result);
    }

    /**
     * Test of getDailyListingFromFileSystem method, of class ListingFileHelper.
     */
    @Test
    public void testGetDailyListingFromFileSystem() {
        ListingFileHelper instance = new ListingFileHelper();
        List<Listing> result = instance.getDailyListingFromFileSystem(getTestListingDateTimeStamp());

        if (result.size() == 1) {
            assertEquals(getTestListing(), result.get(0));
        } else {
            fail("There should only be one Listing on the filesystem");
        }
    }

    /**
     * Test of getDailyListingFromFileSystem method, of class ListingFileHelper.
     */
    @Test
    public void testGetDailyListingFromFileSystemInvalidDate() {
        ListingFileHelper instance = new ListingFileHelper();

        TimeZone.setDefault(TimeZone.getTimeZone("Europe/London"));
        GregorianCalendar calendar = new GregorianCalendar(2009, 0, 1);
        List<Listing> result = instance.getDailyListingFromFileSystem(new Date(calendar.getTimeInMillis()));
        
        assertTrue(result.isEmpty());
    }

    /**
     * Test of getListing method, of class ListingFileHelper.
     */
    @Test
    public void testGetListing() {
        ListingFileHelper instance = new ListingFileHelper();
        instance.setEpisodeCatalogue(mockEpisodeCatalogue);
        Listing expResult = getTestListing();
        Listing actualResult = instance.getListing(getTestPersistedListing());
        assertEquals(expResult, actualResult);
    }

    /**
     * The of getListing method, of class ListingFileHelper with an invalid
     * (incorrect length) persisted listing.
     */
    @Test
    public void testGetEpisodeInvalidPersistedListing() {
        ListingFileHelper instance = new ListingFileHelper();
        String testInvalidListing = "notValid";
        try {
            instance.getListing(testInvalidListing);
            fail("Expected an IllegalArgumentException to be thrown");
        } catch (IllegalArgumentException iaEx) {
            String expectedErrorMessage = "Unable to parse listing: " + testInvalidListing;
            assertEquals(expectedErrorMessage, iaEx.getMessage());
        }
    }

    /**
     * The of getListing method, of class ListingFileHelper with an invalid
     * (badly formed date) persisted listing.
     */
    @Test
    public void testGetListingInvalidDateFormat() {
        ListingFileHelper instance = new ListingFileHelper();
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(getTestListingDateTimeStamp());
            sb.append("~");
            sb.append(testTvShow);
            sb.append("~");
            sb.append(testTitle);
            sb.append("~");
            sb.append(testChannel.getChannelName());
            instance.getListing(sb.toString());
            fail("Expected an IllegalArgumentException to be thrown");
        } catch (IllegalArgumentException iaEx) {
            String expectedErrorMessage = "Unable to parse listing datetime: " + getTestListingDateTimeStamp();
            assertEquals(expectedErrorMessage, iaEx.getMessage());
        }
    }

    /**
     * Test of getCleanEpisodeTitle() method, of class ListingFileHelper.
     */
    @Test
    public void testGetCleanEpisodeTitle() {
        ListingFileHelper instance = new ListingFileHelper();
        String expResult = testTitle;
        String result = instance.getCleanEpisodeTitle(testTitle);
        assertEquals(expResult, result);

        StringBuilder sb = new StringBuilder();
        sb.append("4/11 - ");
        sb.append(testTitle);
        result = instance.getCleanEpisodeTitle(sb.toString());
        assertEquals(expResult, result);
    }

    /**
     * A persisted listing is built up so it looks like the following.
     * "Friday 01 January 2010 17:00~Firefly~Test Episode~BBC3"
     * @return The mock of the persisted listing.
     */
    private String getTestPersistedListing() {
        StringBuilder sb = new StringBuilder();
        SimpleDateFormat sdf = new SimpleDateFormat(ListingFileHelper.DATE_FORMAT);
        sb.append(sdf.format(getTestListingDateTimeStamp()));
        sb.append("~");
        sb.append(testTvShow);
        sb.append("~");
        sb.append(testTitle);
        sb.append("~");
        sb.append(testChannel.getChannelName());
        return sb.toString();
    }

    /**
     * getter for the listing used in test.
     * @return the Listing to be used in test.
     */
    private Listing getTestListing() {
        Listing testListing = new Listing();
        testListing.setListingDateTime(getTestListingDateTimeStamp());
        testListing.setEpisode(getTestEpisode());
        testListing.setChannel(testChannel);
        return testListing;
    }

    /**
     * getter for the episode used in test.
     * @return the episode to be used in test.
     */
    private Episode getTestEpisode() {
        Episode testEpisode = new Episode();
        testEpisode.setTvShow(testTvShow);
        testEpisode.setTitle(testTitle);
        return testEpisode;
    }

    /**
     * getter for the datetime stamp that is used in testing.
     * @return The datetime stamp that is used in testing.
     */
    private Date getTestListingDateTimeStamp() {
        TimeZone.setDefault(TimeZone.getTimeZone("Europe/London"));

        GregorianCalendar calendar = new GregorianCalendar(2010, 0, 1, 17, 0);
        return new Date(calendar.getTimeInMillis());
    }

    /**
     * getter for the date of all the listings in the test env.
     * @return The date of all the listings in the test env.
     */
    private List<Date> getTestDates() {
        List<Date> testDates = new ArrayList<Date>();
        TimeZone.setDefault(TimeZone.getTimeZone("Europe/London"));

        GregorianCalendar calendar = new GregorianCalendar(2010, 0, 1);
        testDates.add(new Date(calendar.getTimeInMillis()));

        calendar = new GregorianCalendar(2010, 0, 2);
        testDates.add(new Date(calendar.getTimeInMillis()));

        calendar = new GregorianCalendar(2010, 1, 1);
        testDates.add(new Date(calendar.getTimeInMillis()));

        calendar = new GregorianCalendar(2011, 0, 1);
        testDates.add(new Date(calendar.getTimeInMillis()));

        return testDates;
    }
}