/*
 * (c) Mark Bailey - www.rockhoppersuk.co.uk - 2010
 */
package uk.co.rockhoppersuk.tvApp.action.listing;

import java.util.Collections;
import org.apache.log4j.Logger;
import uk.co.rockhoppersuk.tvApp.show.TvShow;

/**
 * This action responsible for presenting a <code>Listing</code> for all future dates
 * to the front end.
 *
 * @author mbailey
 * @version 1.0
 */
public class ViewListingForTvShowAction extends AbstractViewListingAction {

    /**
     * serialVersionId for keeping track of changes.
     */
    public static final long serialVersionUID = 42L;
    /**
     * log4j static variable.
     */
    private static Logger logger = Logger.getLogger(ViewListingForTvShowAction.class);
    /**
     * The name of the <code>TvShow</code> to be presented.
     */
    private String tvShowName;

    /**
     * For subclasses to implement the execute() method.
     * @return SUCCESS if successful.
     * @throws Exception Exception if something bad happens
     */
    @Override
    public String execute() throws Exception {
        logger.info("entering execute method. TvShow: " + tvShowName);

        setListings(getListingCatalogue().getListingsForTvShow(TvShow.getTvShow(tvShowName)));
        Collections.reverse(getListings());

        logger.info("exiting execute method. listings.size: " + getListings().size());
        return SUCCESS;
    }

    /**
     * getter for the name of the <code>TvShow</code>.
     * @return the name of the <code>TvShow</code>.
     */
    public String getTvShowName() {
        return tvShowName;
    }

    /**
     * setter for the name of the <code>TvShow</code>.
     * @param tvShowName the name of the <code>TvShow</code>.
     */
    public void setTvShowName(final String tvShowName) {
        this.tvShowName = tvShowName;
    }
}
