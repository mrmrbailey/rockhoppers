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
import uk.co.rockhoppersuk.tvApp.channel.Channel;
import uk.co.rockhoppersuk.tvApp.show.episode.EpisodeStatus;
import static org.junit.Assert.*;

/**
 * Unit test for the listings by channel action.
 *
 * @author mbailey
 * @version 1.0
 */
public class ListingsForChannelHubActionTest extends StrutsTestCase {

    /**
     * Test of execute method, of class ListingsByStatusHubAction.
     */
    @Test
    public void testExecute() throws Exception {
        ListingsForChannelHubAction action = new ListingsForChannelHubAction();
        String result = action.execute();
        assertTrue("Expected a success result!", ActionSupport.SUCCESS.equals(result));
    }

    /**
     * Test of getEpisodeStatis method, of class ListingsByStatusHubAction.
     */
    @Test
    public void testGetChannels() throws Exception {
        ListingsForChannelHubAction action = new ListingsForChannelHubAction();
        String result = action.execute();
        assertTrue("Expected a success result!", ActionSupport.SUCCESS.equals(result));

        List<String> channelListExpected = new ArrayList<String>();
        for (Channel channels : Channel.values()) {
            channelListExpected.add(channels.getChannelName());
        }
        List<String> channelListResult = action.getChannels();
        assertEquals(channelListExpected, channelListResult);

    }
}