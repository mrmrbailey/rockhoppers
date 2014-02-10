/*
 * (c) Mark Bailey - www.rockhoppersuk.co.uk - 2011
 */
package uk.co.rockhoppersuk.tvApp.persistence.url.tvdotcom;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.log4j.Logger;

/**
 * Utility class which parses the source from the tv.com website
 * to return sensible episode data.
 *
 * @author mbailey
 * @version 1.0
 */
public final class TvDotComEpisodeParser {

    /**
     * log4j static variable.
     */
    private static Logger logger = Logger.getLogger(TvDotComEpisodeParser.class);

    /**
     * Utility class should not be instantiated.
     */
    private TvDotComEpisodeParser() {
        logger.error("Attempting to instantiate Utility Class");
        throw new UnsupportedOperationException(
                "Utility Class should not be instantiated.");
    }

    /**
     * Parses the data and returns the id of the <code>TvDotComEpisode</code>.
     * @param data A string of the row in the episode_listings table.
     * @return The Id of the <code>TvDotComEpisode</code>.
     */
    protected static int getEpisodeId(final String data) {
        logger.info("Entering getEpisodeId");
        final String episodeIdStart = "<td class=\"number\">";
        final String episodeIdEnd = "</td>";
        final String episodeIdRegex = episodeIdStart + "\\s*\\d+\\s*" + episodeIdEnd;

        String matchedId = getRegexFromString(episodeIdRegex, data);
        if (matchedId == null) {
            logger.warn("Unable to find EpisodeId");
            return 0;
        }
        matchedId = matchedId.replace(episodeIdStart, "").replace(episodeIdEnd, "");
        Integer episodeId = Integer.valueOf(getCleanParsedString(matchedId));

        logger.info("Found Episode Id: " + episodeId);
        return episodeId.intValue();
    }

    /**
     * Parses the data and returns the title of the <code>TvDotComEpisode</code>.
     * @param data A string of the row in the episode_listings table.
     * @return The title of the <code>TvDotComEpisode</code>.
     */
    protected static String getEpisodeTitle(final String data) {
        logger.info("Entering getEpisodeTitle");
        final String episodeTitleStart = ">";
        final String episodeTitleEnd = "</a>";
        final String episodeTitleRegex = episodeTitleStart + "[\\w+\\s]*" + episodeTitleEnd;

        String matchedTitle = getRegexFromString(episodeTitleRegex, data);
        if (matchedTitle == null) {
            logger.warn("Unable to find Episode Title");
            return null;
        }
        matchedTitle = matchedTitle.replace(episodeTitleEnd, "").replace(episodeTitleStart, "");
        String episodeTitle = getCleanParsedString(matchedTitle);

        logger.info("Found Episode title: " + episodeTitle);
        return episodeTitle;
    }

    /**
     * Parses the data and returns the url of the <code>TvDotComEpisode</code>.
     * @param data A string of the row in the episode_listings table.
     * @return The url of the <code>TvDotComEpisode</code>.
     */
    protected static String getEpisodeUrl(final String data) {
        logger.info("Entering getEpisodeUrl");
        String episodeUrlRegex = "http://www.tv.com/((\\w)*-)*(\\w)+/((\\w)*-)*(\\w)+/episode/\\d+/summary.html";

        String matchedUrl = getRegexFromString(episodeUrlRegex, data);

        logger.info("Found Episode url: " + matchedUrl);
        return matchedUrl;
    }

