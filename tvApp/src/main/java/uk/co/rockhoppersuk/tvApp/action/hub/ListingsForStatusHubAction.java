/*
 * (c) Mark Bailey - www.rockhoppersuk.co.uk - 2010
 */
package uk.co.rockhoppersuk.tvApp.action.hub;

import com.opensymphony.xwork2.ActionSupport;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.log4j.Logger;
import uk.co.rockhoppersuk.tvApp.show.episode.EpisodeStatus;

/**
 * This action is responsible for presenting a List of <code>EpisodeStatus</code>
 * to the front end.
 *
 * @author mbailey
 * @version 1.0
 */

public class ListingsForStatusHubAction  extends ActionSupport {

    /**
     * serialVersionId for keeping track of changes.
     */
    public static final long serialVersionUID = 42L;
    /**
     * log4j static variable.
     */
    private static Logger logger = Logger.getLogger(ListingsForStatusHubAction.class);
    /**
     * List of the <code>TvShow</code> that this system supports.
     */
    private List<EpisodeStatus> statuses;

    /**
     * Action to set the list of EpisodeStatus from the enum.
     * @return SUCCESS if successful.
     * @throws Exception if something bad happens
     */
    @Override
    public String execute() throws Exception {
        logger.info("entering execute method.");

        statuses = new ArrayList<EpisodeStatus>();
        statuses.addAll(Arrays.asList(EpisodeStatus.values()));

        logger.info("exiting execute method. statusList.size: " + statuses.size());
        return SUCCESS;
    }

    /**
     * getter for a list of the <code>EpisodeStatus</code> that this system supports.
     * @return List of the <code>EpisodeStatus</code> that this system supports.
     */
    public List<EpisodeStatus> getEpisodeStatuses() {
        return statuses;
    }
}
