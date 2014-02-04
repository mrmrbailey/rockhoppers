/*
 * (c) Mark Bailey - www.rockhoppersuk.co.uk - 2010
 */
package uk.co.rockhoppersuk.tvApp.persistence.filesystem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.apache.log4j.Logger;
import uk.co.rockhoppersuk.file.FileUtils;
import uk.co.rockhoppersuk.tvApp.channel.Channel;
import uk.co.rockhoppersuk.tvApp.listing.Listing;
import uk.co.rockhoppersuk.tvApp.show.TvShow;
import uk.co.rockhoppersuk.tvApp.show.episode.EpisodeCatalogue;
import uk.co.rockhoppersuk.tvApp.show.episode.EpisodeFactory;
import uk.co.rockhoppersuk.tvApp.show.episode.EpisodeProperties;

/**
 * Helper class which interfaces with the file system to return requested data.
 *
 * <p>
 *
 * An <code>Listing</code> is persisted in a filename that is named after the
 * date it represents and is "~" delimited and contains the following fields
 * <ul>
 *   <li>DateTime Stamp</li>
 *   <li><code>TvShow</code></li>
 *   <li>Episode Title</li>
 *   <li><code>Channel</code></li>
 * </ul>
 *
 * @author mbailey
 * @version 1.0
 */
public class ListingFileHelper {

    /**
     * The file path of the episode file.
     */
    private static final String LISTING_PACKAGE = "uk/co/rockhoppersuk/tvApp/listings/";
    /**
     * The root directory of the listings.
     */
    private static final String LISTING_ROOT = "src/main/resources/uk/co/rockhoppersuk/tvApp/listings/";
    /**
     * The date format of the name of the persisted listings file.
     */
    private static final String FILENAME_FORMAT = "'tvGuide_'yyyyMMdd'.txt'";
    /**
     * The <code>SimpleDateFormat</code> for the persisted listings.
     */
    private static final SimpleDateFormat LISTING_FILENAME_FORMAT = new SimpleDateFormat(FILENAME_FORMAT);
    /**
     * the Format of the full filename and path.
     */
    private static final String FULL_FILENAME_FORMAT = "'" + LISTING_ROOT + "'yyyy/MM'/tvGuide_'yyyyMMdd'.txt'";
    /**
     * The <code>SimpleDateFormat</code> for the full filename and path.
     */
    private static final SimpleDateFormat LISTING_FULL_FILENAME_FORMAT = new SimpleDateFormat(FULL_FILENAME_FORMAT);
    /**
     * the Format of the full filename and path.
     */
    private static final String PACKAGE_FORMAT = "'" + LISTING_PACKAGE + "'yyyy/MM'/tvGuide_'yyyyMMdd'.txt'";
    /**
     * The <code>SimpleDateFormat</code> for the package.
     */
    private static final SimpleDateFormat LISTING_PACKAGE_FORMAT = new SimpleDateFormat(PACKAGE_FORMAT);
    /**
     * Position of the listing date time in the persisted file.
     */
    private static final int PERSISTED_POSITION_DATE = 0;
    /**
     * Position of the Tv Show in the persisted file.
     */
    private static final int PERSISTED_POSITION_SHOW = 1;
    /**
     * Position of the Episode Title in the persisted file.
     */
    private static final int PERSISTED_POSITION_TITLE = 2;
    /**
     * Position of the Channel in the persisted file.
     */
    private static final int PERSISTED_POSITION_CHANNEL = 3;
    /**
     * The number of delimited items in the listing file.
     */
    private static final int NUMBER_LISTING_ITEMS = 4;
    /**
     * The delimiter of the listing file.
     */
    private static final String DELIMINATER = "~";
    /**
     * The format of the persisted listing date.
     */
    protected static final String DATE_FORMAT = "EEEE dd MMMM yyyy HH:mm";
    /**
     * The <code>SimpleDateFormat</code> for the listing date.
     */
    private static final SimpleDateFormat LISTING_DATE_FORMAT = new SimpleDateFormat(DATE_FORMAT);
    /**
     * To enable unit testing.
     */
    private EpisodeCatalogue episodeCatalogue;
    /**
     * log4j static variable.
     */
    private static Logger logger = Logger.getLogger(ListingFileHelper.class);

    /**
     * A list of <code>java.util.Date</code> that represent each date the system
     * has listings for.
     * @return List of <code>java.util.Date</code> that listings exist for on the file system.
     */
    protected List<Date> getListingDatesFromFileSystem() {
        logger.info("entering getListingDatesFromFileSystem method.");
        List<Date> dateList = new ArrayList<Date>();
        List<String> listingsFromFileSystem = new ArrayList<String>();

        // Create an iterator of all the "year" elements
        Iterator<String> yearIter = getFoldersFromFileSystem(LISTING_PACKAGE).iterator();
        while (yearIter.hasNext()) {
            String year = yearIter.next();
            logger.info("reading through year: " + year);

            // Create an iterator of all the "month" elements
            String monthResourceName = LISTING_PACKAGE + year + "/";
            Iterator<String> monthIter = getFoldersFromFileSystem(monthResourceName).iterator();
            while (monthIter.hasNext()) {
                String month = monthIter.next();

                logger.info("reading through month: " + month);
                String dayResourceName = LISTING_PACKAGE + year + "/" + month + "/";
                listingsFromFileSystem.addAll(getFoldersFromFileSystem(dayResourceName));
            }
        }

        Iterator<String> fileIter = listingsFromFileSystem.iterator();
        while (fileIter.hasNext()) {
            String dailyFile = fileIter.next();
            logger.info("Found file: " + dailyFile);
            try {
                dateList.add(LISTING_FILENAME_FORMAT.parse(dailyFile));
            } catch (ParseException ex) {
                logger.error("Unable to parse filename: " + dailyFile);
                throw new IllegalArgumentException("Unable to parse filename: " + dailyFile, ex);
            }
        }

        logger.info("exiting getListingDatesFromFileSystem method. dateList.size: " + dateList.size());
        return dateList;
    }

