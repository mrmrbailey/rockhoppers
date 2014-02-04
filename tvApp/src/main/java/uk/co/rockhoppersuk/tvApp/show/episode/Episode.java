/*
 * (c) Mark Bailey - www.rockhoppersuk.co.uk - 2010
 */
package uk.co.rockhoppersuk.tvApp.show.episode;

import java.util.Date;
import uk.co.rockhoppersuk.tvApp.show.TvShow;

/**
 * Bean represents a single TV episode,
 * or more fully details what <code>TvShow</code> and title defines the episode.
 *
 * <p>
 * A <code>Episode</code> is considered to be valid if
 * it contains both a <code>TvShow</code> and a title.
 * </p>
 *
 * <p>
 * Two <code>Episode</code> are considered to be equal if
 * the <code>TvShow</code> and the title are both equal.
 * </p>
 *
 * <p>
 * A <code>Episode</code> consists of:
 * <ul>
 *   <li><code>TvShow</code></li>
 *   <li>Title</li>
 *   <li><code>EpisodeStatus</code></li>
 *   <li>Series and Episode number</li>
 *   <li>Worldwide AirDate</li>
 *   <li>Synopsis</li>
 * </ul>
 * </p>
 *
 * @author mbailey
 * @version 1.0
 */
public class Episode {

    /**
     * The <code>TvShow</code> this episode relates to.
     */
    private TvShow tvShow;
    /**
     * The title of this episode.
     */
    private String title;
    /**
     * The status of this episode.
     */
    private EpisodeStatus status;
    /**
     * The Series and Episode Number of this episode.
     */
    private String episodeNumber;
    /**
     * The date this episode aired.  Normally this date is the first airing worldwide.
     */
    private Date airDate;
    /**
     * The synopsis of this episode.
     */
    private String synopsis;

    /**
     * getter for the <code>TvShow</code> this episode relates to.
     * @return The <code>TvShow</code> this episode relates to.
     */
    public TvShow getTvShow() {
        return tvShow;
    }

    /**
     * setter for the <code>TvShow</code> this episode relates to.
     * @param tvShow The <code>TvShow</code> this episode relates to.
     */
    public void setTvShow(final TvShow tvShow) {
        this.tvShow = tvShow;
    }

    /**
     * getter for the title of this episode.
     * @return The title of this episode.
     */
    public String getEpisodeTitle() {
        return title;
    }

    /**
     * setter for the title of this episode.
     * @param episodeTitle The title of this episode.
     */
    public void setTitle(final String episodeTitle) {
        this.title = episodeTitle;
    }

    /**
     * getter for the status of this episode.
     * @return The status of this episode.
     */
    public EpisodeStatus getStatus() {
        return status;
    }

    /**
     * setter for the status of this episode.
     * @param episodeStatus The status of this episode.
     */
    public void setStatus(final EpisodeStatus episodeStatus) {
        this.status = episodeStatus;
    }

    /**
     * getter for the Series and Episode Number of this episode.
     * @return The Series and Episode Number of this episode.
     */
    public String getEpisodeNumber() {
        return episodeNumber;
    }

    /**
     * setter for the Series and Episode Number of this episode.
     * @param episodeNumber The Series and Episode Number of this episode.
     */
    public void setEpisodeNumber(final String episodeNumber) {
        this.episodeNumber = episodeNumber;
    }

    /**
     * getter for the date this episode aired.
     * Normally this date is the first airing worldwide.
     * @return The date this episode aired.
     */
    public Date getAirDate() {
        return airDate;
    }

    /**
     * setter for the date this episode aired.
     * Normally this date is the first airing worldwide.
     * @param airDate The date this episode aired.
     */
    public void setAirDate(final Date airDate) {
        this.airDate = airDate;
    }

    /**
     * getter for the synopsis of this episode.
     * @return The synopsis of this episode.
     */
    public String getSynopsis() {
        return synopsis;
    }

    /**
     * setter for the synopsis of this episode.
     * @param synopsis The synopsis of this episode.
     */
    public void setSynopsis(final String synopsis) {
        this.synopsis = synopsis;
    }

    /**
     * Two <code>Episode</code> are considered to be equal if
     * the <code>TvShow</code> and the title are both equal.
     * @param obj Object to be compared to this <code>Episode</code>.
     * @return true if obj is a <code>Episode</code> and both
     * the <code>TvShow</code> and the title are both equal  otherwise false.
     */
    @Override
    public boolean equals(final Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Episode other = (Episode) obj;
        if (this.tvShow != other.tvShow
                && (this.tvShow == null
                || !this.tvShow.equals(other.tvShow))) {
            return false;
        }
        if (this.title == null || !this.title.equals(other.title)) {
            return false;
        }
        return true;
    }

    /**
     * Generates new hashCode based on the hashCode of the <code>TvShow</code>
     * and the Episode Title.
     * @return hashCode based on the hashCode of the the <code>TvShow</code>
     * and the Episode Title.
     */
    @Override
    public int hashCode() {
        final int prime = 53;
        final int seed = 7;
        int hash = seed;
        hash = prime * hash + (this.tvShow != null
                ? this.tvShow.hashCode() : 0);
        hash = prime * hash + (this.title != null ? this.title.hashCode() : 0);
        return hash;
    }
}
