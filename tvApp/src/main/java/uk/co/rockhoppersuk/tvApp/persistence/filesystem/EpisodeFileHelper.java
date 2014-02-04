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
import java.util.List;
import org.apache.log4j.Logger;
import uk.co.rockhoppersuk.file.FileUtils;
import uk.co.rockhoppersuk.tvApp.show.TvShow;
import uk.co.rockhoppersuk.tvApp.show.episode.Episode;
import uk.co.rockhoppersuk.tvApp.show.episode.EpisodeStatus;

/**
 * Helper class which interfaces with the file system to return requested data.
 *
 * <p>
 *
 * An <code>Episode</code> is persisted in a filename that is named after the
 * <code>TvShow</code> and is "~" delimited and contains the following fields
 * <ul>
 *   <li>Title</li>
 *   <li><code>EpisodeStatus</code></li>
 *   <li>Worldwide AirDate</li>
 *   <li>Series and Episode number</li>
 *   <li>Synopsis</li>
 * </ul>
 *
 * @author mbailey
 * @version 1.0
 */
public class EpisodeFileHelper {

    /**
     * Position of the Title in the persisted file.
     */
    private static final int PERSISTED_POSITION_TITLE = 0;
    /**
     * Position of the <code>EpisodeStatus</code> in the persisted file.
     */
    private static final int PERSISTED_POSITION_EPISODE_STATUS = 1;
    /**
     * Position of the worldwide air date in the persisted file.
     */
    private static final int PERSISTED_POSITION_AIRDATE = 2;
    /**
     * Position of the Series and Episode number in the persisted file.
     */
    private static final int PERSISTED_POSITION_EPISODE_NUMBER = 3;
    /**
     * Position of the synopsis in the persisted file.
     */
    private static final int PERSISTED_POSITION_SYNOPSIS = 4;
    /**
     * The number of delimited items in the episode file.
     */
    private static final int NUMBER_EPISODE_ITEMS = 5;
    /**
     * The delimiter of the episode file.
     */
    private static final String DELIMINATER = "~";
    /**
     * The date format of the aired date in the episode file.
     */
    private static final String DATE_FORMAT = "dd MMMM yyyy";
    /**
     * The <code>SimpleDateFormat</code> for the episode air date.
     */
    private static final SimpleDateFormat EPISODE_AIRDATE_FORMAT = new SimpleDateFormat(EpisodeFileHelper.DATE_FORMAT);
    /**
     * The file path of the episode file.
     */
    private static final String EPISODE_PATH = "src/main/resources/";
    /**
     * The file path of the episode file.
     */
    private static final String EPISODE_PACKAGE = "uk/co/rockhoppersuk/tvApp/shows/";
    /**
     * The file type of the episode file.
     */
    private static final String EPISODE_FILETYPE = ".txt";
    /**
     * log4j static variable.
     */
    private static Logger logger = Logger.getLogger(EpisodeFileHelper.class);

    /**
     * Retrieves a list of <code>Episodes</code> for the <code>TvShow</code> requested.
     * @param tvShow The <code>TvShow</code> the <code>Episode</code> are required for.
     * @return a list of <code>Episodes</code> for the <code>TvShow</code> requested.
     */
    protected List<Episode> getEpisodesForTvShowFromFileSystem(final TvShow tvShow) {
        logger.info("entering getEpisodesForTvShowFromFileSystem method. tvShow: " + tvShow.getTvShowName());

        List<Episode> episodes = new ArrayList<Episode>();
        String fileName = EPISODE_PATH + EPISODE_PACKAGE + tvShow.getTvShowName() + EPISODE_FILETYPE;

        if (!FileUtils.isFileExist(fileName)) {
            logger.warn("Episode File not found: " + fileName);
            return episodes;
        }

        String resourceName = EPISODE_PACKAGE + tvShow.getTvShowName() + EPISODE_FILETYPE;
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(
                    this.getClass().getClassLoader().getResourceAsStream(resourceName)));
            String episodeDetails = null;
            while ((episodeDetails = reader.readLine()) != null) {
                episodes.add(getEpisode(episodeDetails, tvShow));
            }
        } catch (IOException ex) {
            logger.error("Something bad has happened: " + ex.getMessage());
            return episodes;
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException ex) {
                    logger.error("Something really bad has happened: " + ex.getMessage());
                    return episodes;
                }
            }
        }

        logger.info("exiting getEpisodesForTvShowFromFileSystem method. episodes.size: " + episodes.size());
        return episodes;
    }

    /**
     * Method that converts a line in the file system to an <code>Episode</code>.
     * @param persistedEpisode A "~" Delimited persisted Episode.
     * @param tvShow The <code>TvShow</code> this episode belongs to.
     * @return An <code>Episode</code> that the persitedEpisode relates to.
     */
    public Episode getEpisode(final String persistedEpisode, final TvShow tvShow) {
        logger.info("entering getEpisode method."
                + " tvShow: " + tvShow.getTvShowName()
                + " persistedEpisode: " + persistedEpisode);
        String[] splitEpisode = persistedEpisode.split(DELIMINATER);

        if (splitEpisode.length != NUMBER_EPISODE_ITEMS) {
            logger.error("Unable to parse episode: " + persistedEpisode);
            throw new IllegalArgumentException("Unable to parse episode: " + persistedEpisode);
        }

        Episode episode = new Episode();
        episode.setTvShow(tvShow);
        episode.setTitle(splitEpisode[PERSISTED_POSITION_TITLE]);
        episode.setStatus(EpisodeStatus.valueOf(splitEpisode[PERSISTED_POSITION_EPISODE_STATUS]));

        try {
            episode.setAirDate(EPISODE_AIRDATE_FORMAT.parse(splitEpisode[PERSISTED_POSITION_AIRDATE]));
        } catch (ParseException ex) {
            logger.error("Unable to parse airDate: " + splitEpisode[PERSISTED_POSITION_AIRDATE]);
            throw new IllegalArgumentException(
                    "Unable to parse airDate: " + splitEpisode[PERSISTED_POSITION_AIRDATE], ex);
        }

        episode.setEpisodeNumber(splitEpisode[PERSISTED_POSITION_EPISODE_NUMBER]);
        episode.setSynopsis(splitEpisode[PERSISTED_POSITION_SYNOPSIS]);

        logger.info("exiting getEpisode method.  episodeTitle: " + episode.getEpisodeTitle());
        return episode;
    }
}
