/*
 * (c) Mark Bailey - www.rockhoppersuk.co.uk - 2011
 */
package uk.co.rockhoppersuk.tvApp.show.tvdotcom;

import java.util.List;
import uk.co.rockhoppersuk.tvApp.show.TvShow;
import uk.co.rockhoppersuk.tvApp.show.episode.Episode;

/**
 * This interface is responsible for any activities with the tv.com website.
 *
 * @author mbailey
 * @version 1.0
 */
public interface TvDotComCatalogue {

    /**
     * Method to refresh the catalogue from the data store.
     * This method will refresh the in memory data from the physical storage.
     */
    void refreshCatalogue();

    /**
     * Method to return a single <code>TvDotComEpisode</code> for a given id.
     * @param tvShow The <code>TvShow</code> for the episode required.
     * @param episodeId The id of the episode required.
     * @return The <code>TvDotComEpisode</code> retrieved by the id.
     */
    TvDotComEpisode getEpisode(TvShow tvShow, int episodeId);

    /**
     * Method to return a list of <code>TvDotComEpisode</code> for a given <code>TvShow</code>.
     * @param tvShow The <code>TvShow</code> that the episodes are required for
     * @return A List of episodes from tv.com for the given show.
     */
    List<TvDotComEpisode> getTvShowEpisodes(TvShow tvShow);

    /**
     * Method to determine if an <code>Episode</code> contains the same details as the one from tv.com.
     * @param episode An episode to compare.
     * @param tvDotComEpisode The episode from tv.com.
     * @return True if any fields in episode differ from those in the tvdotComEpisode.
     */
    boolean isDetailsSame(Episode episode, TvDotComEpisode tvDotComEpisode);
}