    /**
     * Adds the names of the sub folders to a <code>List</code> of file names.
     * @param resourceName The resource name to be read.
     * @return a <code>List</code> of file names in the sub folders.
     */
    private List<String> getFoldersFromFileSystem(final String resourceName) {
        List<String> folderList = new ArrayList<String>();

        BufferedReader br = new BufferedReader(new InputStreamReader(
                this.getClass().getClassLoader().getResourceAsStream(resourceName)));
        String line = null;
        try {
            while ((line = br.readLine()) != null) {
                logger.info("found folder: " + line);
                folderList.add(line);
            }
        } catch (IOException ex) {
            logger.error("Something bad has happened reading folders : " + ex.getMessage());
            return folderList;
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException ex) {
                    logger.error("Something bad has happened whilst closing reader: " + ex.getMessage());
                    return folderList;
                }
            }
        }
        return folderList;
    }

    /**
     * Retrieves a list of <code>Listing</code> for the <code>java.util.Date</code> requested.
     * @param date The <code>java.util.Date</code> the <code>Listing</code> are required for.
     * @return a list of <code>Listing</code> for the <code>java.util.Date</code> requested.
     */
    protected List<Listing> getDailyListingFromFileSystem(final Date date) {
        logger.info("entering getDailyListingFromFileSystem method. date: " + date);

        List<Listing> listings = new ArrayList<Listing>();
        String fileName = LISTING_FULL_FILENAME_FORMAT.format(date);

        if (!FileUtils.isFileExist(fileName)) {
            logger.warn("Listings File not found: " + fileName);
            return listings;
        }

        String resourceName = LISTING_PACKAGE_FORMAT.format(date);
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(
                    this.getClass().getClassLoader().getResourceAsStream(resourceName)));
            String listingsDetails = null;
            while ((listingsDetails = reader.readLine()) != null) {
                listings.add(getListing(listingsDetails));
            }
        } catch (IOException ex) {
            logger.error("Something bad has happened: " + ex.getMessage());
            return listings;
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException ex) {
                    logger.error("Something really bad has happened: " + ex.getMessage());
                    return listings;
                }
            }
        }

        logger.info("exiting getDailyListingFromFileSystem method. listings.size: " + listings.size());
        return listings;
    }

    /**
     * Method that converts a line in the file system to an <code>Listing</code>.
     * @param persistedListing A "~" Delimited persisted Listing.
     * @return An <code>Listing</code> that the persitedListing relates to.
     */
    public Listing getListing(final String persistedListing) {
        logger.info("entering getListing method."
                + " persistedListing: " + persistedListing);

        String[] splitListing = persistedListing.split(DELIMINATER);

        if (splitListing.length != NUMBER_LISTING_ITEMS) {
            logger.error("Unable to parse listing: " + persistedListing);
            throw new IllegalArgumentException("Unable to parse listing: " + persistedListing);
        }

        Listing listing = new Listing();

        try {
            listing.setListingDateTime(LISTING_DATE_FORMAT.parse(splitListing[PERSISTED_POSITION_DATE]));
        } catch (ParseException ex) {
            logger.error("Unable to parse listing datetime: " + splitListing[PERSISTED_POSITION_DATE]);
            throw new IllegalArgumentException("Unable to parse listing datetime: "
                    + splitListing[PERSISTED_POSITION_DATE], ex);
        }

        TvShow listingShow = TvShow.getTvShow(splitListing[PERSISTED_POSITION_SHOW]);
        String title = getCleanEpisodeTitle(splitListing[PERSISTED_POSITION_TITLE]);
        String episodeTitle = EpisodeProperties.getInstance().getEpisodeTitle(listingShow, title);
        listing.setEpisode(getEpisodeCatalogue().getEpisode(listingShow, episodeTitle));

        listing.setChannel(Channel.getChannel(splitListing[PERSISTED_POSITION_CHANNEL]));

        logger.info("exiting getListing method.  listing: " + listing.toString());
        return listing;
    }

    /**
     * The listings are sometimes persisted with a series and episode identifier attached.
     * This takes the form of ee/nn, this method removes that.
     * @param title The title in the persisted listing.
     * @return The title with anything matching the pattern removed.
     */
    protected String getCleanEpisodeTitle(final String title) {
        String prefixPattern = "\\d+/\\d+\\s-\\s";
        return title.replaceFirst(prefixPattern, "");
    }

    /**
     * To enable unit testing.
     * @param mockEpisodeCatalogue mock EpisodeCatalogue
     */
    protected void setEpisodeCatalogue(final EpisodeCatalogue mockEpisodeCatalogue) {
        this.episodeCatalogue = mockEpisodeCatalogue;
    }

    /**
     * getter for the EpisodeCatalogue.  Used to enable catalogue to be mocked out.
     * @return the required EpisodeCatalogue implementation.
     */
    private EpisodeCatalogue getEpisodeCatalogue() {
        if (episodeCatalogue == null) {
            episodeCatalogue = EpisodeFactory.getEpisodeCatalogueInstance();
        }
        return episodeCatalogue;
    }
}
