/*
 * (c) Mark Bailey - www.rockhoppersuk.co.uk - 2010
 */
package uk.co.rockhoppersuk.tvApp.listing;

import java.util.Date;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.junit.Assert.*;
import uk.co.rockhoppersuk.tvApp.channel.Channel;
import uk.co.rockhoppersuk.tvApp.show.episode.Episode;


/**
 * Unit test for the listings bean.
 *
 * @author mbailey
 * @version 1.0
 */
public class ListingTest {

    /**
     * Date to be used to create listing.
     */
    private Date testDate;
    /**
     * Channel to be used to create listing.
     */
    private final Channel testChannel = Channel.BBC1;
    /**
     * Episode to be used to create listing.
     */
    @Mock private Episode mockEpisode;
    /**
     * Listing under test
     */
    private Listing testListing;



    /**
     * Set up the variables used to create the listings under test.
     */
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        testDate = new Date(0);

        testListing = new Listing();
        testListing.setListingDateTime(testDate);
        testListing.setChannel(testChannel);
        testListing.setEpisode(mockEpisode);
    }

    /**
     * Test reflective ie x.equals(x) must always return true
     */
    @Test
    public void isEqualsReflective() {
        assertTrue("Equals isReflective", testListing.equals(testListing));
    }

    /**
     * Test Symmetric ie If x.equals(y) returns true, then y.equals(x) must return true.
     */
    @Test
    public void isEqualsSymmetric() {
        Listing sameListing = new Listing();
        sameListing.setListingDateTime(testDate);
        sameListing.setChannel(testChannel);
        sameListing.setEpisode(mockEpisode);

        assertTrue("Equals isSymmetric", testListing.equals(sameListing));
        assertTrue("Equals isSymmetric", sameListing.equals(testListing));
    }

    /**
     * Test Transitive : For any reference values x, y and z, if x.equals(y) returns true and y.equals(z) returns true, then x.equals(z) must return true
     */
    @Test
    public void isEqualsTransitive() {
        Listing sameListing = new Listing();
        sameListing.setListingDateTime(testDate);
        sameListing.setChannel(testChannel);
        sameListing.setEpisode(mockEpisode);

        Listing stillSameListing = new Listing();
        stillSameListing.setListingDateTime(testDate);
        stillSameListing.setChannel(testChannel);
        stillSameListing.setEpisode(mockEpisode);

        assertTrue("Equals isTransitive", testListing.equals(sameListing));
        assertTrue("Equals isTransitive", sameListing.equals(stillSameListing));
        assertTrue("Equals isTransitive", testListing.equals(stillSameListing));
    }

    /**
     * test that listings does not equal null or a different listing
     */
    @Test
    public void isEqualsNotEqual() {
        assertFalse("Equals does not equal null", testListing.equals(null));
        assertFalse("Equals does not equal channel", testListing.equals(testChannel));

        Listing notTestListing = new Listing();
        notTestListing.setListingDateTime(testDate);
        notTestListing.setEpisode(mockEpisode);
        assertFalse("Equals does not equal notTestListing", testListing.equals(notTestListing));

        Listing stillNotTestListing = new Listing();
        stillNotTestListing.setChannel(testChannel);
        stillNotTestListing.setEpisode(mockEpisode);
        assertFalse("Equals does not equal notTestListing", testListing.equals(stillNotTestListing));
    }

    /**
     * test that same listings have the same hash code
     */
    @Test
    public void isHashCodeConsistent() {
        Listing sameListing = new Listing();
        sameListing.setListingDateTime(testDate);
        sameListing.setChannel(testChannel);
        sameListing.setEpisode(mockEpisode);

        Listing stillSameListing = new Listing();
        stillSameListing.setListingDateTime(testDate);
        stillSameListing.setChannel(testChannel);
        stillSameListing.setEpisode(mockEpisode);

        assertEquals("isHashCodeConsistent", testListing.hashCode(), testListing.hashCode());
        assertEquals("isHashCodeConsistent", testListing.hashCode(), sameListing.hashCode());
        assertEquals("isHashCodeConsistent", testListing.hashCode(), stillSameListing.hashCode());
    }
}
