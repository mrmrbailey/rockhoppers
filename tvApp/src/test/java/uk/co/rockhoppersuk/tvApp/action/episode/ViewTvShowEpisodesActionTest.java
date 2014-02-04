/*
 * (c) Mark Bailey - www.rockhoppersuk.co.uk - 2010
 */
package uk.co.rockhoppersuk.tvApp.action.episode;

import com.opensymphony.xwork2.ActionSupport;
import java.util.ArrayList;
import static org.mockito.Mockito.*;

import java.util.List;
import org.apache.struts2.StrutsTestCase;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import uk.co.rockhoppersuk.tvApp.show.TvShow;
import uk.co.rockhoppersuk.tvApp.show.episode.Episode;
import static org.junit.Assert.*;
import uk.co.rockhoppersuk.tvApp.show.episode.EpisodeCatalogue;

/**
 * Unit test for the View Episode action.
 *
 * @author mbailey
 * @version 1.0
 */
public class ViewTvShowEpisodesActionTest extends StrutsTestCase {

    @Mock private EpisodeCatalogue mockEpisodeCatalogue;
    private Episode testEpisode;
    private List<Episode> testEpisodes;
    private ViewTvShowEpisodesAction action;
    private final static TvShow testShow = TvShow.Lost;

    @Before
    @Override
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        testEpisode = new Episode();
        testEpisode.setTvShow(testShow);
        testEpisode.setTitle("test Title");
        testEpisodes = new ArrayList<Episode>();
        testEpisodes.add(testEpisode);

        when(mockEpisodeCatalogue.getTvShowEpisodes(testShow)).thenReturn(testEpisodes);

        action = new ViewTvShowEpisodesAction();
        action.setEpisodeCatalogue(mockEpisodeCatalogue);

        //These are normally set up by struts
        action.setTvShowName(testShow.getTvShowName());
    }

    /**
     * Test of execute method, of class ViewTvShowEpisodesAction.
     */
    @Test
    public void testExecute() throws Exception {
        String result = action.execute();
        assertTrue("Expected a success result!", ActionSupport.SUCCESS.equals(result));
    }

    /**
     * Test of getEpisodes method, of class ViewTvShowEpisodesAction.
     */
    @Test
    public void testGetEpisodes() throws Exception {
        String result = action.execute();
        assertTrue("Expected a success result!", ActionSupport.SUCCESS.equals(result));

        List<Episode> actionEpisodes = action.getEpisodes();
        assertEquals("getEpisodes", testEpisodes, actionEpisodes);
        assertEquals("getEpisdoe", testEpisode, actionEpisodes.get(0));
    }
}
