/*
 * (c) Mark Bailey - www.rockhoppersuk.co.uk - 2010
 */
package uk.co.rockhoppersuk.tvApp.action.listing;

import com.opensymphony.xwork2.ActionSupport;
import java.util.List;
import uk.co.rockhoppersuk.tvApp.listing.Listing;
import uk.co.rockhoppersuk.tvApp.listing.ListingCatalogue;
import uk.co.rockhoppersuk.tvApp.listing.ListingFactory;

/**
 * This action is the base class responsible for presenting a <code>Listing</code>
 * to the front end.
 * <p> The subclasses are primarily responsible for populating the listings object.
 *
 * @author mbailey
 * @version 1.0
 */
public abstract class AbstractViewListingAction extends ActionSupport {

    /**
     * The list of <code>Listing</code> for the date in question.
     */
    private List<Listing> listings;
    /**
     * To enable unit testing.
     */
    private ListingCatalogue listingCatalogue;

    /**
     * For subclasses to implement the execute() method.
     * @return SUCCESS if successful.
     * @throws Exception Exception if something bad happens
     */
    @Override
    public abstract String execute() throws Exception;

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
    protected ListingCatalogue getListingCatalogue() {
        if (listingCatalogue == null) {
            listingCatalogue = ListingFactory.getListingCatalogueInstance();
        }
        return listingCatalogue;
    }

    /**
     * List of <code>Listing</code> for the front end.
     * @return List of <code>Listing</code> for the front end
     */
    public List<Listing> getListings() {
        return listings;
    }

    /**
     * List of <code>Listing</code> for the front end.
     * @param listings list of <code>Listing</code> for the front end.
     */
    public void setListings(final List<Listing> listings) {
        this.listings = listings;
    }
}
