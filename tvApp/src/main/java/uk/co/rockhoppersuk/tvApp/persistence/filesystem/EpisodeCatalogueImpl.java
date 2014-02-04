/*
 * (c) Mark Bailey - www.rockhoppersuk.co.uk - 2010
 */
package uk.co.rockhoppersuk.tvApp.persistence.filesystem;

import java.util.EnumMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import uk.co.rockhoppersuk.tvApp.show.TvShow;
import uk.co.rockhoppersuk.tvApp.show.episode.Episode;
import uk.co.rockhoppersuk.tvApp.show.episode.EpisodeCatalogue;
import uk.co.rockhoppersuk.tvApp.show.episode.EpisodeStatus;

/**
 * Concrete implementation of <code>EpisodeCatalogueImpl</code>.
 * This implementation uses flat files on the file system to persist the episodes.
 *
 * TODO MRB ADD DETAILS OF FILE STRUCTURE ETC :)
 *
 * @author mbailey
 * @version 1.0
 */
public final class EpisodeCatalogueImpl implements EpisodeCatalogue {

    /**
     * log4j static variable.
     */
    private static Logger logger = Logger.getLogger(EpisodeCatalogueImpl.class);
    /**
     * Map containing a List of <code>Episode</code> for each <code>TvShow</code>.
     */
    private Map<TvShow, List<Episode>> tvShowMap;
    /**
     * To enable unit testing.
     */
    private EpisodeFileHelper episodeFileHelper;

    /**
     * Private default constructor for singleton.
     * Populates the in memory map from the file system.
     */
    private EpisodeCatalogueImpl() {
        logger.info("entering EpisodeCatalogueImpl method");
        tvShowMap = new EnumMap<TvShow, List<Episode>>(TvShow.class);
        logger.info("exiting EpisodeCatalogueImpl method. tvShowMap.size: " + tvShowMap.size());
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
         * Static INSTANCE to hold EpisodeCatalogueImpl singleton.
         */
        private static final EpisodeCatalogue INSTANCE = new EpisodeCatalogueImpl();
    }

    /**
     * Obtains singleton, if not created creates the instance.
     * @return EpisodeCatalogueImpl implementation of <code>EpisodeCatalogue</code>
     */
    public static EpisodeCatalogue getInstance() {
        return LazyHolder.INSTANCE;
    }

    /**
     * Refreshes the Catalogue from the file system.
     */
    public void refreshCatalogue() {
        logger.info("entering refreshCatalogue method");
        Map<TvShow, List<Episode>> tempTvShowMap =
                new EnumMap<TvShow, List<Episode>>(TvShow.class);
        for (TvShow shows : TvShow.values()) {
            if (!shows.equals(TvShow.Unknown)) {
                tempTvShowMap.put(shows, getEpisodesForTvShow(shows));
            }
        }
        tvShowMap.clear();
        tvShowMap.putAll(tempTvShowMap);
        logger.info("exiting refreshCatalogue method.  tvShowMap size : " + tempTvShowMap.size());
    }

    /**
     * Method to return a single <code>Episode</code>.  Retrieved by the
     * <code>TvShow</code> and Title
     * @param tvShow The <code>TvShow</code> for the <code>Episode</code> required.
     * @param episodeTitle the title for the <code>Episode</code> required.
     * @return the <code>Episode</code> retrieved by the <code>TvShow</code> and Title.
     */
    public Episode getEpisode(final TvShow tvShow, final String episodeTitle) {
        logger.info("entering getEpisode method.  tvShow: " + tvShow.getTvShowName()
                + " episodeTitle: " + episodeTitle);
        Iterator<Episode> iter = tvShowMap.get(tvShow).iterator();
        while (iter.hasNext()) {
            Episode episode = iter.next();
            if (episode.getEpisodeTitle().equalsIgnoreCase(episodeTitle)) {
                logger.info("exiting getEpisode method.  Episode found: "
                        + episode.getEpisodeTitle());
                return episode;
            }
        }
        logger.debug("episode not found, creating unkonwn one");
        Episode episode = new Episode();
        episode.setTvShow(tvShow);
        episode.setTitle(episodeTitle);
        episode.setStatus(EpisodeStatus.unknown);
        episode.setEpisodeNumber("");
        logger.info("exiting getEpisode method. Episode not found.");
        return episode;
    }

    /**
     * Method to return a list of <code>Episode</code> for a given <code>TvShow</code>.
     * @param tvShow the <code>TvShow</code> for the <code>Episode</code> required.
     * @return a list of <code>Episode</code> for a given <code>TvShow</code>.
     */
    public List<Episode> getTvShowEpisodes(final TvShow tvShow) {
        logger.info("Calling getTvShowEpisodes method."
                + " tvShow: " + tvShow.getTvShowName()
                + " numberOfEpisodes: " + tvShowMap.get(tvShow).size());
        return tvShowMap.get(tvShow);
    }

    /**
     * To enable unit testing.
     * @param mockEpisodeFileHelper mock EpisodeFileHelper
     */
    protected void setEpisodeFileHelper(final EpisodeFileHelper mockEpisodeFileHelper) {
        episodeFileHelper = mockEpisodeFileHelper;
    }

    /**
     * getter for the episodeFileHelper.  Used to enable helper to be mocked out.
     * @return the required EpisodeFileHelper implementation.
     */
    private EpisodeFileHelper getEpisodeFileHelper() {
        if (episodeFileHelper == null) {
            episodeFileHelper = new EpisodeFileHelper();
        }
        return episodeFileHelper;
    }

    /**
     * Retrieves a list of <code>Episodes</code> for the <code>TvShow</code> requested.
     * @param tvShow The <code>TvShow</code> the <code>Episode</code> are required for.
     * @return a list of <code>Episodes</code> for the <code>TvShow</code> requested.
     */
    private List<Episode> getEpisodesForTvShow(final TvShow tvShow) {
        return getEpisodeFileHelper().getEpisodesForTvShowFromFileSystem(tvShow);
    }
}
