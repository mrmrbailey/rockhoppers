/*
 * (c) Mark Bailey - www.rockhoppersuk.co.uk - 2011
 */
package uk.co.rockhoppersuk.tvApp.persistence.url.tvdotcom;

import java.util.EnumMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import uk.co.rockhoppersuk.tvApp.show.TvShow;
import uk.co.rockhoppersuk.tvApp.show.episode.Episode;
import org.apache.log4j.Logger;
import uk.co.rockhoppersuk.tvApp.show.episode.EpisodeStatus;
import uk.co.rockhoppersuk.tvApp.show.tvdotcom.TvDotComCatalogue;
import uk.co.rockhoppersuk.tvApp.show.tvdotcom.TvDotComEpisode;

/**
 * Concrete implementation of <code>TvDotComCatalogue</code>.
 *
 * @author mbailey
 * @version 1.0
 */
public final class TvDotComCatalogueImpl implements TvDotComCatalogue {

    /**
     * log4j static variable.
     */
    private static Logger logger = Logger.getLogger(TvDotComCatalogueImpl.class);
    /**
     * Map containing a List of <code>TvDotComEpisode</code> for each <code>TvShow</code>.
     */
    private Map<TvShow, List<TvDotComEpisode>> tvShowMap;
    /**
     * To enable unit testing.
     */
    private TvDotComHelper tvDotComHelper;

    /**
     * Private default constructor for singleton.
     */
    private TvDotComCatalogueImpl() {
        logger.info("entering TvDotComCatalogueImpl method");
        tvShowMap = new EnumMap<TvShow, List<TvDotComEpisode>>(TvShow.class);
        logger.info("exiting TvDotComCatalogueImpl method. tvShowMap.size: " + tvShowMap.size());
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
         * Static INSTANCE to hold TvDotComCatalogue singleton.
         */
        private static final TvDotComCatalogue INSTANCE = new TvDotComCatalogueImpl();
    }

    /**
     * Obtains singleton, if not created creates the instance.
     * @return TvDotComCatalogueImpl implementation of <code>TvDotComCatalogue</code>
     */
    public static TvDotComCatalogue getInstance() {
        return LazyHolder.INSTANCE;
    }

    /**
     * Clears out the the catalogue to enable subsequent requests to populate it.
     */
    public void refreshCatalogue() {
        logger.info("entering refreshCatalogue method");
        Map<TvShow, List<TvDotComEpisode>> tempTvShowMap =
                new EnumMap<TvShow, List<TvDotComEpisode>>(TvShow.class);
        for (TvShow shows : TvShow.values()) {
            if (!shows.equals(TvShow.Unknown)) {
                tempTvShowMap.put(shows, null);
            }
        }
        tvShowMap.clear();
        tvShowMap.putAll(tempTvShowMap);
        logger.info("exiting refreshCatalogue method.  tvShowMap size : " + tempTvShowMap.size());
    }

    /**
     * Method to return a single <code>TvDotComEpisode</code> for a given id.
     * @param tvShow The <code>TvShow</code> for the episode required.
     * @param episodeId The id of the episode required.
     * @return The <code>TvDotComEpisode</code> retrieved by the id.
     */
    public TvDotComEpisode getEpisode(final TvShow tvShow, final int episodeId) {
        logger.info("entering getEpisode method.  tvShow: " + tvShow.getTvShowName() + " episodeId: " + episodeId);
        if (tvShowMap.get(tvShow) == null) {
            logger.info("map does not contain this tvShow.  tvShow: " + tvShow.getTvShowName());
            setEpisodesForTvShow(tvShow);
        }

        Iterator<TvDotComEpisode> iter = tvShowMap.get(tvShow).iterator();
        while (iter.hasNext()) {
            TvDotComEpisode episode = iter.next();
            if (episode.getEpisodeId() == episodeId) {
                logger.info("exiting getEpisode method.  Episode found: "
                        + episode.getEpisodeTitle());
                return episode;
            }
        }
        logger.debug("episode not found, creating unkonwn one");
        TvDotComEpisode episode = new TvDotComEpisode();
        episode.setTvShow(tvShow);
        episode.setTitle("");
        episode.setStatus(EpisodeStatus.unknown);
        episode.setEpisodeNumber("");
        episode.setEpisodeId(episodeId);
        logger.info("exiting getEpisode method. Episode not found.");
        return episode;
    }

    /**
     * Method to return a list of <code>TvDotComEpisode</code> for a given <code>TvShow</code>.
     * @param tvShow The <code>TvShow</code> that the episodes are required for
     * @return A List of episodes from tv.com for the given show.
     */
    public List<TvDotComEpisode> getTvShowEpisodes(final TvShow tvShow) {
        logger.info("Calling getTvShowEpisodes method." + " tvShow: " + tvShow.getTvShowName());

        if (tvShowMap.get(tvShow) == null) {
            logger.info("map does not contain this tvShow.  tvShow: " + tvShow.getTvShowName());
            setEpisodesForTvShow(tvShow);
        }

        return tvShowMap.get(tvShow);
    }

    /**
     * Method to determine if an <code>Episode</code> contains the same details as the one from tv.com.
     * @param episode An episode to compare.
     * @param tvDotComEpisode The episode from tv.com.
     * @return True if any fields in episode differ from those in the tvdotComEpisode.
     */
    public boolean isDetailsSame(final Episode episode, final TvDotComEpisode tvDotComEpisode) {

        if (tvDotComEpisode.getTvShow() == null
                || !tvDotComEpisode.getTvShow().equals(episode.getTvShow())) {
            return false;
        } else if (tvDotComEpisode.getEpisodeTitle() == null
                || !tvDotComEpisode.getEpisodeTitle().equals(episode.getEpisodeTitle())) {
            return false;
        } else if (tvDotComEpisode.getEpisodeNumber() == null
                || !tvDotComEpisode.getEpisodeNumber().equals(episode.getEpisodeNumber())) {
            return false;
        } else if (tvDotComEpisode.getAirDate() == null
                || !tvDotComEpisode.getAirDate().equals(episode.getAirDate())) {
            return false;
        } else if (tvDotComEpisode.getSynopsis() == null
                || !tvDotComEpisode.getSynopsis().equals(episode.getSynopsis())) {
            return false;
        }
        return true;
    }

    /**
     * adds a list of <code>TvDotComEpisode</code> for the <code>TvShow</code> to the map.
     * @param tvShow The <code>TvDotComEpisode</code> the <code>Episode</code> are required for.
     */
    private void setEpisodesForTvShow(final TvShow tvShow) {
        logger.info("Calling setEpisodesForTvShow method." + " tvShow: " + tvShow.getTvShowName());
        tvShowMap.put(tvShow, getTvDotComHelper().getEpisodesFromTvDotCom(tvShow));
        logger.info("exiting setEpisodesForTvShow method.  number of episodes: " + tvShowMap.get(tvShow).size());
    }

    /**
     * getter for the TvDotComHelper.  Used to enable helper to be mocked out.
     * @return the required TvDotComHelper implementation.
     */
    private TvDotComHelper getTvDotComHelper() {
        if (tvDotComHelper == null) {
            tvDotComHelper = new TvDotComHelper();
        }
        return tvDotComHelper;
    }

    /**
     * To enable unit testing.
     * @param mockTvDotComHelper mock TvDotComHelper
     */
    protected void setTvDotComHelper(final TvDotComHelper mockTvDotComHelper) {
        tvDotComHelper = mockTvDotComHelper;
    }
}
