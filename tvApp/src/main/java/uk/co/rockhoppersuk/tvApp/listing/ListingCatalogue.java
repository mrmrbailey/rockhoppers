/*
 * (c) Mark Bailey - www.rockhoppersuk.co.uk - 2010
 */
package uk.co.rockhoppersuk.tvApp.listing;

import java.util.Date;
import java.util.List;
import uk.co.rockhoppersuk.tvApp.channel.Channel;
import uk.co.rockhoppersuk.tvApp.show.TvShow;
import uk.co.rockhoppersuk.tvApp.show.episode.EpisodeStatus;

/**
 * This interface is responsible for any activities the manipulate the listings in
 * the system.
 *
 * @author mbailey
 * @version 1.0
 */
public interface ListingCatalogue {

    /**
     * Method to refresh the catalogue from the data store.
     * This method will refresh the in memory data from the physical storage.
     */
    void refreshCatalogue();

    /**
     * A list of <code>java.util.Date</code> that represent each date the system
     * has listings for.
     * @return List of Dates that the system holds a listings for
     */
    List<Date> getListingDates();

    /**
     * A list of <code>Listing</code> for a requested date.
     * @param date the Date of the listings required.
     * @return a list of <code>Listing</code> for a requested date.
     */
    List<Listing> getListingsForDate(Date date);

    /**
     * A list of <code>Listing</code> from the date passed in.
     * @param date the date which <code>Listing</code> are required from.
     * @return a list of <code>Listing</code> form the date passed in.
     */
    List<Listing> getListingsFromDate(Date date);

    /**
     * The earliest <code>java.util.Date</code> that listings are stored for.
     * @return The earliest <code>java.util.Date</code> that listings are stored for.
     */
    Date getMinListingDate();

    /**
     * The latest <code>java.util.Date</code> that listings are stored for.
     * @return The latest <code>java.util.Date</code> that listings are stored for.
     */
    Date getMaxListingDate();

    /**
     * A list of <code>Listing</code> for the Tv Show passed in.
     * @param show the <code>TvShow</code> which <code>Listing</code> are required for.
     * @return a list of <code>Listing</code> for the Tv Show passed in.
     */
    List<Listing> getListingsForTvShow(TvShow show);

    /**
     * A list of <code>Listing</code> for the Tv Show and title passed in.
     * @param show the <code>TvShow</code> which <code>Listing</code> are required for.
     * @param episodeTitle the title of the episode required,
     * @return a list of <code>Listing</code> for the Tv Show and title passed in.
     */
    List<Listing> getListingsForEpisode(TvShow show, String episodeTitle);

    /**
     * A list of <code>Listing</code> for the Status passed in.
     * @param status the <code>EpisodeStatus</code> which <code>Listing</code> are required for.
     * @return a list of <code>Listing</code> for the status passed in.
     */
    List<Listing> getListingsForStatus(EpisodeStatus status);

    /**
     * A list of <code>Listing</code> for the <code>Channel</code> passed in.
     * @param channel the <code>Channel</code> which <code>Listing</code> are required for.
     * @return a list of <code>Listing</code> for the <code>Channel</code> passed in.
     */
    List<Listing> getListingsForChannel(Channel channel);

    /**
     * A list of <code>Listing</code> for any Listing with an <code>EpisodeStatus</code> not of Watched.
     * @return a list of <code>Listing</code> for any Listing with an <code>EpisodeStatus</code> not of Watched.
     */
    List<Listing> getListingsForUnwatchedShows();
}
