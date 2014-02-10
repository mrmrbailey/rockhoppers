/*
 * (c) Mark Bailey - www.rockhoppersuk.co.uk - 2010
 */
package uk.co.rockhoppersuk.tvApp.persistence.url.tvdotcom;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit test for the episode parser utils.
 *
 * @author mbailey
 * @version 1.0
 */
public class TvDotComEpisodeParserTest {

    /**
     * Test of getEpisodeId method, of class TvDotComEpisodeParser.
     */
    @Test
    public void testGetEpisodeId() {
        int expResult = 53;
        String patternStart = "<td class=\"number\">";
        String patternEnd = "</td>";

        // Test against the sample string.
        int result = TvDotComEpisodeParser.getEpisodeId(getSampleEpisodeRow());
        assertEquals("Test with SampleEpisodeRow", expResult, result);

        // Simple test with just the pattern string to match.
        String data = patternStart + expResult + patternEnd;
        result = TvDotComEpisodeParser.getEpisodeId(data);
        assertEquals("Test with just the pattern string", expResult, result);

        // Test with the pattern repeated after the string.
        data = patternStart + expResult + patternEnd + expResult + patternEnd;
        result = TvDotComEpisodeParser.getEpisodeId(data);
        assertEquals("Test with the pattern repeated after", expResult, result);

        // Test with the pattern repeated before the string.
        data = expResult + patternStart + expResult + patternEnd + expResult + patternEnd;
        result = TvDotComEpisodeParser.getEpisodeId(data);
        assertEquals("Test with the pattern repeated after", expResult, result);

        // Test without the pattern.
        data = "";
        result = TvDotComEpisodeParser.getEpisodeId(data);
        assertEquals("Test with invalid data", 0, result);
    }

    /**
     * Test of getEpisodeTitle method, of class TvDotComEpisodeParser.
     */
    @Test
    public void testGetEpisodeTitle() {
        String expResult = "The Firefly";
        String patternStart = ">";
        String patternEnd = "</a>";

        // Test against the sample string.
        String result = TvDotComEpisodeParser.getEpisodeTitle(getSampleEpisodeRow());
        assertEquals("Test with SampleEpisodeRow", expResult, result);

        // Simple test with just the pattern string to match.
        String data = patternStart + expResult + patternEnd;
        result = TvDotComEpisodeParser.getEpisodeTitle(data);
        assertEquals("Test with just the pattern string", expResult, result);

        // Test with the pattern repeated after the string.
        data = patternStart + expResult + patternEnd + expResult + patternEnd;
        result = TvDotComEpisodeParser.getEpisodeTitle(data);
        assertEquals("Test with the pattern repeated after", expResult, result);

        // Test with the pattern repeated before the string.
        data = expResult + patternStart + expResult + patternEnd + expResult + patternEnd;
        result = TvDotComEpisodeParser.getEpisodeTitle(data);
        assertEquals("Test with the pattern repeated after", expResult, result);

        // Test without the pattern.
        data = expResult;
        result = TvDotComEpisodeParser.getEpisodeTitle(data);
        assertNull("Test with invalid data", result);
    }

    /**
     * Test of getEpisodeUrl method, of class TvDotComEpisodeParser.
     */
    @Test
    public void testGetEpisodeUrl() {
        String expResult = "http://www.tv.com/fringe/the-firefly/episode/1367451/summary.html";

        // Test against the sample string.
        String result = TvDotComEpisodeParser.getEpisodeUrl(getSampleEpisodeRow());
        assertEquals("Test with SampleFullEpisode", expResult, result);

        // Simple test with just the pattern string to match.
        String data = expResult;
        result = TvDotComEpisodeParser.getEpisodeUrl(data);
        assertEquals("Test with just the pattern string", expResult, result);

        // Test with some text around the pattern string to match.
        data = "some text around " + expResult + " the exp Text";
        result = TvDotComEpisodeParser.getEpisodeUrl(data);
        assertEquals("Test with some text around the pattern string", expResult, result);

        // Test without the pattern.
        data = expResult.replaceAll("/", "");
        result = TvDotComEpisodeParser.getEpisodeUrl(data);
        assertNull("Test with invalid data - No /", result);

        // Test without the pattern.
        data = expResult.replaceAll("\\d", "");
        result = TvDotComEpisodeParser.getEpisodeUrl(data);
        assertNull("Test with invalid data - No Numbers", result);
    }

