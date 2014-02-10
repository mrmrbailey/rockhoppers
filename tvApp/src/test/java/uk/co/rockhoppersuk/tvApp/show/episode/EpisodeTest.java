/*
 * (c) Mark Bailey - www.rockhoppersuk.co.uk - 2010
 */
package uk.co.rockhoppersuk.tvApp.show.episode;

import java.util.Date;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.junit.Assert.*;
import uk.co.rockhoppersuk.tvApp.show.TvShow;

/**
 * Unit test for the Episode bean.
 *
 * @author mbailey
 * @version 1.0
 */
public class EpisodeTest {
    
    /**
     * The episode under test.
     */
    private Episode testEpisode;
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
     * The synopsis  used to create this test episode.
     */
    private final static String testSynopsis = "Test Synopsis";

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        testEpisode = new Episode();
        testEpisode.setTvShow(testTvShow);
        testEpisode.setTitle(testTitle);
        testEpisode.setStatus(testStatus);
        testEpisode.setEpisodeNumber(testEpisodeNumber);
        testEpisode.setAirDate(mockAirDate);
        testEpisode.setSynopsis(testSynopsis);
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
        Episode sameEpisode = new Episode();
        sameEpisode.setTvShow(testTvShow);
        sameEpisode.setTitle(testTitle);
        sameEpisode.setStatus(testStatus);
        sameEpisode.setEpisodeNumber(testEpisodeNumber);
        sameEpisode.setAirDate(mockAirDate);
        sameEpisode.setSynopsis(testSynopsis);

        assertTrue("Equals isSymmetric", testEpisode.equals(sameEpisode));
        assertTrue("Equals isSymmetric", sameEpisode.equals(testEpisode));
    }

    /**
     * Test Transitive : For any reference values x, y and z, if x.equals(y) returns true and y.equals(z) returns true, then x.equals(z) must return true
     */
    @Test
    public void isEqualsTransitive() {
        Episode sameEpisode = new Episode();
        sameEpisode.setTvShow(testTvShow);
        sameEpisode.setTitle(testTitle);
        sameEpisode.setStatus(testStatus);
        sameEpisode.setEpisodeNumber(testEpisodeNumber);
        sameEpisode.setAirDate(mockAirDate);
        sameEpisode.setSynopsis(testSynopsis);

        Episode stillSameEpisode = new Episode();
        stillSameEpisode.setTvShow(testTvShow);
        stillSameEpisode.setTitle(testTitle);
        stillSameEpisode.setStatus(testStatus);
        stillSameEpisode.setEpisodeNumber(testEpisodeNumber);
        stillSameEpisode.setAirDate(mockAirDate);
        stillSameEpisode.setSynopsis(testSynopsis);

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

        Episode notTestEpisode = new Episode();
        notTestEpisode.setTvShow(testTvShow);
        notTestEpisode.setStatus(testStatus);
        notTestEpisode.setEpisodeNumber(testEpisodeNumber);
        notTestEpisode.setAirDate(mockAirDate);
        notTestEpisode.setSynopsis(testSynopsis);

        assertFalse("Equals does not equal notTestListing", testEpisode.equals(notTestEpisode));

        Episode stillnotTestEpisode = new Episode();
        stillnotTestEpisode.setTitle(testTitle);
        stillnotTestEpisode.setStatus(testStatus);
        stillnotTestEpisode.setEpisodeNumber(testEpisodeNumber);
        stillnotTestEpisode.setAirDate(mockAirDate);
        stillnotTestEpisode.setSynopsis(testSynopsis);
        assertFalse("Equals does not equal notTestListing", testEpisode.equals(stillnotTestEpisode));
    }

    /**
     * test that same episodes have the same hash code
     */
    @Test
    public void isHashCodeConsistent() {
        Episode sameEpisode = new Episode();
        sameEpisode.setTvShow(testTvShow);
        sameEpisode.setTitle(testTitle);
        sameEpisode.setStatus(testStatus);
        sameEpisode.setEpisodeNumber(testEpisodeNumber);
        sameEpisode.setAirDate(mockAirDate);
        sameEpisode.setSynopsis(testSynopsis);

        Episode stillSameEpisode = new Episode();
        stillSameEpisode.setTvShow(testTvShow);
        stillSameEpisode.setTitle(testTitle);
        stillSameEpisode.setStatus(testStatus);
        stillSameEpisode.setEpisodeNumber(testEpisodeNumber);
        stillSameEpisode.setAirDate(mockAirDate);
        stillSameEpisode.setSynopsis(testSynopsis);

        assertEquals("isHashCodeConsistent", testEpisode.hashCode(), testEpisode.hashCode());
        assertEquals("isHashCodeConsistent", testEpisode.hashCode(), sameEpisode.hashCode());
        assertEquals("isHashCodeConsistent", testEpisode.hashCode(), stillSameEpisode.hashCode());
    }
}
