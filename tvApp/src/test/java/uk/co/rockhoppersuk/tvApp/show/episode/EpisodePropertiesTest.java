/*
 * (c) Mark Bailey - www.rockhoppersuk.co.uk - 2010
 */
package uk.co.rockhoppersuk.tvApp.show.episode;

import org.junit.Test;
import static org.junit.Assert.*;
import uk.co.rockhoppersuk.tvApp.show.TvShow;

/**
 * Unit test for the Episode properties file.
 *
 * @author mbailey
 * @version 1.0
 */
public class EpisodePropertiesTest {

    /**
     * Tv Show to be used for testing.
     */
    private final static TvShow testTvShow = TvShow.Lost;
    /**
     * The Episode Title relating to the TvShow under test.
     */
    private final static String propertiesFileTitleKey = "testProperties";
    /**
     * The Episode Title relating to the TvShow under test with spaces.
     */
    private final static String propertiesFileTittleKeyWithSpaces = "Test : Properties";
    /**
     * The Episode Title relating to the TvShow under test.
     */
    private final static String propertiesFileProperty = "This is a test";

    /**
     * Test of getTvShowName method, of class TvShowProperties.
     * With a key that is in the test properties file.
     */
    @Test
    public void testGetTvShowNameContainsKey() {
        String expResult = propertiesFileProperty;
        String result = EpisodeProperties.getInstance().getEpisodeTitle(testTvShow, propertiesFileTitleKey);
        assertEquals(expResult, result);
    }

    /**
     * Test of getTvShowName method, of class TvShowProperties.
     * With a key that is in the test properties file.
     */
    @Test
    public void testGetTvShowNameContainsKeyWithSpaces() {
        String expResult = propertiesFileProperty;
        String result = EpisodeProperties.getInstance().getEpisodeTitle(testTvShow, propertiesFileTittleKeyWithSpaces);
        assertEquals(expResult, result);
    }
    /**
     * Test of getTvShowName method, of class TvShowProperties.
     * With a key that is not in the test properties file.
     */
    @Test
    public void testGetTvShowNameDoesNotContainsKey() {
        final String notInFile = "This Key Is Not in the file";
        String expResult = notInFile;
        String result = EpisodeProperties.getInstance().getEpisodeTitle(testTvShow, notInFile);
        assertEquals(expResult, result);
    }
}