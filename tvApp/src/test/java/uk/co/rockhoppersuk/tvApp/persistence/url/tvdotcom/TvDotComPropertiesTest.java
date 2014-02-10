/*
 * (c) Mark Bailey - www.rockhoppersuk.co.uk - 2010
 */
package uk.co.rockhoppersuk.tvApp.persistence.url.tvdotcom;

import uk.co.rockhoppersuk.tvApp.persistence.url.tvdotcom.TvDotComProperties;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit test for the Tv Show properties file.
 *
 * @author mbailey
 * @version 1.0
 */
public class TvDotComPropertiesTest {

    /**
     * URL to be used for testing.
     */
    private final static String testURL = "testshowname/show/33332";
    /**
     * Tv Show name relating to the TvShow under test.
     */
    private final static String propertiesFileTvShowKey = "testProperties";
    /**
     * Tv Show name relating to the TvShow under test with spaces.
     */
    private final static String propertiesFileTvShowKeyWithSpaces = "Test : Properties";

    /**
     * Test of getTvDotComURL method, of class TvDotComProperties.
     * With a key that is in the test properties file.
     */
    @Test
    public void testGetTvDotComURLContainsKey() {
        String expResult = testURL;
        String result = TvDotComProperties.getInstance().getTvDotComURL(propertiesFileTvShowKey);
        assertEquals(expResult, result);
    }

    /**
     * Test of getTvDotComURL method, of class TvDotComProperties.
     * With a key that is in the test properties file.
     */
    @Test
    public void testGetTvDotComURLContainsKeyWithSpaces() {
        String expResult = testURL;
        String result = TvDotComProperties.getInstance().getTvDotComURL(propertiesFileTvShowKeyWithSpaces);
        assertEquals(expResult, result);
    }
    /**
     * Test of getTvDotComURL method, of class TvDotComProperties.
     * With a key that is not in the test properties file.
     */
    @Test
    public void testGetTvShowNameDoesNotContainsKey() {
        final String notInFile = "This Key Is Not in the file";
        String result = TvDotComProperties.getInstance().getTvDotComURL(notInFile);
        assertNull(result);
    }
}