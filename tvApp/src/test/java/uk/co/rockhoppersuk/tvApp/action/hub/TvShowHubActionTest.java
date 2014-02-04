/*
 * (c) Mark Bailey - www.rockhoppersuk.co.uk - 2010
 */
package uk.co.rockhoppersuk.tvApp.action.hub;

import com.opensymphony.xwork2.ActionSupport;
import java.util.ArrayList;
import java.util.List;
import org.apache.struts2.StrutsTestCase;
import org.junit.Test;
import uk.co.rockhoppersuk.tvApp.show.TvShow;
import static org.junit.Assert.*;

/**
 * Unit test for the tv show hub action.
 *
 * @author mbailey
 * @version 1.0
 */
public class TvShowHubActionTest extends StrutsTestCase {

    /**
     * Test of execute method, of class TvShowHubAction.
     */
    @Test
    public void testExecute() throws Exception {
        TvShowHubAction action = new TvShowHubAction();
        String result = action.execute();
        assertTrue("Expected a success result!", ActionSupport.SUCCESS.equals(result));
    }

    /**
     * Test of getTvShows method, of class TvShowHubAction.
     */
    @Test
    public void testGetTvShows() throws Exception {
        TvShowHubAction action = new TvShowHubAction();
        String result = action.execute();
        assertTrue("Expected a success result!", ActionSupport.SUCCESS.equals(result));

        List<String> tvShowsListExpected = new ArrayList<String>();
        for (TvShow shows : TvShow.values()) {
            if (!shows.equals(TvShow.Unknown)) {
                tvShowsListExpected.add(shows.getTvShowName());
            }
        }
        List<String> tvShowsListResult = action.getTvShows();
        assertEquals(tvShowsListExpected, tvShowsListResult);

    }

}