/*
 * (c) Mark Bailey - www.rockhoppersuk.co.uk - 2010
 */
package uk.co.rockhoppersuk.tvApp.action.hub;

import com.opensymphony.xwork2.ActionSupport;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;
import uk.co.rockhoppersuk.tvApp.listing.ListingCatalogue;
import uk.co.rockhoppersuk.tvApp.listing.ListingFactory;

/**
 * This action responsible for presenting all the listings persisted to the front end.
 *
 * @author mbailey
 * @version 1.0
 */
public class ListingsHubAction extends ActionSupport {

    /**
     * serialVersionId for keeping track of changes.
     */
    public static final long serialVersionUID = 42L;
    /**
     * log4j static variable.
     */
    private static Logger logger = Logger.getLogger(ListingsHubAction.class);
    /**
     * List of the dates of all the listings persisted.
     */
    private List<Date> listingDates;
    /**
     * To enable unit testing.
     */
    private ListingCatalogue listingCatalogue;

    /**
     * To enable unit testing.
     * @param mockListingCatalogue mock ListingsCatalogue
     */
    protected void setListingsCatalogue(final ListingCatalogue mockListingCatalogue) {
        this.listingCatalogue = mockListingCatalogue;
    }

    /**
     * getter for the listingCatalogue.  Used to enable catalogue to be mocked out.
     * @return the required ListingCatalogue implementation.
     */
    private ListingCatalogue getListingCatalogue() {
        if (listingCatalogue == null) {
            listingCatalogue = ListingFactory.getListingCatalogueInstance();
        }
        return listingCatalogue;
    }

    /**
     * Action to set the List of listings from the catalogue, also it sets today's date.
     * @return SUCCESS if successful.
     * @throws Exception if something bad happens
     */
    @Override
    public String execute() throws Exception {
        logger.info("entering execute method.");

        listingDates = getListingCatalogue().getListingDates();
        Collections.reverse(listingDates);

        logger.info("exiting execute method."
                + " listingsDate.size: " + listingDates.size());

        return SUCCESS;
    }

    /**
     * List of the dates of all the listings persisted.
     * @return List of dates representing listings persisted.
     */
    public List<Date> getListingDates() {
        return listingDates;
    }

    /**
     * The current system date.
     * @return The current System date.
     */
    public Date getToday() {
        return new Date();
    }
}
