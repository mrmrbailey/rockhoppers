/*
 * (c) Mark Bailey - www.rockhoppersuk.co.uk - 2010
 */
package uk.co.rockhoppersuk.tvApp.action.listing;

import org.apache.log4j.Logger;

/**
 * This action responsible for presenting a <code>Listing</code> for a given date
 * to the front end.
 *
 * @author mbailey
 * @version 1.0
 */
public class ViewDailyListingAction extends AbstractViewListingWithDateAction {

    /**
     * serialVersionId for keeping track of changes.
     */
    public static final long serialVersionUID = 42L;
    /**
     * log4j static variable.
     */
    private static Logger logger = Logger.getLogger(ViewDailyListingAction.class);

    /**
     * Action to set the List of listings from the catalogue.
     * @return SUCCESS if successful.
     * @throws Exception if something bad happens
     */
    @Override
    public String execute() throws Exception {
        logger.info("entering execute method.");

        setListings(getListingCatalogue().getListingsForDate(getListingDate()));

        logger.info("exiting execute method. listings.size: " + getListings().size());
        return SUCCESS;
    }
}
