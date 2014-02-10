/*
 * (c) Mark Bailey - www.rockhoppersuk.co.uk - 2011
 */
package uk.co.rockhoppersuk.tvApp.persistence.url.tvdotcom;

import org.apache.log4j.Logger;
import uk.co.rockhoppersuk.tvApp.properties.AbstractProperties;

/**
 * Loads and manages the Episode properties file.
 *
 * @author mbailey
 * @version 1.0
 */
public final class TvDotComProperties extends AbstractProperties {

    /**
     * Prevent singleton being instantiated.
     */
    private TvDotComProperties() {
        setLogger(Logger.getLogger(TvDotComProperties.class));
        setPropertyFilename("uk/co/rockhoppersuk/tvApp/shows/tvdotcom.properties");
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
        private static final TvDotComProperties INSTANCE = new TvDotComProperties();
    }

    /**
     * Obtains singleton, if not created creates the instance.
     * @return EpisodeProperties.
     */
    public static TvDotComProperties getInstance() {
        return LazyHolder.INSTANCE;
    }

    /**
     * Returns the variable portion of the URL for a given <code>TvShow</code>.
     * @param showName The name of the show that the url is required for.
     * @return The url of the show from the properties file.  If no url is found NULL is returned.
     */
    public String getTvDotComURL(final String showName) {
        String tvShowKey = showName.replace(" ", "").replace(":", "").toLowerCase();
        if (getProps().containsKey(tvShowKey)) {
            getLogger().info("Found key: " + tvShowKey);
            return getProps().getProperty(tvShowKey);
        } else {
            getLogger().info("Unable to find key: " + showName);
            return null;
        }
    }
}
