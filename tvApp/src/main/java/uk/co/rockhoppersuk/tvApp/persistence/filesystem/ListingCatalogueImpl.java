/*
 * (c) Mark Bailey - www.rockhoppersuk.co.uk - 2010
 */
package uk.co.rockhoppersuk.tvApp.persistence.filesystem;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.apache.log4j.Logger;
import uk.co.rockhoppersuk.date.DateTimeUtils;
import uk.co.rockhoppersuk.tvApp.channel.Channel;
import uk.co.rockhoppersuk.tvApp.listing.Listing;
import uk.co.rockhoppersuk.tvApp.listing.ListingCatalogue;
import uk.co.rockhoppersuk.tvApp.show.TvShow;
import uk.co.rockhoppersuk.tvApp.show.episode.EpisodeStatus;

/**
 * Concrete implementation of <code>ListingCatalogue</code>.
 * This implementation uses flat files on the file system to persist the listings.
 *
 * TODO MRB ADD DETAILS OF FILE STRUCTURE ETC :)
 *
 * @author mbailey
 * @version 1.0
 */
public final class ListingCatalogueImpl implements ListingCatalogue {

    /**
     * log4j static variable.
     */
    private static Logger logger = Logger.getLogger(ListingCatalogueImpl.class);
    /**
     * Map containing a List of <code>Listing</code> for each <code>Date</code>.
     */
    private Map<Date, List<Listing>> listingMap;
    /**
     * To enable unit testing.
     */
    private ListingFileHelper listingFileHelper;

    /**
     * Private default constructor for singleton.
     * Populates the in memory map from the file system.
     */
    private ListingCatalogueImpl() {
        logger.info("entering ListingCatalogueImpl method");
        listingMap = new TreeMap<Date, List<Listing>>();
        logger.info("exiting ListingCatalogueImpl method. listingMap.size: " + listingMap.size());
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
         * Static INSTANCE to hold ListingsCatalogueImpl singleton.
         */
        private static final ListingCatalogue INSTANCE = new ListingCatalogueImpl();
    }

    /**
     * Obtains singleton, if not created creates the instance.
     * @return ListingsCatalogueImpl implementation of <code>ListingsCatalogue</code>
     */
    public static ListingCatalogue getInstance() {
        return LazyHolder.INSTANCE;
    }

    /**
     * Refreshes the Catalogue from the file system.
     */
    public void refreshCatalogue() {
        logger.info("entering refreshCatalogue method");
        Map<Date, List<Listing>> tempListingMap = new TreeMap<Date, List<Listing>>();

        Iterator<Date> iter = getListingFileHelper().getListingDatesFromFileSystem().iterator();
        while (iter.hasNext()) {
            Date listingDate = iter.next();
            tempListingMap.put(listingDate, getDailyListing(listingDate));
        }

        listingMap.clear();
        listingMap.putAll(tempListingMap);
        logger.info("exiting refreshCatalogue method.  listingMap size : " + tempListingMap.size());
    }

    /**
     * A list of <code>java.util.Date</code> that represent each date the system
     * has listings for.
     * @return List of Dates that the system holds a listings for
     */
    public List<Date> getListingDates() {
        return new ArrayList<Date>(listingMap.keySet());
    }

    /**
     * A list of <code>Listing</code> for a requested date.
     * @param date the Date of the listings required.
     * @return a list of <code>Listing</code> for a requested date.
     */
    public List<Listing> getListingsForDate(final Date date) {
        return listingMap.get(date);
    }

    /**
     * A list of <code>Listing</code> form the date passed in.
     * @param date the date which <code>Listing</code> are required from.
     * @return a list of <code>Listing</code> form the date passed in.
     */
    public List<Listing> getListingsFromDate(final Date date) {
        List<Listing> listings = new ArrayList<Listing>();
        Date listingDate = date;
        while (!listingDate.after(getMaxListingDate())) {
            listings.addAll(listingMap.get(listingDate));
            listingDate = DateTimeUtils.getNextDay(listingDate);
        }
        return listings;
    }

    /**
     * Retrieves a list of <code>Listing</code> for the <code>java.util.Date</code> requested.
     * @param date The <code>java.util.Date</code> the <code>Listing</code> are required for.
     * @return a list of <code>Listing</code> for the <code>java.util.Date</code> requested.
     */
    private List<Listing> getDailyListing(final Date date) {
        return getListingFileHelper().getDailyListingFromFileSystem(date);
    }

    /**
     * Retrieves the earliest <code>java.util.Date</code> that is stored.
     * @return Retrieves the earliest <code>java.util.Date</code> that is stored.
     */
    public Date getMinListingDate() {
        return getListingDates().get(0);
    }

