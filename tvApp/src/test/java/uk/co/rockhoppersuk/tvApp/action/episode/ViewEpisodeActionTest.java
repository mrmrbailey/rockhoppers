/*
 * (c) Mark Bailey - www.rockhoppersuk.co.uk - 2010
 */
package uk.co.rockhoppersuk.tvApp.action.episode;

import com.opensymphony.xwork2.ActionSupport;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.struts2.StrutsTestCase;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import uk.co.rockhoppersuk.tvApp.channel.Channel;
import uk.co.rockhoppersuk.tvApp.listing.Listing;
import uk.co.rockhoppersuk.tvApp.listing.ListingCatalogue;
import uk.co.rockhoppersuk.tvApp.show.TvShow;
import static org.junit.Assert.*;
import uk.co.rockhoppersuk.tvApp.show.episode.Episode;
import uk.co.rockhoppersuk.tvApp.show.episode.EpisodeCatalogue;

/**
 * Unit test for the View Episode action.
 *
 * @author mbailey
 * @version 1.0
 */
public class ViewEpisodeActionTest extends StrutsTestCase {

    @Mock
    private EpisodeCatalogue mockEpisodeCatalogue;
    @Mock
    private ListingCatalogue mockListingCatalogue;
    private Episode testEpisode;
    private ViewEpisodeAction action;
    private List<Listing> testListings;
    private final static TvShow testShow = TvShow.Lost;
    private final static String testTitle = "test Title";

    @Before
    @Override
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        testEpisode = new Episode();
        testEpisode.setTvShow(testShow);
        testEpisode.setTitle(testTitle);
        when(mockEpisodeCatalogue.getEpisode(testShow, testTitle)).thenReturn(testEpisode);

        Listing testListing = new Listing();
        testListing.setListingDateTime(new Date());
        testListing.setChannel(Channel.Channel4);
        testListing.setEpisode(testEpisode);
        testListings = new ArrayList<Listing>();
        testListings.add(testListing);
        when(mockListingCatalogue.getListingsForEpisode(testShow, testTitle)).thenReturn(testListings);

        action = new ViewEpisodeAction();
        action.setEpisodeCatalogue(mockEpisodeCatalogue);
        action.setListingCatalogue(mockListingCatalogue);

        //These are normally set up by struts
        action.setTvShowName(testShow.getTvShowName());
        action.setEpisodeTitle(testTitle);

    }

    /**
     * Test of execute method, of class ViewEpisodeAction.
     */
    @Test
    public void testExecute() throws Exception {
        String result = action.execute();
        assertTrue("Expected a success result!", ActionSupport.SUCCESS.equals(result));
    }

    /**
     * Test of getEpisode method, of class ViewEpisodeAction.
     */
    @Test
    public void testGetEpisode() throws Exception {
        String result = action.execute();
        assertTrue("Expected a success result!", ActionSupport.SUCCESS.equals(result));

        Episode actionEpisode = action.getEpisode();
        assertEquals("getEpisode", testEpisode, actionEpisode);
    }

    /**
     * Test of getEpisodeListings method, of class ViewEpisodeAction.
     */
    @Test
    public void testGetEpisodeListings() throws Exception {
        String result = action.execute();
        assertTrue("Expected a success result!", ActionSupport.SUCCESS.equals(result));

        List<Listing> actionListings = action.getEpisodeListings();
        assertEquals("getEpisode", testListings, actionListings);
    }
}
