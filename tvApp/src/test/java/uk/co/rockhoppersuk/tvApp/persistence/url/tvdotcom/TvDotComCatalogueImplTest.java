/*
 * (c) Mark Bailey - www.rockhoppersuk.co.uk - 2010
 */
package uk.co.rockhoppersuk.tvApp.persistence.url.tvdotcom;

import uk.co.rockhoppersuk.tvApp.persistence.url.tvdotcom.TvDotComHelper;
import uk.co.rockhoppersuk.tvApp.persistence.url.tvdotcom.TvDotComCatalogueImpl;
import java.util.ArrayList;
import java.util.Date;
import static org.mockito.Mockito.*;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.junit.Assert.*;
import uk.co.rockhoppersuk.tvApp.show.TvShow;
import uk.co.rockhoppersuk.tvApp.show.episode.Episode;
import uk.co.rockhoppersuk.tvApp.show.tvdotcom.TvDotComCatalogue;
import uk.co.rockhoppersuk.tvApp.show.tvdotcom.TvDotComEpisode;

/**
 * Unit test for the TvDotComCatalogue catalogue.
 *
 * @author mbailey
 * @version 1.0
 */
public class TvDotComCatalogueImplTest {

    @Mock
    TvDotComHelper mockTvDotComHelper;
    private TvDotComCatalogueImpl tvDotComCatalogueImpl;
    private TvDotComEpisode testEpisode;
    private List<TvDotComEpisode> episodes;
    private final static TvShow testShow = TvShow.Lost;
    private final static String testTitle = "Title";
    private final static int testId = 42;
    private final static String testEpisodeNumber = "Test EpisodeNumber";
    @Mock
    private Date mockAirDate;
    private final static String testSynopsis = "Test Synopsis";

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        episodes = new ArrayList<TvDotComEpisode>();

        testEpisode = new TvDotComEpisode();
        testEpisode.setTvShow(testShow);
        testEpisode.setTitle(testTitle);
        testEpisode.setEpisodeId(testId);
        testEpisode.setEpisodeNumber(testEpisodeNumber);
        testEpisode.setAirDate(mockAirDate);
        testEpisode.setSynopsis(testSynopsis);


        TvDotComEpisode anotherEpisode = new TvDotComEpisode();
        anotherEpisode.setTvShow(testShow);
        anotherEpisode.setTitle(testTitle);
        anotherEpisode.setEpisodeId(testId + 1);

        episodes.add(testEpisode);
        episodes.add(anotherEpisode);

        when(mockTvDotComHelper.getEpisodesFromTvDotCom(testShow)).thenReturn(episodes);

        TvDotComCatalogue tvDotComCatalogue = TvDotComCatalogueImpl.getInstance();
        tvDotComCatalogueImpl = (TvDotComCatalogueImpl) tvDotComCatalogue;
        tvDotComCatalogueImpl.setTvDotComHelper(mockTvDotComHelper);
        tvDotComCatalogueImpl.refreshCatalogue();
    }

    /**
     * Test of getInstance method, of class TvDotComCatalogueImpl.
     */
    @Test
    public void testGetInstance() {
        TvDotComCatalogue result = TvDotComCatalogueImpl.getInstance();
        assertTrue("Is the TvDotComCatalogueImpl", result instanceof TvDotComCatalogue);
        assertEquals("returns the same", result, TvDotComCatalogueImpl.getInstance());
    }

    /**
     * Test of refreshCatalogue method, of class TvDotComCatalogueImpl.
     */
    @Test
    public void testRefreshCatalogue() {
        try {
            tvDotComCatalogueImpl.refreshCatalogue();
        } catch (Exception ex) {
            fail("refresh threw exception" + ex.getMessage());
        }
    }

    /**
     * Test of getEpisode method, of class TvDotComCatalogueImpl.
     */
    @Test
    public void testGetEpisode() {
        Episode result = tvDotComCatalogueImpl.getEpisode(testShow, testId);
        assertEquals(testEpisode, result);
    }

    /**
     * Test of getTvShowEpisodes method, of class TvDotComCatalogueImpl.
     */
    @Test
    public void testGetTvShowEpisodes() {
        List<TvDotComEpisode> result = tvDotComCatalogueImpl.getTvShowEpisodes(testShow);
        assertEquals(episodes, result);
    }

    /**
     * Test of isDetailsSame method, of class TvDotComCatalogueImpl.
     */
    @Test
    public void testIsDetailsSame() {

        Episode differentEpisode = new Episode();
        assertFalse(tvDotComCatalogueImpl.isDetailsSame(differentEpisode, testEpisode));

        differentEpisode.setTvShow(testEpisode.getTvShow());
        assertFalse(tvDotComCatalogueImpl.isDetailsSame(differentEpisode, testEpisode));

        differentEpisode.setTitle(testEpisode.getEpisodeTitle());
        assertFalse(tvDotComCatalogueImpl.isDetailsSame(differentEpisode, testEpisode));

        differentEpisode.setEpisodeNumber(testEpisode.getEpisodeNumber());
        assertFalse(tvDotComCatalogueImpl.isDetailsSame(differentEpisode, testEpisode));

        differentEpisode.setAirDate(testEpisode.getAirDate());
        assertFalse(tvDotComCatalogueImpl.isDetailsSame(differentEpisode, testEpisode));

        differentEpisode.setSynopsis(testEpisode.getSynopsis());
        assertTrue(tvDotComCatalogueImpl.isDetailsSame(testEpisode, testEpisode));
    }
}
