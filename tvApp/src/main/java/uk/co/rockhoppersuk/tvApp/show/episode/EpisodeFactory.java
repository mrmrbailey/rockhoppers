/*
 * (c) Mark Bailey - www.rockhoppersuk.co.uk - 2010
 */
package uk.co.rockhoppersuk.tvApp.show.episode;

import uk.co.rockhoppersuk.tvApp.persistence.filesystem.EpisodeCatalogueImpl;

/**
 * The Episode factory.
 *
 * @author mbailey
 * @version 1.0
 */
public final class EpisodeFactory {

    /**
     * The episodeCatalogue singleton.
     */
    private static EpisodeCatalogue episodeCatalogue;

    /**
     * default constructor set to private as singleton.
     */
    private EpisodeFactory() {
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
         * Static INSTANCE to hold EpisodeCatalogue singleton.
         */
        private static final EpisodeCatalogue INSTANCE = EpisodeCatalogueImpl.getInstance();
    }

    /**
     * Factory method to return required <code>EpisodeCatalogue</code> implementation.
     * @return required <code>EpisodeCatalogue</code> implementation
     */
    public static EpisodeCatalogue getEpisodeCatalogueInstance() {
        if (episodeCatalogue == null) {
            episodeCatalogue = LazyHolder.INSTANCE;
            episodeCatalogue.refreshCatalogue();
        }
        return episodeCatalogue;
    }
}
