/*
 * (c) Mark Bailey - www.rockhoppersuk.co.uk - 2011
 */
package uk.co.rockhoppersuk.tvApp.action.listing;

import java.util.Collections;
import org.apache.log4j.Logger;
import uk.co.rockhoppersuk.tvApp.channel.Channel;

/**
 * This action responsible for presenting a <code>Listing</code> for the given channel
 * to the front end.
 *
 * @author mbailey
 * @version 1.0
 */
public class ViewListingForChannelAction extends AbstractViewListingAction {
    /**
     * serialVersionId for keeping track of changes.
     */
    public static final long serialVersionUID = 42L;
    /**
     * log4j static variable.
     */
    private static Logger logger = Logger.getLogger(ViewListingForChannelAction.class);
    /**
     * The name of the <code>Channel</code> to be presented.
     */
    private String channel;

    /**
     * For subclasses to implement the execute() method.
     * @return SUCCESS if successful.
     * @throws Exception Exception if something bad happens
     */
    @Override
    public String execute() throws Exception {
        logger.info("entering execute method. channel: " + channel);

        setListings(getListingCatalogue().getListingsForChannel(Channel.getChannel(channel)));
        Collections.reverse(getListings());

        logger.info("exiting execute method. listings.size: " + getListings().size());
        return SUCCESS;
    }

    /**
     * getter for the <code>EpisodeStatus</code>.
     * @return the <code>EpisodeStatus</code>.
     */
    public String getChannel() {
        return channel;
    }

    /**
     * setter for the <code>Channel</code>.
     * @param channel the <code>Channel</code>.
     */
    public void setChannel(final String channel) {
        this.channel = channel;
    }
}
