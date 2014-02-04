/*
 * (c) Mark Bailey - www.rockhoppersuk.co.uk - 2011
 */
package uk.co.rockhoppersuk.tvApp.persistence.url.tvdotcom;

import java.io.BufferedReader;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import uk.co.rockhoppersuk.tvApp.show.TvShow;
import org.apache.log4j.Logger;
import uk.co.rockhoppersuk.tvApp.persistence.Reader;
import uk.co.rockhoppersuk.tvApp.persistence.url.UrlReader;
import uk.co.rockhoppersuk.tvApp.show.episode.EpisodeStatus;
import uk.co.rockhoppersuk.tvApp.show.tvdotcom.TvDotComEpisode;

/**
 * Helper class which interfaces with the tv.com website return requested data.
 *
 * @author mbailey
 * @version 1.0
 */
public class TvDotComHelper {

    /**
     * log4j static variable.
     */
    private static Logger logger = Logger.getLogger(TvDotComHelper.class);
    /**
     * To enable unit testing.
     */
    private Reader urlReader;
    /**
     * The Pattern for the tv.com URL.
     */
    private static final String URL_PATTERN =
            "http://www.tv.com/{0}/episode.html?shv=list&season=All&tag=container;episode_list_header";
    /**
     * The <code>MessageFormat</code> for the tv.com url.
     */
    private static final MessageFormat URL_FORMAT = new MessageFormat(URL_PATTERN);
    /**
     * The regex for each episode in the episode_listing table on tv.com.
     */
    private static final String EPISODE_TR_START_REGEX = "<tr class=\"episode\">";
    /**
     * The pattern for each episode in the episode_listing table on tv.com.
     */
    private static final Pattern EPISODE_TR_START_PATTERN = Pattern.compile(EPISODE_TR_START_REGEX);
    /**
     * The regex for each episode in the episode_listing table on tv.com.
     */
    private static final String EPISODE_TR_END_REGEX = "</tr>";
    /**
     * The pattern for each episode in the episode_listing table on tv.com.
     */
    private static final Pattern EPISODE_TR_END_PATTERN = Pattern.compile(EPISODE_TR_END_REGEX);
    /**
     * The regex for the start of the episode number on the full listings on tv.com.
     */
    private static final String EPISODE_NUMBER_START_REGEX = "id=\"episode_header\"";
    /**
     * The pattern for the episode number on the full listings on tv.com.
     */
    private static final Pattern EPISODE_NUMBER_START_PATTERN = Pattern.compile(EPISODE_NUMBER_START_REGEX);
    /**
     * The regex for the end of episode number on the full listings on tv.com.
     */
    private static final String EPISODE_NUMBER_END_REGEX = "</div>";
    /**
     * The pattern for the end of the episode number on the full listings on tv.com.
     */
    private static final Pattern EPISODE_NUMBER_END_PATTERN = Pattern.compile(EPISODE_NUMBER_END_REGEX);
    /**
     * The regex for the start of the episode synopsis on the full listings on tv.com.
     */
    private static final String EPISODE_SYNOPSIS_START_REGEX = "<h3>Episode Summary</h3>";
    /**
     * The pattern for the episode synopsis on the full listings on tv.com.
     */
    private static final Pattern EPISODE_SYNOPSIS_START_PATTERN = Pattern.compile(EPISODE_SYNOPSIS_START_REGEX);
    /**
     * The regex for the end of episode synopsis on the full listings on tv.com.
     */
    private static final String EPISODE_SYNOPSIS_END_REGEX = "<a class=\"CONTINUED";
    /**
     * The pattern for the end of the episode synopsis on the full listings on tv.com.
     */
    private static final Pattern EPISODE_SYNOPSIS_END_PATTERN = Pattern.compile(EPISODE_SYNOPSIS_END_REGEX);

