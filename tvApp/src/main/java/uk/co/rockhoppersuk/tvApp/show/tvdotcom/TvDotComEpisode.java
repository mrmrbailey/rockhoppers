/*
 * (c) Mark Bailey - www.rockhoppersuk.co.uk - 2011
 */
package uk.co.rockhoppersuk.tvApp.show.tvdotcom;

import uk.co.rockhoppersuk.tvApp.show.episode.Episode;

/**
 * Bean represents a single TV episode from tv.com.
 *
 * This extends the standard <code>Episode</code> giving more information and the intention is
 * the anything deemed useful will be promoted to Episode, leaving everything else for info.
 *
 * <p>
 * A <code>TvDotComEpisode</code> consists of:
 * <ul>
 *   <li>No.</li>
 *   <li>Prod. No.</li>
 *   <li>url</li>
 * </ul>
 * </p>
 *
 * @author mbailey
 * @version 1.0
 */
public class TvDotComEpisode extends Episode {

    /**
     * A unique id identifying the show.
     */
    private int id;
    /**
     * The id that the production company use.
     */
    private String prodNo;
    /**
     * A link to the tv.com page for this episode.
     */
    private String episodeUrl;

    /**
     * getter for the Id of this episode.
     * @return The Id of this episode.
     */
    public int getEpisodeId() {
        return id;
    }

    /**
     * setter for the Id of this episode.
     * @param id The Id of this episode.
     */
    public void setEpisodeId(final int id) {
        this.id = id;
    }

    /**
     * getter for the production number of this episode.
     * @return The production number of this episode.
     */
    public String getProductionNumber() {
        return prodNo;
    }

    /**
     * setter for the production number of this episode.
     * @param productionNumber The production number of this episode.
     */
    public void setProductionNumber(final String productionNumber) {
        prodNo = productionNumber;
    }

    /**
     * getter for the link for on tv.com for this episode.
     * @return The link for on tv.com for this episode.
     */
    public String getEpisodeUrl() {
        return episodeUrl;
    }

    /**
     * setter for the link for on tv.com for this episode.
     * @param epsisodeUrl The link for on tv.com for this episode.
     */
    public void setEpisodeUrl(final String epsisodeUrl) {
        this.episodeUrl = epsisodeUrl;
    }

    /**
     * Two <code>Episode</code> are considered to be equal if
     * the <code>TvShow</code> and the title and the Id are equal.
     * @param obj Object to be compared to this <code>Episode</code>.
     * @return true if obj is a <code>Episode</code> and the <code>TvShow</code>
     * and the title and the Id are equal otherwise false.
     */
    @Override
    public boolean equals(final Object obj) {
        if (super.equals(obj)) {
            final TvDotComEpisode other = (TvDotComEpisode) obj;
            if (this.id != other.id) {
                return false;
            }
            return true;

        } else {
            return false;
        }
    }

    /**
     * Generates new hashCode based on the hashCode of the <code>TvShow</code>
     * and the Episode Title and the id.
     * @return hashCode based on the hashCode of the <code>TvShow</code>
     * and the Episode Title and the id.
     */
    @Override
    public int hashCode() {
        return super.hashCode() + this.id;
    }
}
