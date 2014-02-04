/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package uk.co.rockhoppersuk.tvApp.action.show.tvdotcom;


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
import uk.co.rockhoppersuk.tvApp.show.tvdotcom.TvDotComCatalogue;
import uk.co.rockhoppersuk.tvApp.show.tvdotcom.TvDotComEpisode;

/**
 * Unit test for the Update Tv Show action.
 *
 * @author mbailey
 * @version 1.0
 */

public class UpdateTvShowActionTest extends StrutsTestCase {

    @Mock private EpisodeCatalogue mockEpisodeCatalogue;
    @Mock private TvDotComCatalogue mockTvDotComCatalogue;
    private Episode testEpisode;
    private List<Episode> testEpisodes;
    private TvDotComEpisode testTvDotComEpisode;
    private List<TvDotComEpisode> testTvDotComEpisodes;
    private UpdateTvShowAction action;
    private final static TvShow testShow = TvShow.Lost;

    @Before
    @Override
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        
        testTvDotComEpisode = new TvDotComEpisode();
        testTvDotComEpisode.setTvShow(testShow);
        testTvDotComEpisode.setTitle("test Title");
        testTvDotComEpisodes = new ArrayList<TvDotComEpisode>();
        testTvDotComEpisodes.add(testTvDotComEpisode);

        testEpisode = testTvDotComEpisode;
        testEpisodes = new ArrayList<Episode>();
        testEpisodes.add(testEpisode);

        when(mockEpisodeCatalogue.getTvShowEpisodes(testShow)).thenReturn(testEpisodes);
        when(mockTvDotComCatalogue.getTvShowEpisodes(testShow)).thenReturn(testTvDotComEpisodes);

        action = new UpdateTvShowAction();
        action.setEpisodeCatalogue(mockEpisodeCatalogue);
        action.setTvDotComCatalogue(mockTvDotComCatalogue);

        //These are normally set up by struts
        action.setTvShowName(testShow.getTvShowName());
    }

    /**
     * Test of execute method, of class UpdateTvShowAction.
     */
    @Test
    public void testExecute() throws Exception {
        String result = action.execute();
        assertTrue("Expected a success result!", ActionSupport.SUCCESS.equals(result));
    }

    /**
     * Test of getEpisodes method, of class UpdateTvShowAction.
     */
    @Test
    public void testGetEpisodes() throws Exception {
        String result = action.execute();
        assertTrue("Expected a success result!", ActionSupport.SUCCESS.equals(result));

        List<Episode> actionEpisodes = action.getEpisodes();
        assertEquals("getEpisodes", testEpisodes, actionEpisodes);
        assertEquals("getEpisdoe", testEpisode, actionEpisodes.get(0));
    }
    
    /**
     * Test of getEpisodes method, of class UpdateTvShowAction.
     */
    @Test
    public void testGetTvDotComEpisodes() throws Exception {
        String result = action.execute();
        assertTrue("Expected a success result!", ActionSupport.SUCCESS.equals(result));

        List<TvDotComEpisode> actionTvDotComEpisodes = action.getTvDotComEpisodes();
        assertEquals("getEpisodes", testTvDotComEpisodes, actionTvDotComEpisodes);
        assertEquals("getEpisdoe", testTvDotComEpisode, actionTvDotComEpisodes.get(0));
        
    }    
}
