/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package uk.co.rockhoppersuk.tvApp.show.episode;

import org.junit.Test;
import uk.co.rockhoppersuk.tvApp.persistence.filesystem.EpisodeCatalogueImpl;
import static org.junit.Assert.*;

/**
 *
 * @author mxbailey
 */
public class EpisodeFactoryTest {


    /**
     * Test of getEpisodeCatalogueInstance method, of class EpisodeFactory.
     */
    @Test
    public void testGetEpisodeCatalogueInstance() {

        EpisodeCatalogue result = EpisodeFactory.getEpisodeCatalogueInstance();
        assertTrue("Is the EpisodeCatalogueImpl",
                result instanceof EpisodeCatalogueImpl);
        assertEquals("returns the same",
                result, EpisodeFactory.getEpisodeCatalogueInstance());

    }

}