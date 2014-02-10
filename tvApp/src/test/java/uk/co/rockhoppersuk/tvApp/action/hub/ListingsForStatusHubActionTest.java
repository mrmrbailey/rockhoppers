/*
 * (c) Mark Bailey - www.rockhoppersuk.co.uk - 2010
 */
package uk.co.rockhoppersuk.tvApp.action.hub;

import com.opensymphony.xwork2.ActionSupport;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.struts2.StrutsTestCase;
import org.junit.Test;
import uk.co.rockhoppersuk.tvApp.show.TvShow;
import uk.co.rockhoppersuk.tvApp.show.episode.EpisodeStatus;
import static org.junit.Assert.*;

/**
 * Unit test for the listings by status action.
 *
 * @author mbailey
 * @version 1.0
 */
public class ListingsForStatusHubActionTest extends StrutsTestCase {

    /**
     * Test of execute method, of class ListingsByStatusHubAction.
     */
    @Test
    public void testExecute() throws Exception {
        ListingsForStatusHubAction action = new ListingsForStatusHubAction();
        String result = action.execute();
        assertTrue("Expected a success result!", ActionSupport.SUCCESS.equals(result));
    }

    /**
     * Test of getEpisodeStatis method, of class ListingsByStatusHubAction.
     */
    @Test
    public void testGetEpisodeStatis() throws Exception {
        ListingsForStatusHubAction action = new ListingsForStatusHubAction();
        String result = action.execute();
        assertTrue("Expected a success result!", ActionSupport.SUCCESS.equals(result));

        List<EpisodeStatus> statusListExpected = new ArrayList<EpisodeStatus>();
        statusListExpected.addAll(Arrays.asList(EpisodeStatus.values()));
        List<EpisodeStatus> statusListListResult = action.getEpisodeStatuses();
        assertEquals(statusListExpected, statusListListResult);

    }
}