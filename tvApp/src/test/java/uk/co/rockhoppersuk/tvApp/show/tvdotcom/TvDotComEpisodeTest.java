/*
 * (c) Mark Bailey - www.rockhoppersuk.co.uk - 2011
 */
package uk.co.rockhoppersuk.tvApp.show.tvdotcom;

import java.util.Date;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.junit.Assert.*;
import uk.co.rockhoppersuk.tvApp.show.TvShow;
import uk.co.rockhoppersuk.tvApp.show.episode.EpisodeStatus;

/**
 * Unit test for the TvDotComEpisode bean.
 *
 * @author mbailey
 * @version 1.0
 */
public class TvDotComEpisodeTest {
    
    

    /**
     * The episode under test.
     */
    private TvDotComEpisode testEpisode;
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
     * The date this episode aired used to create this test episode.
     */
    @Mock
    private Date mockAirDate;
    /**
     * The synopsis used to create this test episode.
     */
    private final static String testSynopsis = "Test Synopsis";
    /**
     * The id used to create this test episode.
     */
    private final static int testId = 42;
    /**
     * The production number to create this test episode.
     */
    private final static String testProdNo = "P1E1";
    /**
     * The link used to create this test episode.
     */
    private final static String testUrl = "Test Link";

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        testEpisode = new TvDotComEpisode();
        testEpisode.setTvShow(testTvShow);
        testEpisode.setTitle(testTitle);
        testEpisode.setStatus(testStatus);
        testEpisode.setEpisodeNumber(testEpisodeNumber);
        testEpisode.setAirDate(mockAirDate);
        testEpisode.setSynopsis(testSynopsis);
        testEpisode.setEpisodeId(testId);
        testEpisode.setProductionNumber(testProdNo);
        testEpisode.setEpisodeUrl(testUrl);
    }

    /**
     * Test reflective ie x.equals(x) must always return true
     */
    @Test
    public void isEqualsReflective() {
        assertTrue("Equals isReflective", testEpisode.equals(testEpisode));
    }

    /**
     * Test Symmetric ie If x.equals(y) returns true, then y.equals(x) must return true.
     */
    @Test
    public void isEqualsSymmetric() {
        TvDotComEpisode sameEpisode = new TvDotComEpisode();
        sameEpisode.setTvShow(testTvShow);
        sameEpisode.setTitle(testTitle);
        sameEpisode.setStatus(testStatus);
        sameEpisode.setEpisodeNumber(testEpisodeNumber);
        sameEpisode.setAirDate(mockAirDate);
        sameEpisode.setSynopsis(testSynopsis);
        sameEpisode.setEpisodeId(testId);
        sameEpisode.setProductionNumber(testProdNo);
        sameEpisode.setEpisodeUrl(testUrl);

        assertTrue("Equals isSymmetric", testEpisode.equals(sameEpisode));
        assertTrue("Equals isSymmetric", sameEpisode.equals(testEpisode));
    }

    /**
     * Test Transitive : For any reference values x, y and z, if x.equals(y) returns true and y.equals(z) returns true, then x.equals(z) must return true
     */
    @Test
    public void isEqualsTransitive() {
        TvDotComEpisode sameEpisode = new TvDotComEpisode();
        sameEpisode.setTvShow(testTvShow);
        sameEpisode.setTitle(testTitle);
        sameEpisode.setStatus(testStatus);
        sameEpisode.setEpisodeNumber(testEpisodeNumber);
        sameEpisode.setAirDate(mockAirDate);
        sameEpisode.setSynopsis(testSynopsis);
        sameEpisode.setEpisodeId(testId);
        sameEpisode.setProductionNumber(testProdNo);
        sameEpisode.setEpisodeUrl(testUrl);

        TvDotComEpisode stillSameEpisode = new TvDotComEpisode();
        stillSameEpisode.setTvShow(testTvShow);
        stillSameEpisode.setTitle(testTitle);
        stillSameEpisode.setStatus(testStatus);
        stillSameEpisode.setEpisodeNumber(testEpisodeNumber);
        stillSameEpisode.setAirDate(mockAirDate);
        stillSameEpisode.setSynopsis(testSynopsis);
        stillSameEpisode.setEpisodeId(testId);
        stillSameEpisode.setProductionNumber(testProdNo);
        stillSameEpisode.setEpisodeUrl(testUrl);

        assertTrue("Equals isTransitive", testEpisode.equals(sameEpisode));
        assertTrue("Equals isTransitive", sameEpisode.equals(stillSameEpisode));
        assertTrue("Equals isTransitive", testEpisode.equals(stillSameEpisode));
    }

    /**
     * test that episodes does not equal null or a different episode
     */
    @Test
    public void isEqualsNotEqual() {
        assertFalse("Equals does not equal null", testEpisode.equals(null));
        assertFalse("Equals does not equal channel", testEpisode.equals(testTvShow));

        TvDotComEpisode notTestEpisode = new TvDotComEpisode();
        notTestEpisode.setTvShow(testTvShow);
        notTestEpisode.setTitle(testTitle);
        notTestEpisode.setStatus(testStatus);
        notTestEpisode.setEpisodeNumber(testEpisodeNumber);
        notTestEpisode.setAirDate(mockAirDate);
        notTestEpisode.setSynopsis(testSynopsis);
        notTestEpisode.setEpisodeId(testId+1);
        notTestEpisode.setProductionNumber(testProdNo);
        notTestEpisode.setEpisodeUrl(testUrl);

        assertFalse("Equals does not equal notTestListing", testEpisode.equals(notTestEpisode));
    }

    /**
     * test that same episodes have the same hash code
     */
    @Test
    public void isHashCodeConsistent() {
        TvDotComEpisode sameEpisode = new TvDotComEpisode();
        sameEpisode.setTvShow(testTvShow);
        sameEpisode.setTitle(testTitle);
        sameEpisode.setStatus(testStatus);
        sameEpisode.setEpisodeNumber(testEpisodeNumber);
        sameEpisode.setAirDate(mockAirDate);
        sameEpisode.setSynopsis(testSynopsis);
        sameEpisode.setEpisodeId(testId);
        sameEpisode.setProductionNumber(testProdNo);
        sameEpisode.setEpisodeUrl(testUrl);

        TvDotComEpisode stillSameEpisode = new TvDotComEpisode();
        stillSameEpisode.setTvShow(testTvShow);
        stillSameEpisode.setTitle(testTitle);
        stillSameEpisode.setStatus(testStatus);
        stillSameEpisode.setEpisodeNumber(testEpisodeNumber);
        stillSameEpisode.setAirDate(mockAirDate);
        stillSameEpisode.setSynopsis(testSynopsis);
        stillSameEpisode.setEpisodeId(testId);
        stillSameEpisode.setProductionNumber(testProdNo);
        stillSameEpisode.setEpisodeUrl(testUrl);

        assertEquals("isHashCodeConsistent", testEpisode.hashCode(), testEpisode.hashCode());
        assertEquals("isHashCodeConsistent", testEpisode.hashCode(), sameEpisode.hashCode());
        assertEquals("isHashCodeConsistent", testEpisode.hashCode(), stillSameEpisode.hashCode());
    }
}