    /**
     * Test of getEpisodeAirDate method, of class TvDotComEpisodeParser.
     */
    @Test
    public void testGetEpisodeAirDate() {
        Date expResult = getTestAirDate();
        String expAirDate = "1/21/2011";

        // Test against the sample string.
        Date result = TvDotComEpisodeParser.getEpisodeAirDate(getSampleEpisodeRow());
        assertEquals("Test with SampleEpisodeRow", expResult, result);

        // Simple test with just the pattern string to match.
        String data = expAirDate;
        result = TvDotComEpisodeParser.getEpisodeAirDate(data);
        assertEquals("Test with just the pattern string", expResult, result);

        // Test with some text around the pattern string to match.
        data = "some text around " + expAirDate + " the exp Text";
        result = TvDotComEpisodeParser.getEpisodeAirDate(data);
        assertEquals("Test with some text around the pattern string", expResult, result);

        // Test without the pattern.
        data = expAirDate.replaceAll("/", "");
        result = TvDotComEpisodeParser.getEpisodeAirDate(data);
        assertNull("Test with invalid data - /", result);

        // Test without the pattern.
        data = expAirDate.replaceAll("\\d", "");
        result = TvDotComEpisodeParser.getEpisodeAirDate(data);
        assertNull("Test with invalid data - No Numbers", result);
    }

    /**
     * Test of getEpisodeProductionNumber method, of class TvDotComEpisodeParser.
     */
    @Test
    public void testGetEpisodeProductionNumber() {

        String patternStart = "<td class=\"prod_no\">";
        String patternEnd = "</td>";
        String expResult = "3X6110";

        // Test against the sample string.
        String result = TvDotComEpisodeParser.getEpisodeProductionNumber(getSampleEpisodeRow());
        assertEquals("Test with SampleEpisodeRow", expResult, result);

        // Simple test with just the pattern string to match.
        String data = patternStart + expResult + patternEnd;
        result = TvDotComEpisodeParser.getEpisodeProductionNumber(data);
        assertEquals("Test with just the pattern string", expResult, result);

        // Test with the pattern repeated after the string.
        data = patternStart + expResult + patternEnd + expResult + patternEnd;
        result = TvDotComEpisodeParser.getEpisodeProductionNumber(data);
        assertEquals("Test with the pattern repeated after", expResult, result);

        // Test with the pattern repeated before the string.
        data = expResult + patternStart + expResult + patternEnd + expResult + patternEnd;
        result = TvDotComEpisodeParser.getEpisodeProductionNumber(data);
        assertEquals("Test with the pattern repeated after", expResult, result);

        // Test without the pattern.
        data = expResult;
        result = TvDotComEpisodeParser.getEpisodeProductionNumber(data);
        assertNull("Test with invalid data", result);
    }

    /**
     * Test of getEpisodeNumber method, of class TvDotComEpisodeParser.
     */
    @Test
    public void testGetEpisodeNumber() {
        String expResult = "Season 3, Episode 10";

        // Test against the sample string.
        String result = TvDotComEpisodeParser.getEpisodeNumber(getSampleFullEpisode());
        assertEquals("Test with SampleFullEpisode", expResult, result);

        // Simple test with just the pattern string to match.
        String data = expResult;
        result = TvDotComEpisodeParser.getEpisodeNumber(data);
        assertEquals("Test with just the pattern string", expResult, result);

        // Test with some text around the pattern string to match.
        data = "some text around " + expResult + " the exp Text";
        result = TvDotComEpisodeParser.getEpisodeNumber(data);
        assertEquals("Test with some text around the pattern string", expResult, result);

        // Test without the pattern.
        data = expResult.replaceAll(",", "");
        result = TvDotComEpisodeParser.getEpisodeNumber(data);
        assertNull("Test with invalid data - No Comma", result);

        // Test without the pattern.
        data = expResult.replaceAll("\\d", "");
        result = TvDotComEpisodeParser.getEpisodeNumber(data);
        assertNull("Test with invalid data - No Numbers", result);
    }

    /**
     * Test of getEpisodeSynopsis method, of class TvDotComEpisodeParser.
     */
    @Test
    public void testGetEpisodeSynopsis() {
        String patternStart = "<p>";
        String patternEnd = "<";
        String expResult = "The Observer reunites musician Roscoe Joyce with his dead son, setting off a series of events that culminate in a life-or-death test for Walter. Meanwhile, Olivia deals with the repercussions of her absence from This Side, and Walter bonds with Joyce, one of his musical heroes.";

        // Test against the sample string.
        String result = TvDotComEpisodeParser.getEpisodeSynopsis(getSampleFullEpisode());
        assertEquals("Test with SampleFullEpisode", expResult, result);

        // Simple test with just the pattern string to match.
        String data = patternStart + expResult + patternEnd;
        result = TvDotComEpisodeParser.getEpisodeSynopsis(data);
        assertEquals("Test with just the pattern string", expResult, result);

        // Test with the pattern repeated after the string.
        data = patternStart + expResult + patternEnd + expResult + patternEnd;
        result = TvDotComEpisodeParser.getEpisodeSynopsis(data);
        assertEquals("Test with the pattern repeated after", expResult, result);

        // Test with the pattern repeated before the string.
        data = expResult + patternStart + expResult + patternEnd + expResult + patternEnd;
        result = TvDotComEpisodeParser.getEpisodeSynopsis(data);
        assertEquals("Test with the pattern repeated after", expResult, result);

        // Test without the pattern.
        data = expResult;
        result = TvDotComEpisodeParser.getEpisodeSynopsis(data);
        assertNull("Test with invalid data", result);
    }

