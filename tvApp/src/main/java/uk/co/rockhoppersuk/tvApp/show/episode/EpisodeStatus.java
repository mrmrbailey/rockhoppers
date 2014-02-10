/*
 * (c) Mark Bailey - www.rockhoppersuk.co.uk - 2010
 */
package uk.co.rockhoppersuk.tvApp.show.episode;

/**
 * A <code>EpisodeStatus</code> is an enum defining the different states an Episode
 * can be.
 *
 * <p>
 * An Episode can be in one of the following states:
 * <ul>
 *   <li>unaired</li>
 *   <li>missed</li>
 *   <li>recorded</li>
 *   <li>watched</li>
 *   <li>unknown</li>
 * </ul>
 * </p>
 *
 * @author mbailey
 * @version 1.0
 */
public enum EpisodeStatus {

    /**
     * EpisodeStatus:unaired.
     */
    unaired,
    /**
     * EpisodeStatus:missed.
     */
    missed,
    /**
     * EpisodeStatus:recorded.
     */
    recorded,
    /**
     * EpisodeStatus:watched.
     */
    watched,
    /**
     * EpisodeStatus:unknown.
     */
    unknown;
}
