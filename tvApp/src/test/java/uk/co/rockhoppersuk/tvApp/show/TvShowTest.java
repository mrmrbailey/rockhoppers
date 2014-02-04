/*
 * (c) Mark Bailey - www.rockhoppersuk.co.uk - 2010
 */
package uk.co.rockhoppersuk.tvApp.show;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit test for the tvShow Enum.
 *
 * @author mbailey
 * @version 1.0
 */
public class TvShowTest {

    /**
     * Tv Show to be used for testing.
     */
    private final static TvShow testTvShow = TvShow.Lost;
    /**
     * Tv Show name relating to the TvShow under test.
     */
    private final static String testTvShowName = "Lost";

    /**
     * Test of getTvShowName method, of class TvShow.
     */
    @Test
    public void testGetTvShowName() {
        String expResult = testTvShowName;
        String result = testTvShow.getTvShowName();
        assertEquals(expResult, result);
    }

    /**
     * Test of getTvShow method, of class TvShow.
     */
    @Test
    public void testGetTvShow() {
        TvShow expResult = testTvShow;
        TvShow result = TvShow.getTvShow(testTvShowName);
        assertEquals(expResult, result);
    }

    /**
     * Test of getTvShow method with an unknown show, of class TvShow.
     */
    @Test
    public void testGetUnknownTvShow() {
        TvShow expResult = TvShow.Unknown;
        TvShow result = TvShow.getTvShow("This is an invalid lookup");
        assertEquals(expResult, result);
    }

}
