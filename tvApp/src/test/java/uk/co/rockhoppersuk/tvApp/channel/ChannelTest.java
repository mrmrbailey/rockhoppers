/*
 * (c) Mark Bailey - www.rockhoppersuk.co.uk - 2010
 */
package uk.co.rockhoppersuk.tvApp.channel;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit test for the channel Enum.
 *
 * @author mbailey
 * @version 1.0
 */
public class ChannelTest {

    /**
     * Channel to be used in Unit Tests.
     */
    private Channel testChannel = Channel.BBC1;

    /**
     * The Channel name relating to the test channel.
     */
    private String testChannelName = "BBC1 London & South East";

    /**
     * Test of getChannelName method, of class Channel.
     */
    @Test
    public void testGetChannelName() {
        String expResult = testChannelName;
        String result = testChannel.getChannelName();
        assertEquals(expResult, result);
    }


    /**
     * Test of getChannel method, of class Channel.
     */
    @Test
    public void testGetChannel() {
        Channel expResult = testChannel;
        Channel result = Channel.getChannel(testChannelName);
        assertEquals(expResult, result);
    }


    /**
     * Test of getChannel method for unknown channel , of class Channel.
     */
    @Test
    public void testGetUnknownChannel() {
        Channel expResult = Channel.Unknown;
        Channel result = Channel.getChannel("This is an invalid lookup");
        assertEquals(expResult, result);
    }
}