/*
 * (c) Mark Bailey - www.rockhoppersuk.co.uk - 20111
 */
package uk.co.rockhoppersuk.tvApp.show.tvdotcom;

import uk.co.rockhoppersuk.tvApp.persistence.url.tvdotcom.TvDotComCatalogueImpl;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit test for the tv.dot Factory.
 *
 * @author mbailey
 * @version 1.0
 */
public class TvDotComFactoryTest {

    /**
     * Test of getListingCatalogueInstance method, of class TvDotComFactory.
     */
    @Test
    public void testGetListingCatalogueInstance() {
        TvDotComCatalogue result = TvDotComFactory.getTvDotComCatalogueInstance();
        assertTrue("Is the TvDotComCatalogueImpl",
                result instanceof TvDotComCatalogueImpl);
        assertEquals("returns the same",
                result, TvDotComFactory.getTvDotComCatalogueInstance());
    }
}