    /**
     * getter for the air date that is used in testing.
     * @return The air date that is used in testing.
     */
    private Date getTestAirDate() {
        TimeZone.setDefault(TimeZone.getTimeZone("Europe/London"));
        GregorianCalendar calendar = new GregorianCalendar(2011, 0, 21);
        return new Date(calendar.getTimeInMillis());
    }

    /**
     * Returns a sample row based on tv.com's Fringe episode The Firefly.
     * <ul>
     * <li>Id - 53</li>
     * <li>Title - The Firefly</li>
     * <li>Url - http://www.tv.com/fringe/the-firefly/episode/1367451/summary.html/li>
     * <li>Air Date - 21st Jan 2011</li>
     * <li>Production Code - 3X6110</li>
     * </ul>
     * @return a sample row.
     */
    private String getSampleEpisodeRow() {
        StringBuilder sb = new StringBuilder();
        sb.append("        <tr class=\"episode\"> ");
        sb.append("            <td class=\"number\"> ");
        sb.append("                53");
        sb.append("            </td> ");
        sb.append("            <td class=\"title\"> ");
        sb.append("                <a href=\"http://www.tv.com/fringe/the-firefly/episode/1367451/summary.html?tag=ep_list;summary\">The Firefly</a> ");
        sb.append("            </td> ");
        sb.append("            <td class=\"videos\"> ");
        sb.append("                                    <a class=\"offsite\" target=\"_blank\"  href=\"http://www.videosurf.com/webui/inc/go.php?redirect=http%3A%2F%2Fwww.fox.com%2Ffringe%2Ffull-episodes%2F757649365001%2Fthe-firefly&amp;client_id=tv.com\">Full&nbsp;Episode</a> ");
        sb.append("                            </td> ");
        sb.append("            <td class=\"air_date\"> ");
        sb.append("                                    1/21/2011");
        sb.append("                            </td> ");
        sb.append("            <td class=\"prod_no\">3X6110</td> ");
        sb.append("            <td class=\"score\"> ");
        sb.append("                9.5            </td> ");
        sb.append("                    </tr> ");
        return sb.toString();
    }

    /**
     * Returns a sample Full episode taken from tv.com for Fringe and is The Firefly.
     * <ul>
     * <li>Episode Number - Season 3, Episode 10</li>
     * <li>Synopsis - The Observer reunites musician Roscoe Joyce with his dead son, setting off a series of events that culminate in a life-or-death test for Walter. Meanwhile, Olivia deals with the repercussions of her absence from This Side, and Walter bonds with Joyce, one of his musical heroes.</li>
     * </ul>
     * @return a sample Full episode.
     */
    private String getSampleFullEpisode() {
        StringBuilder sb = new StringBuilder();
        sb.append("<div class=\"MODULE topslot\" id=\"episode_header\" >        ");
        sb.append("<div class=\"crumbs\">        ");
        sb.append("<a href=\"http://www.tv.com/fringe/show/75146/episode.html?tag=episode_header;episode\">Episode Guide</a>        ");
        sb.append("&gt;        ");
        sb.append("<span>                            Season 3, Episode 10                    </span>    ");
        sb.append("</div>            <h3>Episode Summary</h3>             ");
        sb.append("<a class=\"SEE_MORE login_required\" href=\"http://www.tv.com/usersubmission/episode_synopsis.html?show_id=75146&episode_id=1367451/\">Add/Edit</a>         ");
        sb.append("</div>                     );");
        sb.append("<p>The Observer reunites musician Roscoe Joyce with his dead son, setting off a series of events that culminate in a life-or-death test for Walter. Meanwhile, Olivia deals with the repercussions of her absence from This Side, and Walter bonds with Joyce, one of his musical heroes.</p>                             ");
        sb.append("<a class=\"CONTINUED\" href=\"http://www.tv.com/fringe/the-firefly/episode/1367451/recap.html?tag=episode_recap;recap\">Read Full Recap &raquo;</a> ");

        return sb.toString();
    }
}