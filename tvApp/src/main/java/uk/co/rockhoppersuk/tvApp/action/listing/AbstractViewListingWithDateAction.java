/*
 * (c) Mark Bailey - www.rockhoppersuk.co.uk - 2010
 */
package uk.co.rockhoppersuk.tvApp.action.listing;

import java.util.Date;
import org.apache.log4j.Logger;
import uk.co.rockhoppersuk.date.DateTimeUtils;

/**
 * This action is the base class responsible for presenting a <code>Listing</code>
 * for a single day to the front end.
 * <p> The subclasses are primarily responsible for populating the listings object.
 *
 * @author mbailey
 * @version 1.0
 */
public abstract class AbstractViewListingWithDateAction extends AbstractViewListingAction {

    /**
     * log4j static variable.
     */
    private static Logger logger = Logger.getLogger(AbstractViewListingWithDateAction.class);
    /**
     * The date that the <code>Listing</code> are required for.
     */
    private Date listingDate;

    /**
     * For subclasses to implement the execute() method.
     * @return SUCCESS if successful.
     * @throws Exception Exception if something bad happens
     */
    @Override
    public abstract String execute() throws Exception;

    /**
     * Date which the <code>Listing</code> are required.
     * @return Date which the <code>Listing</code> are required.
     */
    public Date getListingDate() {
        if (listingDate == null) {
            return getToday();
        } else {
            return listingDate;
        }
    }

    /**
     * Date which the <code>Listing</code> are required.
     * @param listingDate Date which the <code>Listing</code> are required.
     */
    public void setListingDate(final Date listingDate) {
        this.listingDate = listingDate;
    }

    /**
     * The current system date without a time component as the listings are date not date time.
     * @return The current system date without a time component.
     */
    public Date getToday() {
        logger.info("Calling getToday();");
        return DateTimeUtils.getToday();
    }

    /**
     * Date of the next listing date.
     * @return Date of the next listing date.
     */
    public Date getNextListingDate() {
        if (DateTimeUtils.getNextDay(getListingDate()).after(getListingCatalogue().getMaxListingDate())) {
            logger.info("getNextListingDate method date is last that we have listings for.");
            return getListingDate();
        } else {
            logger.info("getNextListingDate method getting next date.");
            return DateTimeUtils.getNextDay(getListingDate());
        }
    }

    /**
     * Date of the previous listing date.
     * @return Date of the previous listing date.
     */
    public Date getPreviousListingDate() {
        if (DateTimeUtils.getPreviousDay(getListingDate()).before(getListingCatalogue().getMinListingDate())) {
            logger.info("getPreviousListingDate method date is first that we have listings for.");
            return getListingDate();
        } else {
            logger.info("getPreviousListingDate method getting previous date.");
            return DateTimeUtils.getPreviousDay(getListingDate());
        }
    }
}