    /**
     * Retrieves a list of <code>TvDotComEpisode</code> for the <code>TvShow</code> requested.
     * @param tvShow The <code>TvShow</code> the <code>Episode</code> are required for.
     * @return a list of <code>TvDotComEpisode</code> for the <code>TvShow</code> requested.
     **/
    public List<TvDotComEpisode> getEpisodesFromTvDotCom(final TvShow tvShow) {
        logger.info("entering getEpisodesFromTvDotCom method. tvShow: " + tvShow.getTvShowName());
        List<TvDotComEpisode> episodes = new ArrayList<TvDotComEpisode>();

        Object[] showUrl = {TvDotComProperties.getInstance().getTvDotComURL(tvShow.getTvShowName())};
        BufferedReader reader = getUrlReader().getReader(URL_FORMAT.format(showUrl));

        try {
            String episodeDetails;
            while ((episodeDetails = reader.readLine()) != null) {
                // If it is not the start of the episode skip through
                Matcher episodeTrStartMatcher = EPISODE_TR_START_PATTERN.matcher(episodeDetails);
                if (!episodeTrStartMatcher.find()) {
                    continue;
                }
                logger.info("Found the start of a row. episodes.size: " + episodes.size());
                StringBuilder episodeBuilder = new StringBuilder(episodeDetails);
                String episodeTableRow;

                //build up a string representing the TR episode.
                while ((episodeTableRow = reader.readLine()) != null) {
                    episodeBuilder.append(episodeTableRow);
                    Matcher episodeTrEndMatcher = EPISODE_TR_END_PATTERN.matcher(episodeTableRow);
                    if (episodeTrEndMatcher.find()) {
                        logger.info("Found the end of a row.");
                        break;
                    }
                }
                episodes.add(getEpisode(episodeBuilder.toString(), tvShow));
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
        logger.info("exiting getEpisodesFromTvDotCom method. episodes.size: " + episodes.size());
        return episodes;
    }

    /**
     * Method that converts a table row in the episode_listings to an <code>TvDotComEpisode</code>.
     * @param episodeRow A string of the row in the episode_listings table.
     * @param tvShow The <code>TvShow</code> this episode belongs to.
     * @return An <code>TvDotComEpisode</code> that the episodeRow relates to.
     */
    public TvDotComEpisode getEpisode(final String episodeRow, final TvShow tvShow) {
        TvDotComEpisode episode = new TvDotComEpisode();
        episode.setTvShow(tvShow);
        episode.setStatus(EpisodeStatus.unknown);

        episode.setEpisodeId(TvDotComEpisodeParser.getEpisodeId(episodeRow));
        episode.setEpisodeUrl(TvDotComEpisodeParser.getEpisodeUrl(episodeRow));
        episode.setTitle(TvDotComEpisodeParser.getEpisodeTitle(episodeRow));
        episode.setAirDate(TvDotComEpisodeParser.getEpisodeAirDate(episodeRow));
        episode.setProductionNumber(TvDotComEpisodeParser.getEpisodeProductionNumber(episodeRow));

        String episodeFullDetails = getFullEpisodeDetails(episode.getEpisodeUrl());

        episode.setEpisodeNumber(TvDotComEpisodeParser.getEpisodeNumber(episodeFullDetails));
        episode.setSynopsis(TvDotComEpisodeParser.getEpisodeSynopsis(episodeFullDetails));

        return episode;
    }

    /**
     * Method that converts the episode details into a String.
     * @param episodeUrl The URL of the episode that is required.
     * @return A String representation of the URL.
     */
    private String getFullEpisodeDetails(final String episodeUrl) {

        BufferedReader reader = getUrlReader().getReader(episodeUrl);
        StringBuilder fullEpisodeDetails = new StringBuilder();

        try {
            String episodeDetails;
            while ((episodeDetails = reader.readLine()) != null) {
                // If it is not the start of the episode skip through
                if (!EPISODE_NUMBER_START_PATTERN.matcher(episodeDetails).find()) {
                    continue;
                }
                fullEpisodeDetails.append(episodeDetails);
                String episodeNumberRow;
                //build up a string representing the number.
                while ((episodeNumberRow = reader.readLine()) != null) {
                    fullEpisodeDetails.append(episodeNumberRow);
                    if (EPISODE_NUMBER_END_PATTERN.matcher(episodeNumberRow).find()) {
                        break;
                    }
                }
                // we have the number now grap the synopsis
                while ((episodeNumberRow = reader.readLine()) != null) {
                    // If it is not the start of the episode skip through
                    if (!EPISODE_SYNOPSIS_START_PATTERN.matcher(episodeNumberRow).find()) {
                        continue;
                    }
                    fullEpisodeDetails.append(episodeNumberRow);
                    String episodeSynopsisRow;
                    while ((episodeSynopsisRow = reader.readLine()) != null) {
                        fullEpisodeDetails.append(episodeSynopsisRow);
                        if (EPISODE_SYNOPSIS_END_PATTERN.matcher(episodeSynopsisRow).find()) {
                            break;
                        }
                    }
                }
            }
        } catch (IOException ex) {
            logger.error("Something bad has happened: " + ex.getMessage());
            return fullEpisodeDetails.toString();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException ex) {
                    logger.error("Something really bad has happened: " + ex.getMessage());
                    return fullEpisodeDetails.toString();
                }
            }
        }
        logger.info("exiting getFullEpisodeDetails method.");
        return fullEpisodeDetails.toString();
    }

    /**
     * getter for the urlReaderHelper.  Used to enable helper to be mocked out.
     * @return the required UrlReaderHelper implementation.
     */
    private Reader getUrlReader() {
        if (urlReader == null) {
            urlReader = new UrlReader();
        }
        return urlReader;
    }

    /**
     * To enable unit testing.
     * @param mockUrlReader mock UrlReaderHelper
     */
    protected void setUrlReader(final Reader mockUrlReader) {
        this.urlReader = mockUrlReader;
    }
}
