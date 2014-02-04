/*
 * (c) Mark Bailey - www.rockhoppersuk.co.uk - 2010
 */
package uk.co.rockhoppersuk.tvApp.listing;

import org.junit.Test;
import uk.co.rockhoppersuk.tvApp.persistence.filesystem.ListingCatalogueImpl;
import static org.junit.Assert.*;

/**
 * Unit test for the Listings Factory.
 *
 * @author mbailey
 * @version 1.0
 */
public class ListingFactoryTest {

    /**
     * Test of getListingCatalogueInstance method, of class ListingFactory.
     */
    @Test
    public void testGetListingCatalogueInstance() {

        ListingCatalogue result = ListingFactory.getListingCatalogueInstance();
        assertTrue("Is the ListingsCatalogueImpl",
                result instanceof ListingCatalogueImpl);
        assertEquals("returns the same",
                result, ListingFactory.getListingCatalogueInstance());
    }
}