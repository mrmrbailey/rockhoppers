/*
 * (c) Mark Bailey - www.rockhoppersuk.co.uk - 2011
 */
package uk.co.rockhoppersuk.tvApp.action.hub;

import com.opensymphony.xwork2.ActionSupport;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import uk.co.rockhoppersuk.tvApp.channel.Channel;

/**
 * This action is responsible for presenting a List of <code>Channel</code>
 * to the front end.
 *
 * @author mbailey
 * @version 1.0
 */
public class ListingsForChannelHubAction extends ActionSupport {

    /**
     * serialVersionId for keeping track of changes.
     */
    public static final long serialVersionUID = 42L;
    /**
     * log4j static variable.
     */
    private static Logger logger = Logger.getLogger(ListingsForChannelHubAction.class);
    /**
     * List of the <code>TvShow</code> that this system supports.
     */
    private List<String> channels;

    /**
     * Action to set the list of Channels from the enum.
     * @return SUCCESS if successful.
     * @throws Exception if something bad happens
     */
    @Override
    public String execute() throws Exception {
        logger.info("entering execute method.");

        channels = new ArrayList<String>();
        for (Channel eachChannels : Channel.values()) {
            channels.add(eachChannels.getChannelName());
        }

        logger.info("exiting execute method. statusList.size: " + channels.size());
        return SUCCESS;
    }

    /**
     * getter for a list of the <code>Channel</code> that this system supports.
     * @return List of the <code>Channel</code> that this system supports.
     */
    public List<String> getChannels() {
        return channels;
    }
}