    /**
     * Retrieves the latest <code>java.util.Date</code> that is stored.
     * @return Retrieves the latest <code>java.util.Date</code> that is stored.
     */
    public Date getMaxListingDate() {
        return getListingDates().get(getListingDates().size() - 1);
    }

    /**
     * A list of <code>Listing</code> for the Tv Show passed in.
     * @param show the <code>TvShow</code> which <code>Listing</code> are required for.
     * @return a list of <code>Listing</code> for the Tv Show passed in.
     */
    public List<Listing> getListingsForTvShow(final TvShow show) {
        List<Listing> listings = new ArrayList<Listing>();
        Iterator<List<Listing>> iter = listingMap.values().iterator();
        while (iter.hasNext()) {
            Iterator<Listing> iterListing = iter.next().iterator();
            while (iterListing.hasNext()) {
                Listing listing = iterListing.next();
                if (listing.getEpisode().getTvShow().equals(show)) {
                    listings.add(listing);
                }
            }
        }
        return listings;
    }

    /**
     * A list of <code>Listing</code> for the Tv Show and title passed in.
     * @param show the <code>TvShow</code> which <code>Listing</code> are required for.
     * @param episodeTitle the title of the episode required,
     * @return a list of <code>Listing</code> for the Tv Show and title passed in.
     */
    public List<Listing> getListingsForEpisode(final TvShow show, final String episodeTitle) {
        List<Listing> listings = new ArrayList<Listing>();
        Iterator<Listing> iter = getListingsForTvShow(show).iterator();
        while (iter.hasNext()) {
            Listing listing = iter.next();
            if (listing.getEpisode().getEpisodeTitle().equals(episodeTitle)) {
                listings.add(listing);
            }
        }
        return listings;
    }

    /**
     * A list of <code>Listing</code> for the Status passed in.
     * @param status the <code>EpisodeStatus</code> which <code>Listing</code> are required for.
     * @return a list of <code>Listing</code> for the status passed in.
     */
    public List<Listing> getListingsForStatus(final EpisodeStatus status) {
        List<Listing> listings = new ArrayList<Listing>();
        Iterator<List<Listing>> iter = listingMap.values().iterator();
        while (iter.hasNext()) {
            Iterator<Listing> iterListing = iter.next().iterator();
            while (iterListing.hasNext()) {
                Listing listing = iterListing.next();
                if (listing.getEpisode().getStatus().equals(status)) {
                    listings.add(listing);
                }
            }
        }
        return listings;
    }

    /**
     * A list of <code>Listing</code> for the <code>Channel</code> passed in.
     * @param channel the <code>Channel</code> which <code>Listing</code> are required for.
     * @return a list of <code>Listing</code> for the <code>Channel</code> passed in.
     */
    public List<Listing> getListingsForChannel(final Channel channel) {
        List<Listing> listings = new ArrayList<Listing>();
        Iterator<List<Listing>> iter = listingMap.values().iterator();
        while (iter.hasNext()) {
            Iterator<Listing> iterListing = iter.next().iterator();
            while (iterListing.hasNext()) {
                Listing listing = iterListing.next();
                if (listing.getChannel().equals(channel)) {
                    listings.add(listing);
                }
            }
        }
        return listings;
    }

    /**
     * A list of <code>Listing</code> for any Listing with an <code>EpisodeStatus</code> not of Watched.
     * @return a list of <code>Listing</code> for any Listing with an <code>EpisodeStatus</code> not of Watched.
     */
    public List<Listing> getListingsForUnwatchedShows() {
        List<Listing> unwatchedListings = new ArrayList<Listing>();
        Iterator<List<Listing>> iter = listingMap.values().iterator();
        while (iter.hasNext()) {
            Iterator<Listing> iterListing = iter.next().iterator();
            while (iterListing.hasNext()) {
                Listing listing = iterListing.next();
                if (!listing.getEpisode().getStatus().equals(EpisodeStatus.watched)) {
                    unwatchedListings.add(listing);
                }
            }
        }
        return unwatchedListings;
    }

    /**
     * To enable unit testing.
     * @param mockListingFileHelper mock ListingFileHelper
     */
    protected void setListingFileHelper(final ListingFileHelper mockListingFileHelper) {
        this.listingFileHelper = mockListingFileHelper;
    }

    /**
     * getter for the listingFileHelper.  Used to enable helper to be mocked out.
     * @return the required ListingFileHelper implementation.
     */
    private ListingFileHelper getListingFileHelper() {
        if (listingFileHelper == null) {
            listingFileHelper = new ListingFileHelper();
        }
        return listingFileHelper;
    }
}
