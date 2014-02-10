/*
 * (c) Mark Bailey - www.rockhoppersuk.co.uk - 2010
 */
package uk.co.rockhoppersuk.tvApp.listing;

import uk.co.rockhoppersuk.tvApp.persistence.filesystem.ListingCatalogueImpl;

/**
 * The Listing factory.
 *
 * @author mbailey
 * @version 1.0
 */
public final class ListingFactory {

    /**
     * the listing singleton.
     */
    private static ListingCatalogue listingCatalogue;

    /**
     * default constructor set to private as singleton.
     */
    private ListingFactory() {
    }

    /**
     * Enable lazy load of singleton.
     */
    private static final class LazyHolder {

        /**
         * default constructor set to private as singleton.
         */
        private LazyHolder() {
        }
        /**
         * Static INSTANCE to hold ListingCatalogue singleton.
         */
        private static final ListingCatalogue INSTANCE = ListingCatalogueImpl.getInstance();
    }

    /**
     * Factory method to return required <code>ListingCatalogue</code> implementation.
     * @return required <code>ListingCatalogue</code> implementation
     */
    public static ListingCatalogue getListingCatalogueInstance() {
        if (listingCatalogue == null) {
            listingCatalogue = LazyHolder.INSTANCE;
            listingCatalogue.refreshCatalogue();
        }
        return listingCatalogue;
    }
}