    /**
     * Parses the data and returns the air date of the <code>TvDotComEpisode</code>.
     * @param data A string of the row in the episode_listings table.
     * @return The air date of the <code>TvDotComEpisode</code>.
     */
    protected static Date getEpisodeAirDate(final String data) {
        logger.info("Entering getEpisodeAirDate");
        final String episodeAirDateRegex = "\\d{1,2}/\\d{1,2}/\\d{4}";

        String matchedAirDate = getRegexFromString(episodeAirDateRegex, data);

        if (matchedAirDate == null) {
            logger.warn("Unable to find Air Date");
            return null;
        }

        logger.info("Found Episode AirDate: " + matchedAirDate);

        String airDatePattern = "M/d/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(airDatePattern);
        try {
            return sdf.parse(matchedAirDate);
        } catch (ParseException ex) {
            logger.error("Unable to parse air date: " + matchedAirDate);
            throw new IllegalArgumentException("Unable to parse air date: " + matchedAirDate, ex);
        }
    }

    /**
     * Parses the data and returns the prod no of the <code>TvDotComEpisode</code>.
     * @param data A string of the row in the episode_listings table.
     * @return The prod no of the <code>TvDotComEpisode</code>.
     */
    protected static String getEpisodeProductionNumber(final String data) {
        logger.info("Entering getEpisodeProductionNumber");
        final String episodeProdNoStart = "<td class=\"prod_no\">";
        final String episodeProdNoEnd = "</td>";
        final String episodeProdNoRegex = episodeProdNoStart + "[\\w+\\s]*" + episodeProdNoEnd;

        String matchedProdNo = getRegexFromString(episodeProdNoRegex, data);
        if (matchedProdNo == null) {
            logger.warn("Unable to find Production Number");
            return null;
        }
        matchedProdNo = matchedProdNo.replace(episodeProdNoEnd, "").replace(episodeProdNoStart, "");

        String episodeProdNo = getCleanParsedString(matchedProdNo);
        logger.info("Found Episode production number: " + episodeProdNo);
        return episodeProdNo;
    }

    /**
     * Parses the data and returns the episode number of the <code>TvDotComEpisode</code>.
     * @param data A string of the row in the episode_listings table.
     * @return The episode number of the <code>TvDotComEpisode</code>.
     */
    protected static String getEpisodeNumber(final String data) {
        logger.info("Entering getEpisodeNumber");
        final String episodeNumberRegex = "Season \\d+, Episode \\d+";

        String episodeNumber = getRegexFromString(episodeNumberRegex, data);

        logger.info("Found Episode Number: " + episodeNumber);
        return episodeNumber;
    }

    /**
     * Parses the data and returns the synopsis of the <code>TvDotComEpisode</code>.
     * @param data A string of the row in the episode_listings table.
     * @return The synopsis of the <code>TvDotComEpisode</code>.
     */
    protected static String getEpisodeSynopsis(final String data) {
        logger.info("Entering getEpisodeSynopsis");
        final String episodeSynopsisStart = "<p>";
        final String episodeSynopsisRegex = episodeSynopsisStart + "[^<]*";

        String synopsis = getRegexFromString(episodeSynopsisRegex, data);
        if (synopsis == null) {
            logger.warn("Unable to find Synopsis");
            return null;
        }
        synopsis = getCleanParsedString(synopsis.substring(episodeSynopsisStart.length()));

        logger.info("Found synopsis: " + synopsis);
        return synopsis;
    }

    /**
     * Parses the data parsed in for the string represented by the regex.
     * @param regEx The regEx of the string.
     * @param data The data that may or may not contain the pattern
     * @return the String between the start and end pattern.
     */
    private static String getRegexFromString(final String regEx, final String data) {

        logger.info("Parsing " + data);

        final Matcher matcher = Pattern.compile(regEx).matcher(data);

        if (!matcher.find()) {
            logger.warn("Unable to match " + regEx + " to: " + data);
            return null;
        }

        //Take the sub string matched start and end.
        String matchedString = data.substring(matcher.start(), matcher.end());
        logger.info("Matched String: " + matchedString);
        return matchedString;
    }

    /**
     * There may be some cleaning that is required.
     * @param matchedString The string that requires cleaning.
     * @return A Cleaned string.
     */
    private static String getCleanParsedString(final String matchedString) {
        return matchedString.trim().replaceAll("\\b\\s(2,)\\b", " ");
    }
}
