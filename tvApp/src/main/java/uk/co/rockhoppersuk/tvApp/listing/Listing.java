/*
 * (c) Mark Bailey - www.rockhoppersuk.co.uk - 2010
 */
package uk.co.rockhoppersuk.tvApp.listing;

import java.util.Date;
import uk.co.rockhoppersuk.tvApp.channel.Channel;
import uk.co.rockhoppersuk.tvApp.show.episode.Episode;

/**
 * Bean represents a single TV Listing,
 * or more fully details what time and <code>Channel</code>
 * a particular <code>Episode</code> is shown.
 *
 * <p>
 * A <code>Listing</code> is considered to be valid if
 * it contains both a <code>Channel</code> and a time.
 * </p>
 *
 * <p>
 * Two <code>Listing</code> are considered to be equal if
 * the <code>Channel</code> and the time are both equal.
 * </p>
 *
 * <p>
 * A listing consists of:
 * <ul>
 *   <li>DateTime Stamp</li>
 *   <li><code>Channel</code></li>
 *   <li><code>Episode</code></li>
 * </ul>
 * </p>
 *
 * @author mbailey
 * @version 1.0
 */
public class Listing {

    /**
     * DateTime stamp representing the time and date of the listing.
     */
    private Date listingDateTime;
    /**
     * the <code>Channel</code> this listing airs on.
     */
    private Channel channel;
    /**
     * the <code>Episode</code> this listing represents.
     */
    private Episode episode;

    /**
     * getter for the dateTime stamp representing the time of the listing.
     * @return dateTime stamp representing the time of the listing.
     */
    public Date getListingDateTime() {
        return listingDateTime;
    }

    /**
     * setter for the dateTime stamp representing the time of the listing.
     * @param listingDateTime dateTime stamp representing the time of the listing.
     */
    public void setListingDateTime(final Date listingDateTime) {
        this.listingDateTime = listingDateTime;
    }

    /**
     * getter for the <code>Channel</code> this listing airs on.
     * @return the <code>Channel</code> this listing airs on.
     */
    public Channel getChannel() {
        return channel;
    }

    /**
     * setter for the <code>Channel</code> this listing airs on.
     * @param channel the <code>Channel</code> this listing airs on.
     */
    public void setChannel(final Channel channel) {
        this.channel = channel;
    }

    /**
     * getter for the <code>Episode</code> this listing represents.
     * @return the <code>Episode</code> this listing represents.
     */
    public Episode getEpisode() {
        return episode;
    }

    /**
     * setter for the <code>Episode</code> this listing represents.
     * @param episode the <code>Episode</code> this listing represents.
     */
    public void setEpisode(final Episode episode) {
        this.episode = episode;
    }

    /**
     * Two <code>Listing</code> are considered to be equal if
     * the <code>Channel</code> and the time are both equal.
     * @param obj Object to be compared to this <code>Listing</code>.
     * @return true if obj is a <code>Listing</code> and both the datetime
     * and <code>Channel</code> are equal to this <code>Listing</code> otherwise false.
     */
    @Override
    public boolean equals(final Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Listing other = (Listing) obj;
        if (this.listingDateTime != other.listingDateTime
                && (this.listingDateTime == null
                || !this.listingDateTime.equals(other.listingDateTime))) {
            return false;
        }
        if (this.channel != other.channel
                && (this.channel == null
                || !this.channel.equals(other.channel))) {
            return false;
        }
        return true;
    }

    /**
     * Generates new hashCode based on the hashCode of the time stamp
     * and the <code>Channel</code>.
     * @return hashCode based on the hashCode of the time stamp
     * and the <code>Channel</code>.
     */
    @Override
    public int hashCode() {
        final int prime = 53;
        final int seed = 7;
        int hash = seed;
        hash = prime * hash + (this.listingDateTime != null
                ? this.listingDateTime.hashCode() : 0);
        hash = prime * hash + (this.channel != null ? this.channel.hashCode() : 0);
        return hash;
    }
}
