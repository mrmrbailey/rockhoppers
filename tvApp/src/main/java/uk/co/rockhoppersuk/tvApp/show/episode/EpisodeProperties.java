/*
 * (c) Mark Bailey - www.rockhoppersuk.co.uk - 2010
 */
package uk.co.rockhoppersuk.tvApp.show.episode;

import org.apache.log4j.Logger;
import uk.co.rockhoppersuk.tvApp.properties.AbstractProperties;
import uk.co.rockhoppersuk.tvApp.show.TvShow;

/**
 * Loads and manages the Episode properties file.
 *
 * @author mbailey
 * @version 1.0
 */
public final class EpisodeProperties extends AbstractProperties {

    /**
     * Prevent singleton being instantiated.
     */
    private EpisodeProperties() {
        setLogger(Logger.getLogger(EpisodeProperties.class));
        setPropertyFilename("uk/co/rockhoppersuk/tvApp/shows/episode.properties");
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
         * Static INSTANCE to hold EpisodeProperties singleton.
         */
        private static final EpisodeProperties INSTANCE = new EpisodeProperties();
    }

    /**
     * Obtains singleton, if not created creates the instance.
     * @return EpisodeProperties.
     */
    public static EpisodeProperties getInstance() {
        return LazyHolder.INSTANCE;
    }

    /**
     * Returns the correct name for the title of an episode, if the alternative name is
     * present in the properties file.
     * This is due to different channels specifying different titles and the UK and US having
     * different spellings etc.
     * @param show The TvShow that this episode corresponds to.
     * @param episodeTitle The title of the episode that needs checking.
     * @return The mapping this responds to if present, otherwise returns episodeTitle.
     */
    public String getEpisodeTitle(final TvShow show, final String episodeTitle) {
        String episodeKey = (show.name() + "~" + episodeTitle).replace(" ", "").replace(":", "").toLowerCase();
        if (getProps().containsKey(episodeKey)) {
            getLogger().info("Found key: " + episodeKey);
            return getProps().getProperty(episodeKey);
        } else {
            return episodeTitle;
        }
    }
}
