/*
 * (c) Mark Bailey - www.rockhoppersuk.co.uk - 2011
 */
package uk.co.rockhoppersuk.tvApp.show.tvdotcom;

import uk.co.rockhoppersuk.tvApp.persistence.url.tvdotcom.TvDotComCatalogueImpl;

/**
 * The tv.com factory.
 *
 * @author mbailey
 * @version 1.0
 */
public final class TvDotComFactory {

    /**
     * the tv.com singleton.
     */
    private static TvDotComCatalogue tvDotComCatalogue;

    /**
     * default constructor set to private as singleton.
     */
    private TvDotComFactory() {
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
        private static final TvDotComCatalogue INSTANCE = TvDotComCatalogueImpl.getInstance();
    }

    /**
     * Factory method to return required <code>ListingCatalogue</code> implementation.
     * @return required <code>ListingCatalogue</code> implementation
     */
    public static TvDotComCatalogue getTvDotComCatalogueInstance() {
        if (tvDotComCatalogue == null) {
            tvDotComCatalogue = LazyHolder.INSTANCE;
            tvDotComCatalogue.refreshCatalogue();
        }
        return tvDotComCatalogue;
    }
}
