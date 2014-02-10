/*
 * (c) Mark Bailey - www.rockhoppersuk.co.uk - 2010
 */
package uk.co.rockhoppersuk.tvApp.persistence.url.tvdotcom;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.junit.Before;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.junit.Test;
import uk.co.rockhoppersuk.tvApp.persistence.Reader;
import uk.co.rockhoppersuk.tvApp.show.TvShow;
import uk.co.rockhoppersuk.tvApp.show.episode.EpisodeStatus;
import uk.co.rockhoppersuk.tvApp.show.tvdotcom.TvDotComEpisode;

/**
 * Unit test for the episode file helper.
 *
 * @author mbailey
 * @version 1.0
 */
public class TvDotComHelperTest {

    TvDotComHelper tvDotComHelper;
    /**
     * The URL used to generate episodes
     */
    private final static String showUrl = "Test Show URL";
    /**
     * The <code>TvShow</code> used to create this test episode.
     */
    private final static TvShow testTvShow = TvShow.Fringe;
    /**
     * The id used to create this test episode.
     */
    private final static int testId = 1;
    /**
     * The title used to create this test episode.
     */
    private final static String testTitle = "Test Episode";
    /**
     * The Prod No used to create this test episode.
     */
    private final static String testProdNo = "Test Prod No";
    /**
     * The URL used to create this test episode.
     * http://www.tv.com/fringe/amber-31422/episode/1360825/summary.html?tag=ep_list;summary
     */
    private final static String testURL = "http://www.tv.com/fringe/the-firefly/episode/1367451/summary.html";
    /**
     * The air date used to create this test episode.
     */
    private final static String testAirDateString = "11/4/2010";
    /**
     * The status used to create this test episode.
     */
    private final static EpisodeStatus testStatus = EpisodeStatus.unknown;
    /**
     * The Series and Episode Number used to create this test episode.
     */
    private final static String testEpisodeNumber = "Test EpisodeNumber";
    /**
     * The synopsis  used to create this test episode.
     */
    private final static String testSynopsis = "Test Synopsis";

    @Before
    public void setUp() {
        tvDotComHelper = new TvDotComHelper();
        tvDotComHelper.setUrlReader(new TvDotComTestReader());
    }

    /**
     * Test of getEpisode method, of class EpisodeFileHelper.
     */
    @Test
    public void testGetEpisode() {
        TvDotComEpisode expResult = getTestEpisode();
        TvDotComEpisode result = tvDotComHelper.getEpisode(episodeText(), testTvShow);
        System.out.println(episodeText());
        System.out.println(result.getEpisodeId());
        System.out.println(result.getAirDate());
        System.out.println(result.getEpisodeNumber());
        System.out.println(result.getEpisodeTitle());
        System.out.println(result.getEpisodeUrl());
        System.out.println(result.getProductionNumber());
        System.out.println(result.getStatus());
        System.out.println(result.getSynopsis());
        System.out.println(result.getTvShow());
        System.out.println(result.getEpisodeId());

        assertEquals(expResult, result);
    }

    /**
     * getter for the episode under test.
     * @return the Episode under test.
     */
    private TvDotComEpisode getTestEpisode() {
        TvDotComEpisode testEpisode = new TvDotComEpisode();
        testEpisode.setTvShow(testTvShow);
        testEpisode.setTitle(testTitle);
        testEpisode.setStatus(testStatus);
        testEpisode.setAirDate(getTestAirDate());
        testEpisode.setEpisodeNumber(testEpisodeNumber);
        testEpisode.setSynopsis(testSynopsis);
        testEpisode.setProductionNumber(testEpisodeNumber);
        testEpisode.setEpisodeId(testId);
        testEpisode.setProductionNumber(testProdNo);
        testEpisode.setEpisodeUrl(testURL);
        return testEpisode;
    }

    /**
     * getter for the air date that is used in testing.
     * @return The air date that is used in testing.
     */
    private Date getTestAirDate() {
        TimeZone.setDefault(TimeZone.getTimeZone("Europe/London"));
        GregorianCalendar calendar = new GregorianCalendar(2010, 3, 11);
        return new Date(calendar.getTimeInMillis());
    }

    /**
     * A episodeText is built up so it looks like the TR on tv.com.
     * @return
     */
    private String episodeText() {
        StringBuilder sb = new StringBuilder();
        sb.append("<tr class=\"episode\">");
        sb.append("<td class=\"number\">");
        sb.append(testId);
        sb.append("</td>");
        sb.append("<td class=\"title\">");
        sb.append("<a href=\"" + testURL + "\">" + testTitle + "</a>");
        sb.append("</td>");
        sb.append("<td class=\"videos\">");
        sb.append("&#150;");
        sb.append("</td>");
        sb.append("<td class=\"air_date\">");
        sb.append(testAirDateString);
        sb.append("</td>");
        sb.append("<td class=\"prod_no\">" + testProdNo + "</td>");
        sb.append("<td class=\"score\">");
        sb.append("9.5            </td>");
        sb.append("</tr>");

        return sb.toString();
    }

    /**
     * Rather than reading the data from a URL this uses the files in the filesystem
     * to allow for better testing.
     */
    private class TvDotComTestReader implements Reader {

        private static final String TV_DOT_COM_PACKAGE = "uk/co/rockhoppersuk/tvApp/persistence/url/";

        public BufferedReader getReader(String tvShowURL) {


            String resourceName = TV_DOT_COM_PACKAGE + getFileName(tvShowURL);
            return new BufferedReader(new InputStreamReader(
                    this.getClass().getClassLoader().getResourceAsStream(resourceName)));
        }

        private String getFileName(String tvShowURL) {
            String episodeUrlRegex = "http://www.tv.com/((\\w)*-)*(\\w)+/((\\w)*-)*(\\w)+/episode/\\d+/summary.html";
            final Pattern episodeUrlPattern = Pattern.compile(episodeUrlRegex);
            final Matcher episodeUrlMatcher = episodeUrlPattern.matcher(tvShowURL);

            if (!episodeUrlMatcher.matches()) {
                return tvShowURL;
            }
            
            String[] splitURL = tvShowURL.split("/");
            return splitURL[4] + ".txt";
        }
    }
}