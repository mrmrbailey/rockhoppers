/*
 * (c) Mark Bailey - www.rockhoppersuk.co.uk - 2010
 */
package uk.co.rockhoppersuk.tvApp.show;

import org.apache.log4j.Logger;
import uk.co.rockhoppersuk.tvApp.properties.AbstractProperties;

/**
 * Loads and manages the TvShow properties file.
 *
 * @author mbailey
 * @version 1.0
 */
public final class TvShowProperties extends AbstractProperties {

    /**
     * Prevent singleton being instantiated.
     */
    private TvShowProperties() {
        setLogger(Logger.getLogger(TvShowProperties.class));
        setPropertyFilename("uk/co/rockhoppersuk/tvApp/shows/tvShow.properties");
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
         * Static INSTANCE to hold TvShowProperties singleton.
         */
        private static final TvShowProperties INSTANCE = new TvShowProperties();
    }

    /**
     * Obtains singleton, if not created creates the instance.
     * @return TvShowProperties.
     */
    public static TvShowProperties getInstance() {
        return LazyHolder.INSTANCE;
    }

    /**
     * Returns the correct name for the TV Show if it is present in the properties file.
     * This is due to different listing sites and channels having different names for the
     * same <code>TvShow</code>.
     * @param showName The name of the show that needs checking.
     * @return The mapping this responds to if present, otherwise returns showName.
     */
    public String getTvShowName(final String showName) {
        String tvShowKey = showName.replace(" ", "").replace(":", "").toLowerCase();
        if (getProps().containsKey(tvShowKey)) {
            getLogger().info("Found key: " + tvShowKey);
            return getProps().getProperty(tvShowKey);
        } else {
            return showName;
        }
    }
}
