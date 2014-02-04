/*
 * (c) Mark Bailey - www.rockhoppersuk.co.uk - 2011
 */
package uk.co.rockhoppersuk.tvApp.action.listing;

import java.util.Collections;
import org.apache.log4j.Logger;

/**
 * This action responsible for presenting a <code>Listing</code> for unwatched shows
 * to the front end.
 *
 * @author mbailey
 * @version 1.0
 */
public class ViewListingForUnwatchedShowsAction extends AbstractViewListingAction {

    /**
     * serialVersionId for keeping track of changes.
     */
    public static final long serialVersionUID = 42L;
    /**
     * log4j static variable.
     */
    private static Logger logger = Logger.getLogger(ViewListingForUnwatchedShowsAction.class);

    /**
     * For subclasses to implement the execute() method.
     * @return SUCCESS if successful.
     * @throws Exception Exception if something bad happens
     */
    @Override
    public String execute() throws Exception {
        logger.info("entering execute method.");

        setListings(getListingCatalogue().getListingsForUnwatchedShows());
        Collections.reverse(getListings());

        logger.info("exiting execute method. listings.size: " + getListings().size());
        return SUCCESS;
    }
}
