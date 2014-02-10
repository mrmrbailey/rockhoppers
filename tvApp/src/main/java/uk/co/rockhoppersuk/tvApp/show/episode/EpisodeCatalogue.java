/*
 * (c) Mark Bailey - www.rockhoppersuk.co.uk - 2010
 */
package uk.co.rockhoppersuk.tvApp.show.episode;

import java.util.List;
import uk.co.rockhoppersuk.tvApp.show.TvShow;

/**
 * This interface is responsible for any activities the manipulate the
 * <code>Episode</code> in the system.
 *
 * @author mbailey
 * @version 1.0
 */
public interface EpisodeCatalogue {

    /**
     * Method to refresh the catalogue from the data store.
     * This method will refresh the in memory data from the physical storage.
     */
    void refreshCatalogue();

    /**
     * Method to return a single <code>Episode</code>.  Retrieved by the
     * <code>TvShow</code> and Title
     * @param tvShow  The <code>TvShow</code> for the <code>Episode</code> required.
     * @param episodeTitle the title for the <code>Episode</code> required.
     * @return the <code>Episode</code> retrieved by the <code>TvShow</code> and Title.
     */
    Episode getEpisode(TvShow tvShow, String episodeTitle);

    /**
     * @param tvShow the <code>TvShow</code> for the <code>Episode</code> required.
     * @return a list of <code>Episode</code> for a given <code>TvShow</code>.
     */
    List<Episode> getTvShowEpisodes(TvShow tvShow);
}
