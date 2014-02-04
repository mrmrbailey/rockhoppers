/*
 * (c) Mark Bailey - www.rockhoppersuk.co.uk - 2010
 */
package uk.co.rockhoppersuk.tvApp.persistence.filesystem;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;
import org.junit.Test;
import static org.junit.Assert.*;
import uk.co.rockhoppersuk.tvApp.show.TvShow;
import uk.co.rockhoppersuk.tvApp.show.episode.Episode;
import uk.co.rockhoppersuk.tvApp.show.episode.EpisodeStatus;

/**
 * Unit test for the episode file helper.
 *
 * @author mbailey
 * @version 1.0
 */
public class EpisodeFileHelperTest {

    /**
     * The <code>TvShow</code> used to create this test episode.
     */
    private final static TvShow testTvShow = TvShow.Lost;
    /**
     * The title used to create this test episode.
     */
    private final static String testTitle = "Test Episode";
    /**
     * The status used to create this test episode.
     */
    private final static EpisodeStatus testStatus = EpisodeStatus.watched;
    /**
     * The Series and Episode Number used to create this test episode.
     */
    private final static String testEpisodeNumber = "Test EpisodeNumber";
    /**
     * The synopsis  used to create this test episode.
     */
    private final static String testSynopsis = "Test Synopsis";

    /**
     * Test of getEpisodesForTvShowFromFileSystem method, of class EpisodeFileHelper.
     */
    @Test
    public void testGetEpisodesForTvShowFromFileSystem() {
        EpisodeFileHelper instance = new EpisodeFileHelper();
        List<Episode> result = instance.getEpisodesForTvShowFromFileSystem(testTvShow);

        if (result.size() == 1) {
            assertEquals(getTestEpisode(), result.get(0));
        } else {
            fail("There should only be one Epsiode on the filesystem");
        }
    }
    
    /**
     * Test of getEpisodesForTvShowFromFileSystem method, of class EpisodeFileHelper.
     */
    @Test
    public void testGetEpisodesForTvShowFromFileSystemNoShow() {
        EpisodeFileHelper instance = new EpisodeFileHelper();
        List<Episode> result = instance.getEpisodesForTvShowFromFileSystem(TvShow.Unknown);

        assertTrue(result.isEmpty());
    }    

    /**
     * Test of getEpisode method, of class EpisodeFileHelper.
     */
    @Test
    public void testGetEpisode() {
        EpisodeFileHelper instance = new EpisodeFileHelper();
        Episode expResult = getTestEpisode();
        Episode result = instance.getEpisode(getTestPersistedEpisode(), testTvShow);
        assertEquals(expResult, result);
    }

    /**
     * The of getEpisode method, of class EpisodeFileHelper with an invalid
     * (incorrect length) persisted episode.
     */
    @Test
    public void testGetEpisodeInvalidPersistedEpisode() {
        EpisodeFileHelper instance = new EpisodeFileHelper();
        String testInvalidEpisode = "notValid";
        try {
            instance.getEpisode(testInvalidEpisode, testTvShow);
            fail("Expected an IllegalArgumentException to be thrown");
        } catch (IllegalArgumentException iaEx) {
            String expectedErrorMessage = "Unable to parse episode: " + testInvalidEpisode;
            assertEquals(expectedErrorMessage, iaEx.getMessage());
        }
    }

    /**
     * The of getEpisode method, of class EpisodeFileHelper with an invalid
     * (badly formed date) persisted episode.
     */
    @Test
    public void testGetEpisodeInvalidDateFormat() {
        EpisodeFileHelper instance = new EpisodeFileHelper();
        String testInvalidDate = "22/09/2004";
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(testTitle);
            sb.append("~");
            sb.append(testStatus);
            sb.append("~");
            sb.append(testInvalidDate);
            sb.append("~");
            sb.append(testEpisodeNumber);
            sb.append("~");
            sb.append(testSynopsis);
            instance.getEpisode(sb.toString(), testTvShow);
            fail("Expected an IllegalArgumentException to be thrown");
        } catch (IllegalArgumentException iaEx) {
            String expectedErrorMessage = "Unable to parse airDate: " + testInvalidDate;
            assertEquals(expectedErrorMessage, iaEx.getMessage());
        }
    }

    /**
     * getter for the episode under test.
     * @return the Episode under test.
     */
    private Episode getTestEpisode() {
        Episode testEpisode = new Episode();
        testEpisode.setTvShow(testTvShow);
        testEpisode.setTitle(testTitle);
        testEpisode.setStatus(testStatus);
        testEpisode.setAirDate(getTestAirDate());
        testEpisode.setEpisodeNumber(testEpisodeNumber);
        testEpisode.setSynopsis(testSynopsis);
        return testEpisode;
    }

    /**
     * A persisted episode is built up so it looks like the following.
     * "Pilot~watched~22 September 2004~Season 1 Episode 1~The Pilot show";
     * @return
     */
    private String getTestPersistedEpisode() {
        StringBuilder sb = new StringBuilder();
        sb.append(testTitle);
        sb.append("~");
        sb.append(testStatus);
        sb.append("~");
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy");
        sb.append(sdf.format(getTestAirDate()));
        sb.append("~");
        sb.append(testEpisodeNumber);
        sb.append("~");
        sb.append(testSynopsis);
        return sb.toString();
    }

    /**
     * getter for the air date that is used in testing.
     * @return The air date that is used in testing.
     */
    private Date getTestAirDate() {
        TimeZone.setDefault(TimeZone.getTimeZone("Europe/London"));
        GregorianCalendar calendar = new GregorianCalendar(2004, 9, 22);
        return new Date(calendar.getTimeInMillis());
    }
}
