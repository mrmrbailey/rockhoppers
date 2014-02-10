/*
 * (c) Mark Bailey - www.rockhoppersuk.co.uk - 2010
 */
package uk.co.rockhoppersuk.tvApp.persistence.filesystem;

import java.util.ArrayList;
import static org.mockito.Mockito.*;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.junit.Assert.*;
import uk.co.rockhoppersuk.tvApp.show.TvShow;
import uk.co.rockhoppersuk.tvApp.show.episode.Episode;
import uk.co.rockhoppersuk.tvApp.show.episode.EpisodeCatalogue;
import uk.co.rockhoppersuk.tvApp.show.episode.EpisodeStatus;

/**
 * Unit test for the episode catalogue.
 *
 * @author mbailey
 * @version 1.0
 */
public class EpisodeCatalogueImplTest {
    
    @Mock EpisodeFileHelper mockEpisodeFileHelper;
    private EpisodeCatalogueImpl episodeCatalogueImpl;
    private Episode testEpisode;
    private List<Episode> episodes;
    private final static TvShow testShow = TvShow.Lost;
    private final static String testTitle = "Title";

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        episodes = new ArrayList<Episode>();

        testEpisode = new Episode();
        testEpisode.setTvShow(testShow);
        testEpisode.setTitle(testTitle);

        Episode anotherEpisode =  new Episode();
        anotherEpisode.setTvShow(testShow);
        anotherEpisode.setTitle("This is not " + testTitle);

        episodes.add(testEpisode);
        episodes.add(anotherEpisode);

        when(mockEpisodeFileHelper.getEpisodesForTvShowFromFileSystem(testShow)).thenReturn(episodes);

        for (TvShow shows : TvShow.values()) {
            if (!shows.equals(testShow)) {
                when(mockEpisodeFileHelper.getEpisodesForTvShowFromFileSystem(shows)).thenReturn(null);
            }
        }

        EpisodeCatalogue episodeCatalogue = EpisodeCatalogueImpl.getInstance();
        episodeCatalogueImpl = (EpisodeCatalogueImpl) episodeCatalogue;
        episodeCatalogueImpl.setEpisodeFileHelper(mockEpisodeFileHelper);
    }


    /**
     * Test of getInstance method, of class EpisodeCatalogueImpl.
     */
    @Test
    public void testGetInstance() {
        EpisodeCatalogue result = EpisodeCatalogueImpl.getInstance();
        assertTrue("Is the EpisodeCatalogueImpl", result instanceof EpisodeCatalogue);
        assertEquals("returns the same", result, EpisodeCatalogueImpl.getInstance());
    }

    /**
     * Test of refreshCatalogue method, of class EpisodeCatalogueImpl.
     */
    @Test
    public void testRefreshCatalogue() {
        try {
            episodeCatalogueImpl.refreshCatalogue();
        } catch (Exception ex) {
            fail("refresh threw exception" + ex.getMessage());
        }
    }

    /**
     * Test of getEpisode method, of class EpisodeCatalogueImpl.
     */
    @Test
    public void testGetEpisode() {
        Episode result = episodeCatalogueImpl.getEpisode(testShow, testTitle);
        assertEquals(testEpisode, result);
    }

    /**
     * Test of getEpisode method, of class EpisodeCatalogueImpl.
     */
    @Test
    public void testGetEpisodeDifferentCase() {
        Episode result = episodeCatalogueImpl.getEpisode(testShow, testTitle.toLowerCase());
        assertEquals(testEpisode, result);
    }
    
    @Test
    public void testGetUnknownEpisode() {
        String notTestTitle = testTitle + "123";
        Episode result = episodeCatalogueImpl.getEpisode(testShow, notTestTitle);
        assertNotSame(testEpisode, result);
        assertEquals(notTestTitle, result.getEpisodeTitle());
        assertEquals(EpisodeStatus.unknown, result.getStatus());
    }

    /**
     * Test of getTvShowEpisodes method, of class EpisodeCatalogueImpl.
     */
    @Test
    public void testGetTvShowEpisodes() {
        List<Episode> result = episodeCatalogueImpl.getTvShowEpisodes(testShow);
        assertEquals(episodes, result);
    }
}
