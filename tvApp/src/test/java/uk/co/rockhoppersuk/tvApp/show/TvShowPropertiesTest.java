/*
 * (c) Mark Bailey - www.rockhoppersuk.co.uk - 2010
 */
package uk.co.rockhoppersuk.tvApp.show;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit test for the Tv Show properties file.
 *
 * @author mbailey
 * @version 1.0
 */
public class TvShowPropertiesTest {

    /**
     * Tv Show to be used for testing.
     */
    private final static TvShow testTvShow = TvShow.Lost;
    /**
     * Tv Show name relating to the TvShow under test.
     */
    private final static String propertiesFileTvShowKey = "testProperties";
    /**
     * Tv Show name relating to the TvShow under test with spaces.
     */
    private final static String propertiesFileTvShowKeyWithSpaces = "Test : Properties";

    /**
     * Test of getTvShowName method, of class TvShowProperties.
     * With a key that is in the test properties file.
     */
    @Test
    public void testGetTvShowNameContainsKey() {
        String expResult = testTvShow.getTvShowName();
        String result = TvShowProperties.getInstance().getTvShowName(propertiesFileTvShowKey);
        assertEquals(expResult, result);
    }

    /**
     * Test of getTvShowName method, of class TvShowProperties.
     * With a key that is in the test properties file.
     */
    @Test
    public void testGetTvShowNameContainsKeyWithSpaces() {
        String expResult = testTvShow.getTvShowName();
        String result = TvShowProperties.getInstance().getTvShowName(propertiesFileTvShowKeyWithSpaces);
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
        String result = TvShowProperties.getInstance().getTvShowName(notInFile);
        assertEquals(expResult, result);
    }
}