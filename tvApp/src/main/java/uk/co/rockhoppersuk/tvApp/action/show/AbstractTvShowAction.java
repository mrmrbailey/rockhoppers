/*
 * (c) Mark Bailey - www.rockhoppersuk.co.uk - 2010
 */
package uk.co.rockhoppersuk.tvApp.action.show;

import com.opensymphony.xwork2.ActionSupport;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import uk.co.rockhoppersuk.tvApp.show.TvShow;

/**
 * This action is the base class responsible for presenting a List of <code>TvShow</code>
 * to the front end.
 *
 * @author mbailey
 * @version 1.0
 */
public class AbstractTvShowAction extends ActionSupport {

    /**
     * serialVersionId for keeping track of changes.
     */
    public static final long serialVersionUID = 42L;
    /**
     * log4j static variable.
     */
    private static Logger logger = Logger.getLogger(AbstractTvShowAction.class);
    /**
     * List of the <code>TvShow</code> that this system supports.
     */
    private List<String> tvShows;

    /**
     * Action to set the list of TvShow from the enum.
     * @return SUCCESS if successful.
     * @throws Exception if something bad happens
     */
    @Override
    public String execute() throws Exception {
        logger.info("entering execute method.");

        tvShows = new ArrayList<String>();
        for (TvShow shows : TvShow.values()) {
            if (!shows.equals(TvShow.Unknown)) {
                tvShows.add(shows.getTvShowName());
            }
        }

        logger.info("exiting execute method. tvShows.size: " + tvShows.size());
        return SUCCESS;
    }

    /**
     * getter for a list of the <code>TvShow</code> that this system supports.
     * @return List of the <code>TvShow</code> that this system supports.
     */
    public List<String> getTvShows() {
        return tvShows;
    }
}
